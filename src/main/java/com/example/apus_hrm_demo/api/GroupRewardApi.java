package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.GroupRewardDTO;
import com.example.apus_hrm_demo.service.GroupRewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/group-reward")
@RequiredArgsConstructor
public class GroupRewardApi {
    private final GroupRewardService groupRewardService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<ResponsePage<GroupRewardDTO>>> getAll(@RequestParam(required = false) String name,
                                                                             @RequestParam(required = false) Boolean isActive,
                                                                             Pageable pageable) {
        return ResponseEntity.ok(groupRewardService.getAll(name, isActive,pageable));
    }
    @PostMapping("")
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> create(@RequestBody GroupRewardDTO groupRewardDTO) {
        return ResponseEntity.ok(groupRewardService.create(groupRewardDTO));
    }
    @PutMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> update(@RequestBody GroupRewardDTO groupRewardDTO) {
        return ResponseEntity.ok(groupRewardService.update(groupRewardDTO));
    }
    @DeleteMapping("")
    public void delete(@RequestParam Long id) {
        groupRewardService.delete(id);
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<GroupRewardDTO>> getById(@RequestParam Long id) {
        return ResponseEntity.ok(groupRewardService.findById(id));
    }
}
