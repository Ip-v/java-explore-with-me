#TODO check docker file
FROM amazoncorretto:11
COPY target/*.jar stats-service.jar
ENTRYPOINT ["java","-jar","stats-service.jar"]