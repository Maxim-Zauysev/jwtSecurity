# Инструкция по запуску и использованию API

## Запуск

1. Запустите Docker Compose командой:
    ```
    docker compose up
    ```
2. Запустите приложение.

## Работа с API

### Регистрация пользователя

Для регистрации пользователя отправьте POST-запрос на `/api/v1/auth/register` с данными пользователя.

### Вход в систему

Для входа в систему отправьте POST-запрос на `/api/v1/auth/signin`. В ответ вы получите Bearer token.

### Использование API

При отправке запросов к `/api/v1/app`, добавьте полученный Bearer token в заголовок `Authorization`.

## Нереализованные функции

На данный момент тесты не реализованы.