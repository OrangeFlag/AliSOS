# AliSOS
Навык Яндекс Алисы для вызова профильного врача основываясь на жалобах пользователя

# Installation
Проект находится в Alpha версии и доступен на платформе Алисы только в тестовом режиме

# Deploy
## Env
CLINIC_URL - url клиники для отправки запросов на помощь больному   
PORT - порт для развертывания  
DB_URL - адрес БД PostgreSQL в формате jdbc:postgresql://[host]:[port]/[db_name]?sslmode=disable   
DB_USER  - имя пользователя для доступа к БД  
DB_PASSWORD  - пароль для доступа к БД

## Other
Для запуска достатоточно иметь:
* описанные выше переменные окружения
* mock сервис клиники [опционально]
* запущенный инстанс Postgres, приложения создаст необходимые таблицы самостоятельно при их отсутствии 

# External Clinic API
```yaml
openapi: 3.0.0
info:
  title: Clinic API
  description: Clinic API for accepting statements for help from patients
  version: 1.0.0
paths:
  /patient:
    post:
      summary: Add a patient
      requestBody:
        description: Optional description in *Markdown*
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/UserClinicRequest'
      responses:
        '201':
          description: Created
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/UserClinicResponse'

components:
  schemas:
    UserClinicRequest:
      type: object
      properties:
        userId:
          type: string
        anamnesis:
          type: string
        address:
          type: string
        phone:
          type: string
        doctorType:
          type: string
      required: 
        - userId
        - anamnesis
        - address
        - phone
    UserClinicResponse:
      type: object
      allOf:
        - $ref: '#/components/schemas/UserClinicRequest'
        - type: object
          properties:
            timestamp:
              type: integer
          required:
            - timestamp
```