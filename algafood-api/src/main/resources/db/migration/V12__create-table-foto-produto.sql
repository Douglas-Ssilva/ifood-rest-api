create table foto_produto (
	produto_id bigint not null,
    nome varchar(150) not null,
    descricao varchar(150) not null,
	content_type varchar(50) not null,
    length bigint not null,
    
    primary key(produto_id),
    constraint fk_foto_produto foreign key (produto_id) references produto (id)

)engine=InnoDB charset=UTF8;