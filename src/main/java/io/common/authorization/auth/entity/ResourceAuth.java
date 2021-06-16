package io.common.authorization.auth.entity;

import io.common.authorization.auth.dto.request.ReqResourceAuthDTO;
import io.common.authorization.auth.dto.request.ReqResourceAuthListDTO;
import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.AnswerType;
import io.common.authorization.common.type.converter.AnswerTypeConverter;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.resource.menu.entity.ResourceMenu;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;

/**
 * Resource Menu 권한
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ResourceAuth extends JpaEntityDateAudity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Resource menu ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resource_menu_id")
    private ResourceMenu resourceMenu;

    // Program ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="program_id")
    private Program program;

    // Role Group Auth ID
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="role_group_auth_id")
    private RoleGroupAuth roleGroupAuth;

    // 접근권한
    @Convert(converter = AnswerTypeConverter.class)
    private AnswerType isRead;

    // 수정권한
    @Convert(converter = AnswerTypeConverter.class)
    private AnswerType isModify;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    // 신규생성
    public ResourceAuth(ReqResourceAuthListDTO.CreateResourceAuthListDTO reqResourceAuthDTO, Program program, ResourceMenu resourceMenu, RoleGroupAuth roleGroupAuth, User user) {
        if(reqResourceAuthDTO.getId() != null) {
            this.id = reqResourceAuthDTO.getId();
        }
        this.resourceMenu = resourceMenu;
        this.program = program;
        this.isRead = reqResourceAuthDTO.getIsRead();
        this.isModify = reqResourceAuthDTO.getIsModify();
        this.mngUser = user;
//        setRoleGroupAuth(roleGroupAuth);
    }

    // 수정된 내용 셋팅
    public void updateResourceAuth(ReqResourceAuthDTO.UpdateResourceAuthDTO reqResourceAuthDTO) {
        this.isRead = reqResourceAuthDTO.getIsRead();
        this.isModify = reqResourceAuthDTO.getIsModify();
    }

    // RoleGroupAuth 셋팅 , 양방향 관계 맵핑
    public void setRoleGroupAuth(RoleGroupAuth roleGroupAuth) {
        this.roleGroupAuth = roleGroupAuth;
        roleGroupAuth.getResourceAuths().add(this);
    }
}
