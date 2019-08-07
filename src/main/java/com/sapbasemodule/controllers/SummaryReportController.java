package com.sapbasemodule.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.model.CutomerSummaryReportDetails;
import com.sapbasemodule.service.CustomersService;
import com.sapbasemodule.utils.CommonUtility;

@Controller
@RequestMapping("ext/")
public class SummaryReportController {

	@Autowired
	private CustomersService customersService;

	@Autowired
	private CommonUtility commonUtility;

	@GetMapping("/summary-report")
	public ModelAndView getCustomerSummaryReport(ModelAndView modelAndView) {

		List<CutomerSummaryReportDetails> customerSummaryReportDetailsList = new ArrayList<CutomerSummaryReportDetails>();

		// List<String> customersList = new ArrayList<String>();
		// List<String> salesEmpNamesList = new ArrayList<String>();
		// List<String> brandsList = new ArrayList<String>();

		modelAndView.setViewName("customer-summary-report/customer-summary-report");

		try {
			customerSummaryReportDetailsList = customersService.getCustomerSummaryReportDetails(null);

			// for (CutomerSummaryReportDetails cutomerSummaryReportDetails :
			// customerSummaryReportDetailsList) {
			//
			// if (!brandsList.contains(cutomerSummaryReportDetails.getBrand()))
			// brandsList.add(cutomerSummaryReportDetails.getBrand());
			// if
			// (!salesEmpNamesList.contains(cutomerSummaryReportDetails.getSalesEmpName()))
			// salesEmpNamesList.add(cutomerSummaryReportDetails.getSalesEmpName());
			// if
			// (!customersList.contains(cutomerSummaryReportDetails.getCardName()))
			// customersList.add(cutomerSummaryReportDetails.getCardName());
			// }

			// modelAndView.addObject("customersList", customersList);
			// modelAndView.addObject("salesEmpNamesList", salesEmpNamesList);
			// modelAndView.addObject("brandsList", brandsList);

			modelAndView.addObject("customerSummaryReportDetailsList", customerSummaryReportDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.ERROR_MSSG_LABEL, Constants.ERROR_OCCURED);
		}

		return modelAndView;
	}

	@PostMapping("summary-report-filter")
	public ModelAndView doGetSummaryReportFiltered(HttpServletRequest servletRequest, ModelAndView modelAndView) {

		List<CutomerSummaryReportDetails> customerSummaryReportDetailsList = new ArrayList<CutomerSummaryReportDetails>();

		modelAndView.setViewName("customer-summary-report/customer-summary-report");
		List<String> selectedBrandsList = new ArrayList<String>();
		try {
			String bs = servletRequest.getParameter("bs");
			String rpc = servletRequest.getParameter("rpc");
			String uppc = servletRequest.getParameter("uppc");

			System.out.println("bs = " + bs + ", rpc = " + rpc + ", uppc = " + uppc);
			if (null != bs && bs.equals("1"))
				selectedBrandsList.add("Birla Super");
			if (null != rpc && rpc.equals("1"))
				selectedBrandsList.add("Rajashree Plus Cement");
			if (null != uppc && uppc.equals("1"))
				selectedBrandsList.add("Ultratech PPC");

			System.out.println("selectedBrandsList = " + selectedBrandsList.toString());
			customerSummaryReportDetailsList = customersService
					.getCustomerSummaryReportDetailsByBrands(selectedBrandsList);

			List<CutomerSummaryReportDetails> sortedSummaryReportList = new ArrayList<CutomerSummaryReportDetails>();
			Map<String, CutomerSummaryReportDetails> custSummHashMap = new HashMap<String, CutomerSummaryReportDetails>();

			for (CutomerSummaryReportDetails summaryReport : customerSummaryReportDetailsList) {

				String cardName = summaryReport.getCardCode();

				CutomerSummaryReportDetails sumarryReportRecord = null;

				if (custSummHashMap.containsKey(cardName)) {

					sumarryReportRecord = custSummHashMap.get(cardName);

					sumarryReportRecord.setBrand(sumarryReportRecord.getBrand() + ", " + summaryReport.getBrand());
					sumarryReportRecord.setApr(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getApr() == null || sumarryReportRecord.getApr().isEmpty()
									? "0" : sumarryReportRecord.getApr())
							+ Float.parseFloat(summaryReport.getApr() == null || summaryReport.getApr().isEmpty() ? "0"
									: summaryReport.getApr())),
							2)));
					sumarryReportRecord.setMay(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getMay() == null || sumarryReportRecord.getMay().isEmpty()
									? "0" : sumarryReportRecord.getMay())
							+ Float.parseFloat(summaryReport.getMay() == null || summaryReport.getMay().isEmpty() ? "0"
									: summaryReport.getMay())),
							2)));
					sumarryReportRecord.setJun(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getJun() == null || sumarryReportRecord.getJun().isEmpty()
									? "0" : sumarryReportRecord.getJun())
							+ Float.parseFloat(summaryReport.getJun() == null || summaryReport.getJun().isEmpty() ? "0"
									: summaryReport.getJun())),
							2)));
					sumarryReportRecord.setJul(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getJul() == null || sumarryReportRecord.getJul().isEmpty()
									? "0" : sumarryReportRecord.getJul())
							+ Float.parseFloat(summaryReport.getJul() == null || summaryReport.getJul().isEmpty() ? "0"
									: summaryReport.getJul())),
							2)));
					sumarryReportRecord.setAug(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getAug() == null || sumarryReportRecord.getAug().isEmpty()
									? "0" : sumarryReportRecord.getAug())
							+ Float.parseFloat(summaryReport.getAug() == null || summaryReport.getAug().isEmpty() ? "0"
									: summaryReport.getAug())),
							2)));
					sumarryReportRecord.setSep(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getSep() == null || sumarryReportRecord.getSep().isEmpty()
									? "0" : sumarryReportRecord.getSep())
							+ Float.parseFloat(summaryReport.getSep() == null || summaryReport.getSep().isEmpty() ? "0"
									: summaryReport.getSep())),
							2)));
					sumarryReportRecord.setOct(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getOct() == null || sumarryReportRecord.getOct().isEmpty()
									? "0" : sumarryReportRecord.getOct())
							+ Float.parseFloat(summaryReport.getOct() == null || summaryReport.getOct().isEmpty() ? "0"
									: summaryReport.getOct())),
							2)));
					sumarryReportRecord.setNov(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getNov() == null || sumarryReportRecord.getNov().isEmpty()
									? "0" : sumarryReportRecord.getNov())
							+ Float.parseFloat(summaryReport.getNov() == null || summaryReport.getNov().isEmpty() ? "0"
									: summaryReport.getNov())),
							2)));
					sumarryReportRecord.setDec(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getDec() == null || sumarryReportRecord.getDec().isEmpty()
									? "0" : sumarryReportRecord.getDec())
							+ Float.parseFloat(summaryReport.getDec() == null || summaryReport.getDec().isEmpty() ? "0"
									: summaryReport.getDec())),
							2)));
					sumarryReportRecord.setJan(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getJan() == null || sumarryReportRecord.getJan().isEmpty()
									? "0" : sumarryReportRecord.getJan())
							+ Float.parseFloat(summaryReport.getJan() == null || summaryReport.getJan().isEmpty() ? "0"
									: summaryReport.getJan())),
							2)));
					sumarryReportRecord.setFeb(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getFeb() == null || sumarryReportRecord.getFeb().isEmpty()
									? "0" : sumarryReportRecord.getFeb())
							+ Float.parseFloat(summaryReport.getFeb() == null || summaryReport.getFeb().isEmpty() ? "0"
									: summaryReport.getFeb())),
							2)));
					sumarryReportRecord.setMar(Double.toString(commonUtility.round((Float
							.parseFloat(sumarryReportRecord.getMar() == null || sumarryReportRecord.getMar().isEmpty()
									? "0" : sumarryReportRecord.getMar())
							+ Float.parseFloat(summaryReport.getMar() == null || summaryReport.getMar().isEmpty() ? "0"
									: summaryReport.getMar())),
							2)));
				} else {
					sumarryReportRecord = summaryReport;
				}

				custSummHashMap.put(cardName, sumarryReportRecord);
			}

			Set<String> custHashMapKeySet = custSummHashMap.keySet();
			for (String custHashmapKey : custHashMapKeySet) {
				CutomerSummaryReportDetails cutomerSummaryReportDetails = custSummHashMap.get(custHashmapKey);
				sortedSummaryReportList.add(cutomerSummaryReportDetails);
			}

			modelAndView.addObject("customerSummaryReportDetailsList", sortedSummaryReportList);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.ERROR_MSSG_LABEL, Constants.ERROR_OCCURED);
		}

		return modelAndView;
	}
}
