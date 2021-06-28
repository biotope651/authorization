package io.common.authorization.group.controller;

import io.common.authorization.group.dto.request.ReqDeleteRoleGroupJoinDTO;
import io.common.authorization.group.dto.request.ReqRoleGroupJoinDTO;
import io.common.authorization.group.dto.response.ResCreateRoleGroupJoinDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupDTO;
import io.common.authorization.group.dto.response.ResGetRoleGroupJoinDTO;
import io.common.authorization.group.service.RoleGroupJoinService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/role-group-join")
public class RoleGroupJoinController {

    private final RoleGroupJoinService roleGroupJoinService;

    @ApiOperation(
            value = "롤 그룹에 그룹 권한 맵핑",
            notes = "롤 그룹에 그룹 권한을 맵핑 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 조인 ID", response = Boolean.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean createRoleGroupJoin(@Validated @RequestBody ReqRoleGroupJoinDTO reqRoleGroupJoinDTO) {

        return roleGroupJoinService.createRoleGroupJoin(reqRoleGroupJoinDTO);
    }

    @ApiOperation(
            value = "롤 그룹 맵핑 리스트 조회",
            notes = "롤 그룹 맵핑 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleGroupId", value = "롤 그룹 ID", required = true, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 리스트", response = ResGetRoleGroupDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupJoinDTO getRoleGroupJoin(@RequestParam(value = "companyId", required = false) Long companyId,
                                                   @RequestParam(value = "roleGroupId",  required = true) Long roleGroupId) {

        return roleGroupJoinService.getRoleGroupJoin(companyId, roleGroupId);
    }


    @ApiOperation(
            value = "롤 그룹, 롤 그룹 권한 맵핑 해제",
            notes = "롤 그룹, 롤 그룹 권한 맵핑을 해제한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공/실패", response = Boolean.class)
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteRoleGroupAuthMapping(@Validated @RequestBody ReqDeleteRoleGroupJoinDTO reqDeleteRoleGroupJoinDTO) {
        return roleGroupJoinService.deleteRoleGroupJoin(reqDeleteRoleGroupJoinDTO);
    }

}
