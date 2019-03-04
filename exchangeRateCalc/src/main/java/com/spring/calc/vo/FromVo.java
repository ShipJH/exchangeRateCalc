package com.spring.calc.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * submit을 통한, 환율과, 송금액을 받을 VO객체 
 * @author ShipJH
 */
@Getter @Setter
public class FromVo {

	//현재 반환된 환율
	private String rate;
	
	//송금액
	@Max(value = 10000, message = "10000 단위보다 낮게 입력해주세요.")
	@NotNull(message = "송금할 액수를 입력하여 주세요.")
	@Min(value = 1, message = "0보다 높게 입력해주세요.")
	private Double sendMoney; 
	
}
