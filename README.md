# AliSOS
Навык Яндекс Алисы для вызова профильного врача основываясь на жалобах пользователя

# Deploy
## Env
CLINIC_URL - url клиники для отправки запросов на помощь больному   
PORT - порт для развертывания  
DB_URL - адрес БД PostgreSQL в формате jdbc:postgresql://[host]:[port]/[db_name]?sslmode=disable   
DB_USER  - имя пользователя для доступа к БД  
DB_PASSWORD  - пароль для доступа к БД

## How to use?
Для запуска достатоточно иметь:
* описанные выше переменные окружения
* сервис клиники [опционально]
* PostgreSQL database, приложения создаст необходимые таблицы самостоятельно при их отсутствии 

# External Clinic API

Эндпоинт POST /patient  
Request body:
```json
{
    "userId": "string",
    "anamnesis": "string",
    "address": "string",
    "phone": "string",
    "doctorType": "string?"
}
```
Response body(201 Created):
```json
{
  "userId": "string",
  "anamnesis": "string",
  "address": "string",
  "phone": "string",
  "doctorType": "string",
  "timestamp": 0
}
```

