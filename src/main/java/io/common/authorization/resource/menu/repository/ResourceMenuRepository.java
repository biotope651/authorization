package io.common.authorization.resource.menu.repository;

import io.common.authorization.resource.menu.entity.ResourceMenu;
import io.common.authorization.resource.program.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceMenuRepository extends JpaRepository<ResourceMenu, Long> {
    Page<ResourceMenu> findByProgram(Program program, Pageable pageable);
    List<ResourceMenu> findByProgram(Program program);
}
