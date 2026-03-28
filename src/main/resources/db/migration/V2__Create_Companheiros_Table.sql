CREATE TABLE aventura.companheiros (
   aventureiro_id BIGINT PRIMARY KEY,
   nome VARCHAR(120) NOT NULL,
   especie VARCHAR(255) NOT NULL,
   lealdade INT NOT NULL,
   CONSTRAINT fk_companheiro_aventureiro
       FOREIGN KEY(aventureiro_id)
           REFERENCES aventura.aventureiros(id)
           ON DELETE CASCADE,
   CONSTRAINT chk_lealdade_range CHECK (lealdade >= 0 AND lealdade <= 100)
);

CREATE INDEX idx_companheiro_especie ON aventura.companheiros(especie, lealdade);
