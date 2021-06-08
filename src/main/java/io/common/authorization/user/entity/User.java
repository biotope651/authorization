package io.common.authorization.user.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.EntryType;
import io.common.authorization.common.type.UserStatus;
import io.common.authorization.common.type.UserType;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.common.type.converter.EntryTypeConverter;
import io.common.authorization.common.type.converter.UserStatusConverter;
import io.common.authorization.common.type.converter.UserTypeConverter;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.user.dto.request.ReqUserDTO;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
@DynamicUpdate
public class User extends JpaEntityDateAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_group_id")
    private RoleGroup roleGroup;

    // 로그인 ID
    private String loginId;

    // Password
    private String password;

    // 유저명
    private String name;

    // Email
    private String email;

    // Mobile
    private String mobile;

    // 연락처
    private String tel;

    // 사용자 상태
    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus;

    // 계정 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus accountStatus;

    // 유저 Type
    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    // 가입 방법
    @Convert(converter = EntryTypeConverter.class)
    private EntryType entryType;

    // 비고
    private String etc;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    // User Update 정보 셋팅
    public void updateUserInfo(ReqUserDTO.UpdateUserDTO reqUserDTO) {

        if(isNotBlank(reqUserDTO.getLoginId())) {
            this.loginId = reqUserDTO.getLoginId();
        }

        if(isNotBlank(reqUserDTO.getPassword())) {
            this.password = reqUserDTO.getPassword();
        }

        if(isNotBlank(reqUserDTO.getName())) {
            this.name = reqUserDTO.getName();
        }

        if(isNotBlank(reqUserDTO.getEmail())) {
            this.email = reqUserDTO.getEmail();
        }

        if(isNotBlank(reqUserDTO.getMobile())) {
            this.mobile = reqUserDTO.getMobile();
        }

        if(isNotBlank(reqUserDTO.getTel())) {
            this.tel = reqUserDTO.getTel();
        }

        if(isNotBlank(reqUserDTO.getUserStatus().getValue())) {
            this.userStatus = reqUserDTO.getUserStatus();
        }

        if(isNotBlank(reqUserDTO.getAccountStatus().getValue())) {
            this.accountStatus = reqUserDTO.getAccountStatus();
        }

        if(isNotBlank(reqUserDTO.getUserType().getValue())) {
            this.userType = reqUserDTO.getUserType();
        }

        if(isNotBlank(reqUserDTO.getEntryType().getValue())) {
            this.entryType = reqUserDTO.getEntryType();
        }

        if(isNotBlank(reqUserDTO.getEtc())) {
            this.etc = reqUserDTO.getEtc();
        }
    }

    private boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }
}
