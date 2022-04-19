# spring-medical-crud-api

# Arquitetura
Foi utilizado arquitetura de microserviços, orientada a eventos com RabbitMQ e Design Pattern façade.
O Cadastro de médicos foi feito assíncrono para que seja feita a consulta do CEP antes e foi utilizado um sistema de Feed para acompanhar o estado da solicitação.
A API usada foi a Viacep
Mecanismo de busca foi feito utilizando MATCH() e AGAINST()
A validação dos campos foi feita utilizando funções lambda.
Não consegui utilizar Docker, então para inicializar os dados no banco foi utilizado JPA ao invés de um arquivo init.sql.
Foi utilizado Spring Cloud Gateway
