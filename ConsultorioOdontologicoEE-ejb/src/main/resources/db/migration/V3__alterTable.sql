drop TABLE imagem;
drop TABLE categoria_imagem;

CREATE TABLE categoria_imagem
(
   id BIGSERIAL not null,
   descricao character varying(255) NOT NULL,
   CONSTRAINT categoria_imagem_pk PRIMARY KEY (id),
   CONSTRAINT categoria_imagem_uk UNIQUE (descricao)
);

CREATE TABLE imagem
(
   id BIGSERIAL not NULL,
   descricao character varying(255) NOT NULL,
   data_inclusao timestamp without time zone NOT NULL,
   categoria_imagem_id bigint NOT NULL,
   orcamento_id bigint NOT NULL,
   imagem bytea[] NOT NULL,
   CONSTRAINT imagem_pk PRIMARY KEY (id),
   CONSTRAINT imagem_orcamento_fk
   FOREIGN KEY (orcamento_id) REFERENCES orcamento (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT imagem_categoria_fk
   FOREIGN KEY (categoria_imagem_id) REFERENCES categoria_imagem (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION
);