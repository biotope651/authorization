package io.common.authorization.resource.program.service;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.util.PageRequest;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.resource.program.dto.request.ReqProgramDTO;
import io.common.authorization.resource.program.dto.response.ResGetProgramSelectBoxDTO;
import io.common.authorization.resource.program.dto.response.ResGetProgramsDTO;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.resource.program.repository.ProgramRepository;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    /**
     * Program 생성
     * @param reqProgramDTO
     * @return
     */
    @Transactional
    public Long createProgram(ReqProgramDTO.CreateProgramDTO reqProgramDTO) {

        Program program = modelMapper.map(reqProgramDTO, new TypeToken<Program>() {}.getType());

        // 마지막 수정 유저
        if (reqProgramDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqProgramDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            program.setMngUser(user);
        }

        programRepository.save(program);

        return program.getId();
    }

    /**
     * 프로그램 리스트 조회
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public ResGetProgramsDTO getProgramList(Long userId, int page, int size) {

        // TODO - User 권한 조회해서 최고관리자만 Program 리스트를 조회한다.
//        if(최고관리자가 아니면 권한없음 에러 발생!!) {
//            throw new ErrorException(ErrorCode.FORBIDDEN_ERROR);
//        }

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC);
        Page<Program> programList = programRepository.findAll(pageRequest.of("createDt"));

        return new ResGetProgramsDTO(programList.getContent(), page, size, programList.getTotalPages());
    }

    /**
     * 프로그램 수정
     */
    @Transactional
    public Long updateProgram(ReqProgramDTO.UpdateProgramDTO reqProgramDTO) {

        Program program = programRepository.findById(reqProgramDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 변경 값 셋팅
        program.updateProgram(reqProgramDTO);

        // 마지막 수정 유저
        if (reqProgramDTO.getMngUserId() != null) {
            User user = userRepository.findById(reqProgramDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            program.setMngUser(user);
        }

        return program.getId();
    }

    /**
     * 프로그램 리스트 조회
     * @return
     */
    public ResGetProgramSelectBoxDTO getProgramSelectBox() {

        List<Program> programList = programRepository.findByProgramStatusOrderById(ActiveStatus.ACTIVE);

        return new ResGetProgramSelectBoxDTO(programList);
    }
}
