alter table pedido add codigo varchar(36) not null after id;
update pedido set codigo = uuid() ;

alter table pedido add unique key pk_pedido_codigo (codigo);