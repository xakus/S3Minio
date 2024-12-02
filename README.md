# Запуск Docker Compose для MinIO

## Скачайте Docker Compose файл
### 1. Скачайте Docker Compose файл Убедитесь, что у вас есть файл `docker-compose.yml` для запуска MinIO.
#### Вот пример содержимого этого файла:

```yaml
services:
  minio:
    image: quay.io/minio/minio
    container_name: minio
    ports:
      - "9000:9000"
      - "9001:9001" # Порт для MinIO Console
    environment:
      MINIO_ROOT_USER:  ruhani
      MINIO_ROOT_PASSWORD: Q!122WS@ws))-=+32
    volumes:
      - /Users/xakus/Documents/projeects/S3Minio/docker_file/data:/data
    command: server /data --console-address ":9001"
```
### 2. Запустите Docker Compose
#### Убедитесь, что у вас установлен Docker и Docker Compose.
#### В директории, где находится файл docker-compose.yml, выполните команду:   bash   Копировать код
```shell
sudo docker-compose up -d
```
---
### 3. Доступ к MinIO Console
####    После успешного запуска, MinIO будет доступен по следующим адресам:

#### MinIO API: http://localhost:9000
#### MinIO Console (веб-интерфейс): http://localhost:9001
### 4. Вход в MinIO Console
####    По умолчанию, MinIO использует следующие учетные данные:

#### Access Key: ruhani
#### Secret Key: Q!122WS@ws))-=+32
#### Войдите в веб-интерфейс с этими данными, чтобы управлять бакетами и файлами.

------
## Отправка файлов через Postman
### 1. Запуск Spring Boot приложения
####    Убедитесь, что ваше Spring Boot приложение настроено на взаимодействие с MinIO. Для отправки файлов через API используйте следующие шаги.

### 2. Конфигурация в Postman
### 2.1. Выбор метода POST
####    Откройте Postman и выберите метод POST для отправки файлов.

### 2.2. Указание URL
#### Введите URL вашего эндпоинта для загрузки файла (например, если ваше приложение работает на localhost:8128): bash
#### Копировать код http://localhost:8128/files/upload
### 2.3. Вкладка Body в Postman
#### Перейдите на вкладку Body в Postman и выберите form-data.

### 2.4. Указание параметра формы
#### В поле Key укажите параметр, который соответствует @RequestParam("file") в вашем контроллере (например, "file").
#### В поле Value выберите файл для отправки, нажав на поле и выбрав файл через диалоговое окно.
#### Пример:

#### Key: file
#### Value: выберите файл для отправки.
### 2.5. Отправка запроса
#### Нажмите кнопку Send, чтобы отправить запрос и загрузить файл в MinIO через ваше Spring Boot приложение.
