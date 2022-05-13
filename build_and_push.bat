./gradlew.bat clean build
docker build . -t <your Docker Hub account>/spring-boot-template:1.0.0
docker push <your Docker Hub account>/spring-boot-template:1.0.0