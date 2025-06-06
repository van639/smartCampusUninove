create database smartcampus;
use smartcampus;
create table tb_categoria(
id_categoria int not null auto_increment primary key,
ds_nome varchar(100) not null,
dt_cadastro datetime not null

);

create table tb_produto(

id_produto int not null auto_increment primary key,
ds_nome varchar(100) not null,
int_quantidade int not null,
dt_validade datetime,
dt_cadastro datetime not null,
dm_valor decimal(15,2),
ds_observacao VARCHAR(150),
id_categoria int not null,

constraint FK_Categorias_Produtos
    foreign key (id_categoria) 
    references tb_categoria (id_categoria),
    
    unique index (id_produto)
);

INSERT INTO tb_categoria (ds_nome, dt_cadastro) VALUES
('Bebida', NOW()),
('Alimento', NOW()),
('Eletrônicos', NOW()),
('Higiene Pessoal', NOW()),
('Vestuário', NOW()),
('Casa', NOW()),
('Acessórios', NOW());
