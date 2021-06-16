package io.common.authorization.group.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupJoinDTO {

    @ApiModelProperty(value="롤 그룹 권한 리스트")
    private List<ResGetRoleGroupJoinDTO.ResRoleGroupJoinWrapperDTO> list = new ArrayList<>();

    public ResGetRoleGroupJoinDTO(ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO, ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO) {
        this.list.addAll(resGetRoleGroupAssignedDTO.getList());
        this.list.addAll(resGetRoleGroupUnassignedDTO.getList());
    }

//    @ApiModelProperty(value="롤 그룹 assigned")
//    private ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO;
//
//    @ApiModelProperty(value="롤 그룹 unassigned")
//    private ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO;
//
//    public ResGetRoleGroupJoinDTO(ResGetRoleGroupAssignedDTO resGetRoleGroupAssignedDTO, ResGetRoleGroupUnassignedDTO resGetRoleGroupUnassignedDTO) {
//        this.resGetRoleGroupAssignedDTO = resGetRoleGroupAssignedDTO;
//        this.resGetRoleGroupUnassignedDTO = resGetRoleGroupUnassignedDTO;
//    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ResRoleGroupJoinWrapperDTO {

    }
}
