package br.com.infnet.guilda_dos_aventureiros.repositories.audit;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
