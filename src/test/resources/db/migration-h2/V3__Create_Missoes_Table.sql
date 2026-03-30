CREATE TABLE aventura.missoes (
    id BIGSERIAL PRIMARY KEY,
    organizacao_id BIGINT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    nivel_perigo VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio TIMESTAMP,
    data_termino TIMESTAMP,
    CONSTRAINT fk_missao_organizacao
        FOREIGN KEY(organizacao_id)
            REFERENCES audit.organizacoes(id)
    );

CREATE INDEX idx_missao_organizacao_id ON aventura.missoes(organizacao_id);
CREATE INDEX idx_missao_status ON aventura.missoes(status);
