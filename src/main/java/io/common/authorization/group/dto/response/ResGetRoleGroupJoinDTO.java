package io.common.authorization.group.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupJoinDTO {

    @ApiModelProperty(value="롤 그룹 assigned")
    private ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO;

    @ApiModelProperty(value="롤 그룹 unassigned")
    private ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO;

    public ResGetRoleGroupJoinDTO(ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO, ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO) {
        this.resGetRoleGroupAssignedDTO = resGetRoleGroupAssignedDTO;
        this.resGetRoleGroupUnassignedDTO = resGetRoleGroupUnassignedDTO;
    }
}
