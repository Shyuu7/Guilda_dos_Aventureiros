insert into audit.organizacoes (id, nome, ativo, created_at)
values (900001, 'Org Audit Test', true, now());

insert into audit.usuarios (id, organizacao_id, nome, email, senha_hash, status, created_at, updated_at)
values (900100, 900001, 'User Audit', 'user.audit@test.com', 'hash', 'ATIVO', now(), now());

insert into audit.roles (id, organizacao_id, nome, descricao, created_at)
values (900200, 900001, 'ADMIN', 'Administrador', now());

insert into audit.user_roles (usuario_id, role_id, granted_at)
values (900100, 900200, now());

insert into audit.permissions (id, code, descricao)
values (900300, 'user.read', 'Ler usuarios'),
       (900301, 'user.write', 'Escrever usuarios');

insert into audit.role_permissions (role_id, permission_id)
values (900200, 900300),
       (900200, 900301);
