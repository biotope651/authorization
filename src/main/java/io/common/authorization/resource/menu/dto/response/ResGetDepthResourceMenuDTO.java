package io.common.authorization.resource.menu.dto.response;

import io.common.authorization.resource.menu.entity.ResourceMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetDepthResourceMenuDTO {

    @ApiModelProperty(value="메뉴 리스트")
    private List<ResGetResourcesMenuDTO.GetMenus> list = new ArrayList<>();

    public ResGetDepthResourceMenuDTO(List<ResourceMenu> list) {
        this.list = list.stream().map(o -> new ResGetResourcesMenuDTO.GetMenus(o)).collect(Collectors.toList());
    }
}
