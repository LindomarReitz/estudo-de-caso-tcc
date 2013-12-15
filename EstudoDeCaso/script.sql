create table usuarios (id serial primary key, nome varchar(50) not null, data_nascimento date not null, 
login varchar(50) not null, senha varchar(50) not null);

create table bancos (id serial primary key, nome varchar(50) not null);
insert into bancos(nome) values ('Santander');
insert into bancos(nome) values ('Bradesco');
insert into bancos(nome) values ('Banco do Brasil');
insert into bancos(nome) values ('Itaú');
insert into bancos(nome) values ('Caixa Econômica');
insert into bancos(nome) values ('HSBC');

create table contas (id serial primary key, nome varchar(50) not null, numero_agencia int not null, numero_conta int not null,
saldo_atual float not null, id_usuario int references usuarios(id), id_banco int references bancos(id));

create table lancamentos (id serial primary key, descricao text not null, data_lancamento date not null, valor float not null, tipo_lancamento varchar(50) not null,
observacao text, id_conta int references contas(id));

create table transferencias (id serial primary key, valor float not null, data_transferencia date not null, 
id_conta_origem int references contas(id), id_conta_destino int references contas(id));

create table contas_lancamentos (id_conta int references contas(id), id_lancamento int references lancamentos(id));