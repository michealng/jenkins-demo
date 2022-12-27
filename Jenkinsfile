node {
    // reference to maven
    // ** NOTE: This 'maven-3.6.3' Maven tool must be configured in the Jenkins Global Configuration.
    def mvnHome = tool 'maven-3.6.3'

    // holds reference to docker image
    def dockerImage
    // ip address of the docker private repository(nexus)
 
    def dockerImageTag = "jenkinsexample${env.BUILD_NUMBER}"
    
    stage('Clone Repo') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/michealng/jenkins-demo.git'
      // Get the Maven tool.
      // ** NOTE: This 'maven-3.6.3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'maven-3.6.3'
    }    
  
    stage('Build Project') {
      // build project via maven
      sh "'${mvnHome}/bin/mvn' clean install"
    }
		
    stage('Build Docker Image') {
      // build docker image
      dockerImage = docker.build("jenkinsexample:${env.BUILD_NUMBER}")
    }
   
    stage('Deploy Docker Image'){
      
      // deploy docker image to nexus
		
      echo "Docker Image Tag Name: ${dockerImageTag}"
	  
	  sh "docker stop jenkinsexample || true"
	  
	  sh "docker rm jenkinsexample || true"
	  
	  sh "docker run --name jenkinsexample -d -p 2222:2222 jenkinsexample:${env.BUILD_NUMBER}"
	  
	  docker.withRegistry('https://registry.hub.docker.com', 'jenkins-docker-hub') {
         dockerImage.push("${env.BUILD_NUMBER}")
           dockerImage.push("latest")
       }
      
    }
}
