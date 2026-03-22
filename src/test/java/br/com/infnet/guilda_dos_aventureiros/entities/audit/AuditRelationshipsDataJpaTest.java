package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/audit-test-cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/audit-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuditRelationshipsDataJpaTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void usuarioEOrganizacaoSaoCarregados() {
        User user = em.createQuery("""
                select u from User u
                join fetch u.organizationUser
                where u.email = :email
                """, User.class)
                .setParameter("email", "user.audit@test.com")
                .getSingleResult();

        assertThat(user.getOrganizationUser().getName()).isEqualTo("Org Audit Test");
    }

    @Test
    void rolesDoUsuarioSaoCarregadas() {
        User user = em.createQuery("""
                select u from User u
                where u.email = :email
                """, User.class)
                .setParameter("email", "user.audit@test.com")
                .getSingleResult();

        List<UserRole> userRoles = em.createQuery("""
                select ur from UserRole ur
                join fetch ur.roles r
                where ur.userRoles.id = :userId
                """, UserRole.class)
                .setParameter("userId", user.getId())
                .getResultList();

        assertThat(userRoles).hasSize(1);
        assertThat(userRoles.getFirst().getRoles().getName()).isEqualTo("ADMIN");
    }

    @Test
    void permissoesEstaoAcessiveisViaRole() {
        Role role = em.createQuery("""
        select distinct r
        from Role r
        join fetch r.permissions p
        join r.organization o
        where r.name = :name and o.name = :org
        """, Role.class)
                .setParameter("name", "ADMIN")
                .setParameter("org", "Org Audit Test")
                .getSingleResult();

        List<Permission> permissions = role.getPermissions();
        assertThat(permissions)
                .extracting(Permission::getCode)
                .containsExactlyInAnyOrder("user.read", "user.write");
    }
}

