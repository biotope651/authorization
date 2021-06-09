package io.common.authorization.resource.menu.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateResourceMenuDTO {
    @ApiModelProperty(value="메뉴 ID")
    @NotNull
    private Long id;
}
