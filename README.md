# Gestão para Condomínios

Sistema para gestão de condomínios desenvolvido em Java com Spring Boot.

## Módulos

- Login
- Condomínios
- Financeiro
- Contabilidade
- Relatórios

## Requisitos

- Java JDK >= 17
- Apache Maven >= 3.8.6
- MySql 8.0.30
- Docker (Opcional)

## Tecnologias

- Java
- JPA
- Maven
- Lombok
- Spring Boot
- Thymeleaf
- MySql
- Docker-Compose

## Instalação

### Docker
Abra o terminal e execute o seguinte comando:

```
$ docker-compose up
```

OBS: É necessário ter o Docker e docker-compose instalado.
Caso não tenha instalado o Docker ou dê alguma falha na instalação, tente com os comandos abaixo.

### MySql
Crie um banco de dados com o nome "condominio" no seu Mysql.<br>
Abra o arquivo .env e efetue a configuração correta para conexão do seu banco de dados.<br>

```
$ mysql -u<seu usuário> -p<sua senha>

mysql> create database `condominio`;
```

### Maven
Para rodar o projeto com Maven, é necessário ter a versão 3.8.6 instalada.<br>
Além disso, é preciso ter o Java 15 e o MySql 8 instalado.<br>

Tendo tudo instalado e rodando localmente, basta executar o seguinte comando:

```
$ cd sistema-gestao-condominio
$ mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

## Acesso ao Sistema

Para acessar o sistema utilize o seguinte endereço:<br>
http://localhost:8080/

Depois é só criar um usuário e senha para poder logar no sistema.

## Licença

Projeto licenciado sob <a href="LICENSE">The MIT License (MIT)</a>.<br><br>

## Screenshots

![Screenshots](screenshots/screenshot01.png) <br><br>
![Screenshots](screenshots/screenshot02.png) <br><br>
![Screenshots](screenshots/screenshot03.png) <br><br>
![Screenshots](screenshots/screenshot04.png) <br><br>
![Screenshots](screenshots/screenshot05.png) <br><br>
![Screenshots](screenshots/screenshot06.png) <br><br>
![Screenshots](screenshots/screenshot07.png) <br><br>
![Screenshots](screenshots/screenshot08.png) <br><br>
![Screenshots](screenshots/screenshot09.png) <br><br>
![Screenshots](screenshots/screenshot10.png) <br><br>
