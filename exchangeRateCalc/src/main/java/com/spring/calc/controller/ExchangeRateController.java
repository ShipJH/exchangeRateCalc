package com.spring.calc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.calc.service.ExchangeRateService;
import com.spring.calc.vo.FromVo;

@Controller
public class ExchangeRateController {

	private ExchangeRateService exchangeRateService;
	
	//@autowird 대신 생성자로 의존성 주입
    public ExchangeRateController(ExchangeRateService exchangeRateService) { 
        this.exchangeRateService = exchangeRateService;
    }
	
    /**
     * 첫페이지
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String home(Model model){
    	
    	model.addAttribute("initRate", exchangeRateService.getExchangeRateVal("KRW"));
    	
        return "index";
    }

    /**
     * 콤보박스 체인지 될때 호출
     * @param req
     * @return
     */
    @RequestMapping("/exchangeRates")
    public ResponseEntity<String> exchangeRates(HttpServletRequest req){
    	
    	String currency = req.getParameter("currency");
    	
        return new ResponseEntity<String>(exchangeRateService.getExchangeRateVal(currency), HttpStatus.OK);
    }
    
    /**
     * 환율과 송금액 계산
     * @param vo
     * @param br
     * @return
     */
    @RequestMapping("/exchangeRateSubmit")
//    public ResponseEntity<String> exchangeRateSubmit(FromVo vo){
   	public ResponseEntity<String> exchangeRateSubmit(@Validated FromVo vo, BindingResult br){
    	
    	if(br.hasErrors()) {
    		
    		String errorMsg = "";
    		
    		List<FieldError> errors = br.getFieldErrors();
    		
    		errorMsg = errors.get(0).getDefaultMessage();
//			for (FieldError error : errors) {
//				errorMsg = error.getDefaultMessage();
//			}
    		
    		return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
    	}
    	
    	String result = exchangeRateService.getRateMultiply(Double.parseDouble(vo.getRate().replaceAll(",", "")), vo.getSendMoney());
    	
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
    
	
}
