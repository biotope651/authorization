package io.common.authorization.auth.dto.response;

import io.common.authorization.auth.entity.ResourceAuth;
import io.common.authorization.common.type.AnswerType;
import io.common.authorization.resource.menu.dto.response.ResGetResourcesMenuDTO;
import io.common.authorization.resource.menu.entity.ResourceMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Resource 권한 조회용
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetResourceAuthDTO {

    @ApiModelProperty(value="메뉴 리스트")
    private List<ResGetResourcesMenuDTO.GetMenus> resourceMenuList = new ArrayList<>();

    @ApiModelProperty(value="리소스 메뉴 권한 리스트")
    private List<GetResourceAuth> resourceAuthList = new ArrayList<>();

    public ResGetResourceAuthDTO(List<ResourceMenu> menuList, List<ResourceAuth> authList) {
        this.resourceMenuList = menuList.stream().map(o -> new ResGetResourcesMenuDTO.GetMenus(o)).collect(Collectors.toList());
        this.resourceAuthList = authList.stream().map(o -> new GetResourceAuth(o)).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetResourceAuth {

        @ApiModelProperty(value="리소스 권한 ID")
        private Long id;

        @ApiModelProperty(value="리소스 메뉴 ID")
        private Long resourceMenuId;

        @ApiModelProperty(value="프로그램 ID")
        private Long programId;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        private Long roleGroupAuthId;

        @ApiModelProperty(value="조회 권한")
        private AnswerType isRead;

        @ApiModelProperty(value="수정 권한")
        private AnswerType isModify;

        @ApiModelProperty(value="생성일")
        private LocalDateTime createDt;

        @ApiModelProperty(value="수정일")
        private LocalDateTime updateDt;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;

        public GetResourceAuth(ResourceAuth resourceAuth) {
            this.id = resourceAuth.getId();
            this.resourceMenuId = resourceAuth.getResourceMenu() == null ? null : resourceAuth.getResourceMenu().getId();
            this.programId = resourceAuth.getProgram() == null ? null : resourceAuth.getProgram().getId();
            this.roleGroupAuthId = resourceAuth.getRoleGroupAuth() == null ? null : resourceAuth.getRoleGroupAuth().getId();
            this.isRead = resourceAuth.getIsRead();
            this.isModify = resourceAuth.getIsModify();
            this.createDt = resourceAuth.getCreateDt();
            this.updateDt = resourceAuth.getUpdateDt();
            this.mngUserId = resourceAuth.getMngUser() == null ? null : resourceAuth.getMngUser().getId();
        }
    }
}
