CREATE DATABASE IF NOT EXISTS sd;
USE sd;
SET foreign_key_checks = 0;

DROP TABLE IF EXISTS utilizador;
DROP TABLE IF EXISTS faculdade;
DROP TABLE IF EXISTS departamento;
DROP TABLE IF EXISTS eleicao;
DROP TABLE IF EXISTS voto;
DROP TABLE IF EXISTS estado;
DROP TABLE IF EXISTS lista;
DROP TABLE IF EXISTS lista_util;
DROP TABLE IF EXISTS maquina;
SET foreign_key_checks = 1;

CREATE TABLE utilizador
(
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	tipo VARCHAR(15) NOT NULL,
	contacto VARCHAR(9) NOT NULL,
	username VARCHAR(30) NOT NULL,
	password VARCHAR(30) NOT NULL,
	dept_id INTEGER NOT NULL,
	morada VARCHAR(50) NOT NULL,
	cartao VARCHAR(30) NOT NULL,
	validade DATE NOT NULL,
	CONSTRAINT pk_user_id PRIMARY KEY (user_id)
);

CREATE TABLE faculdade
(
	fac_id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_fac_id PRIMARY KEY (fac_id)
);

CREATE TABLE departamento
(
	dept_id INTEGER NOT NULL AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	fac_id INTEGER NOT NULL,
	CONSTRAINT pk_departamento PRIMARY KEY (dept_id)
);

CREATE TABLE eleicao
(
	eleicao_id INTEGER NOT NULL AUTO_INCREMENT,
	dept_id INTEGER,
	titulo VARCHAR(50) NOT NULL,
	descricao VARCHAR(500) NOT NULL,
	inicio TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	fim TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	tipo VARCHAR(30) NOT NULL,
	terminado INTEGER DEFAULT 0,
	CONSTRAINT pk_eleicao_id PRIMARY KEY (eleicao_id)
);

CREATE TABLE voto
(
	voto_id INTEGER NOT NULL AUTO_INCREMENT,
	user_id INTEGER NOT NULL,
	dept_id INTEGER NOT NULL,
	eleicao_id INTEGER NOT NULL,
	voto VARCHAR(30) NOT NULL,
	tempo TIMESTAMP NOT NULL,
	apresentado INTEGER DEFAULT 0,
	CONSTRAINT pk_voto_id PRIMARY KEY (voto_id)
);

CREATE TABLE lista
(
	lista_id INTEGER NOT NULL AUTO_INCREMENT,
	eleicao_id INTEGER NOT NULL,
	tipo VARCHAR(30) NOT NULL,
	nome VARCHAR(30) NOT NULL,
	CONSTRAINT pk_lista_id PRIMARY KEY (lista_id)
);

CREATE TABLE maquina
(
	maquina_id INTEGER NOT NULL AUTO_INCREMENT,
	dept_id INTEGER NOT NULL,
	eleicao_id INTEGER NOT NULL,
	CONSTRAINT pk_maquina_id PRIMARY KEY (maquina_id)
);


CREATE TABLE estado
(
	estado_id INTEGER NOT NULL AUTO_INCREMENT,
	maquina_id INTEGER,
	eleicao_id INTEGER,
	tipo VARCHAR(100) NOT NULL,
	estado VARCHAR(100) NOT NULL,
	tempo TIMESTAMP NOT NULL,
	apresentado INTEGER DEFAULT 0,
	CONSTRAINT pl_estado_id PRIMARY KEY (estado_id)
);

CREATE TABLE lista_util
(
	user_id INTEGER NOT NULL,
	lista_id INTEGER NOT NULL,
	eleicao_id INTEGER NOT NULL
);

ALTER TABLE utilizador
	ADD CONSTRAINT fk_dept_id_utilizador FOREIGN KEY (dept_id)
REFERENCES departamento(dept_id);

ALTER TABLE departamento
	ADD CONSTRAINT fk_fac_id_departamento FOREIGN KEY (fac_id)
REFERENCES faculdade(fac_id);

ALTER TABLE eleicao
	ADD CONSTRAINT fk_dept_id_eleicao FOREIGN KEY (dept_id)
REFERENCES departamento(dept_id);

ALTER TABLE voto
	ADD CONSTRAINT fk_dept_id_voto FOREIGN KEY (dept_id)
REFERENCES departamento(dept_id);

ALTER TABLE voto
	ADD CONSTRAINT fk_user_id_voto FOREIGN KEY (user_id)
REFERENCES utilizador(user_id);

ALTER TABLE voto
	ADD CONSTRAINT fk_eleicao_id_voto FOREIGN KEY (eleicao_id)
REFERENCES eleicao(eleicao_id);

ALTER TABLE lista
	ADD CONSTRAINT fk_eleicao_id_lista FOREIGN KEY (eleicao_id)
REFERENCES eleicao(eleicao_id);

ALTER TABLE maquina
	ADD CONSTRAINT fk_eleicao_id_maquina FOREIGN KEY (eleicao_id)
REFERENCES eleicao(eleicao_id);

ALTER TABLE maquina
	ADD CONSTRAINT fk_dept_id_maquina FOREIGN KEY (dept_id)
REFERENCES departamento(dept_id);

ALTER TABLE estado
	ADD CONSTRAINT fk_maquina_id_estado FOREIGN KEY (maquina_id)
REFERENCES maquina(maquina_id);

ALTER TABLE estado
	ADD CONSTRAINT fk_eleicao_id_estado FOREIGN KEY (eleicao_id)
REFERENCES eleicao(eleicao_id);

ALTER TABLE lista_util
	ADD CONSTRAINT fk_user_id_lista_util FOREIGN KEY (user_id)
REFERENCES utilizador(user_id);

ALTER TABLE lista_util
	ADD CONSTRAINT fk_lista_id_lista_util FOREIGN KEY (lista_id)
REFERENCES lista(lista_id);

ALTER TABLE lista_util
	ADD CONSTRAINT fk_eleicao_id_lista_util FOREIGN KEY (eleicao_id)
REFERENCES eleicao(eleicao_id);
