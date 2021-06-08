package io.common.authorization.resource.program.controller;

import io.common.authorization.resource.program.dto.request.ReqProgramDTO;
import io.common.authorization.resource.program.dto.response.ResCreateProgramDTO;
import io.common.authorization.resource.program.dto.response.ResGetProgramsDTO;
import io.common.authorization.resource.program.dto.response.ResUpdateProgramDTO;
import io.common.authorization.resource.program.service.ProgramService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource/program")
public class ProgramController {

    private final ProgramService programService;

    @ApiOperation(
            value = "Program 생성",
            notes = "Program 생성 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로그램 ID", response = ResCreateProgramDTO.class)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResCreateProgramDTO createProgram(@Validated @RequestBody ReqProgramDTO.CreateProgramDTO reqProgramDTO) {

        long programId = programService.createProgram(reqProgramDTO);

        return new ResCreateProgramDTO(programId);
    }

    @ApiOperation(
            value = "Program 리스트 조회",
            notes = "등록되어 있는 Program 리스트를 조회한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, defaultValue = "1", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = false, defaultValue = "10", dataType = "string", paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로그램 리스트", response = ResGetProgramsDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResGetProgramsDTO getProgramList(@RequestParam(value = "userId", required = false) Long userId,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

            return programService.getProgramList(userId, page, size);
    }

    @ApiOperation(
            value = "Program 내용 수정",
            notes = "Program 내용 수정 후 ID 값을 Return 한다."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로그램 ID", response = ResUpdateProgramDTO.class)
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResUpdateProgramDTO updateProgram(@Validated @RequestBody ReqProgramDTO.UpdateProgramDTO reqProgramDTO) {

        long programId = programService.updateProgram(reqProgramDTO);

        return new ResUpdateProgramDTO(programId);
    }

}