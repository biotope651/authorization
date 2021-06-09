package io.common.authorization.resource.menu.dto.response;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.Depth;
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
public class ResGetResourcesMenuDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="메뉴 리스트")
    private List<GetMenus> list = new ArrayList<>();

    public ResGetResourcesMenuDTO(List<ResourceMenu> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetMenus(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetMenus {

        @ApiModelProperty(value="메뉴 ID", required = true)
        private Long id;

        @ApiModelProperty(value="프로그램 ID", required = true)
        private Long programId;

        @ApiModelProperty(value="메뉴명", required = true)
        private String menuName;

        @ApiModelProperty(value="메뉴 URL", required = true)
        private String menuUrl;

        @ApiModelProperty(value="메뉴 활성 상태", required = true)
        private ActiveStatus menuStatus;

        @ApiModelProperty(value="메뉴 레벨", required = true)
        private Depth menuLevel;

        @ApiModelProperty(value="순서", required = true)
        private int sort;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="상위 메뉴 ID")
        private Long parent_menu_id;

        public GetMenus(ResourceMenu resourceMenu) {
            this.id = resourceMenu.getId();
            this.programId = resourceMenu.getProgram() == null ? null : resourceMenu.getProgram().getId();
            this.menuName = resourceMenu.getMenuName();
            this.menuUrl = resourceMenu.getMenuUrl();
            this.menuStatus = resourceMenu.getMenuStatus();
            this.menuLevel = resourceMenu.getMenuLevel();
            this.sort = resourceMenu.getSort();
            this.etc = resourceMenu.getEtc();
            this.parent_menu_id = resourceMenu.getParent() == null ? null : resourceMenu.getParent().getId();
        }
    }
}
