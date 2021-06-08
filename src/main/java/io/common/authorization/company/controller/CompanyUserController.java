package io.common.authorization.company.controller;

import io.common.authorization.company.dto.request.ReqCompanyUserDTO;
import io.common.authorization.company.dto.response.ResCreateCompanyUserDTO;
import io.common.authorization.company.dto.response.ResGetCompanyUsersDTO;
import io.common.authorization.company.service.CompanyUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company/user")
public class CompanyUserController {

    private final CompanyUserService companyUserService;

    @ApiOperation(
            value = "회사 유저 생성",
            notes = "회사 하위에 유저를 추가하고 companyUserId 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 유저 ID", response = ResCreateCompanyUserDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateCompanyUserDTO createCompany(@Validated @RequestBody ReqCompanyUserDTO.CreateCompanyUserDTO reqCompanyUserDTO) {

        long companyUserId = companyUserService.createCompanyUser(reqCompanyUserDTO);

        return new ResCreateCompanyUserDTO(companyUserId);
    }

    @ApiOperation(
            value = "회사 소속 유저 리스트 조회",
            notes = "회사 소속 유저 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 소속 유저 리스트", response = ResGetCompanyUsersDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetCompanyUsersDTO getCompanyUserList(@RequestParam(value = "companyId", required = true) Long companyId,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return companyUserService.getCompanyUsers(companyId, page, size);
    }

    @ApiOperation(
            value = "회사 소속 유저 상세 정보 조회",
            notes = "회사 소속 유저 상세 정보를 조회 한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 소속 유저 상세 정보", response = ResGetCompanyUsersDTO.GetCompanyUsers.class)
    })
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetCompanyUsersDTO.GetCompanyUsers getCompanyUserInfo(@Validated @PathVariable(value = "userId", required = true) Long userId,
                                                    @RequestParam(value = "companyId", required = true) Long companyId) {

        return companyUserService.getCompanyUserInfo(userId,companyId);
    }
}
