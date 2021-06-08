package io.common.authorization.company.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.BusinessType;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.common.type.converter.BusinessTypeConverter;
import io.common.authorization.company.dto.request.ReqCompanyDTO;
import io.common.authorization.user.entity.User;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
public class Company extends JpaEntityDateAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회사명
    private String companyName;

    // 사업자번호
    private String businessNo;

    // 사업자등록증 Path
    private String businessUrl;

    // 사업자 종류
    @Convert(converter = BusinessTypeConverter.class)
    private BusinessType businessType;

    // 대표자명
    private String ceoName;

    // 회사 대표번호
    private String companyTel;

    // 팩스번호
    private String fax;

    // 주소1
    private String address1;

    // 주소2
    private String address2;

    // 우편번호
    private String postNo;

    // 회사 로고 이미지
    private String companyLogo;

    // 활성 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus availableStatus;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    // Company Update 정보 셋팅
    public void updateCompanyInfo(ReqCompanyDTO.UpdateCompanyDTO reqCompanyDTO) {

        if(isNotBlank(reqCompanyDTO.getCompanyName())) {
            this.companyName = reqCompanyDTO.getCompanyName();
        }

        if(isNotBlank(reqCompanyDTO.getBusinessNo())) {
            this.businessNo = reqCompanyDTO.getBusinessNo();
        }

        if(isNotBlank(reqCompanyDTO.getBusinessUrl())) {
            this.businessUrl = reqCompanyDTO.getBusinessUrl();
        }

        if(isNotBlank(reqCompanyDTO.getBusinessType().getValue())) {
            this.businessType = reqCompanyDTO.getBusinessType();
        }

        if(isNotBlank(reqCompanyDTO.getCeoName())) {
            this.ceoName = reqCompanyDTO.getCeoName();
        }

        if(isNotBlank(reqCompanyDTO.getCompanyTel())) {
            this.companyTel = reqCompanyDTO.getCompanyTel();
        }

        if(isNotBlank(reqCompanyDTO.getFax())) {
            this.fax = reqCompanyDTO.getFax();
        }

        if(isNotBlank(reqCompanyDTO.getAddress1())) {
            this.address1 = reqCompanyDTO.getAddress1();
        }

        if(isNotBlank(reqCompanyDTO.getAddress2())) {
            this.address2 = reqCompanyDTO.getAddress2();
        }

        if(isNotBlank(reqCompanyDTO.getPostNo())) {
            this.postNo = reqCompanyDTO.getPostNo();
        }

        if(isNotBlank(reqCompanyDTO.getCompanyLogo())) {
            this.companyLogo = reqCompanyDTO.getCompanyLogo();
        }

        if(isNotBlank(reqCompanyDTO.getAvailableStatus().getValue())) {
            this.availableStatus = reqCompanyDTO.getAvailableStatus();
        }

    }

    private boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }
}
