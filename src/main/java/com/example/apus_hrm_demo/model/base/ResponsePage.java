package com.example.apus_hrm_demo.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage <T>{
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private String sort;
    private int numberOfElements;
}
