package br.com.infnet.guilda_dos_aventureiros.repositories.audit;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
