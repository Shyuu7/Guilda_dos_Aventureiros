package br.com.infnet.guilda_dos_aventureiros.repositories.audit;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
