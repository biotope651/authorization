package io.common.authorization.resource.program.repository;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.resource.program.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Page<Program> findAll(Pageable pageable);
    List<Program> findByProgramStatusOrderById(ActiveStatus activeStatus);
}
