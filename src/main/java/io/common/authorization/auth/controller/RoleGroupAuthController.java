package io.common.authorization.auth.controller;

import io.common.authorization.auth.dto.request.ReqRoleGroupAuthDTO;
import io.common.authorization.auth.dto.response.ResCreateRoleGroupAuthDTO;
import io.common.authorization.auth.dto.response.ResGetRoleGroupAuthDTO;
import io.common.authorization.auth.dto.response.ResUpdateRoleGroupAuthDTO;
import io.common.authorization.auth.service.RoleGroupAuthService;
import io.common.authorization.company.dto.response.ResUpdateCompanyDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/role-group")
public class RoleGroupAuthController {

    private final RoleGroupAuthService roleGroupAuthService;

    @ApiOperation(
            value = "롤 그룹 권한 생성",
            notes = "롤 그룹 권한 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 권한 ID", response = ResCreateRoleGroupAuthDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateRoleGroupAuthDTO createRoleGroupAuth(@Validated @RequestBody ReqRoleGroupAuthDTO.CreateRoleGroupAuthDTO reqRoleGroupAuthDTO) {

        long roleGroupAuthId = roleGroupAuthService.createRoleGroupAuth(reqRoleGroupAuthDTO);

        return new ResCreateRoleGroupAuthDTO(roleGroupAuthId);
    }

    /**
     * case 1) companyId를 인자로 받은 경우
     * roleGroupId 를 인자로 받아야 하고, company에 속해 있으면서
     * role_group에 속해있는 리스트와 속하지 않은 리스트를 리턴한다.
     * case 2) companyId를 인자로 받지 않은 경우
     * roleGroupId 를 인자로 받아야 하고, company에 속해 있지 않으면서
     * role_group에 속해있는 리스트와 속하지 않은 리스트를 리턴한다.
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(
            value = "롤 그룹 권한 리스트 조회",
            notes = "회사 ID, 롤 그룹 ID가 null이면 모든 롤 그룹 권한 리스트 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 권한 리스트", response = ResGetRoleGroupAuthDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupAuthDTO getRoleGroupAuth(@RequestParam(value = "companyId", required = false) Long companyId,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return roleGroupAuthService.getRoleGroupAuth(companyId, page, size);
    }

    @ApiOperation(
            value = "롤 그룹 권한 상세 정보 조회",
            notes = "롤 그룹 권한 상세 정보를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "롤 그룹 권한 ID", required = false, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "롤 그룹 권한 상세 정보", response = ResGetRoleGroupAuthDTO.GetRoleGroupAuth.class)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetRoleGroupAuthDTO.GetRoleGroupAuth getRoleGroupAuthInfo(@Validated @PathVariable("id") Long roleGroupAuthId) {

        return roleGroupAuthService.getRoleGroupAuthInfo(roleGroupAuthId);
    }

    @ApiOperation(
            value = "롤 그룹 권한 수정",
            notes = "롤 그룹 권한 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 ID", response = ResUpdateCompanyDTO.class)
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateRoleGroupAuthDTO updateRoleGroupAuth(@Validated @RequestBody ReqRoleGroupAuthDTO.UpdateRoleGroupAuthDTO reqRoleGroupAuthDTO) {

        Long roleGRoupAuthId = roleGroupAuthService.updateRoleGroupAuth(reqRoleGroupAuthDTO);

        return new ResUpdateRoleGroupAuthDTO(roleGRoupAuthId);
    }

}
