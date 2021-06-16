package io.common.authorization.user.controller;

import io.common.authorization.common.type.UserType;
import io.common.authorization.user.dto.request.ReqUserDTO;
import io.common.authorization.user.dto.response.ResCreateUserDTO;
import io.common.authorization.user.dto.response.ResGetUsersDTO;
import io.common.authorization.user.dto.response.ResUpdateUserDTO;
import io.common.authorization.user.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(
            value = "유저 생성",
            notes = "유저 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 ID", response = ResCreateUserDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateUserDTO createUser(@Validated @RequestBody ReqUserDTO.CreateUserDTO reqUserDTO) {

        /**
         * 관리자, 유저, 비회원 모두 접근할 수 있는 API 기능..
         * 회사가 생성되어 있다는 가정하에 진행한다.
         */
        // TODO : 기업회원 가입 로직 고려할 것!! (회사 생성 -> 유저 생성 -> 회사/유저 맵핑)
        long userId = userService.createUser(reqUserDTO);

        return new ResCreateUserDTO(userId);
    }

    @ApiOperation(
            value = "유저 리스트 조회",
            notes = "유저 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userType", value = "유저 타입", required = false, defaultValue = "Nomal", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "회사 소속 유저 리스트", response = ResGetUsersDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetUsersDTO getUserList(@RequestParam(value = "userType", required = false, defaultValue = "Nomal") String userType,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return userService.getUsers(UserType.enumOf(userType), page, size);
    }

        @ApiOperation(
            value = "유저 상세 정보 조회",
            notes = "유저 상세 정보를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저 ID", required = false, dataType = "string")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 상세 정보", response = ResGetUsersDTO.GetUser.class)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetUsersDTO.GetUser getUserInfo(@Validated @PathVariable("id") Long userId) {

        return userService.getUserInfo(userId);
    }

    @ApiOperation(
            value = "유저 수정",
            notes = "유저 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 ID", response = ResUpdateUserDTO.class)
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateUserDTO updateUser(@Validated @RequestBody ReqUserDTO.UpdateUserDTO reqUserDTO) {

        long userId = userService.updateUser(reqUserDTO);

        return new ResUpdateUserDTO(userId);
    }

    @ApiOperation(
            value = "유저 상태 변경",
            notes = "유저 상태 변경 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "유저 ID", response = ResUpdateUserDTO.class)
    })
    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateUserDTO updateUserStatus(@Validated @RequestBody ReqUserDTO.UpdateUserStatusDTO reqUserDTO) {

        long userId = userService.updateUserStatus(reqUserDTO);

        return new ResUpdateUserDTO(userId);
    }

}
