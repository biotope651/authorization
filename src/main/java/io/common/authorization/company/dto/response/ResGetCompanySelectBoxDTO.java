package io.common.authorization.company.dto.response;

import io.common.authorization.company.entity.Company;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetCompanySelectBoxDTO {

    @ApiModelProperty(value="회사 리스트")
    private List<GetCompany> list = new ArrayList<>();

    public ResGetCompanySelectBoxDTO(List<Company> list) {
        this.list = list.stream().map(o -> new GetCompany(o)).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetCompany {

        @ApiModelProperty(value="회사 ID")
        private Long id;

        @ApiModelProperty(value="회사명")
        private String companyName;

        public GetCompany(Company company) {
            this.id = company.getId();
            this.companyName = company.getCompanyName();
        }
    }
}
