package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDetailDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.service.AllowancePolicyService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/allowance-policy")
@RequiredArgsConstructor
public class AllowancePolicyApi {
    private final AllowancePolicyService allowancePolicyService;

    @PostMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> create(@RequestBody AllowancePolicyDTO allowancePolicyDTO) {
        return ResponseEntity.ok(allowancePolicyService.create(allowancePolicyDTO));
    }
    @PutMapping()
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> update(@RequestBody AllowancePolicyDTO allowancePolicyDTO) {
        return ResponseEntity.ok(allowancePolicyService.update(allowancePolicyDTO));
    }
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<ResponsePage<AllowancePolicyGetAllDto>>> list(Pageable pageable,
                                                                     @ParameterObject PolicyFliter policyFliter) {
        return ResponseEntity.ok( allowancePolicyService.findAll(pageable, policyFliter));
    }
    @DeleteMapping()
    public void delete(@RequestParam Long allowancePolicyId) {
        allowancePolicyService.delete(allowancePolicyId);
    }
    @GetMapping()
    public ResponseEntity<BaseResponse<AllowancePolicyDetailDTO>> findById(@RequestParam Long allowancePolicyId) {
        return ResponseEntity.ok(allowancePolicyService.findById(allowancePolicyId));
    }
}
