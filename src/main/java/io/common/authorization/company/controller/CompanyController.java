package io.common.authorization.company.controller;

import io.common.authorization.company.dto.request.ReqCompanyDTO;
import io.common.authorization.company.dto.response.ResCreateCompanyDTO;
import io.common.authorization.company.dto.response.ResGetCompanyDTO;
import io.common.authorization.company.dto.response.ResUpdateCompanyDTO;
import io.common.authorization.company.service.CompanyService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @ApiOperation(
            value = "회사 생성",
            notes = "회사 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 ID", response = ResCreateCompanyDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateCompanyDTO createCompany(@Validated @RequestBody ReqCompanyDTO.CreateCompanyDTO reqCompanyDTO) {

        long companyId = companyService.createCompany(reqCompanyDTO);

        return new ResCreateCompanyDTO(companyId);
    }

    @ApiOperation(
            value = "회사 리스트 조회",
            notes = "회사 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 리스트", response = ResGetCompanyDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetCompanyDTO getCompany(@RequestParam(value = "userId", required = false) Long userId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return companyService.getCompany(userId, page, size);
    }

    @ApiOperation(
            value = "회사 상세 정보 조회",
            notes = "회사 상세 정보를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "회사 ID", required = false, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 상세 정보", response = ResGetCompanyDTO.GetCompany.class)
    })
    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetCompanyDTO.GetCompany getCompanyInfo(@Validated @PathVariable("companyId") Long companyId) {

        return companyService.getCompanyInfo(companyId);
    }


    @ApiOperation(
            value = "회사 수정",
            notes = "회사 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 ID", response = ResUpdateCompanyDTO.class)
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateCompanyDTO updateCompany(@Validated @RequestBody ReqCompanyDTO.UpdateCompanyDTO reqCompanyDTO) {

        long companyId = companyService.updateCompany(reqCompanyDTO);

        return new ResUpdateCompanyDTO(companyId);
    }
}
