package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import br.com.infnet.guilda_dos_aventureiros.config.TestConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
@DisplayName("Testes de Relacionamento JPA para Entidades de Auditoria")
class AuditRelationshipsDataJpaTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Deve carregar o usuário e sua organização corretamente")
    void usuarioEOrganizacaoSaoCarregados() {
        String userEmail = "admin@guilda.com";

        User user = em.createQuery("""
                select u from User u
                join fetch u.organizationUser
                where u.email = :email
                """, User.class)
                .setParameter("email", userEmail)
                .getSingleResult();

        assertThat(user).isNotNull();
        assertThat(user.getOrganizationUser().getName()).isEqualTo("Guilda Principal");
    }

    @Test
    @DisplayName("Deve carregar as roles de um usuário")
    void rolesDoUsuarioSaoCarregadas() {
        String userEmail = "admin@guilda.com";
        String expectedRoleName = "Administrador do Sistema";

        User user = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", userEmail)
                .getSingleResult();

        List<UserRole> userRoles = em.createQuery("""
                select ur from UserRole ur
                join fetch ur.roles r
                where ur.userRoles.id = :userId
                """, UserRole.class)
                .setParameter("userId", user.getId())
                .getResultList();

        assertThat(userRoles).hasSize(1);
        assertThat(userRoles.getFirst().getRoles().getName()).isEqualTo(expectedRoleName);
    }

    @Test
    @DisplayName("Deve carregar as permissões de uma role")
    void permissoesEstaoAcessiveisViaRole() {
        String roleName = "Administrador do Sistema";
        String orgName = "Guilda Principal";

        Role role = em.createQuery("""
        select distinct r
        from Role r
        join fetch r.permissions p
        join r.organization o
        where r.name = :name and o.name = :org
        """, Role.class)
                .setParameter("name", roleName)
                .setParameter("org", orgName)
                .getSingleResult();

        List<Permission> permissions = role.getPermissions();
        assertThat(permissions).hasSize(9);
        assertThat(permissions)
                .extracting(Permission::getCode)
                .containsExactlyInAnyOrder(
                        "users:read", "users:write",
                        "roles:read", "roles:write",
                        "audit:read",
                        "aventureros:read", "aventureros:write",
                        "missoes:read", "missoes:write"
                );
    }
}
