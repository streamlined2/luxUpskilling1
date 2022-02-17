-- Table: yaos.product

-- DROP TABLE yaos.product;

CREATE TABLE IF NOT EXISTS yaos.product
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price numeric(15,2) NOT NULL,
    creation_date date NOT NULL,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name),
    CONSTRAINT positive_price CHECK (price > 0::numeric)
)
