package io.common.authorization.group.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqDeleteRoleGroupJoinDTO {

    @ApiModelProperty(value="롤 그룹 ID")
    @NotNull
    private Long roleGroupId;

    @ApiModelProperty(value="회사 ID")
    private Long companyId;

    @ApiModelProperty(value="롤 그룹 조인 ID 리스트")
    private List<DeleteRoleGroupJoinDTO> roleGroupJoinList;

    // delete
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteRoleGroupJoinDTO {

        @ApiModelProperty(value="롤 그룹 조인 ID", required = true)
        private Long id;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        @NotNull
        private Long roleGroupAuthId;

    }
}
