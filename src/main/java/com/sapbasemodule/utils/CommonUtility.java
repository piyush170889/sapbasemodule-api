package com.sapbasemodule.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.sapbasemodule.configuration.CustomUserDetails;
import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.domain.OtpDtl;
import com.sapbasemodule.domain.RoleMasterDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.ExcelSheet;
import com.sapbasemodule.model.ExcelSheetTable1;
import com.sapbasemodule.model.ExcellSheetWrapper;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.persitence.ConfigurationRepository;
import com.sapbasemodule.persitence.OtpDtlRepository;

@Component
public class CommonUtility {

	@Autowired
	Properties configProperties;

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	Properties responseMessageProperties;

	@Autowired
	private MessageUtility messageUtility;

	@Autowired
	private OtpDtlRepository otpDtlRepository;

	// FOR PDF CREATIOn
	/*
	 * static Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
	 * Font.BOLD); static Font defFont = new Font(Font.FontFamily.TIMES_ROMAN,
	 * 8); static Font defFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
	 * Font.BOLD);
	 */
	public static final String logo = "https://dl.dropboxusercontent.com/s/l4mn9ua405hazmm/NTC-LOGO-2018.png?dl=0";
	// static Font invoiceDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN,
	// 12, Font.BOLD);
	final static String INVOICE_HEADER = "NEXUS TECHNOCRAFTS AUTOMATIONS & SOLUTIONS PVT. LTD.";

	// final String pdfFileDrivePath =
	// configProperties.getProperty("pdf.file.path");
	// static String pdfFileDrivePath =
	// "C:\\\\Users\\\\Administrator\\\\Documents\\\\NTCPDF";
	static String pdfFileExt = ".pdf";

	public static Date getParsedDate(String dateToParse) throws ParseException {

		String[] formatStrings = new String[] { "M/y", "M/d/y", "M-d-y", "dd-mm-yy", "dd/mm/yyyy", "dd.mm.yyyy",
				"d month yyyy", "yyyy-mm-dd", " MMM/DD/YYYY", "yyyymmdd", "dd-mm-yyyy", "DD-MM-YY ", "YYYYMMDD",
				"dd-mmm-yyyy HH:MM:SS", "yyyymmddTHHMMSS", "yyyy-mm-dd HH:MM:SS", "mmm.dd.yyyy HH:MM:SS" };

		System.out.println("date To Parse : " + dateToParse);
		return DateUtils.parseDateStrictly(dateToParse, formatStrings);
	}

	/**
	 * Description : Validates the input parameters for null check and empty
	 * 
	 * @param Map<Object,
	 *            Object>
	 * @return Object
	 */
	public Object isInputValid(Map<Object, Object> inputParams) {

		Object returnVal = null;

		Set<Object> keySet = inputParams.keySet();
		for (Object key : keySet) {
			Object inputParam = inputParams.get(key);
			if (null == inputParam) {
				returnVal = key;
				break;
			} else if ((inputParam instanceof String) && ((String) inputParam).isEmpty()) {
				returnVal = key;
				break;
			}
		}

		return returnVal;
	}

	public boolean sendOTP(String cntcNum, int otp, String deviceInfo) throws Exception {
		List<OtpDtl> otpDetailsList = otpDtlRepository.findByCellnumber(cntcNum);
		int otpDetailsSize = otpDetailsList.size();
		if (otpDetailsSize == 0) {
			// Insert OTP Record
			OtpDtl otpDtl = new OtpDtl();
			otpDtl.setCellnumber(cntcNum);
			otpDtl.setDeviceInfo(deviceInfo);
			otpDtl.setOtp(otp);
			otpDtlRepository.save(otpDtl);
		} else if (otpDetailsSize == 1) {
			OtpDtl otpDetails = otpDetailsList.get(0);
			int maxtAllowedAttempts = (int) selectConfigurationValue("maxNoOfAttempts");

			// Check max no of attempts
			if (otpDetails.getNumOfAttempts() < maxtAllowedAttempts) { // If max
																		// attempts
																		// not
																		// exceeded
																		// update
																		// the
																		// otp
																		// record
				otpDetails.setOtpId(otpDetails.getOtpId());
				otpDetails.setCellnumber(cntcNum);
				otpDetails.setDeviceInfo(deviceInfo);
				otpDetails.setOtp(otp);
				otpDetails.setNumOfAttempts(otpDetails.getNumOfAttempts() + 1);
				otpDtlRepository.save(otpDetails);
			} else { // If max attempts exceeded throw exception
				throw new ServicesException("628");
			}
		} else {
			throw new ServicesException("622");
		}

		return messageUtility.sendMessage(cntcNum, otp);
	}

	public Object selectConfigurationValue(String configName) {

		return configurationRepository.findByConfigName(configName);
		// return jdbcTemplate.queryForObject("select CONFIG_VAL from
		// configuration where CONFIG_NAME=?", Integer.class,
		// configName);
	}

	// GENEARTE RANDOM OTP
	public String getOTP() {
		Random random = new Random();
		int otp = random.nextInt(10000);
		return Integer.toString(otp);
	}

	// VERIFY OTP USING CNTC NUM AND OTP AND DEVICE INFO
	public boolean verifyOTP(String cntcNum, int otp, String deviceInfo) throws ServicesException, Exception {
		List<OtpDtl> otpDetailsList = otpDtlRepository.findByCellnumberAndOtpAndDeviceInfo(cntcNum, otp, deviceInfo);
		int otpDetailsSize = otpDetailsList.size();
		if (otpDetailsSize == 1) {
			OtpDtl otpDetails = otpDetailsList.get(0);
			// TODO: Check if OTP is Expired. i.e. OTP sent time more than 15
			// mins
			if (otpDetails.getOtp() == otp) {
				otpDtlRepository.delete(otpDetails.getOtpId()); // Clear out the
																// OTP record
				return true;
			} else {
				return false;
			}
		} else if (otpDetailsSize == 0) {
			return false;
		} else {
			throw new ServicesException("622");
		}
	}

	// GENERATE UUID USING UUID
	public String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * Sends email to the toAddress specified, with message body containing the
	 * baseURL appended with activate user account page. This forms the
	 * activation link for the user's account
	 * 
	 * @param toAddress
	 *            The email address to which mail is to be sent
	 * @param baseURL
	 *            The base URl of the application
	 * @return isEmailSent true if mail is sent successfully.false if mail
	 *         sending fails
	 */
	public boolean sendEmail(String toAddress, String message, String subject) {
		final String userName = configProperties.getProperty("email.username");

		final String password = configProperties.getProperty("email.password");
		boolean isEmailSent = false;
		try {
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put(configProperties.getProperty("smtp.server.host"),
					configProperties.getProperty("smtp.server.gmail"));
			properties.put("mail.smtp.port", 587);
			properties.put("mail.smtp.auth", "true");
			properties.put(configProperties.getProperty("smtp.server.starttls"),
					configProperties.getProperty("smtp.server.starttls.value"));
			System.out.println(configProperties.getProperty("smtp.server.gmail"));
			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);
			// creates a new e-mail message
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(userName));
			InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
			mimeMessage.setSubject(subject);
			mimeMessage.setSentDate(new Date());

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setContent(message, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			mimeMessage.setContent(multipart);

			mimeMessage.setText(message);

			// sends the e-mail
			Transport.send(mimeMessage);
			isEmailSent = true;
			return isEmailSent;
		} catch (Exception e) {
			e.printStackTrace();
			return isEmailSent;
		}
	}

	public void sendEmailToAdmin(String message, String subject) {
		final String userName = configProperties.getProperty("email.username");

		final String password = configProperties.getProperty("email.password");

		try {
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.put(configProperties.getProperty("smtp.server.host"),
					configProperties.getProperty("smtp.server.gmail"));
			properties.put("mail.smtp.port", 587);
			properties.put("mail.smtp.auth", "true");
			properties.put(configProperties.getProperty("smtp.server.starttls"),
					configProperties.getProperty("smtp.server.starttls.value"));
			System.out.println(configProperties.getProperty("smtp.server.gmail"));
			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);
			// creates a new e-mail message
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(userName));
			// InternetAddress[] toAddresses = { new
			// InternetAddress("tekchandbheda@gmail.com"), new
			// InternetAddress("komaldahanu@gmail.com") };
			InternetAddress[] toAddresses = { new InternetAddress("piyush.jadhav@repleteinc.com"),
					new InternetAddress("piyushjadhav65@gmail.com") };
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
			mimeMessage.setSubject(subject);
			mimeMessage.setSentDate(new Date());

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setContent(message, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			mimeMessage.setContent(multipart);

			mimeMessage.setText(message);

			// sends the e-mail
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void checkRowAffected(int rowAffected) throws ServicesException {
		if (rowAffected != 1)
			throw new ServicesException("Something went wrong while performing updates");
	}

	public CustomUserDetails getLoggedUser() {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (CustomUserDetails) userDetails;
	}

	public Set<RoleMasterDtl> getLoggedUserRolesDtl() {

		CustomUserDetails userDetails = getLoggedUser();
		return userDetails.getRoles();
	}

	public List<String> getLoggedUserRoles() {

		CustomUserDetails userDetails = getLoggedUser();
		Set<RoleMasterDtl> roles = userDetails.getRoles();
		List<String> rolesList = new ArrayList<String>();

		for (RoleMasterDtl roleMasterDtl : roles) {
			rolesList.add(roleMasterDtl.getRolesMasterDtlsId());
		}

		return rolesList;
	}

	public void checkRowUpdated(int rowAffected) {
		if (rowAffected != 1) {
			throw new ServiceException("608");
		}

	}

	public String getLoggedUserId() {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
		return customUserDetails.getUserDtl().getUserDtlsId();
	}

	public PageRequest getPageRequest(int pageNo, Sort sort) {

		int maxFetchSize = Integer.parseInt(configProperties.getProperty("records.maxfetch.size"));
		return new PageRequest((pageNo - 1), maxFetchSize, sort);
	}

	public PageRequest getPageRequest(int pageNo) {

		int maxFetchSize = Integer.parseInt(configProperties.getProperty("records.maxfetch.size"));
		return new PageRequest((pageNo - 1), maxFetchSize);
	}

	public PageRequest getPageRequestSortedByCreatedTs(int pageNo) {
		int maxFetchSize = Integer.parseInt(configProperties.getProperty("records.maxfetch.size"));
		Sort sort = new Sort(Direction.DESC, "createdTs");
		return new PageRequest((pageNo - 1), maxFetchSize, sort);
	}

	public PaginationDetails getPaginationDetails(double totalCount, double currentPageNo) {

		double maxFetchSize = Double.parseDouble(configProperties.getProperty("records.maxfetch.size"));
		double totalPages = Math.ceil(totalCount / maxFetchSize);
		System.out.println("Total Count = " + totalCount + ", Total Pages = " + totalPages);

		PaginationDetails paginationDetails = new PaginationDetails(totalPages, currentPageNo);
		return paginationDetails;
	}

	public float calGrandTotal(float subTotal, float taxPercentage, float discount) {
		return ((subTotal) + (subTotal / 100) * taxPercentage) - discount;
	}

	public String getISTTimeFromUTCTime12HourFormat(String utcTime) throws ParseException {

		System.out.println("UTC Time String = " + utcTime);

		DateFormat sdf = new SimpleDateFormat("hh:mm a");
		// sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		DateFormat df = new SimpleDateFormat("HHmmss");

		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(utcTime));

		String utcTime12HourFormat = sdf.format(cal.getTime());
		System.out.println("UTC Time 12 Hour = " + utcTime12HourFormat);

		cal.add(Calendar.HOUR, 5);
		cal.add(Calendar.MINUTE, 30);

		String istTime12HourFormat = sdf.format(cal.getTime());
		System.out.println("IST Time = " + istTime12HourFormat);

		return istTime12HourFormat;
	}

	public Double getDistanceFromLatLonInKm(Double lat1, Double lon1, Double lat2, Double lon2) {

		// System.out.println("lat1 = " + lat1 + ", lon2 = " + lon2 + ", lat2 =
		// " + lat2 + ", lon2 = " + lon2);

		int R = 6371; // Radius of the earth in km
		Double dLat = deg2rad(lat2 - lat1); // deg2rad below
		Double dLon = deg2rad(lon2 - lon1);
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double d = R * c; // Distance in km

		return d;
	}

	public Double deg2rad(Double deg) {
		return deg * (Math.PI / 180);
	}

	public Double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public boolean hasRole(String roleToCheck) {

		List<String> loggedInUserRoles = getLoggedUserRoles();
		return loggedInUserRoles.contains(roleToCheck);
	}

	public long getDiffInMinutes(long startTs, long endTs) {

		long diff = endTs - startTs;
		System.out.println("diff = " + diff + ", endTs = " + endTs + ",  startTs = " + startTs);

		long diffInMinutes = diff / (60 * 1000) % 60;
		System.out.println("Diff in Mins = " + diffInMinutes);

		return diffInMinutes;
	}

	public Date getDateFromUtcDtAndTmFromTln(String utcDt, String utcTm) throws ParseException {
		String dateStringToConvert = utcDt + utcTm;

		DateFormat dfDdMmYyHhMmSs = new SimpleDateFormat("ddMMyyHHmmss");
		DateFormat dfYyyyMmDdHhMmSs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date convertedDate = dfYyyyMmDdHhMmSs.parse(dfYyyyMmDdHhMmSs.format(dfDdMmYyHhMmSs.parse(dateStringToConvert)));
		return convertedDate;
	}

	public Date convertUTCToIST(Date dateOfEntryOrExit) throws ParseException {

		DateFormat dfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfIST.setTimeZone(TimeZone.getTimeZone("IST"));
		Date dateToReturn = dfIST.parse(dfIST.format(dateOfEntryOrExit));
		return dateToReturn;
	}

	public String getCurrentTsString() {

		DateFormat dfYyyyMmDdHhMmSs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfYyyyMmDdHhMmSs.setTimeZone(TimeZone.getTimeZone("UTC"));

		return dfYyyyMmDdHhMmSs.format(new Date());
	}

	public void generateExcel(@RequestBody ExcellSheetWrapper excellSheetWrapper, String lastCloseDayTs,
			String currentCloseDayTs) throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();

		List<ExcelSheet> excelSheetTable2 = excellSheetWrapper.getExcelSheetTable2();
		ExcelSheetTable1 excelSheetTable1 = excellSheetWrapper.getExcelSheetTable1();
		HSSFSheet sheet = workbook.createSheet("Report");

		// CREATE FIRST TABLE
		HSSFRow reportDate = sheet.createRow(1);
		HSSFRow fromDate = sheet.createRow(2);
		HSSFRow toDate = sheet.createRow(3);
		HSSFRow cashLoadedRow = sheet.createRow(4);
		HSSFRow creditIssuedRow = sheet.createRow(5);
		HSSFRow withdrawnRow = sheet.createRow(6);
		HSSFRow unusedRow = sheet.createRow(7);
		HSSFRow totalSaleRow = sheet.createRow(8);

		// Report Overview Table
		DateFormat dfIst = new SimpleDateFormat("dd MMM yyyy hh:mm a");
		dfIst.setTimeZone(TimeZone.getTimeZone("IST"));

		String currentTs = dfIst.format(new Date());

		cellWithBorder(1, 2, reportDate, workbook).setCellValue("Report Date");
		cellWithBorder(1, 3, reportDate, workbook).setCellValue(currentTs);

		cellWithBorder(2, 2, fromDate, workbook).setCellValue("From");
		cellWithBorder(2, 3, fromDate, workbook).setCellValue(lastCloseDayTs);

		cellWithBorder(3, 2, toDate, workbook).setCellValue("To");
		cellWithBorder(3, 3, toDate, workbook).setCellValue(currentCloseDayTs);

		cellWithBorder(4, 2, cashLoadedRow, workbook).setCellValue("Cash Loaded");
		cellWithBorder(4, 3, cashLoadedRow, workbook).setCellValue(excelSheetTable1.getCl());

		cellWithBorder(5, 2, creditIssuedRow, workbook).setCellValue("Credit Issued");
		cellWithBorder(5, 3, creditIssuedRow, workbook).setCellValue(excelSheetTable1.getCreditIssued());

		cellWithBorder(6, 2, withdrawnRow, workbook).setCellValue("Cash Withdrawn");
		cellWithBorder(6, 3, withdrawnRow, workbook).setCellValue(excelSheetTable1.getWd());

		cellWithBorder(7, 2, unusedRow, workbook).setCellValue("Unused Cash");
		cellWithBorder(7, 3, unusedRow, workbook).setCellValue(excelSheetTable1.getUu());

		cellWithBorder(8, 2, totalSaleRow, workbook).setCellValue("Total Sale");
		cellWithBorder(8, 3, totalSaleRow, workbook).setCellValue(excelSheetTable1.getTs());

		// Stock Used Details Summary Table
		HSSFRow header = sheet.createRow(10);

		// Header Row
		cellWithBorder(10, 1, header, workbook).setCellValue("SR NO.");
		cellWithBorder(10, 2, header, workbook).setCellValue("DATE");
		cellWithBorder(10, 3, header, workbook).setCellValue("PRODUCT");
		cellWithBorder(10, 4, header, workbook).setCellValue("QTY");
		cellWithBorder(10, 5, header, workbook).setCellValue("INFINITY PRICE");
		cellWithBorder(10, 6, header, workbook).setCellValue("MRP");
		// cellWithBorder(10, 7, header, workbook).setCellValue("CARD TYPE");
		cellWithBorder(10, 7, header, workbook).setCellValue("SUBTOTAL");
		cellWithBorder(10, 8, header, workbook).setCellValue("DISCOUNT");
		cellWithBorder(10, 9, header, workbook).setCellValue("TOTAL");

		// Data Rows
		// Counter To Increment SR NO in table
		int counter = 1;
		/*
		 * int startRow = 8; // START INDEX OF ROW TO CHECK AND INSERT LAST ROW
		 * OF // 2ND TABLE NOT INCREMENTED
		 */ int rowCounter = 10; // START NEW ROW FROM THIS INDEX ITS INCREMENTED
		int excelSheetTable2Length = excelSheetTable2.size();
		float grandTotal = 0F;
		float subTotal = 0F;
		float discount = 0F;

		for (ExcelSheet e : excelSheetTable2) {
			HSSFRow row = sheet.createRow(++rowCounter);
			cellWithBorder(rowCounter, 1, row, workbook).setCellValue(counter);
			cellWithBorder(rowCounter, 2, row, workbook).setCellValue(e.getDt());
			// cellWithBorder(rowCounter, 3, row,
			// workbook).setCellValue(e.getTm());
			cellWithBorder(rowCounter, 3, row, workbook).setCellValue(e.getPn());
			cellWithBorder(rowCounter, 4, row, workbook).setCellValue(e.getQt());
			cellWithBorder(rowCounter, 5, row, workbook).setCellValue(e.getIp());
			cellWithBorder(rowCounter, 6, row, workbook).setCellValue(e.getMrp());
			// cellWithBorder(rowCounter, 7, row,
			// workbook).setCellValue(e.getTy());
			cellWithBorder(rowCounter, 7, row, workbook).setCellValue(e.getSubTotal());
			cellWithBorder(rowCounter, 8, row, workbook).setCellValue(e.getDiscount());
			cellWithBorder(rowCounter, 9, row, workbook).setCellValue(e.getT());

			subTotal = subTotal + Float.parseFloat(e.getSubTotal());
			discount = discount + Float.parseFloat(e.getDiscount());
			grandTotal = grandTotal + Float.parseFloat(e.getT());

			System.out.println("excelSheetTable2Length : " + excelSheetTable2Length + " Counter :" + counter);
			if (excelSheetTable2Length == counter) {
				HSSFRow row1 = sheet.createRow(++rowCounter);
				cellWithBorder(rowCounter, 1, row1, workbook).setCellValue("");
				cellWithBorder(rowCounter, 2, row1, workbook).setCellValue("");
				cellWithBorder(rowCounter, 3, row1, workbook).setCellValue("");
				cellWithBorder(rowCounter, 4, row1, workbook).setCellValue("");
				cellWithBorder(rowCounter, 5, row1, workbook).setCellValue("");
				cellWithBorder(rowCounter, 6, row1, workbook).setCellValue("Total");
				// cellWithBorder(rowCounter, 7, row1,
				// workbook).setCellValue("Total");
				cellWithBorder(rowCounter, 7, row1, workbook).setCellValue(subTotal);
				cellWithBorder(rowCounter, 8, row1, workbook).setCellValue(discount);
				cellWithBorder(rowCounter, 9, row1, workbook).setCellValue(grandTotal);
			}
			counter++;
		}

		// Create Report Xcel and Email
		String fileName = "Report_" + currentTs.replace(" ", "_") + ".xls";
		String filePath = configProperties.getProperty("reports.file.path") + fileName;

		File file = new File(filePath);
		FileOutputStream fileOut = new FileOutputStream(file);
		workbook.write(fileOut);
		fileOut.close();

		// SEND AN EMAIL
		String emails = configProperties.getProperty("email.admin.list");
		String message = "Please Find Attachment";
		String subject = "Close Day Report for " + currentTs;
		String attachmentFilePath = filePath;
		emailUtility.sendEmail(emails, message, subject, attachmentFilePath, fileName);

		// DELETE FILE FROM LOCAL STORAGE
		file.delete();
	}

	@Autowired
	private EmailUtility emailUtility;

	// CREATE CELLS WITH BORDER
	public Cell cellWithBorder(int rowCounter, int cellNo, HSSFRow row, HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		Cell cell = row.createCell(cellNo);
		cell.setCellStyle(style);
		return cell;

	}

	public String getUtcTsFromIst(String tsToCompare) throws ParseException {

		DateFormat dfUtc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfUtc.setTimeZone(TimeZone.getTimeZone("UTC"));

		DateFormat dfIst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfIst.setTimeZone(TimeZone.getTimeZone("IST"));

		Date istDate = dfIst.parse(tsToCompare);
		System.out.println("istDate = " + istDate);

		String utcTs = dfUtc.format(istDate);
		System.out.println("utcTs = " + utcTs);

		return utcTs;
	}

	public String getISTDateTimeFromUTCDateTime(String utcDateTime) throws ParseException {

		DateFormat dfUtc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfUtc.setTimeZone(TimeZone.getTimeZone("UTC"));

		DateFormat dfIst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfUtc.setTimeZone(TimeZone.getTimeZone("IST"));

		Date utcFormatDate = dfUtc.parse(utcDateTime);

		String istDateTime = dfIst.format(utcFormatDate);

		System.out.println("UTC Time = " + utcDateTime + ", IST = " + istDateTime);

		return istDateTime;
	}

	public Double calculateDistanceFromLatLongList(List<NormalPacketDescDtls> latLongList) {

		int latLongListSize = latLongList.size();
		Double distanceTravelled = 0D;

		for (int i = 0; i < latLongListSize; i++) {

			if ((i + 1) == latLongListSize)
				break;

			NormalPacketDescDtls normalPacketDescDtls1 = latLongList.get(i);
			NormalPacketDescDtls normalPacketDescDtls2 = latLongList.get(i + 1);

			Double lat1 = normalPacketDescDtls1.getLatitude();
			Double lon1 = normalPacketDescDtls1.getLongitude();
			Double lat2 = normalPacketDescDtls2.getLatitude();
			Double lon2 = normalPacketDescDtls2.getLongitude();

			Double distanceBetwLatsLongs = getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2);
			distanceTravelled += distanceBetwLatsLongs;
		}

		// System.out.println("distanceTravelled = " + round(distanceTravelled,
		// 2));

		return distanceTravelled;
	}

	public List<NormalPacketDescDtls> removeInvalidLatLongRecords(List<NormalPacketDescDtls> todaysLatLongList) {
		int todaysLatLongListSize = todaysLatLongList.size();

		for (int i = 0; i < todaysLatLongListSize; i++) {
			if (todaysLatLongList.get(i).getLatitude() == 0 || todaysLatLongList.get(i).getLongitude() == 0) {
				todaysLatLongList.remove(i);
			}
		}

		return todaysLatLongList;
	}

	public Date getISTDateFromUtcDtAndTmFromTln(String utcDt, String utcTm) throws ParseException {
		String dateStringToConvert = utcDt + utcTm;

		DateFormat dfDdMmYyHhMmSs = new SimpleDateFormat("ddMMyyHHmmss");
		dfDdMmYyHhMmSs.setTimeZone(TimeZone.getTimeZone(Constants.UTC_TIMEZONE));

		DateFormat dfYyyyMmDdHhMmSs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfYyyyMmDdHhMmSs.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		Date convertedDate = dfYyyyMmDdHhMmSs.parse(dfYyyyMmDdHhMmSs.format(dfDdMmYyHhMmSs.parse(dateStringToConvert)));
		return convertedDate;
	}

	public String getISTDateStringFromISTDate(Date istDate) throws ParseException {

		DateFormat dfYyyyMmDdHhMmSs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dfYyyyMmDdHhMmSs.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String convertedISTDateString = dfYyyyMmDdHhMmSs.format(istDate);
		return convertedISTDateString;
	}

	public boolean checkIfISTDateIsOfToday(Date istDateToCompare) {

		DateFormat dfIST = new SimpleDateFormat("yyyy-MM-dd");
		dfIST.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		String currentDate = dfIST.format(new Date());
		String istDateStringToCompare = dfIST.format(istDateToCompare);
		System.out.println("currentDate = " + currentDate + ", istDateStringToCompare = " + istDateStringToCompare);

		if (currentDate.equalsIgnoreCase(istDateStringToCompare))
			return true;
		else
			return false;
	}

	public Connection getDbConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + Constants.DB_SERVER_IP + ":1433;databaseName=" + Constants.DB_NAME,
				Constants.DB_USERNM, Constants.DB_PASS);

		return conn;
	}

	public long getDaysDiffBetweenDates(String startDate, String endDate) throws ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		Date firstDate = df.parse(startDate);
		Date secondDate = df.parse(endDate);

		long diff = secondDate.getTime() - firstDate.getTime();
		long diffOfDays = diff / 1000 / 60 / 60 / 24;
		System.out.println("Days: " + diffOfDays);

		return diffOfDays;
	}

}
