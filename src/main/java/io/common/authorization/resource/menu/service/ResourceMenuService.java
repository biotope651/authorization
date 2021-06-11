package io.common.authorization.resource.menu.service;

import io.common.authorization.common.type.Depth;
import io.common.authorization.common.util.PageRequest;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.resource.menu.dto.request.ReqResourceMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResGetDepthResourceMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResGetResourcesMenuDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResourceMenuService {

    private final ResourceMenuRepository resourceMenuRepository;
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    /**
     * 리소스 메뉴 생성
     * @param reqResourceMenuDTO
     * @return
     */
    @Transactional
    public Long createResourceMenu(ReqResourceMenuDTO.CreateMenuDTO reqResourceMenuDTO) {

        ResourceMenu resourceMenu = modelMapper.map(reqResourceMenuDTO, new TypeToken<ResourceMenu>() {}.getType());

        log.debug("[param] programId : {}", reqResourceMenuDTO.getProgramId());

        // Program 셋팅
        Program program = programRepository.findById(
                reqResourceMenuDTO.getProgramId()
        ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        resourceMenu.setProgram(program);

        // Parent ResourceMenu 셋팅
        if (reqResourceMenuDTO.getParentMenuId() != null) {
            ResourceMenu parent = resourceMenuRepository.findById(
                    reqResourceMenuDTO.getParentMenuId()
            ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceMenu.setParent(parent);
        }

        // 마지막 수정 유저
        if (reqResourceMenuDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqResourceMenuDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceMenu.setMngUser(user);
        }

        resourceMenuRepository.save(resourceMenu);

        return resourceMenu.getId();

    }

    /**
     * 메뉴 관리용 리스트 조회
     * @param programId
     * @param page
     * @param size
     * @return
     */
    public ResGetResourcesMenuDTO getResourceMenus(Long userId, long programId, int page, int size) {

        // TODO - User 권한 조회해서 최고관리자만 Menu 리스트를 조회한다.
//        if(최고관리자가 아니면 권한없음 에러 발생!!) {
//            throw new ErrorException(ErrorCode.FORBIDDEN_ERROR);
//        }

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC);
        Page<ResourceMenu> menuList = resourceMenuRepository.findByProgram(program, pageRequest.of("createDt"));
        return new ResGetResourcesMenuDTO(menuList.getContent(), page, size, menuList.getTotalPages());
    }

    /**
     * 메뉴 수정
     * @param reqResourceMenuDTO
     * @return
     */
    @Transactional
    public Long updateResourceMenu(ReqResourceMenuDTO.UpdateMenuDTO reqResourceMenuDTO) {

        ResourceMenu resourceMenu = resourceMenuRepository.findById(reqResourceMenuDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // Program 셋팅
        Program program = programRepository.findById(
                resourceMenu.getProgram().getId()
        ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        resourceMenu.setProgram(program);

        // Parent ResourceMenu 셋팅
        ResourceMenu parent = null;

        // 상위 메뉴 ID가 입력된 경우
        if(reqResourceMenuDTO.getParentMenuId() != null) {
            parent = resourceMenuRepository.findById(
                    reqResourceMenuDTO.getParentMenuId()
            ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceMenu.setParent(parent);
        }

        resourceMenu.updateResourceMenu(reqResourceMenuDTO);

        // 마지막 수정 유저
        if (reqResourceMenuDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqResourceMenuDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            resourceMenu.setMngUser(user);
        }

        return resourceMenu.getId();
    }

    /**
     * 메뉴 레벨별 리스트 조회
     * @param menuLevel
     * @return
     */
    public ResGetDepthResourceMenuDTO getDepthResourceMenus(Long programId, Integer menuLevel) {
        // Program 셋팅
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        List<ResourceMenu> menuList = resourceMenuRepository.findByProgramAndMenuLevelOrderByMenuName(program, Depth.enumOf(menuLevel));
        return new ResGetDepthResourceMenuDTO(menuList);
    }
}
