FROM eclipse-temurin:17-jdk-alpine
COPY ../certs/cert.crt /etc/ssl/certs/cert.crt
COPY ../certs/cert.key /etc/ssl/private/cert.key
WORKDIR /app
COPY target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce-0.0.1-SNAPSHOT.jar
EXPOSE 443
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]