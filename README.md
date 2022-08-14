### Полный рефакторинг (не переписывание с нуля) бэкэнда проекта с [хакатона РГСУ](https://github.com/Widowan/hackathon-fit-2022)

### Что изменилось?

- Использована нормальная архитектура с теперь уже _значительно_ более глубоким знанием Spring
- Активное использование Spring Data JPA и Derived Queries вместе с JPQL вместо JdbcTemplate
- Валидация всего и вся ещё на этапе запроса с помощью кастомных валидаторов, в т.ч. [с использованием SpEL](src/main/java/com/hypnotoad/configurations/validators/SpelAssert.java)
- Возможность развертывать проект через Docker вместе с PostgresQL через docker-compose, в котором используется [билд контейнера через native-image](./build-docker-image.sh)
- Полностью переделана схема API, хотя смысловая нагрузка у эндпоинтов та же самая (новый формат + версионирование: /api/v2/...)

### Что НЕ изменилось?

- Схема ответов (`{"ok": true, "games": [...]}`), код ответов (responses) практически не изменился
- Схема БД, однако были сняты некоторые `NOT NULL` constraint'ы и значения по-умолчанию в пользу `NULL` и добавлены указания на то что некоторые поля являются fk (`references(...)`)
- Сборка всё так же через Maven

### Чего (пока) нет?
- Юнит тестов
- Документации API

### Как собрать/запустить?
#### С Docker:
```shell
$ ./build-docker-image.sh
$ docker-compose up
```

#### Без Docker:
- Подготовить БД согласно настройкам в [docker-compose.yml](./docker-compose.yml)
- Разметить её согласно схеме [setup_db.sql](./setup_db.sql)
- `$ ./mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:postgresql://postgres:5432/rssu-hackathon --spring.datasource.username=hackathon --spring.datasource.password=SuperSecretPassword --spring.server.port=8081"`