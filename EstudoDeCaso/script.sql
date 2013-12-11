create table usuarios (id serial primary key, nome varchar(50) not null, data_nascimento date not null, 
login varchar(50) not null, senha varchar(50) not null);

create table bancos (id serial primary key, nome varchar(50) not null);

create table contas (id serial primary key, nome varchar(50) not null, numero_agencia int not null, numero_conta int not null,
saldo_atual float not null, id_usuario int references usuarios(id));

create table lancamentos (id serial primary key, descricao text not null, data_lancamento date not null, valor float not null,
observacao text, id_conta int references contas(id));

create table transferencias (id serial primary key, valor float not null, data_transferencia date not null, 
id_conta_origem int references contas(id), id_conta_destino int references contas(id));

create table contas_lancamentos (id_conta int references contas(id), id_lancamento int references lancamentos(id));