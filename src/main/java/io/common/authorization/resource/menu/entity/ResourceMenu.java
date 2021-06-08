package io.common.authorization.resource.menu.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.Depth;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.common.type.converter.DepthConverter;
import io.common.authorization.resource.menu.dto.request.ReqResourceMenuDTO;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 리소스 메뉴 테이블
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ResourceMenu extends JpaEntityDateAudity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Program
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    // 메뉴명
    private String menuName;

    // 메뉴 URL
    private String menuUrl;

    // 메뉴 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus menuStatus;

    // 메뉴 레벨
    @Convert(converter = DepthConverter.class)
    private Depth menuLevel;

    // 순서
    private int sort;

    // 비고
    private String etc;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    /**
     * menuId 순환참조를 위해 부모, 자식 관계를 정의한다.
     */

    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
//    참조하는 키가 PK가 아닌 경우에는 referencedColumnName에 아래처럼 부모 키를 넣어준다.
//    @JoinColumn(name = "id", referencedColumnName = "parent_menu_id")
    private ResourceMenu parent;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ResourceMenu> children;

    public void updateResourceMenu(ReqResourceMenuDTO.UpdateMenuDTO updateMenuDTO) {
        this.menuName = updateMenuDTO.getMenuName();
        this.menuUrl = updateMenuDTO.getMenuUrl();
        this.menuStatus = updateMenuDTO.getMenuStatus();
        this.menuLevel = updateMenuDTO.getMenuLevel();
        this.sort = updateMenuDTO.getSort();
        this.etc = updateMenuDTO.getEtc();
    }
}
