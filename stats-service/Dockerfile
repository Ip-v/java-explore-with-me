#TODO chack docker file
FROM amazoncorretto:11
COPY target/*.jar gateway.jar
ENTRYPOINT ["java","-jar","stats-service.jar"]