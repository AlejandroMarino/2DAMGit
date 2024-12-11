FROM maven:3.9-sapmachine-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Define las variables como argumentos de construcción
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET
ARG MAIL_USERNAME
ARG MAIL_PASSWORD
ARG DATASOURCE_URL

# Pasar los argumentos al entorno
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV DATASOURCE_URL=${DATASOURCE_URL}


FROM openjdk:17-jdk-oracle

COPY --from=build /app/target/spring_Pagao_docker.jar /spring_Pagao_docker.jar
ENTRYPOINT ["java","-jar","/spring_Pagao_docker.jar"]
