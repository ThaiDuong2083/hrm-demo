package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import com.example.apus_hrm_demo.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reward")
@RequiredArgsConstructor
public class RewardApi {
    private final RewardService rewardService;

    @PostMapping("/")
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> createReward(@RequestBody RewardDTO rewardFormDTO) {
        return ResponseEntity.ok(rewardService.createReward(rewardFormDTO));
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<RewardDTO>> getReward(@RequestParam Long id) {
        return  ResponseEntity.ok(rewardService.findById(id));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> updateReward(@RequestBody RewardDTO rewardFormDTO) {
        return ResponseEntity.ok(rewardService.updateReward(rewardFormDTO));
    }

    @GetMapping("list")
    public ResponseEntity<BaseResponse<ResponsePage<RewardGetAllDTO>>> getAllReward(@RequestParam(required = false) String name,
                                                                                    @RequestParam(required = false) Boolean isActive,
                                                                                    Pageable pageable) {
        return ResponseEntity.ok(rewardService.getAll(name, isActive, pageable));
    }

    @DeleteMapping
    public void deleteReward(@RequestParam Long id) {
        rewardService.deleteReward(id);
    }
}
