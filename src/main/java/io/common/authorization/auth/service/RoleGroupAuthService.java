package io.common.authorization.auth.service;

import io.common.authorization.auth.dto.request.ReqRoleGroupAuthDTO;
import io.common.authorization.auth.dto.response.ResGetRoleGroupAuthDTO;
import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.auth.repository.RoleGroupAuthRepository;
import io.common.authorization.common.util.PageRequest;
import io.common.authorization.company.entity.Company;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleGroupAuthService {

    private final RoleGroupAuthRepository roleGroupAuthRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProgramRepository programRepository;

    private final ModelMapper modelMapper;

    /**
     * 롤 그룹 권한 생성
     * @param reqRoleGroupAuthDTO
     * @return
     */
    @Transactional
    public Long createRoleGroupAuth(ReqRoleGroupAuthDTO.CreateRoleGroupAuthDTO reqRoleGroupAuthDTO) {

        RoleGroupAuth roleGroupAuth = modelMapper.map(reqRoleGroupAuthDTO, new TypeToken<RoleGroupAuth>() {}.getType());

        // Company 조회
        if (reqRoleGroupAuthDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(reqRoleGroupAuthDTO.getCompanyId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupAuth.setCompany(company);
        }

        // Program 조회
        Program program = programRepository.findById(reqRoleGroupAuthDTO.getProgramId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        roleGroupAuth.setProgram(program);

        // 마지막 수정 유저
        if (reqRoleGroupAuthDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqRoleGroupAuthDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupAuth.setMngUser(user);
        }

        roleGroupAuthRepository.save(roleGroupAuth);

        return roleGroupAuth.getId();
    }

    /**
     * 롤 그룹 권한 리스트 조회 - 롤 그룹 권한 리스트 관리용
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    public ResGetRoleGroupAuthDTO getRoleGroupAuth(Long companyId, int page, int size) {

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC);
        Page<RoleGroupAuth> roleGroupAuthList = null;

        /**
         * Case 1 : 디폴트 롤 그룹 권한 관리용도
         * Case 2 : 회사별 롤 그룹 권한 관리용도
         */
        if (companyId == null) {
            /**
             * Case 1. companyId (X)
             * 전체 list 1개
             */
            roleGroupAuthList = roleGroupAuthRepository.findByCompanyIsNull(pageRequest.of("roleGroupAuthName"));

        } else if (companyId != null) {
            /**
             * Case 2. companyId (O)
             * 전체 list 1개
             */
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupAuthList = roleGroupAuthRepository.findByCompany(company, pageRequest.of("roleGroupAuthName"));

        }

        return new ResGetRoleGroupAuthDTO(roleGroupAuthList.getContent(), page, size, roleGroupAuthList.getTotalPages());
    }

    /**
     * 롤 그룹 권한 상세 정보 조회
     * @param roleGroupAuthId
     * @return
     */
    public ResGetRoleGroupAuthDTO.GetRoleGroupAuth getRoleGroupAuthInfo(Long roleGroupAuthId) {

        RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(roleGroupAuthId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        return new ResGetRoleGroupAuthDTO.GetRoleGroupAuth(roleGroupAuth);
    }

    /**
     * 롤 그룹 권한 수정
     * @param reqRoleGroupAuthDTO
     * @return
     */
    @Transactional
    public Long updateRoleGroupAuth(ReqRoleGroupAuthDTO.UpdateRoleGroupAuthDTO reqRoleGroupAuthDTO) {

        RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(reqRoleGroupAuthDTO.getRoleGroupAuthId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // Company 조회
        if (reqRoleGroupAuthDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(reqRoleGroupAuthDTO.getCompanyId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupAuth.setCompany(company);
        }

        // Program 조회
        Program program = programRepository.findById(reqRoleGroupAuthDTO.getProgramId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        roleGroupAuth.setProgram(program);

        // 롤 그룹 권한 수정사항 셋팅
        roleGroupAuth.updateRoleGroupAuth(reqRoleGroupAuthDTO);

        // 마지막 수정 유저
        if (reqRoleGroupAuthDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqRoleGroupAuthDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            roleGroupAuth.setMngUser(user);
        }

        return roleGroupAuth.getId();
    }
}
