def CONTAINER_NAME="jenkins-pipeline"
def CONTAINER_TAG="latest"
def DOCKER_HUB_USER="razvan95"
def HTTP_PORT="8090"

node {
    
    stage('Initialize'){
        def dockerHome = tool 'myDocker'
        def mavenHome = tool 'myMaven'
        
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }   
    
    notify('Started')
    
    try{
        stage('checkout') {
           git 'https://github.com/RazvanSebastian/maven-sample-jenkins.git'
        }
        
        stage('compiling, testing, packaging'){
            bat label: '', script: 'mvn clean verify'
        }
        
        stage('archive'){
            archiveArtifacts 'target/*.jar'
            step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
            publishHTML(target: [
                allowMissing: true, 
                alwaysLinkToLastBuild: false, 
                keepAll: true, 
                reportDir: 'target/site/jacoco/', 
                reportFiles: 'index.html', 
                reportName: 'HTML Report', 
                reportTitles: 'Code coverage'])
        }
        
        notify('Success')
    }
    catch (err) {
        notify("Error ${err}")
        currentBuild.result = 'FAILURE'
    }
    
}

node{
	stage("Image Prune"){
		imagePrune(CONTAINER_NAME)
	}

	stage("Image Build"){
		imageBuild(CONTAINER_NAME, CONTAINER_TAG)
	}

	stage("Push to Docker Registry"){
		withCredentials([usernamePassword(credentialsId: 'dockerHubAccount', usernameVariable: 'username', passwordVariable: 'password')]) {
			pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
		}
	}

	stage("Run App"){
		runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
	}
}


def imagePrune(containerName){
    try {
        bat label: 'Docker image prune', script: "docker image prune -f"
        bat label: 'Docker stop $containerName', script: "docker stop ${containerName}"
    } catch(error){
		notify("Error $error")
	}
}

def imageBuild(containerName, tag){
	try{
	    bat label: 'Docker build image', script: "docker build -t ${containerName}:${tag} ."
	} catch(error){
		notify("Error $error")
	}
	
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    bat label: 'Docker login', script: "docker login -u ${dockerUser} -p ${dockerPassword}"
    bat label: 'Docker tag', script:  "docker tag ${containerName}:${tag} ${dockerUser}/${containerName}:${tag}"
    bat label: 'Docker push', script:  "docker push ${dockerUser}/${containerName}:${tag}"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    bat label: 'Docker pull image', script: "docker pull ${dockerHubUser}/${containerName}"
    bat label: 'Docker create container from image', script: "docker run -d --rm -p ${httpPort}:8080 --name ${containerName} ${containerName}:${tag}"
    echo "Application started on port: ${httpPort} (http)"
}

//Email notifier 
def notify(status){
    emailext (
        to: 'rzvs95@gmail.com',
        subject: "${status}: Job '${env.JOB_NAME}' [${env.BUILD_NUMBER}]  ", 
        body: "<p> ${status}: Job '${env.JOB_NAME}' [${env.BUILD_NUMBER}] </p>  <p> Check console output <a href='${env.BUILD_URL}'>here</a> </p>"
    )
}