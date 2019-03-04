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
	
    // restTemplate 초기화
    public ExchangeRateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @Value("${apikey}")
    private String accessKey;

    private final String source = "USD";
    
    
    
    

    
	public String getExchangeRateVal(String currency) {
		double val = getExchangeRate(currency);
		
		DecimalFormat df = new DecimalFormat("###,###.00");
		return df.format(val);
	}

	private double getExchangeRate(String currency) {       
	    ApiVo apiVo = restTemplate.getForObject("http://www.apilayer.net/api/live?access_key=" + accessKey + "&currencies=" + currency + "&source=" + source, ApiVo.class);
        // TODO : restTemplate 은 절대 null 을 반환하지 않는다. 따라서 Optional 을 사용할 필요가 없다.
        // TODO : optional 을 사용하면 가독성이 떨어지기 때문에 상황에 맞게 써야 한다.

	    System.out.println(apiVo.getQuotes().get(source+currency));
	    
	    return apiVo.getQuotes().get(source+currency);
	    
	    //        return Optional.ofNullable(apiData)
//                .map(ApiData::getQuotes)                    // 국가별 환율 정보 맵을 가져옴
//                .map(map -> map.get(source + currency))     // 맵으로 부터 해당 국가의 환율 가져옴
//                .orElseThrow(RuntimeException::new);        // Todo : 어떠한 오류를 나타내는지 정확히 알고 처리하는게 좋다.
//                // 이 부분도
    }
	
}
