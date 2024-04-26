FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce-0.0.1-SNAPSHOT.jar
EXPOSE 443
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]