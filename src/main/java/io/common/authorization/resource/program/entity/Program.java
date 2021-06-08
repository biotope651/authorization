package io.common.authorization.resource.program.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.resource.program.dto.request.ReqProgramDTO;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "programs")
public class Program extends JpaEntityDateAudity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 프로그램 한글명
    private String programNameKr;

    // 프로그램 영문명
    private String programNameEn;

    // 프로그램 카테고리
    private String programCategory;

    // 프로그램 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus programStatus;

    // 비고
    private String etc;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    public void updateProgram(ReqProgramDTO.UpdateProgramDTO updateProgramDTO) {
        this.programNameKr = updateProgramDTO.getProgramNameKr();
        this.programNameEn = updateProgramDTO.getProgramNameEn();
        this.programCategory = updateProgramDTO.getProgramCategory();
        this.programStatus = updateProgramDTO.getProgramStatus();
        this.etc = updateProgramDTO.getEtc();
    }
}
