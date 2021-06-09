package io.common.authorization.user.service;

import io.common.authorization.common.type.UserType;
import io.common.authorization.common.util.PageRequest;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.group.repository.RoleGroupRepository;
import io.common.authorization.user.dto.request.ReqUserDTO;
import io.common.authorization.user.dto.response.ResGetUsersDTO;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleGroupRepository roleGroupRepository;

    private final ModelMapper modelMapper;

    /**
     * 유저 생성
     * @return
     */
    @Transactional
    public Long createUser(ReqUserDTO.CreateUserDTO reqUserDTO) {

        User user = modelMapper.map(reqUserDTO, new TypeToken<User>() {}.getType());

        // RoleGroup 설정
        if (reqUserDTO.getRoleGroupId() != null) {
            RoleGroup roleGroup = roleGroupRepository.findById(
                    reqUserDTO.getRoleGroupId()
            ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            user.setRoleGroup(roleGroup);
        }

        // 마지막 수정 유저
        if(reqUserDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqUserDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            user.setMngUser(mngUser);
        }

        userRepository.save(user);

        return user.getId();
    }

    /**
     * 관리자 전용 - 유저 리스트 조회
     * @param userType
     * @param page
     * @param size
     */
    public ResGetUsersDTO getUsers(UserType userType, int page, int size) {

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC);
        Page<User> userList = userRepository.findByUserType(userType,pageRequest.of("createDt"));

        return new ResGetUsersDTO(userList.getContent(), page, size, userList.getTotalPages());
    }

    /**
     * 유저 상세 정보 조회
     * @param userId
     */
    public ResGetUsersDTO.GetUser getUserInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        return new ResGetUsersDTO.GetUser(user);
    }

    /**
     * 유저 정보 수정
     * @return
     */
    @Transactional
    public Long updateUser(ReqUserDTO.UpdateUserDTO reqUserDTO) {

        User user = userRepository.findById(reqUserDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // RoleGroup 셋팅
        if (reqUserDTO.getRoleGroupId() != null) {
            RoleGroup roleGroup = roleGroupRepository.findById(
                    reqUserDTO.getRoleGroupId()
            ).orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            user.setRoleGroup(roleGroup);
        }

        user.updateUserInfo(reqUserDTO);

        // 마지막 수정 유저
        if(reqUserDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqUserDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            user.setMngUser(mngUser);
        }

        userRepository.save(user);

        return user.getId();
    }

    /**
     * 유저 상태 변경 (일반유저, 회사유저)
     * @param reqUserDTO
     * @return
     */
    @Transactional
    public Long updateUserStatus(ReqUserDTO.UpdateUserStatusDTO reqUserDTO) {
        User user = userRepository.findById(reqUserDTO.getId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        // 사용자 상태 변경
        user.setUserStatus(reqUserDTO.getUserStatus());
        // 계정 상태 변경
        user.setAccountStatus(reqUserDTO.getAccountStatus());

        // 마지막 수정 유저
        if (reqUserDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqUserDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            user.setMngUser(mngUser);
        }

        userRepository.save(user);

        return user.getId();
    }
}
