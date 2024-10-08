# community-center-api

### Tecnologias utilizadas
- **Java com Spring Boot**
- **RabbitMQ**
- **MongoDB**
- **Testes: JUnit e Mockito**

#### Configuração e execução

1. Clone o repositório:

`git clone https://github.com/augustogs/community-center-api`

2. Execute o projeto.

3. Teste os endpoints em:

`http://localhost:8080/swagger-ui/index.html`

#### Endpoints implementados

![Endpoints](https://drive.google.com/uc?id=19cFWvlSjoGeMBY17TBlE_U1MNh2XpOFy)

##### 1. Cadastro de centros comunitários
`POST /api/community-centers`

##### Corpo da requisição (JSON)
```json
{
  "name": "Centro 1",
  "address": "Rua Exemplo, 123, Bairro Exemplo",
  "location": "João Pessoa, PB",
  "capacity": 12,
  "occupancy": 11,
  "resources": {
    "MEDICAL_DOCTOR": 10,
    "VOLUNTEER": 10,
    "MEDICAL_SUPPLIES": 10,
    "TRANSPORT_VEHICLE": 18,
    "BASIC_FOOD_BASKET": 50
  }
}
```

##### 2. Troca de recursos entre centros comunitários
`POST /api/resource-exchanges`

##### Corpo da requisição (JSON)
```json
{
    "centerFromId": "66db57e525c230335e80198e",
    "centerToId": "66db57eb25c230335e80198f",
    "resourcesOffered": {
        "MEDICAL_DOCTOR": 1,
        "VOLUNTEER": 1
    },
    "resourcesRequested": {
        "MEDICAL_SUPPLIES": 1,
        "TRANSPORT_VEHICLE": 1
    }
}
```

##### 3. Atualizar ocupação de um centro comunitário
`PUT /api/community-centers/{centerId}/occupancy`

- `{centerId}`: *O id do centro comunitário que deseja atualizar*
- `Query Parameter`: 
      - `occupancy`: *O valor da ocupação a ser atualizado*.
 

##### 4. Listar centros comunitários com ocupação maior que 90%
`GET /api/community-centers/high-occupancy`

##### 5. Listar média de recursos existentes nos centros comunitários
`GET /api/community-centers/average-resources`

##### 6. Listar histórico de trocas por centro comunitário
`GET api/resource-exchanges/by-center`
- `Query Parameter`: 
        - `centerId`: *O id do centro comunitário*

##### 7. Listar histórico de trocas por data
`GET api/resource-exchanges/by-date-range`
- `Query Parameter`: 
        - `startTime`: *Data Inicial* `"2024-09-01T00:00:00Z"`
        - `endTime`: *Data Final* `"2024-09-07T23:59:59Z"`


