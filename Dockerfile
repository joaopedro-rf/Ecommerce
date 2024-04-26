FROM eclipse-temurin:17-jdk-alpine
COPY certs/main.crt /etc/pki/tls/certs/cert.crt
COPY private/main.key /etc/pki/tls/private/cert.key
WORKDIR /app
COPY target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce-0.0.1-SNAPSHOT.jar
EXPOSE 443
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]