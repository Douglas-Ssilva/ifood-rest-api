alter table restaurante add ativo tinyint(1) not null; -- boolean é um apelido para tinyint
update restaurante set ativo = true; -- pelo fato de já ter restaurantes cadastrados, vamos set true a todos 