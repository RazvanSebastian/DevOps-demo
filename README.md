# DevOps demo with: Java, Spring, Maven, JFrog, Jenkins and Docker 

The goal of this project is to simulate the integration of Spring application with the following tools:
* JFrog - artifactory
* Jenkins - pipeline
* Docker - containers

#### Environment preparation
##### 1. Setup Maven, Java and Docker locally
##### 2. Download the following Docker images
* JFrog Docker setup
    * Pull the JFrog Open Source image: `$ docker pull docker.bintray.io/jfrog/artifactory-oss:latest `
    * Create and run a JFrog container: ` $ docker run --name artifactory -d -p 8081:8081 docker.bintray.io/jfrog/artifactory-oss:latest ` 


* MailHog Docker setup (used as mail service to simulate the send message step in Jenkins)
    * Pull the Mail-Service image: `$ docker pull mailhog/mailhog`
    * Create and run a Mail-Service container: `$ docker run -d -p 8025:8025 -p 1025:1025 <image-id>`

##### 3. Setup Jenkins
1. Download Jenkins .war from : https://updates.jenkins-ci.org/download/war/
2. Start Jenkins : ``` java -jar jenkins.war ```
3. Setup the Jenkins
3.1. Install additional plugins: HTML Report
3.2. Add your `DockerHub` credentials to Credentials section
4. Create a Jenkins Pipeline
5. Use the code from Jenkinsfile to create the pipeline

##### 4, Setup Jfrog and Maven
4.1. Deploy the JAR generated from maven-sample-jenkins-commons project to JFrog
4.2. Edit the `pom.xml` from `hello-jenkins` application

        <dependencies>
            ...
            <dependency>
				<groupId>edu.sample</groupId>
				<artifactId>maven-sample-jenkins-commons</artifactId>
				<version>0.0.1-SNAPSHOT</version>
			</dependency>
    		...
		</dependencies>
		
		<repositories>
			<repository>
				<id>local-repo</id>
				<name>Local Repo</name>
				<url>http://localhost:8081/artifactory/libs-snapshot-local</url>
			</repository>
		</repositories>
		
		<distributionManagement>
		    <snapshotRepository>
    			<id> ID_NAME </id>
    			<name> GENERATED_NAME_BY_JFROG </name>
    			<url>http://localhost:8081/artifactory/libs-snapshot-local</url>
		    </snapshotRepository>
	    </distributionManagement>

4.3. Edit the `pom.xml` from `maven-sample-jenkins-commons` application

	<distributionManagement>
		<snapshotRepository>
			<id>snapshots</id>
			<name>97e3d7cb004b-snapshots</name>
			<url>http://localhost:8081/artifactory/libs-snapshot-local</url>
		</snapshotRepository>
	</distributionManagement>
	
use `mvn deploy` to upload the commons project into JFrog artifactory

4.4. Edit `settings.xml`

    <?xml version="1.0" encoding="UTF-8"?>
    <settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.1.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <servers>
        	<server>
        		<username> JFROG_USERNAME </username>
        		<password>JFROG_PASSWORD</password>
        		<id> ID_NAME </id>
        	</server>
        </servers>
    </settings>