MERGE INTO audit.organizacoes (id, nome, ativo, created_at)
    KEY(id)
VALUES (1, 'Guilda Principal', TRUE, CURRENT_TIMESTAMP);

MERGE INTO audit.permissions (id, code, descricao)
    KEY(id)
VALUES
    (1, 'users:read', 'Ler informações de usuários'),
    (2, 'users:write', 'Criar e editar usuários'),
    (3, 'roles:read', 'Ler informações de papéis e permissões'),
    (4, 'roles:write', 'Criar e editar papéis e associar permissões'),
    (5, 'audit:read', 'Visualizar logs de auditoria'),
    (6, 'aventureros:read', 'Visualizar aventureiros'),
    (7, 'aventureros:write', 'Gerenciar aventureiros'),
    (8, 'missoes:read', 'Visualizar missões'),
    (9, 'missoes:write', 'Gerenciar missões');

MERGE INTO audit.roles (id, organizacao_id, nome, descricao, created_at)
    KEY(id)
VALUES
    (1, 1, 'Administrador do Sistema', 'Acesso total a todas as funcionalidades do sistema.', CURRENT_TIMESTAMP),
    (2, 1, 'Mestre da Guilda', 'Gerencia usuários, aventureiros e missões dentro da sua guilda.', CURRENT_TIMESTAMP);

MERGE INTO audit.role_permissions (role_id, permission_id)
    KEY(role_id, permission_id)
VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9),
    (2, 1), (2, 2), (2, 6), (2, 7), (2, 8), (2, 9);

MERGE INTO audit.usuarios (id, organizacao_id, nome, email, senha_hash, status, created_at, updated_at)
    KEY(id)
VALUES (1, 1, 'Admin', 'admin@guilda.com', 'senha_super_segura_hash', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO audit.user_roles (usuario_id, role_id, granted_at)
    KEY(usuario_id, role_id)
VALUES (1, 1, CURRENT_TIMESTAMP);
