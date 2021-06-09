package io.common.authorization.group.service;

import io.common.authorization.common.util.PageRequest;
import io.common.authorization.company.entity.Company;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.group.dto.request.ReqRoleGroupDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupDTO;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.group.repository.RoleGroupRepository;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.resource.program.repository.ProgramRepository;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleGroupService {

    private final RoleGroupRepository roleGroupRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProgramRepository programRepository;

    private final ModelMapper modelMapper;

    /**
     * 롤 그룹 생성
     * @param reqRoleGroupDTO
     * @return
     */
    @Transactional
    public Long createRoleGroup(ReqRoleGroupDTO.CreateRoleGroupDTO reqRoleGroupDTO) {

        RoleGroup roleGroup = modelMapper.map(reqRoleGroupDTO, new TypeToken<RoleGroup>() {}.getType());

        // 회사 ID가 있는 경우 - 회사 전용 롤 그룹
        if (reqRoleGroupDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(reqRoleGroupDTO.getCompanyId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroup.setCompany(company);
        }

        // Program 조회
        Program program = programRepository.findById(reqRoleGroupDTO.getProgramId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        roleGroup.setProgram(program);

        // 마지막 수정 유저
        if (reqRoleGroupDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqRoleGroupDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroup.setMngUser(mngUser);
        }

        roleGroupRepository.save(roleGroup);

        return roleGroup.getId();
    }

    /**
     * 롤 그룹 정보 수정
     * @param reqRoleGroupDTO
     * @return
     */
    @Transactional
    public Long updateRoleGroup(ReqRoleGroupDTO.UpdateRoleGroupDTO reqRoleGroupDTO) {

        RoleGroup roleGroup = roleGroupRepository.findById(reqRoleGroupDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 회사 ID가 있는 경우 - 회사 전용 롤 그룹
        if (reqRoleGroupDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(reqRoleGroupDTO.getCompanyId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroup.setCompany(company);
        }

        // Program 조회
        Program program = programRepository.findById(reqRoleGroupDTO.getProgramId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        roleGroup.setProgram(program);

        // 수정 데이터 셋팅
        roleGroup.updateRoleGroup(reqRoleGroupDTO);

        // 마지막 수정 유저
        if (reqRoleGroupDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqRoleGroupDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroup.setMngUser(mngUser);
        }

        return roleGroup.getId();
    }

    /**
     * 롤 그룹 리스트 조회
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    public ResGetRoleGroupDTO getRoleGroups(Long companyId, int page, int size) {

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC);
        Page<RoleGroup> roleGroupList = null;

        if (companyId != null) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupList = roleGroupRepository.findByCompany(company,pageRequest.of("createDt"));
        } else {
            roleGroupList = roleGroupRepository.findByCompanyIsNullOrderByCreateDt(pageRequest.of("createDt"));
        }

        return new ResGetRoleGroupDTO(roleGroupList.getContent(), page, size, roleGroupList.getTotalPages());
    }

    /**
     * 롤 그룹 상세 정보 조회
     * @param roleGroupId
     * @return
     */
    public ResGetRoleGroupDTO.GetRoleGroups getRoleGroupInfo(Long roleGroupId) {

        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        return new ResGetRoleGroupDTO.GetRoleGroups(roleGroup);
    }
}
