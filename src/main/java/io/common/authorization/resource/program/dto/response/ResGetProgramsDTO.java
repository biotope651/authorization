package io.common.authorization.resource.program.dto.response;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.resource.program.entity.Program;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetProgramsDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="프로그램 리스트")
    private List<GetPrograms> list = new ArrayList<>();

    public ResGetProgramsDTO(List<Program> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetPrograms(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetPrograms {

        @ApiModelProperty(value="프로그램명 ID", required = true)
        private Long id;

        @ApiModelProperty(value="프로그램명 (KR)", required = true)
        private String programNameKr;

        @ApiModelProperty(value="프로그램명 (EN)", required = true)
        private String programNameEn;

        @ApiModelProperty(value="프로그램 카테고리")
        private String programCategory;

        @ApiModelProperty(value="프로그램 활성 상태")
        private ActiveStatus programStatus;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="등록일")
        private LocalDateTime createDt;

        @ApiModelProperty(value="수정일")
        private LocalDateTime updateDt;

        public GetPrograms(Program program) {
            this.id = program.getId();
            this.programNameKr = program.getProgramNameKr();
            this.programNameEn = program.getProgramNameEn();
            this.programCategory = program.getProgramCategory();
            this.programStatus = program.getProgramStatus();
            this.etc = program.getEtc();
            this.createDt = program.getCreateDt();
            this.updateDt = program.getUpdateDt();
        }
    }
}
