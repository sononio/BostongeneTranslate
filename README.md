# BostongeneTranslate

Test case for bostongene. Translate app.

### Задание

У Яндекс переводчика (translate.yandex.ru) имеется REST API. Необходимо реализовать консольное
приложение на одном из языков Java8, Groovy или Kotlin, которое переводит введенную
английскую фразу на русский язык, пользуясь предложенным API.

### Описание результата
- Программа реализована на Java 8
- Было использовано API от Яндекс.Переводчика
- Приложение переводит текст с английского на русский
- Приложение имеет консольный интерфейс
- Код документирован для создания javadoc

### Запуск
Проект является обычным gradle проектом. Для сборки следует использовать команду:
```
gradlew build
```

Запуск осуществляется командой:
```
java -jar build/libs/translate-0.1.0.jar
```