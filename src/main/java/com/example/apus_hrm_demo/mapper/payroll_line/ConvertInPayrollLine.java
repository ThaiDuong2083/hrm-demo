package com.example.apus_hrm_demo.mapper.payroll_line;

import com.example.apus_hrm_demo.entity.*;
import com.example.apus_hrm_demo.mapper.allowance.AllowanceMapper;
import com.example.apus_hrm_demo.mapper.reward.RewardMapper;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.payroll_line.line.LineDTO;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyLineRepository;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.repository.RewardPolicyLineRepository;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConvertInPayrollLine {
    private final AllowanceRepository allowanceRepository;
    private final RewardRepository rewardRepository;
    private final AllowanceMapper allowanceMapper;
    private final RewardMapper rewardMapper;
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final RewardPolicyLineRepository rewardPolicyLineRepository;

    @Named("toLineDTO")
    public LineDTO toLineDTO(Long targetId,@Context PayrollLineContext payrollLineContext) {
        if (PayrollLineType.ALLOWANCE == payrollLineContext.getPayrollLineType()) {
            AllowanceDTO allowanceDTO = allowanceMapper.toDto(new AllowanceEntity(payrollLineContext.getGroupTargetId(),targetId));
            return new LineDTO(allowanceDTO.getId(),allowanceDTO.getName(),allowanceDTO.getCode(),
                    allowanceDTO.getIncludeType(),allowanceDTO.getGroupAllowance());
        } else {
            RewardDTO rewardDTO= rewardMapper.toDto(new RewardEntity(targetId,payrollLineContext.getGroupTargetId()));
            return new LineDTO(rewardDTO.getId(),rewardDTO.getName(),rewardDTO.getCode(),
                    rewardDTO.getIncludeType(),rewardDTO.getGroupReward());
        }
    }

    @Named("getCycleOfTarget")
    public Cycle getCycleOfTarget(Long targetId, @Context PayrollLineContext payrollLineContext) {
        if (PayrollLineType.ALLOWANCE == payrollLineContext.getPayrollLineType()) {
            List<AllowancePolicyLineEntity> listAllowancePolicyLineEntities = allowancePolicyLineRepository.findByAllowanceId(targetId);
            if (listAllowancePolicyLineEntities.isEmpty()) {
                return null;
            }
            return listAllowancePolicyLineEntities.getFirst().getCycle();
        }else {
            List<RewardPolicyLineEntity> rewardPolicyLineEntities = rewardPolicyLineRepository.findByRewardPolicyId(targetId);
            if (rewardPolicyLineEntities.isEmpty()) {
                return null;
            }
            return rewardPolicyLineEntities.getFirst().getCycle();
        }
    }

    @Named("getTargetName")
    public String getTargetName(Long targetId, @Context PayrollLineContext payrollLineContext) {
        if (PayrollLineType.ALLOWANCE == payrollLineContext.getPayrollLineType()) {
            Optional<AllowanceEntity> allowanceEntityOptional = allowanceRepository.findById(targetId);
            if (allowanceEntityOptional.isEmpty()){
                return null;
            }
            return allowanceEntityOptional.get().getName();
        }else {
            Optional<RewardEntity> rewardEntityOptional = rewardRepository.findById(targetId);
            if (rewardEntityOptional.isEmpty()){
                return null;
            }
            return rewardEntityOptional.get().getName();
        }
    }
}
