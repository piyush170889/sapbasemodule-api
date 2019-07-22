package com.sapbasemodule.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.domain.SiteDtls;
import com.sapbasemodule.domain.SiteVisitHistory;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.service.UsersService;
import com.sapbasemodule.utils.CommonUtility;
import com.sapbasemodule.utils.GeocodingUtility;

@Controller
@RequestMapping("/ext")
public class VisitSiteController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private GeocodingUtility geocodingUtility;

	@GetMapping("/visit-history")
	public ModelAndView viewVisitHistory(ModelAndView modelAndView) {

		try {
			List<SiteVisitHistory> siteVisitHistoriesList = usersService.doGetUsersVisitHistory();

			modelAndView = createViewVisitHistoryData(siteVisitHistoriesList, modelAndView);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive Invoice Details");
			modelAndView.setViewName("visit-site/visit-site-history");
		} finally {
			// Populate LocationsList
			List<SiteDtls> locationsList = usersService.doGetLocationsListToPopulateDD();
			modelAndView.addObject("locationsList", locationsList);

			modelAndView.setViewName("visit-site/visit-site-history");
		}

		return modelAndView;
	}

	private ModelAndView createViewVisitHistoryData(List<SiteVisitHistory> siteVisitHistoriesList,
			ModelAndView modelAndView) throws ParseException {

		List<SiteVisitHistory> finalSiteVisitHistoriesList = new ArrayList<SiteVisitHistory>();

		DateFormat dfDDMMYYHHmmss = new SimpleDateFormat("ddMMyy HHmmss");
		dfDDMMYYHHmmss.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		DateFormat dfddMMMyyhhmmssa = new SimpleDateFormat("dd MMM yy hh:mm a");
		dfddMMMyyhhmmssa.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		List<SiteVisitHistory> siteVisitHistoryListToSave = new ArrayList<SiteVisitHistory>();
		for (SiteVisitHistory siteVisitHistory : siteVisitHistoriesList) {
			String entryTs = siteVisitHistory.getEntryDt() + " " + siteVisitHistory.getEntryTm();
			String updatedEntryTs = dfddMMMyyhhmmssa.format(dfDDMMYYHHmmss.parse(entryTs));
			siteVisitHistory.setEntryTs(updatedEntryTs);

			String exitDt = siteVisitHistory.getExitDt();
			if (null != exitDt && !exitDt.isEmpty()) {
				String exitTs = exitDt + " " + siteVisitHistory.getExitTm();

				String updatedExitTs = dfddMMMyyhhmmssa.format(dfDDMMYYHHmmss.parse(exitTs));
				siteVisitHistory.setExitTs(updatedExitTs);

				String exitLocation = siteVisitHistory.getExitLocation();
				if (null == exitLocation || exitLocation.isEmpty()) {
					exitLocation = geocodingUtility.reverseGeocodeLatLonToAddress(siteVisitHistory.getExitLatitude(),
							siteVisitHistory.getExitLongitude());
					siteVisitHistory.setExitLocation(exitLocation);

					if (null != exitLocation && !exitLocation.isEmpty())
						siteVisitHistoryListToSave.add(siteVisitHistory);
				}
			}

			finalSiteVisitHistoriesList.add(siteVisitHistory);
		}

		if (siteVisitHistoryListToSave.size() > 0)
			usersService.saveVisitSites(siteVisitHistoryListToSave);

		modelAndView.addObject("siteVisitHistoriesList", finalSiteVisitHistoriesList);

		return modelAndView;
	}

	@Autowired
	private CommonUtility commonUtility;

	/*
	 * @Autowired private SiteDtlsRepository siteDtlsRepository;
	 */
	@PostMapping("visit-history-search")
	public ModelAndView searchVisitHistory(HttpServletRequest servletRequest, ModelAndView modelAndView) {

		try {
			String searchDateRange = servletRequest.getParameter("searchDateRange");
			String cust = servletRequest.getParameter("cust");

			System.out.println("cust = " + cust + ", searchDateRange = " + searchDateRange);

			if ((null == cust || cust.isEmpty()) && (null == searchDateRange || searchDateRange.isEmpty()))
				throw new ServicesException("Please Supply Valid Values");

			boolean isCustomerSelected = (null == cust || cust.isEmpty()) ? false : true;

			String[] dateRangeTokens = null == searchDateRange || searchDateRange.isEmpty() ? null
					: searchDateRange.split("-");
			String fromDate = null == dateRangeTokens ? null : dateRangeTokens[0].trim();
			String toDate = null == dateRangeTokens ? null : dateRangeTokens[1].trim();

			String salesExecutive = null;

			boolean isFromDateApplied = !commonUtility.isEmpty(fromDate);
			boolean isToDateApplied = !commonUtility.isEmpty(toDate);
			boolean isSalesExecutiveSelected = !commonUtility.isEmpty(salesExecutive);

			System.out.println("isCustomerSelected = " + isCustomerSelected + ", isFromDateApplied = "
					+ isFromDateApplied + ", isToDateApplied = " + isToDateApplied + ", isSalesExecutiveSelected = "
					+ isSalesExecutiveSelected);

			String visitHistorySql = "SELECT TOP 100 SITE_VISIT_HISTORY_ID,svh.GEOFENCING_DTLS_ID,VISITOR_ID,ENTRY_DT,"
					+ "ENTRY_TM,VISIT_PURPOSE,EXIT_DT,EXIT_TM,REMARKS,svh.LATITUDE,svh.LONGITUDE,EXIT_LATITUDE,"
					+ "EXIT_LONGITUDE,EXIT_LOCATION,VISITOR_NM,GEOFENCE_NAME FROM site_visit_history svh "
					+ " INNER JOIN site_dtls sd on svh.GEOFENCING_DTLS_ID=sd.GEOFENCING_DTLS_ID WHERE ";

			String customerSelectedSql = "";
			String fromDateSql = "";
			String toDateSql = "";
			String seSql = "";
			DateFormat dfMMDDYY = new SimpleDateFormat("MM/dd/yy");
			DateFormat dfDDMMYY = new SimpleDateFormat("ddMMyy");
			// String custName = "";

			if (isCustomerSelected) {
				// SiteDtls siteDtls = siteDtlsRepository.findByCardCode(cust);
				// int geoFencingDtlsId = siteDtls.getGeofencingDtlsId();
				// custName = siteDtls.getGeofenceName();
				// customerSelectedSql = " svh.GEOFENCING_DTLS_ID=" +
				// geoFencingDtlsId + " ";
				customerSelectedSql = " sd.CARD_CODE='" + cust + "'";
			}

			if (isFromDateApplied)
				fromDateSql = " svh.ENTRY_DT >= " + dfDDMMYY.format(dfMMDDYY.parse(fromDate)) + " ";

			if (isToDateApplied)
				toDateSql = " svh.ENTRY_DT <= " + dfDDMMYY.format(dfMMDDYY.parse(toDate)) + " ";

			if (isSalesExecutiveSelected)
				seSql = " svh.VISITOR_ID=" + salesExecutive;

			if (isCustomerSelected && isFromDateApplied && isToDateApplied && isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + customerSelectedSql + " AND " + fromDateSql + " AND " + toDateSql
						+ " AND " + seSql;
			} else if (isCustomerSelected && isFromDateApplied && isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + customerSelectedSql + " AND " + fromDateSql + " AND " + toDateSql;
			} else if (isCustomerSelected && isFromDateApplied && !isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + customerSelectedSql + " AND " + fromDateSql;
			} else if (isCustomerSelected && !isFromDateApplied && !isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + customerSelectedSql;
			} else if (isCustomerSelected && !isFromDateApplied && isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + customerSelectedSql + " AND " + toDateSql;
			} else if (isFromDateApplied && !isCustomerSelected && isToDateApplied && isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + fromDateSql + " AND " + toDateSql + " AND " + seSql;
			} else if (isFromDateApplied && !isCustomerSelected && isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + fromDateSql + " AND " + toDateSql;
			} else if (isFromDateApplied && !isCustomerSelected && !isToDateApplied && isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + fromDateSql + " AND " + seSql;
			} else if (isFromDateApplied && !isCustomerSelected && !isToDateApplied && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + fromDateSql;
			} else if (isToDateApplied && !isFromDateApplied && !isCustomerSelected && isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + toDateSql + " AND " + seSql;
			} else if (isToDateApplied && !isFromDateApplied && !isCustomerSelected && !isSalesExecutiveSelected) {
				visitHistorySql = visitHistorySql + toDateSql;
			}

			visitHistorySql = visitHistorySql + " ORDER BY SITE_VISIT_HISTORY_ID DESC";
			System.out.println("visitHistorySql = " + visitHistorySql);

			Connection con = commonUtility.getDbConnection();
			PreparedStatement ps = con.prepareStatement(visitHistorySql);

			ResultSet rs = ps.executeQuery();

			List<SiteVisitHistory> siteVisitHistoriesList = new ArrayList<SiteVisitHistory>();
			while (rs.next()) {
				SiteDtls siteDtls = new SiteDtls();
				siteDtls.setGeofencingDtlsId(rs.getInt("GEOFENCING_DTLS_ID"));
				siteDtls.setGeofenceName(rs.getString("GEOFENCE_NAME"));

				SiteVisitHistory siteVisitHistory = new SiteVisitHistory(rs.getInt("SITE_VISIT_HISTORY_ID"), siteDtls,
						rs.getString("VISITOR_ID"), rs.getString("ENTRY_DT"), rs.getString("ENTRY_TM"),
						rs.getString("VISIT_PURPOSE"), rs.getString("EXIT_DT"), rs.getString("EXIT_TM"),
						rs.getString("REMARKS"), rs.getString("LATITUDE"), rs.getString("LONGITUDE"),
						rs.getString("EXIT_LATITUDE"), rs.getString("EXIT_LONGITUDE"), rs.getString("EXIT_LOCATION"),
						rs.getString("VISITOR_NM"));
				siteVisitHistoriesList.add(siteVisitHistory);
			}

			modelAndView = createViewVisitHistoryData(siteVisitHistoriesList, modelAndView);
		} catch (ServicesException se) {
			se.printStackTrace();
			modelAndView.addObject(Constants.ERROR_MSSG_LABEL, se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.ERROR_MSSG_LABEL, "Unable To Retreive Visit Search History Details");
		} finally {
			// Populate LocationsList
			List<SiteDtls> locationsList = usersService.doGetLocationsListToPopulateDD();
			modelAndView.addObject("locationsList", locationsList);

			modelAndView.setViewName("visit-site/visit-site-history");
		}

		return modelAndView;
	}

}
