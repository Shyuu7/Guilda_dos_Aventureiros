INSERT INTO audit.organizacoes (id, nome, created_at)
VALUES (3, 'Ordem dos Protetores', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

INSERT INTO audit.usuarios(id, organizacao_id, nome, email, senha_hash, status, ultimo_login_em, created_at, updated_at)
VALUES (4, '3', 'Gertrulio GM', 'mestre@guilda.com', 'senha_super_segura', 'ATIVO', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;


INSERT INTO aventura.aventureiros (nome, classe, nivel, ativo, organizacao_id, usuario_id, data_criacao, data_atualizacao) VALUES
('Aragorn', 'GUERREIRO', 15, true, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gandalf', 'MAGO', 20, true, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Legolas', 'ARQUEIRO', 18, true, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gimli', 'GUERREIRO', 17, true, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Frodo Bolseiro', 'LADINO', 5, true, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lydia', 'GUERREIRO', 12, false, 3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

INSERT INTO aventura.missoes (organizacao_id, titulo, nivel_perigo, status, data_criacao) VALUES
(1, 'Resgatar o Amuleto de Yendor', 'ALTO', 'PLANEJADA', CURRENT_TIMESTAMP),
(1, 'Limpar o Covil dos Goblins', 'BAIXO', 'EM_ANDAMENTO', CURRENT_TIMESTAMP),
(1, 'Escolta do Comerciante de Especiarias', 'MEDIO', 'CONCLUIDA', CURRENT_TIMESTAMP);

INSERT INTO aventura.participacoes_missoes (missao_id, aventureiro_id, papel_missao, recompensa_ouro, destaque, data_registro) VALUES
(1, 2, 'LIDER', 5000.00, true, CURRENT_TIMESTAMP),
(1, 1, 'COMBATENTE', 2500.00, false, CURRENT_TIMESTAMP),
(2, 4, 'COMBATENTE', 300.00, false, CURRENT_TIMESTAMP),
(2, 3, 'BATEDOR', 350.00, true, CURRENT_TIMESTAMP),
(3, 5, 'ESPECIALISTA', 1000.00, false, CURRENT_TIMESTAMP);


INSERT INTO aventura.companheiros (aventureiro_id, nome, especie, lealdade) VALUES
(1, 'Brego', 'LOBO', 95),
(2, 'Fenrir', 'LOBO', 90),
(3, 'Elen', 'CORUJA', 85),
(4, 'Gimli Jr.', 'DRAGAO_MINIATURA', 80),
(5, 'Samwise Gamgee', 'GOLEM', 100),
(6, 'Shadowfax', 'DRAGAO_MINIATURA', 98);

