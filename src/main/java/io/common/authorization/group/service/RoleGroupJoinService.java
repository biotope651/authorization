package io.common.authorization.group.service;

import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.auth.repository.RoleGroupAuthRepository;
import io.common.authorization.common.util.PageRequest;
import io.common.authorization.company.entity.Company;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.group.dto.request.ReqDeleteRoleGroupJoinDTO;
import io.common.authorization.group.dto.request.ReqRoleGroupJoinDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupAssignedDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupJoinDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupUnassignedDTO;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.group.entity.RoleGroupJoin;
import io.common.authorization.group.repository.RoleGroupJoinRepository;
import io.common.authorization.group.repository.RoleGroupRepository;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.resource.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleGroupJoinService {

    private final RoleGroupJoinRepository roleGroupJoinRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final RoleGroupAuthRepository roleGroupAuthRepository;
    private final CompanyRepository companyRepository;
    private final ProgramRepository programRepository;

    /**
     * ??? ?????? ?????? ??????
     * @param roleGroupJoinDTO
     * @return
     */
    @Transactional
    public boolean createRoleGroupJoin(ReqRoleGroupJoinDTO roleGroupJoinDTO) {

        // ??? ?????? ??????
        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupJoinDTO.getRoleGroupId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // ?????? ??????
        Company company = null;
        if (roleGroupJoinDTO.getCompanyId() != null) {
            company = companyRepository.findById(roleGroupJoinDTO.getCompanyId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        }

        for (ReqRoleGroupJoinDTO.CreateRoleGroupJoinDTO createRoleGroupJoinDTO : roleGroupJoinDTO.getRoleGroupJoinList()) {

            RoleGroupJoin roleGroupJoin = new RoleGroupJoin();

            roleGroupJoin.setId(createRoleGroupJoinDTO.getId());

            roleGroupJoin.setRoleGroup(roleGroup);

            // ??? ?????? ?????? ??????
            RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(createRoleGroupJoinDTO.getRoleGroupAuthId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

            roleGroupJoin.setRoleGroupAuth(roleGroupAuth);

            if (roleGroupJoinDTO.getCompanyId() != null) {
                roleGroupJoin.setCompany(company);
            }

            roleGroupJoinRepository.save(roleGroupJoin);
        }


        return true;
    }

    /**
     * ??? ?????? ?????? ??????
     * @param reqDeleteRoleGroupJoinDTO
     * @return
     */
    @Transactional
    public boolean deleteRoleGroupJoin(ReqDeleteRoleGroupJoinDTO reqDeleteRoleGroupJoinDTO) {

        List<Long> ids = reqDeleteRoleGroupJoinDTO.getRoleGroupJoinList().stream().map(c->c.getId()).collect(Collectors.toList());

        Long deleteCount = roleGroupJoinRepository.deleteAllByIdIn(ids);

        if(ids.size() != deleteCount) {
            return false;
        }

        return true;

    }

    /**
     * ??? ?????? ?????? ????????? ?????? ?????? - ??? ?????? ?????? ????????? ?????? ????????? ??????
     * @param companyId
     * @param roleGroupId
     * @return
     */
    public ResGetRoleGroupJoinDTO getRoleGroupJoin(Long companyId, Long roleGroupId) {

        // RoleGroup??? ????????? RoleGroupAuth List
        List<RoleGroupJoin> assignedRoleGroupJoinList = null;
        // RoleGroup??? ???????????? ?????? RoleGroupAuth List
        List<RoleGroupAuth> unAssignedRoleGroupList = null;

        // RoleGroup??? ???????????? ?????? RoleGroupAuthId List
        List<Long> unAssignedRoleGroupAuthIds = null;

        Company company = null;

        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        /**
         * Case 1 : ????????? ??? ?????? ?????? ?????? ??????
         * Case 2 : ????????? ??? ?????? ?????? ?????? ??????
         */
        if (companyId == null && roleGroupId != null) {
            /**
             * Case 1. companyId (X), roleGroupId (O)
             * roleGroup??? ????????? ??? ?????? ?????? ????????? 1???
             * roleGroup??? ???????????? ?????? ??? ?????? ?????? ????????? 1???
             */
            assignedRoleGroupJoinList = roleGroupJoinRepository.findByCompanyIsNullAndRoleGroup(roleGroup);

            // ????????? ??? ?????? ?????? ID??? ???????????? ????????????.
            unAssignedRoleGroupAuthIds = assignedRoleGroupJoinList
                    .stream().map(c -> c.getRoleGroupAuth().getId()).collect(Collectors.toList());
        } else if (companyId != null && roleGroupId != null) {
            /**
             * Case 2. companyId (O), roleGroupId (O)
             * roleGroup??? ????????? ????????? 1???
             * roleGroup??? ???????????? ?????? ????????? 1???
             */
            company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

            assignedRoleGroupJoinList = roleGroupJoinRepository.findByCompanyAndRoleGroup(company, roleGroup);

            // ????????? ??? ?????? ?????? ID??? ???????????? ????????????.
            unAssignedRoleGroupAuthIds = assignedRoleGroupJoinList
                    .stream().map(c -> c.getRoleGroupAuth().getId()).collect(Collectors.toList());
        }

        // ???????????? ??????
        Program program = programRepository.findById(roleGroup.getProgram().getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // ???????????? ?????? ??? ?????? ?????? ???????????? ????????????.
        if(unAssignedRoleGroupAuthIds.size() > 0) {
            unAssignedRoleGroupList = roleGroupAuthRepository.findByIdNotInAndCompanyAndProgram(unAssignedRoleGroupAuthIds, company, program);
        } else {
            unAssignedRoleGroupList = roleGroupAuthRepository.findByCompanyAndProgram( company, program);
        }

        ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO = new ResGetRoleGroupAssignedDTO(assignedRoleGroupJoinList);
        ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO = new ResGetRoleGroupUnassignedDTO(unAssignedRoleGroupList);

        return new ResGetRoleGroupJoinDTO(resGetRoleGroupAssignedDTO,resGetRoleGroupUnassignedDTO);

    }
}
