create table pedido (
	id bigint not null auto_increment,
    taxa_frete decimal(10,2) not null,
    sub_total decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    data_criacao datetime not null,
    data_confirmacao datetime,
    data_cancelamento datetime,
    data_entrega datetime,
    status varchar(10) not null,
    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    endereco_cidade_id bigint not null,
    endereco_cep varchar(15) not null,
    endereco_logradouro varchar(30) not null,
    endereco_numero varchar(10) not null,
    endereco_bairro varchar(30) not null,
    endereco_complemento varchar(30),    
    
    primary key(id),
    constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
    constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)

)engine=InnoDB charset=utf8;


create table itemPedido (
	id bigint not null auto_increment,
    quantidade smallint(6) not null,
    precoUnitario decimal(10,2) not null,
    precoTotal decimal(10,2) not null,
    observacao varchar(50),
    pedido_id bigint not null,
    produto_id bigint not null,
    
    primary key(id),
	unique key uk_item_pedido_produto (pedido_id, produto_id),

    constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
    constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)

)engine=InnoDB charset=utf8; 


