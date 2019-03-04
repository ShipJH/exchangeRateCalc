package com.spring.calc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.calc.service.ExchangeRateService;

@Controller
public class ExchangeRateController {

	private ExchangeRateService exchangeRateService;
	
	// 서비스 생성자 주입
    public ExchangeRateController(ExchangeRateService exchangeRateService) { 
        this.exchangeRateService = exchangeRateService;
    }
	
    @RequestMapping("/")
    public String home(Model model){
        return "index";
    }

    @RequestMapping("/exchangeRates")
    public ResponseEntity<String> exchangeRates(HttpServletRequest req){
    	
    	String currency = req.getParameter("currency");
    	
        return new ResponseEntity<String>(exchangeRateService.getExchangeRateVal(currency), HttpStatus.OK);
    }
    
	
}
