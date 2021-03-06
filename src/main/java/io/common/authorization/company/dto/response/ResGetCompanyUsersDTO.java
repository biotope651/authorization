package io.common.authorization.company.dto.response;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.EntryType;
import io.common.authorization.common.type.UserStatus;
import io.common.authorization.common.type.UserType;
import io.common.authorization.company.entity.CompanyUser;
import io.common.authorization.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetCompanyUsersDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="회사 유저 리스트")
    private List<GetCompanyUsers> list = new ArrayList<>();

    public ResGetCompanyUsersDTO(List<CompanyUser> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetCompanyUsers(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetCompanyUsers {

        @ApiModelProperty(value="회사 유저 ID")
        private Long id;

        @ApiModelProperty(value="유저 ID")
        private Long userId;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;

        @ApiModelProperty(value="회사명")
        private String companyName;

        @ApiModelProperty(value="롤 그룹 ID")
        private Long roleGroupId;

        @ApiModelProperty(value="롤 그룹명")
        private String roleGroupName;

        @ApiModelProperty(value="로그인 ID", required = true)
        private String loginId;

        @ApiModelProperty(value="비밀번호", required = true)
        private String password;

        @ApiModelProperty(value="유저명", required = true)
        private String name;

        @ApiModelProperty(value="이메일", required = true)
        private String email;

        @ApiModelProperty(value="모바일", required = true)
        private String mobile;

        @ApiModelProperty(value="연락처")
        private String tel;

        @ApiModelProperty(value="사용자 상태", required = true)
        private UserStatus userStatus;

        @ApiModelProperty(value="계정 상태", required = true)
        private ActiveStatus accountStatus;

        @ApiModelProperty(value="유저 Type", required = true)
        private UserType userType;

        @ApiModelProperty(value="유저 Type명", required = true)
        private String userTypeName;

        @ApiModelProperty(value="가입 방법", required = true)
        private EntryType entryType;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="가입일 / 생성일")
        private LocalDateTime createDt;


        public GetCompanyUsers(CompanyUser companyUser) {

            this.id = companyUser.getId();

            if (companyUser.getCompany() != null) {
                this.companyId = companyUser.getCompany().getId();
                this.companyName = companyUser.getCompany().getCompanyName();
            }

            User user = companyUser.getUser() == null ? null : companyUser.getUser();

            if (user.getRoleGroup() != null) {
                this.roleGroupId = user.getRoleGroup().getId();
                this.roleGroupName = user.getRoleGroup().getRoleGroupName();
            }

            this.userId = user.getId();
            this.loginId = user.getLoginId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.mobile = user.getMobile();
            this.tel = user.getTel();
            this.userStatus = user.getUserStatus();
            this.accountStatus = user.getAccountStatus();
            this.userType = user.getUserType();
            this.userTypeName = user.getUserType().getDescription();
            this.entryType = user.getEntryType();
            this.etc = user.getEtc();
            this.createDt = user.getCreateDt();
        }

    }
}
