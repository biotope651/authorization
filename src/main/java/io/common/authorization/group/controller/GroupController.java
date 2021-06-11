package io.common.authorization.group.controller;

import io.common.authorization.company.dto.request.ReqCompanyDTO;
import io.common.authorization.company.dto.response.ResCreateCompanyDTO;
import io.common.authorization.company.dto.response.ResGetCompanyDTO;
import io.common.authorization.company.dto.response.ResUpdateCompanyDTO;
import io.common.authorization.group.dto.request.ReqRoleGroupDTO;
import io.common.authorization.group.dto.response.ResCreateRoleGroupDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupSelectboxDTO;
import io.common.authorization.group.service.RoleGroupService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/role-group")
public class GroupController {

    private final RoleGroupService roleGroupService;

    @ApiOperation(
            value = "롤 그룹 생성",
            notes = "롤 그룹 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 ID", response = ResCreateRoleGroupDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateRoleGroupDTO createRoleGroup(@Validated @RequestBody ReqRoleGroupDTO.CreateRoleGroupDTO reqRoleGroupDTO) {

        long roleGroupId = roleGroupService.createRoleGroup(reqRoleGroupDTO);

        return new ResCreateRoleGroupDTO(roleGroupId);
    }

    @ApiOperation(
            value = "롤 그룹 리스트 조회",
            notes = "롤 그룹 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 리스트", response = ResGetRoleGroupDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupDTO getRoleGroups(@RequestParam(value = "companyId", required = false) Long companyId,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return roleGroupService.getRoleGroups(companyId, page, size);
    }

    @ApiOperation(
            value = "롤 그룹 상세 정보 조회",
            notes = "롤 그룹 상세 정보를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "롤 그룹 ID", required = false, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 상세 정보", response = ResGetRoleGroupDTO.GetRoleGroups.class)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupDTO.GetRoleGroups getRoleGroupInfo(@Validated @PathVariable("id") Long roleGroupId) {

        return roleGroupService.getRoleGroupInfo(roleGroupId);
    }


    @ApiOperation(
            value = "롤 그룹 수정",
            notes = "롤 그룹 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 ID", response = ReqRoleGroupDTO.UpdateRoleGroupDTO.class)
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ReqRoleGroupDTO.UpdateRoleGroupDTO updateRoleGroup(@Validated @RequestBody ReqRoleGroupDTO.UpdateRoleGroupDTO reqRoleGroupDTO) {

        long roleGroupId = roleGroupService.updateRoleGroup(reqRoleGroupDTO);

        return new ReqRoleGroupDTO.UpdateRoleGroupDTO(roleGroupId);
    }

    @ApiOperation(
            value = "롤 그룹 리스트 조회 - selectbox",
            notes = "롤 그룹 리스트를 조회한다. - selectbox"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = false, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 리스트", response = ResGetRoleGroupSelectboxDTO.class)
    })
    @GetMapping("/selectbox")
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupSelectboxDTO getRoleGroupSelectbox(@RequestParam(value = "companyId", required = false) Long companyId) {

        return roleGroupService.getRoleGroupSelectbox(companyId);
    }
}
