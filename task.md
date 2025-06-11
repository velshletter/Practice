# 5. API для системы голосований (опросов)

## Содержание

- [Функционал](#Функционал)
- [Примеры хранимых сущностей](#Примеры-хранимых-сущностей)
- [Используемые технологии](#Используемые-технологии)
- [Полезные ссылки](#Полезные-ссылки)

## Функционал

### Основные функции
1. **Управление опросами**
    - Создание/редактирование/удаление опросов
    - Добавление/удаление вариантов ответов
    - Установка времени начала и окончания голосования
    - Возможность сделать опрос анонимным

2. **Работа с голосами**
    - Голосование за выбранный вариант (1 пользователь = 1 голос)
    - Проверка, проголосовал ли уже пользователь
    - Досрочное завершение опроса (для администраторов)

3. **Статистика и аналитика**
    - Просмотр результатов по звершению опроса (экспорт в csv)
    - Фильтрация результатов по датам

4. **Безопасность и доступ**
    - JWT-аутентификация пользователей
    - Ролевая модель (администратор, обычный пользователь)
    - Валидация входных данных


## Примеры хранимых сущностей

### 1. Пользователь (User)
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "username": "user123",
  "email": "user@example.com",
  "passwordHash": "hashed_password",
  "role": "user",
  "createdAt": "2023-01-01T00:00:00Z",
  "isActive": true
}
```

### 2. Опрос (Poll)
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa7",
  "title": "Favorite Programming Language",
  "description": "Which language do you prefer for backend development?",
  "isAnonymous": false,
  "isMultipleChoice": false,
  "startDate": "2023-01-10T00:00:00Z",
  "endDate": "2023-01-20T00:00:00Z",
  "createdBy": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "createdAt": "2023-01-05T12:00:00Z",
  "tags": ["programming", "survey"]
}
```

### 3. Вариант ответа (Option)
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa8",
  "pollId": "3fa85f64-5717-4562-b3fc-2c963f66afa7",
  "text": "JavaScript",
  "order": 1
}
```

### 4. Голос (Vote)
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa9",
  "pollId": "3fa85f64-5717-4562-b3fc-2c963f66afa7",
  "optionId": "3fa85f64-5717-4562-b3fc-2c963f66afa8",
  "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "createdAt": "2023-01-15T14:30:00Z",
  "ipAddress": "192.168.1.1"
}
```
### 5. Результат опроса (PollResult)
```json
{
  "pollId": "3fa85f64-5717-4562-b3fc-2c963f66afa7",
  "totalVotes": 150,
  "options": [
    {
      "optionId": "3fa85f64-5717-4562-b3fc-2c963f66afa8",
      "text": "JavaScript",
      "votes": 65,
      "percentage": 43.33
    },
    {
      "optionId": "3fa85f64-5717-4562-b3fc-2c963f66afb0",
      "text": "Python",
      "votes": 45,
      "percentage": 30.00
    }
  ],
  "calculatedAt": "2023-01-21T00:00:00Z"
}
```

## Используемые технологии

- Java 17+
- Spring Boot
- Spring Web
- Spring Security (JWT)
- Spring Data JPA
- Liquibase / Flyway
- Swagger / OpenAPI (springdoc-openapi)
- JUnit 5
- GitFlow
- OpenCSV

## Полезные ссылки

- [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Liquibase Documentation](https://docs.liquibase.com/)
- [Flyway Documentation](https://documentation.red-gate.com/fd)
- [springdoc-openapi Documentation](https://springdoc.org/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [GitFlow Documentation (GitHub Flow)](https://docs.github.com/en/get-started/using-github/github-flow)
- [OpenCSV Documentation](http://opencsv.sourceforge.net/)
- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)
