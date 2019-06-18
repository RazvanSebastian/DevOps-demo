FROM java:8

#expose port outside of container
EXPOSE 8080

#copy the jar into the container
COPY /target/hello-jenkins-0.0.1-SNAPSHOT.jar app.jar

#run jar file
ENTRYPOINT ["java","-jar","app.jar"]