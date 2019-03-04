package com.spring.calc.vo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiVo {
    private boolean success;
    private String terms;
    private String privacy;
    private String timestamp;
    private String source;
    private Map<String, Double> quotes;
}
