package io.common.authorization.auth.controller;

import io.common.authorization.auth.dto.request.ReqResourceAuthDTO;
import io.common.authorization.auth.dto.request.ReqResourceAuthListDTO;
import io.common.authorization.auth.dto.request.ReqRoleGroupAuthDTO;
import io.common.authorization.auth.dto.response.*;
import io.common.authorization.auth.service.ResourceAuthService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/resource")
public class ResourceAuthController {

    private final ResourceAuthService resourceAuthService;

    @ApiOperation(
            value = "리소스 권한 생성,수정",
            notes = "리소스 권한을 생성/수정 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "결과 값", response = Boolean.class)
    })
    @PostMapping("/array")
    @ResponseStatus(HttpStatus.OK)
    public boolean createResourceAuthList(@Validated @RequestBody ReqResourceAuthListDTO reqResourceAuthDTO) {

        return resourceAuthService.createResourceAuthList(reqResourceAuthDTO);
    }

//    @ApiOperation(
//            value = "리소스 권한 생성",
//            notes = "리소스 권한을 생성한다."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "리소스 권한 ID", response = ResCreateResourceAuthDTO.class)
//    })
//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ResCreateResourceAuthDTO createResourceAuth(@Validated @RequestBody ReqResourceAuthDTO.CreateResourceAuthDTO reqResourceAuthDTO) {
//
//        long resourceAuthId = resourceAuthService.createResourceAuth(reqResourceAuthDTO);
//
//        return new ResCreateResourceAuthDTO(resourceAuthId);
//    }

    @ApiOperation(
            value = "메뉴 리스트, 리소스 권한 리스트 조회",
            notes = "메뉴 리스트, 리소스 권한 리스트 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "programId", value = "프로그램 ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleGroupAuthId", value = "롤 그룹 권한 ID", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "메뉴, 리소스 권한 리스트", response = ResGetResourceAuthDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetResourceAuthDTO getRoleGroupAuth(@RequestParam(value = "userId", required = true) Long userId,
                                                  @RequestParam(value = "programId", required = true) Long programId,
                                                  @RequestParam(value = "roleGroupAuthId", required = true) Long roleGroupAuthId) {

        return resourceAuthService.getResourceAuth(userId, programId, roleGroupAuthId);
    }

//    @ApiOperation(
//            value = "리소스 권한 수정",
//            notes = "리소스 권한을 수정한다."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "리소스 권한 ID", response = ResUpdateResourceAuthDTO.class)
//    })
//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ResUpdateResourceAuthDTO updateResourceAuth(@Validated @RequestBody ReqResourceAuthDTO.UpdateResourceAuthDTO reqResourceAuthDTO) {
//
//        long resourceAuthId = resourceAuthService.updateResourceAuth(reqResourceAuthDTO);
//
//        return new ResUpdateResourceAuthDTO(resourceAuthId);
//    }
}
