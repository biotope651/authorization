package io.common.authorization.company.service;

import io.common.authorization.common.util.PageRequest;
import io.common.authorization.company.dto.request.ReqCompanyUserDTO;
import io.common.authorization.company.dto.response.ResGetCompanyUsersDTO;
import io.common.authorization.company.entity.Company;
import io.common.authorization.company.entity.CompanyUser;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.company.repository.CompanyUserRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyUserService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyUserRepository companyUserRepository;

    private final ModelMapper modelMapper;

    /**
     * 회사 유저 생성
     * @param reqCompanyUserDTO
     * @return
     */
    @Transactional
    public Long createCompanyUser(ReqCompanyUserDTO.CreateCompanyUserDTO reqCompanyUserDTO) {

        CompanyUser companyUser = new CompanyUser();

        Company company = companyRepository.findById(reqCompanyUserDTO.getCompanyId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        companyUser.setCompany(company);

        // Company 유저 셋팅
        User user = userRepository.findById(reqCompanyUserDTO.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
        companyUser.setUser(user);

        companyUserRepository.save(companyUser);

        return companyUser.getId();
    }

    /**
     * 회사 유저 리스트 조회
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    public ResGetCompanyUsersDTO getCompanyUsers(Long companyId, int page, int size) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.ASC);
        Page<CompanyUser> companyUserList = companyUserRepository.findByCompany(company,pageRequest.of("createDt"));

        return new ResGetCompanyUsersDTO(companyUserList.getContent(), page, size, companyUserList.getTotalPages());
    }

    /**
     * 회사 소속 유저 상세 정보 조회
     * @param userId
     * @return
     */
    public ResGetCompanyUsersDTO.GetCompanyUsers getCompanyUserInfo(Long userId, Long companyId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        CompanyUser companyUser = companyUserRepository.findByUserAndCompanyOrderByCreateDt(user, company)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        return new ResGetCompanyUsersDTO.GetCompanyUsers(companyUser);
    }

}
