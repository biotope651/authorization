package io.common.authorization.group.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResUpdateRoleGroupDTO {
    @ApiModelProperty(value="롤 그룹 ID")
    @NotNull
    private Long id;
}
