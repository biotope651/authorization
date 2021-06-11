package io.common.authorization.resource.program.dto.response;

import io.common.authorization.resource.program.entity.Program;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetProgramSelectBoxDTO {

    @ApiModelProperty(value="프로그램 리스트")
    private List<GetPrograms> list = new ArrayList<>();

    public ResGetProgramSelectBoxDTO(List<Program> list) {
        this.list = list.stream().map(o -> new GetPrograms(o)).collect(Collectors.toList());
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

        public GetPrograms(Program program) {
            this.id = program.getId();
            this.programNameKr = program.getProgramNameKr();
            this.programNameEn = program.getProgramNameEn();
            this.programCategory = program.getProgramCategory();
        }
    }
}
