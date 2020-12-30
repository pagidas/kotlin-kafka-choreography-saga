ARG VERSION=14
FROM openjdk:${VERSION}-alpine
COPY build/libs/orders*-all.jar /bin/app.jar
CMD ["java", "-jar", "/bin/app.jar"]