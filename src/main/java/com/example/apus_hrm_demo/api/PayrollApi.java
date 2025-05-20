package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.filter.PayrollFilter;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollDetailDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import com.example.apus_hrm_demo.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payroll")
@RequiredArgsConstructor
public class PayrollApi {
    private final PayrollService payrollService;

    @PostMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> create(@RequestBody PayrollDTO dto){
        return ResponseEntity.ok(payrollService.create(dto));
    }
    @PutMapping()
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> update(@RequestBody PayrollDTO dto){
        return ResponseEntity.ok(payrollService.update(dto));
    }
    @GetMapping()
    public ResponseEntity<BaseResponse<PayrollDetailDTO>> findById(@RequestParam("id") Long id){
        return ResponseEntity.ok(payrollService.findById(id));
    }
    @GetMapping("list")
    public ResponseEntity<BaseResponse<ResponsePage<PayrollGetAllDTO>>> findAll(Pageable pageable, @ParameterObject PayrollFilter payrollFilter){
        return ResponseEntity.ok(payrollService.getAll(payrollFilter ,pageable));
    }
}
