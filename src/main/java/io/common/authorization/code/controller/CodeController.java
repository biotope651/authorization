package io.common.authorization.code.controller;

import io.common.authorization.code.controller.dto.ResCodeDTO;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.BusinessType;
import io.common.authorization.common.type.Depth;
import io.common.authorization.common.type.UserType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    @ApiOperation(
            value = "활성 / 비활성 코드",
            notes = "활성 / 비활성 코드"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Active Status", response = ActiveStatus.class)
    })
    @GetMapping("/active-status")
    @ResponseStatus(HttpStatus.OK)
    public ResCodeDTO getActiveStatus() {
        return new ResCodeDTO(ActiveStatus.getEnumToListMap());
    }

    @ApiOperation(
            value = "회사 구분",
            notes = "회사 구분"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Business Type", response = BusinessType.class)
    })
    @GetMapping("/business-type")
    @ResponseStatus(HttpStatus.OK)
    public ResCodeDTO getBusinessType() {
        return new ResCodeDTO(BusinessType.getEnumToListMap());
    }

    @ApiOperation(
            value = "메뉴 레벨 구분",
            notes = "메뉴 레벨 구분"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Business Type", response = Depth.class)
    })
    @GetMapping("/menu-level")
    @ResponseStatus(HttpStatus.OK)
    public ResCodeDTO getDepthType() {
        return new ResCodeDTO(Depth.getEnumToListMap());
    }

    @ApiOperation(
            value = "유저 타입",
            notes = "유저 타입"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Business Type", response = UserType.class)
    })
    @GetMapping("/user-type")
    @ResponseStatus(HttpStatus.OK)
    public ResCodeDTO getUserType() {
        return new ResCodeDTO(UserType.getEnumToListMap());
    }

}
