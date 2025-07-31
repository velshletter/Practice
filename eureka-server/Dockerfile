# syntax=docker/dockerfile:1

# Стадия сборки — билдим весь проект и копируем нужный jar
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /build

# Копируем весь проект (снаружи должен быть context с корнем проекта)
COPY . /build/

ARG SERVICE_NAME

# Копируем jar нужного сервиса в корень сборочной стадии для удобства
RUN cp /build/${SERVICE_NAME}/target/*.jar /build/app.jar


# Стадия рантайма — легковесный JRE образ для запуска приложения
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Создаем непользовательскую группу и пользователя для безопасности
RUN addgroup -S spring && adduser -S spring -G spring

ARG SERVICE_NAME

# Копируем собранный jar из стадии build
COPY --from=build /build/app.jar ./app.jar

# Открываем порт (лучше, чтобы совпадал с портом в приложении)
EXPOSE 8080

# Запускаем от непользовательского юзера
USER spring:spring

# Оптимальные опции JVM для контейнера
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Команда запуска — используем sh -c, чтобы прокинуть JAVA_OPTS из env
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
