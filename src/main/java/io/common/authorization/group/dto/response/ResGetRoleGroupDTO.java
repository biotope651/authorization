package io.common.authorization.group.dto.response;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.AnswerType;
import io.common.authorization.common.type.UserType;
import io.common.authorization.group.entity.RoleGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RoleGroup 리스트 조회용
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="롤 그룹 리스트")
    private List<GetRoleGroups> list = new ArrayList<>();

    public ResGetRoleGroupDTO(List<RoleGroup> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetRoleGroups(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetRoleGroups {

        @ApiModelProperty(value="롤 그룹 ID")
        private Long id;

        @ApiModelProperty(value="롤 그룹명")
        @NotNull
        private String roleGroupName;

        @ApiModelProperty(value="롤 그룹 상태")
        @NotNull
        private ActiveStatus roleGroupStatus;

        @ApiModelProperty(value="디폴트 그룹 Y/N")
        @NotNull
        private AnswerType isDefault;

        @ApiModelProperty(value="롤 유저 타입")
        @NotNull
        private UserType roleUserType;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;

        @ApiModelProperty(value="프로그램 ID")
        private Long programId;

        @ApiModelProperty(value="생성일")
        private LocalDateTime createDt;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;

        public GetRoleGroups(RoleGroup roleGroup) {
            this.id = roleGroup.getId();
            this.roleGroupName = roleGroup.getRoleGroupName();
            this.roleGroupStatus = roleGroup.getRoleGroupStatus();
            this.isDefault = roleGroup.getIsDefault();
            this.roleUserType = roleGroup.getRoleUserType();
            this.companyId = roleGroup.getCompany() == null ? null : roleGroup.getCompany().getId();
            this.programId = roleGroup.getProgram() == null ? null : roleGroup.getProgram().getId();
            this.createDt = roleGroup.getCreateDt();
            this.mngUserId = roleGroup.getMngUser() == null ? null : roleGroup.getMngUser().getId();
        }
    }
}
