[![Build status](https://ci.appveyor.com/api/projects/status/kt9uq5rk9am96hh6?svg=true)](https://ci.appveyor.com/project/molottva/diploma)

# Курсовой проект по модулю «Автоматизированное тестирование»

## Запуск SUT, авто-тестов и генерация репорта

### Подключение SUT к MySQL

1. Запустить Docker Desktop
2. Открыть проект в IntelliJ IDEA
3. В терминале в корне проекта запустить контейнеры:

   `docker-compose up -d`
4. Запустить приложение:
   `java -jar .\artifacts\aqa-shop\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
5. Открыть второй терминал
6. Запустить тесты:

   `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`
7. Создать отчёт Allure и открыть в браузере

   `.\gradlew allureServe`
8. Закрыть отчёт:

   **CTRL + C -> y -> Enter**
9. Перейти в первый терминал
10. Остановить приложение:

    **CTRL + C**
11. Остановить контейнеры:

    `docker-compose down`

## Документация

1. [Текст задания](TaskStatementReadMe.md)

2. [План автоматизации](docs/Plan.md)

3. [Отчётные документы по итогам тестирования](docs/Report.md)

4. [Отчётные документы по итогам автоматизации](docs/Summary.md)

