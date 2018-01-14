USE sd;

INSERT INTO faculdade (fac_id,nome) VALUES (1,"faculdade de medicina");
INSERT INTO faculdade (fac_id,nome) VALUES (2,"faculdade de ciencias do desporto");
INSERT INTO faculdade (fac_id,nome) VALUES (3,"faculdade de farmacia");
INSERT INTO faculdade (fac_id,nome) VALUES (4,"faculdade de psicologia");
INSERT INTO faculdade (fac_id,nome) VALUES (5,"faculdade ciencias e tecnologia");
INSERT INTO faculdade (fac_id,nome) VALUES (6,"faculdade de direito");

INSERT INTO departamento (nome,fac_id) VALUES ("departamento de informatica",5);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de engenharia fisica",5);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de educacao fisica",2);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de anatomia",1);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de direito internacional",6);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de farmacologia",3);
INSERT INTO departamento (nome,fac_id) VALUES ("departamento de estudos da mente",4);



INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Nuno Ferreira','estudante','348150648','nunoferreira','password',1,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('José Paulo','funcionario','011844142','josepaulo','password',2,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Leonardo Alves','docente','967840996','leonardoalves','password',3,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Luis Dias','estudante','289081531','luisdias','password',4,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Yasmin Barbosa','funcionario','232405628','yasminbarbosa','password',5,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Luisa Sousa','docente','232425628','luisasousa','password',6,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Emily Santos','estudante','232435628','emilysantos','password',7,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Mateus Gomes','funcionario','232465628','mateusgomes','password',6,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Beatriz Silva','docente','232475628','beatrizsilva','password',5,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Vitória Almeida','estudante','232485628','vitoriaalmeida','password',3,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('José Castro','funcionario','232195628','josecastro','password',4,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Paulo Simões','docente','759123486','paulosimoes','password',2,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Carolina Ferreira','estudante','986473819','carolinaferreira','password',1,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Margarida Lopes','funcionario','755123987','margaridalopes','password',7,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Fernando Marques','docente','666555908','fernandomarques','password',4,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Hugo Santos','estudante','222435988','hugosantos','password',2,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Carla Assunção','funcionario','111444888','carlaassuncao','password',3,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Guilherme Martins','docente','999555678','guilhermemartins','password',1,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Samuel Rocha','estudante','000123099','samuelrocha','password',5,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Isabel Rodrigues','funcionario','878888657','isabelrodrigues','password',5,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Tiago Fernandes','docente','900872645','tiagofernandes','password',6,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));
INSERT INTO utilizador (nome,tipo,contacto,username,password,dept_id,morada,cartao,validade) VALUES ('Júlia Rocha','estudante','678876543','juliarocha','password',4,'asd','1231023ZZ12',STR_TO_DATE('01-01-2018','%d-%m-%Y'));

#INSERT INTO eleicao (dept_id,titulo,descricao,inicio,fim,tipo,terminado) VALUES (4,'asd','asd',STR_TO_DATE('01-01-2018','%d-%m-%Y'),STR_TO_DATE('02-02-2018','%d-%m-%Y'),'nucleo de estudantes',0);
#INSERT INTO eleicao (dept_id,titulo,descricao,inicio,fim,tipo,terminado) VALUES (5,'qwerty','123',STR_TO_DATE('12-09-2017','%d-%m-%Y'),STR_TO_DATE('01-01-2018','%d-%m-%Y'),'conselho geral',0);