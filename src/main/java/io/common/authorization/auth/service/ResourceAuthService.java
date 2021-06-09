package io.common.authorization.auth.service;

import io.common.authorization.auth.dto.request.ReqResourceAuthDTO;
import io.common.authorization.auth.dto.response.ResGetResourceAuthDTO;
import io.common.authorization.auth.entity.ResourceAuth;
import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.auth.repository.ResourceAuthRepository;
import io.common.authorization.auth.repository.RoleGroupAuthRepository;
import io.common.authorization.common.type.UserType;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.resource.menu.entity.ResourceMenu;
import io.common.authorization.resource.menu.repository.ResourceMenuRepository;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.resource.program.repository.ProgramRepository;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResourceAuthService {

    private final ResourceAuthRepository resourceAuthRepository;
    private final ResourceMenuRepository resourceMenuRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleGroupAuthRepository roleGroupAuthRepository;
    private final ProgramRepository programRepository;

    private final ModelMapper modelMapper;

    /**
     * 리소스 권한 생성
     * @param reqResourceAuthDTO
     * @return
     */
    @Transactional
    public Long createResourceAuth(ReqResourceAuthDTO.CreateResourceAuthDTO reqResourceAuthDTO) {

        ResourceAuth resourceAuth = modelMapper.map(reqResourceAuthDTO, new TypeToken<ResourceAuth>() {}.getType());

        // 리소스 메뉴 셋팅
        if (reqResourceAuthDTO.getResourceMenuId() != null) {
            ResourceMenu resourceMenu = resourceMenuRepository.findById(reqResourceAuthDTO.getResourceMenuId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setResourceMenu(resourceMenu);
        }

        // 프로그램 셋팅
        if (reqResourceAuthDTO.getProgramId() != null) {
            Program program = programRepository.findById(reqResourceAuthDTO.getProgramId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setProgram(program);
        }

        // 롤 그룹 권한 셋팅
        if (reqResourceAuthDTO.getRoleGroupAuthId() != null) {
            RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(reqResourceAuthDTO.getRoleGroupAuthId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setRoleGroupAuth(roleGroupAuth);
        }

        // 마지막 수정 유저
        if (reqResourceAuthDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqResourceAuthDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setMngUser(user);
        }

        resourceAuthRepository.save(resourceAuth);

        return resourceAuth.getId();
    }

    /**
     * 리소스 권한 수정
     * @param reqResourceAuthDTO
     * @return
     */
    @Transactional
    public Long updateResourceAuth(ReqResourceAuthDTO.UpdateResourceAuthDTO reqResourceAuthDTO) {

        ResourceAuth resourceAuth = resourceAuthRepository.findById(reqResourceAuthDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        resourceAuth.updateResourceAuth(reqResourceAuthDTO);

        // 리소스 메뉴 셋팅
        if (reqResourceAuthDTO.getResourceMenuId() != null) {
            ResourceMenu resourceMenu = resourceMenuRepository.findById(reqResourceAuthDTO.getResourceMenuId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setResourceMenu(resourceMenu);
        }

        // 프로그램 셋팅
        if (reqResourceAuthDTO.getProgramId() != null) {
            Program program = programRepository.findById(reqResourceAuthDTO.getProgramId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setProgram(program);
        }

        // 롤 그룹 권한 셋팅
        if (reqResourceAuthDTO.getRoleGroupAuthId() != null) {
            RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(reqResourceAuthDTO.getRoleGroupAuthId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setRoleGroupAuth(roleGroupAuth);
        }

        // 마지막 수정 유저
        if (reqResourceAuthDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqResourceAuthDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceAuth.setMngUser(user);
        }

        return resourceAuth.getId();
    }

    /**
     * 리소스 권한 리스트 조회
     * User Type이 Super-Admin인 경우 프로그램 하위에 속한 모든 리소스 메뉴를 조회한다.
     * 그외 권한이 있는 리소스 메뉴 리스트만 조회한다.
     */
    public ResGetResourceAuthDTO getResourceAuth(Long userId, Long programId, Long roleGroupAuthId) {

        // 프로그램 조회
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 롤 그룹 권한 조회
        RoleGroupAuth roleGroupAuth = roleGroupAuthRepository.findById(roleGroupAuthId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 권한 리스트 조회
        List<ResourceAuth> resourceAuthList = resourceAuthRepository.findByProgramAndRoleGroupAuth(program, roleGroupAuth);

        // 프로그램에 속해있는 메뉴 리스트 조회
        List<ResourceMenu> menuList = null;

        /**
         * 유저 Type 비교
         * Case 1. Super-Admin : 프로그램 하위 모든 리소스 메뉴 리스트 조회
         * Case 2. 그외 : 권한이 있는 리소스 메뉴 리스트 조회
         */
        if (user.getUserType().equals(UserType.SUPER_ADMIN)) {
            // SUPER_ADMIN은 인자로 받은 roleGroupAuthId로 권한을 조회했을때, 권한을 가지고 있지 않은
            // 메뉴 리스트도 내보내줘야 하기 때문에 프로그램으로 메뉴리스트를 조회한다.
            menuList = resourceMenuRepository.findByProgram(program);
        } else {
            // 그외 유저 타입은 인자로 받은 roleGroupAuthId로 권한을 조회했을때, 권한을 가지고 있는
            // 메뉴 리스트만 내보내줘야 하기 때문에, 위에서 조회한 권한 리스트에서 메뉴 값을 별도로 셋팅해서 내보낸다.
            menuList = new ArrayList<>();
            for(ResourceAuth ra : resourceAuthList) {
                menuList.add(ra.getResourceMenu());
            }
        }

        return new ResGetResourceAuthDTO(menuList,resourceAuthList);
    }
}
