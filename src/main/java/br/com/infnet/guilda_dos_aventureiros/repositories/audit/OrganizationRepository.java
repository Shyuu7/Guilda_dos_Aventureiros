package br.com.infnet.guilda_dos_aventureiros.repositories.audit;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
