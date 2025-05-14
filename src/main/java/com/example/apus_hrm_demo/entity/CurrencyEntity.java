package com.example.apus_hrm_demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CurrencyEntity {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String symbol;
    private String fullName;
    private String position;
    private Boolean isMainCurrency;
    private String currencyMin;
    private Boolean activated;
    private LocalDateTime updatedAt;
}
