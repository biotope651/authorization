package io.common.authorization.auth.repository;

import io.common.authorization.auth.entity.ResourceAuth;
import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.resource.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceAuthRepository extends JpaRepository<ResourceAuth,Long> {
    List<ResourceAuth> findByProgramAndRoleGroupAuth(Program program, RoleGroupAuth roleGroupAuth);
}
