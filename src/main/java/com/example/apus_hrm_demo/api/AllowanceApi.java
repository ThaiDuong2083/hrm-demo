package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import com.example.apus_hrm_demo.service.AllowanceSercive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/allowance")
@RequiredArgsConstructor
public class AllowanceApi {
    private final AllowanceSercive allowanceSercive;

    @PostMapping("/")
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> createAllowance(@RequestBody AllowanceDTO allowanceFormDTO) {
        return ResponseEntity.ok(allowanceSercive.createAllowance(allowanceFormDTO));
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<AllowanceDTO>> getAllowance(@RequestParam Long id) {
        return  ResponseEntity.ok(allowanceSercive.findById(id));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> updateAllowance(@RequestBody AllowanceDTO allowanceFormDTO) {
        return ResponseEntity.ok(allowanceSercive.updateAllowance(allowanceFormDTO));
    }

    @GetMapping("list")
    public ResponseEntity<BaseResponse<ResponsePage<AllowanceGetAllDTO>>> getAllAllowance(@RequestParam(required = false) String name,
                                                                                          @RequestParam(required = false) Boolean isActive,
                                                                                          Pageable pageable) {
        return ResponseEntity.ok(allowanceSercive.getAll(name,isActive,pageable));
    }

    @DeleteMapping
    public void deleteAllowance(@RequestParam Long id) {
        allowanceSercive.deleteAllowance(id);
    }
}
