package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyGetAllDto;
import com.example.apus_hrm_demo.service.RewardPolicyService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reward-policy")
@RequiredArgsConstructor
public class RewardPolicyApi {
    private final RewardPolicyService rewardPolicyService;

    @PostMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> create(@RequestBody RewardPolicyDTO rewardPolicyDTO) {
        return ResponseEntity.ok(rewardPolicyService.create(rewardPolicyDTO));
    }
    @PutMapping()
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> update(@RequestBody RewardPolicyDTO rewardPolicyDTO) {
        return ResponseEntity.ok(rewardPolicyService.update(rewardPolicyDTO));
    }
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<ResponsePage<RewardPolicyGetAllDto>>> list(Pageable pageable,
                                                                     @ParameterObject PolicyFliter policyFliter) {
        return ResponseEntity.ok( rewardPolicyService.findAll(pageable, policyFliter));
    }
    @DeleteMapping()
    public void delete(@RequestParam Long rewardPolicyId) {
        rewardPolicyService.delete(rewardPolicyId);
    }
    @GetMapping()
    public ResponseEntity<BaseResponse<RewardPolicyDTO>> findById(@RequestParam Long rewardPolicyId) {
        return ResponseEntity.ok(rewardPolicyService.findById(rewardPolicyId));
    }
}
