package io.common.authorization.company.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateCompanyUserDTO {
    @ApiModelProperty(value="회사 유저 ID")
    @NotNull
    private Long companyUserId;
}
