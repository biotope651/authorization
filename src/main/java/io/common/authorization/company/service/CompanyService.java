package io.common.authorization.company.service;

import io.common.authorization.common.util.PageRequest;
import io.common.authorization.company.dto.request.ReqCompanyDTO;
import io.common.authorization.company.dto.response.ResGetCompanyDTO;
import io.common.authorization.company.entity.Company;
import io.common.authorization.company.repository.CompanyRepository;
import io.common.authorization.error.ErrorCode;
import io.common.authorization.error.ErrorException;
import io.common.authorization.user.entity.User;
import io.common.authorization.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final ModelMapper modelMapper;

    /**
     * 회사 생성
     * @param reqCompanyDTO
     * @return
     */
    @Transactional
    public Long createCompany(ReqCompanyDTO.CreateCompanyDTO reqCompanyDTO) {

        Company company = modelMapper.map(reqCompanyDTO, new TypeToken<Company>() {}.getType());

        // 마지막 수정 유저
        if (reqCompanyDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqCompanyDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            company.setMngUser(mngUser);
        }

        companyRepository.save(company);

        return company.getId();
    }

    /**
     * 회사 정보 수정
     * @param reqCompanyDTO
     * @return
     */
    @Transactional
    public Long updateCompany(ReqCompanyDTO.UpdateCompanyDTO reqCompanyDTO) {

        Company company = companyRepository.findById(reqCompanyDTO.getCompanyId())
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        company.updateCompanyInfo(reqCompanyDTO);

        // 마지막 수정 유저
        if (reqCompanyDTO.getMngUserId() != null) {
            User mngUser = userRepository.findById(reqCompanyDTO.getMngUserId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));
            company.setMngUser(mngUser);
        }

        return company.getId();
    }

    /**
     * 회사 리스트 조회
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public ResGetCompanyDTO getCompany(Long userId, int page, int size) {

//        TODO - User 권한 조회해서 최고관리자만 Program 리스트를 조회한다.
//        if(최고관리자가 아니면 권한없음 에러 발생!!) {
//            throw new ErrorException(ErrorCode.FORBIDDEN_ERROR);
//        }

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC);
        Page<Company> companyList = companyRepository.findAll(pageRequest.of("id"));

        return new ResGetCompanyDTO(companyList.getContent(), page, size, companyList.getTotalPages());
    }

    /**
     * 회사 상세 정보 조회
     * @param companyId
     * @return
     */
    public ResGetCompanyDTO.GetCompany getCompanyInfo(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ErrorException(ErrorCode.RESOURCE_ID_NOT_VALID));

        return new ResGetCompanyDTO.GetCompany(company);
    }
}
