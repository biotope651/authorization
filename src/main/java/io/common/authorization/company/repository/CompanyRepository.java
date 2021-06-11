package io.common.authorization.company.repository;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByAvailableStatusOrderByCompanyName(ActiveStatus activeStatus);
}
