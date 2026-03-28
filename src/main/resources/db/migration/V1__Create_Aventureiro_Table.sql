CREATE TABLE aventura.aventureiros (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    classe VARCHAR(255) NOT NULL,
    nivel INT NOT NULL CHECK (nivel >= 1),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    organizacao_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_aventureiros_org FOREIGN KEY (organizacao_id) REFERENCES audit.organizacoes(id),
    CONSTRAINT fk_aventureiros_usuario FOREIGN KEY (usuario_id) REFERENCES audit.usuarios(id)
);

CREATE INDEX idx_aventureiros_org_classe_nivel ON aventura.aventureiros (organizacao_id, classe, nivel);
