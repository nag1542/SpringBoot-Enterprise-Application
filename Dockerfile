FROM eclipse-temurin:17-jre
WORKDIR /app
COPY employee-management/employee-boot/target/employee-boot-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=prod"]
