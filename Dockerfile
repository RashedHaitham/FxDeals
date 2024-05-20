FROM openjdk:21

COPY target/FxDeals-1.0.jar file.jar

CMD ["java", "-jar", "file.jar"]