package com.sapbasemodule.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ext/")
public class MapController {

	@GetMapping("map/{appOrdrDlvryId}")
	public ModelAndView showOrderDeliveryTrackingOnMap(@PathVariable("appOrdrDlvryId") int appOrdrDlvryId, ModelAndView modelAndView) {
		
		modelAndView.setViewName("maps/vehicle-tracking");
		
		return modelAndView;
	}
}
