package io.common.authorization.resource.menu.controller;

import io.common.authorization.resource.menu.dto.request.ReqResourceMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResCreateResourceMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResGetDepthResourceMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResGetResourcesMenuDTO;
import io.common.authorization.resource.menu.dto.response.ResUpdateResourceMenuDTO;
import io.common.authorization.resource.menu.service.ResourceMenuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource/menu")
public class MenuController {

    private final ResourceMenuService resourceMenuService;

    @ApiOperation(
            value = "메뉴 생성",
            notes = "메뉴 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메뉴 ID", response = ResCreateResourceMenuDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateResourceMenuDTO createMenu(@Validated @RequestBody ReqResourceMenuDTO.CreateMenuDTO reqMenuDTO) {

         long menuId = resourceMenuService.createResourceMenu(reqMenuDTO);

        return new ResCreateResourceMenuDTO(menuId);
    }

    @ApiOperation(
            value = "Menu 리스트 조회",
            notes = "프로그램 하위에 등록되어 있는 메뉴 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "programId", value = "프로그램 ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메뉴 리스트", response = ResGetResourcesMenuDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetResourcesMenuDTO getMenuList(@RequestParam(value = "userId", required = false) Long userId,
                                              @RequestParam(value = "programId", required = true) long programId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return resourceMenuService.getResourceMenus(userId, programId, page, size);
    }

    @ApiOperation(
            value = "메뉴 수정",
            notes = "메뉴 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메뉴 ID", response = ResUpdateResourceMenuDTO.class)
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateResourceMenuDTO updateMenu(@Validated @RequestBody ReqResourceMenuDTO.UpdateMenuDTO reqMenuDTO) {

        long menuId = resourceMenuService.updateResourceMenu(reqMenuDTO);

        return new ResUpdateResourceMenuDTO(menuId);
    }

    @ApiOperation(
            value = "depth별 메뉴 리스트 조회",
            notes = "depth별 메뉴 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programId", value = "프로그램 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "menuLevel", value = "메뉴 레벨", required = true, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메뉴 리스트", response = ResGetDepthResourceMenuDTO.class)
    })
    @GetMapping("/depth")
    @ResponseStatus(HttpStatus.OK)
    public ResGetDepthResourceMenuDTO getDepthMenuList(@RequestParam(value = "programId", required = false) Long programId,
                                                         @RequestParam(value = "menuLevel", required = true) Integer menuLevel) {

        return resourceMenuService.getDepthResourceMenus(programId, menuLevel);
    }
}
