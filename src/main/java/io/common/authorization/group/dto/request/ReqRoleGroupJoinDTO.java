package io.common.authorization.group.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReqRoleGroupJoinDTO {

    @ApiModelProperty(value="롤 그룹 ID")
    @NotNull
    private Long roleGroupId;

    @ApiModelProperty(value="회사 ID")
    private Long companyId;

    @ApiModelProperty(value="롤 그룹 조인 ID 리스트")
    private List<CreateRoleGroupJoinDTO> roleGroupJoinList;

    // create
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRoleGroupJoinDTO {

        @ApiModelProperty(value="롤 그룹 조인 ID", required = true)
        private Long id;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        @NotNull
        private Long roleGroupAuthId;

    }
}
