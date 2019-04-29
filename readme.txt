WeatherAPI

Arquitetura 

Aplicação React -> API Cidades
API Cidades -> OpenWeather
API Cidades -> Aplicação React

API Cidades
O sistema desenvolvido, consiste em uma aplicação para Back-end, utilizando Spring Boot. Essa app consiste em uma API com algumas operações básicas.

Para executá-la é possivel importar o projeto em alguma IDE (feito no NetBeans), ou simplesmente executar java -jar no arquivo jar dentro da pasta target.

Aplicação React
Foi criada uma aplicação de Front-end para consumo da API e busca de cidade na API do openweather. Para executá-la é necessário entrar no diretório da aplicação e executar um npm start

DB
Para que ambas as aplicações funcionem, é necessário importar o dump de dados APÓS executar a aplicação Spring.