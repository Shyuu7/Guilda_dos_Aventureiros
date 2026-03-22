package br.com.infnet.guilda_dos_aventureiros.repositories.audit;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
}
