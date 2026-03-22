delete from audit.role_permissions where role_id = 900200;
delete from audit.user_roles where usuario_id = 900100 or role_id = 900200;
delete from audit.permissions where id in (900300, 900301);
delete from audit.roles where id = 900200;
delete from audit.usuarios where id = 900100;
delete from audit.organizacoes where id = 900001;
