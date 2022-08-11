## Бэкэнд

Написан на Java с использованием Spring, Vavr.io и Immutables

### Сборка

Скопируйте файл `src/resources/datatsourceSecrets.properties.example` без суффикса example
(`mv src/resources/datasourceSecrets{.properties.}`)
и отредактируйте по необходимости вместе с файлом `src/resources/application.properties`

Сборка:  
```bash
./mvnw clean install
```

Готовый к развертыванию .jar файл будет в `target/backend.jar`

### Развертывание

По умолчанию используется и рекомендуется PostgreSQL. Разметка базы данных описана в [setup_db.sql](setup_db.sql).
Более подробные инструкции по развертке БД можно найти на [ArchWiki](https://wiki.archlinux.org/title/PostgreSQL)

### Документация

API описано в [wiki](https://github.com/Widowan/hackathon-fit-2022/wiki).
Поскольку оно может быть немного устаревшим, для точной информации можно просмотреть исходный код
в файлах вида XxxController.java