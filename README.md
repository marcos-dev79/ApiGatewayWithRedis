# ApiGatewayWithRedis
Api Gateway with Redis and Springboot example 

## Obs: INSTANCIA AWS FOI DESATIVADA.
Rodava na AWS em containeres usando Docker

## Exemplos de chamada:

### Query por Hotel ID
http://3.208.24.30:8080/api/v1/query?cityID=1032&checkInDate=2021-10-02&checkOutDate=2021-10-10&numberOfAdults=2&numberOfChildren=1&hotelID=3

### Query por Cidade
http://3.208.24.30:8080/api/v1/query?cityID=1032&checkInDate=2021-10-02&checkOutDate=2021-10-10&numberOfAdults=2&numberOfChildren=1

Basta passar os parametros GET que a API retornará a resposta esperada.

## Uso Local
- Basta rodar:
mvn clean install

- Criar o container da aplicação:
docker build ./ -t apigateway

- Subir o Docker compose:
docker-compose up

Substituir o IP da AWS por localhost:8080

## Tecnologias

Desenvolvido usando Spring BOOT + Redis para Cache, feito o Deploy com Docker na AWS. Foram adicionados dois testes unitarios com Junit e Mockito.

# TODOs

Quanto ao cache, minha idéia era utilizar oo Redis de maneira mais inteligente e gerenciada "à mão", possivelmente criando um serviço que rodasse a noite
e atualizasse automaticamente os caches, notando mudanças nos dados e acelerando o processo.

Também poderia colocar uma thread paralela que atualizasse o REDIS durante a consulta.

Mas faltou-me o recurso mais caro do mundo: TEMPO.

