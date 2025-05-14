package com.example.apus_hrm_demo.api;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.GroupAllowanceDTO;
import com.example.apus_hrm_demo.service.GroupAllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/group-allowance")
@RequiredArgsConstructor
public class GroupAllowanceApi {
    private final GroupAllowanceService groupAllowanceService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<ResponsePage<GroupAllowanceDTO>>> getAll(@RequestParam(required = false) String name,
                                                                                @RequestParam(required = false) Boolean isActive,
                                                                                Pageable pageable) {
        return ResponseEntity.ok(groupAllowanceService.getAll(name, isActive,pageable));
    }
    @PostMapping("")
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> create(@RequestBody GroupAllowanceDTO groupAllowanceDTO) {
        return ResponseEntity.ok(groupAllowanceService.create(groupAllowanceDTO));
    }
    @PutMapping
    public ResponseEntity<BaseResponse<ResponseAfterCUDTO>> update(@RequestBody GroupAllowanceDTO groupAllowanceDTO) {
        return ResponseEntity.ok(groupAllowanceService.update(groupAllowanceDTO));
    }
    @DeleteMapping("")
    public void delete(@RequestParam Long id) {
        groupAllowanceService.delete(id);
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<GroupAllowanceDTO>> getById(@RequestParam Long id) {
        return ResponseEntity.ok(groupAllowanceService.findById(id));
    }
}
