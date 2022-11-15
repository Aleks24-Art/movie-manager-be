# Описание проекта Movie Manager

Модуль включает в себя:
* *Swagger OpenAPI 3.0*;
* Работу с СУБД, а именно с **Postgresql**;
* Миграцию БД с использованием *sql*-патчей в формате *sql*;
* **docker-compose** для быстрого старта разработки;

| Характерисика                    | Значение                                                                 |
|----------------------------------|--------------------------------------------------------------------------|
| java                             | 11                                                                       |
| build                            | gradle wrapper 7.1                                                       |
| test                             | junit (модульное), TestContainers (интеграционное), pitest (мутационное) |
| check task <br/>(gradle plugins) | checkstyle                 |
 
Запуск приложения:
1. Клонировать репозиторий;
2. Убедиться что *docker-compose* установлен;
3. Запустить развертку контейнеров *docker-compose up*, используя docker-compose.yml файл в проекте;
4. Запустить **spring-boot** приложение /gradlew bootRun
