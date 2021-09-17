create table restaurante_responsaveis(
	restaurante_id bigint not null,
    usuario_id bigint not null,
    
    primary key (restaurante_id, usuario_id)
)engine=InnoDB default charset=utf8;

alter table restaurante_responsaveis add constraint fk_restaurante_usuarios
foreign key (restaurante_id) references restaurante (id);

alter table restaurante_responsaveis add constraint fk_usuarios_restaurante
foreign key (usuario_id) references usuario (id);