RENAME table itempedido TO item_pedido;

ALTER TABLE item_pedido change precoUnitario preco_unitario decimal(10,2) not null;

ALTER TABLE item_pedido change precoTotal preco_total decimal(10,2) not null;