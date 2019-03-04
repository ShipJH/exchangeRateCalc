package com.spring.calc.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.calc.vo.ApiVo;

@Service
public class ExchangeRateService {

	private final RestTemplate restTemplate;
	
    public ExchangeRateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    //application.yml 에 저장된 프로퍼티성 api키를 가져와 accessKey에 할당한다.
    @Value("${apikey}")
    private String accessKey;

    //USD로 고정.
    private final String source = "USD";
    
    
    
    

    /**
     * 선택한 국가의 환율을 가져온다.
     * @param currency [선택한 국가의 파라미터]
     * @return API를 통해 반환된 값을 소수점포맷하여 반환되는 값
     */
	public String getExchangeRateVal(String currency) {
		double val = getExchangeRate(currency);
		return formatter(val); 
	}

	/**
	 * 실시간 API를 통하여 환율을 가져오는 메소드.
	 * @param currency [선택한 국가의 파라미터]
	 * @return API를 통한 현재 실시간 환율의 반환값
	 */
	private double getExchangeRate(String currency) {       
	    
		String apiUrl = "http://www.apilayer.net/api/live?access_key=" + accessKey + "&currencies=" + currency + "&source=" + source;
		ApiVo apiVo = restTemplate.getForObject(apiUrl, ApiVo.class);

	    return apiVo.getQuotes().get(source+currency);
    }

	
	/**
	 * 환율과 송금액을 계산해준다.
	 * @param rate [환율]
	 * @param sendMoney [송금액]
	 * @return 환율x송금액
	 */
	public String getRateMultiply(double rate, Double sendMoney) {
		return formatter(rate * sendMoney);
	}
	
	
	/**
	 * 소수점을 format해주는 메소드.
	 * @param val [소수점 포맷을 요구하는 파라미터]
	 * @return 포맷된 값
	 */
	public String formatter(double val) {
		DecimalFormat df = new DecimalFormat("###,###.00");
		return df.format(val);
	}
	
}
