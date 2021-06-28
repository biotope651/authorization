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
     * 롤 그룹 조인 매칭
     * @param roleGroupJoinDTO
     * @return
     */
    @Transactional
    public boolean createRoleGroupJoin(ReqRoleGroupJoinDTO roleGroupJoinDTO) {

        // 롤 그룹 셋팅
        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupJoinDTO.getRoleGroupId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 회사 셋팅
        Company company = null;
        if (roleGroupJoinDTO.getCompanyId() != null) {
            company = companyRepository.findById(roleGroupJoinDTO.getCompanyId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        }

        for (ReqRoleGroupJoinDTO.CreateRoleGroupJoinDTO createRoleGroupJoinDTO : roleGroupJoinDTO.getRoleGroupJoinList()) {

            RoleGroupJoin roleGroupJoin = new RoleGroupJoin();

            roleGroupJoin.setId(createRoleGroupJoinDTO.getId());

            roleGroupJoin.setRoleGroup(roleGroup);

            // 롤 그룹 권한 셋팅
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
     * 롤 그룹 조인 해제
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
     * 롤 그룹 권한 리스트 매칭 조회 - 롤 그룹 권한 매칭을 위한 리스트 조회
     * @param companyId
     * @param roleGroupId
     * @return
     */
    public ResGetRoleGroupJoinDTO getRoleGroupJoin(Long companyId, Long roleGroupId) {

        // RoleGroup에 할당된 RoleGroupAuth List
        List<RoleGroupJoin> assignedRoleGroupJoinList = null;
        // RoleGroup에 할당되지 않은 RoleGroupAuth List
        List<RoleGroupAuth> unAssignedRoleGroupList = null;

        // RoleGroup에 할당되지 않은 RoleGroupAuthId List
        List<Long> unAssignedRoleGroupAuthIds = null;

        Company company = null;

        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        /**
         * Case 1 : 디폴트 롤 그룹 권한 매칭 용도
         * Case 2 : 회사별 롤 그룹 권한 매칭 용도
         */
        if (companyId == null && roleGroupId != null) {
            /**
             * Case 1. companyId (X), roleGroupId (O)
             * roleGroup에 할당된 롤 그룹 권한 리스트 1개
             * roleGroup에 할당되지 않은 롤 그룹 권한 리스트 1개
             */
            assignedRoleGroupJoinList = roleGroupJoinRepository.findByCompanyIsNullAndRoleGroup(roleGroup);

            // 할당한 롤 그룹 권한 ID를 리스트로 가져온다.
            unAssignedRoleGroupAuthIds = assignedRoleGroupJoinList
                    .stream().map(c -> c.getRoleGroupAuth().getId()).collect(Collectors.toList());
        } else if (companyId != null && roleGroupId != null) {
            /**
             * Case 2. companyId (O), roleGroupId (O)
             * roleGroup에 할당된 리스트 1개
             * roleGroup에 할당되지 않은 리스트 1개
             */
            company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

            assignedRoleGroupJoinList = roleGroupJoinRepository.findByCompanyAndRoleGroup(company, roleGroup);

            // 할당한 롤 그룹 권한 ID를 리스트로 가져온다.
            unAssignedRoleGroupAuthIds = assignedRoleGroupJoinList
                    .stream().map(c -> c.getRoleGroupAuth().getId()).collect(Collectors.toList());
        }

        // 프로그램 조회
        Program program = programRepository.findById(roleGroup.getProgram().getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 할당되지 않은 롤 그룹 권한 리스트를 조회한다.
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
