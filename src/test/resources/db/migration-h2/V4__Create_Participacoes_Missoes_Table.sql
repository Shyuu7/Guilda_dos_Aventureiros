CREATE TABLE aventura.participacoes_missoes (
    missao_id BIGINT NOT NULL,
    aventureiro_id BIGINT NOT NULL,
    papel_missao VARCHAR(255) NOT NULL,
    recompensa_ouro NUMERIC(10, 2),
    destaque BOOLEAN NOT NULL DEFAULT FALSE,
    data_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (missao_id, aventureiro_id),

    CONSTRAINT fk_participacao_missao
        FOREIGN KEY(missao_id)
            REFERENCES aventura.missoes(id),

    CONSTRAINT fk_participacao_aventureiro
        FOREIGN KEY(aventureiro_id)
            REFERENCES aventura.aventureiros(id),

    CONSTRAINT chk_recompensa_ouro_positive CHECK (recompensa_ouro IS NULL OR recompensa_ouro >= 0)
);


CREATE INDEX idx_participacao_missao_id ON aventura.participacoes_missoes(missao_id);
CREATE INDEX idx_participacao_aventureiro_id ON aventura.participacoes_missoes(aventureiro_id);