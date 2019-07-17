package com.sapbasemodule.controllers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.sapbasemodule.model.InvoicesDetails;
import com.sapbasemodule.persitence.CustomersRepository;
import com.sapbasemodule.service.CustomersService;

@Controller
public class InvoiceController {

	@Autowired
	private CustomersService customerService;

	@GetMapping("ext/invoice-acknowledgement")
	public ModelAndView viewInvoiceAcknowledgement(ModelAndView modelAndView)
			throws ClassNotFoundException, ParseException, SQLException {

		modelAndView.setViewName("invoices/invoice-acknowledgment");

		try {
			List<InvoicesDetails> invoicesDetailsList = customerService.getInvoiceAcknowledgments();
			modelAndView.addObject("invoicesDetailsList", invoicesDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive acknowledgements");
		}

		return modelAndView;
	}

	@Autowired
	private CustomersRepository customersRepository;

	@GetMapping("ext/invoice-details/{invoice-no}")
	public ModelAndView showInvoiceDetails(@PathVariable("invoice-no") String invoiceNo, ModelAndView modelAndView) {

		try {
			InvoicesDetails invoicesDetails = customerService.getInvoiceDetailsByInvoiceId(invoiceNo);
			String cardCode = invoicesDetails.getCardCode();

			System.out.println("tax Amount totoal = " + invoicesDetails.getTotalTax());
			String cardName = customersRepository.findCardNameByCardCode(cardCode);

			modelAndView.addObject("invoice", invoicesDetails);
			modelAndView.addObject("cardName", cardName);

			modelAndView.setViewName("invoices/invoice-details");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive Invoice Details");
			modelAndView.setViewName("invoices/invoice-acknowledgment");
		}

		return modelAndView;
	}

	@GetMapping("ext/recordings")
	public ModelAndView getRecordingsView(ModelAndView modelAndView) {

		try {

			modelAndView.setViewName("invoices/recordings");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errorMssg", "Unable to retreive Invoice Details");
			modelAndView.setViewName("invoices/invoice-acknowledgment");
		}

		return modelAndView;
	}
}
