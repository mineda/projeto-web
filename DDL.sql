create schema avaliacao;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on avaliacao.* to user@'localhost';

use avaliacao;

create table usu_usuario (
  usu_id bigint unsigned primary key auto_increment,
  usu_nome_usuario varchar(50) not null,
  usu_senha varchar(50) not null,
  constraint usu_nome_usuario_uk unique (usu_nome_usuario)
);

create table pro_professor (
  usu_id bigint unsigned primary key,
  pro_titulo varchar(10),
  constraint pro_usu_fk foreign key (usu_id)
    references usu_usuario (usu_id)
);

create table alu_aluno (
  usu_id bigint unsigned primary key,
  alu_ra bigint unsigned not null,
  constraint alu_ra_uk unique (alu_ra),
  constraint alu_usu_fk foreign key (usu_id)
    references usu_usuario (usu_id)
);

create table tra_trabalho (
  tra_id bigint unsigned primary key auto_increment,
  tra_titulo varchar(50) not null,
  tra_data_hora_entrega datetime not null,
  tra_local_arquivo varchar(200) not null,
  pro_avaliador_id bigint unsigned,
  constraint tra_pro_fk foreign key (pro_avaliador_id)
    references pro_professor (usu_id)
);

create table ent_entrega (
  alu_id bigint unsigned,
  tra_id bigint unsigned,
  primary key (alu_id, tra_id),
  constraint ent_alu_fk foreign key (alu_id)
    references alu_aluno (usu_id),
  constraint ent_tra_fk foreign key (tra_id)
    references tra_trabalho (tra_id)
);