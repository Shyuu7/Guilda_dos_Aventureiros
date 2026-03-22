package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import br.com.infnet.guilda_dos_aventureiros.enums.audit.AuditEntryAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "audit_entries", schema = "audit", indexes = {
        @Index(name = "idx_audit_action", columnList = "action"),
        @Index(name = "idx_audit_entity", columnList = "entity_schema, entity_name, entry_id"),
        @Index(name = "idx_audit_org_time", columnList = "organizacao_id, ocurred_at")
        },
        check = {
        @CheckConstraint(name = "chk_audit_auction",
                        constraint = "action IN ('CREATE', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT','EXPORT', 'IMPORT','ERROR')"),
        })
@Getter
@Setter
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_entries_id")
    @SequenceGenerator(schema = "audit", name = "audit_entries_id", sequenceName = "audit_entries_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizacao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_audit_org"))
    private Organization organizationEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="actor_user_id", foreignKey = @ForeignKey(name = "fk_audit_actor_user"))
    private User actorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="actor_api_key_id", foreignKey = @ForeignKey(name = "fk_audit_actor_api_key"))
    private ApiKey actorApiKey;

    @Enumerated(EnumType.STRING)
    @Column(name="action", nullable = false, length = 30)
    private AuditEntryAction action;

    @Column(name="entity_schema", length = 60, nullable = false)
    private String entitySchema;

    @Column(name = "entity_name", length = 60, nullable = false)
    private String entityName;

    @Column(name="entity_id", length = 80)
    private String entityId;

    @Column(name="occurred_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime ocurredAt;

    @Column(name = "ip")
    private InetAddress ip;

    @Column(name="user_agent")
    private String userAgent;

    @Column(name = "diff", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> diff = new HashMap<>();

    @Column(name = "metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata = new HashMap<>();

    @Column(name="success", nullable = false)
    private Boolean isSuccessful;
}
