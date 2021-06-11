package io.common.authorization.group.dto.response;

import io.common.authorization.group.entity.RoleGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RoleGroup selectbox 리스트 조회용
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupSelectboxDTO {

    @ApiModelProperty(value="롤 그룹 리스트")
    private List<GetRoleGroups> list = new ArrayList<>();

    public ResGetRoleGroupSelectboxDTO(List<RoleGroup> list) {
        this.list = list.stream().map(o -> new GetRoleGroups(o)).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetRoleGroups {

        @ApiModelProperty(value="롤 그룹 ID")
        private Long id;

        @ApiModelProperty(value="롤 그룹명")
        @NotNull
        private String roleGroupName;

        public GetRoleGroups(RoleGroup roleGroup) {
            this.id = roleGroup.getId();
            this.roleGroupName = roleGroup.getRoleGroupName();
        }
    }
}
