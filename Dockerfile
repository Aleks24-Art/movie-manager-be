FROM openjdk:11
ARG JAR_FILE=*.jar
COPY build/libs/${JAR_FILE} app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app.jar"]