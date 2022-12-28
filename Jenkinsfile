node {
    // reference to maven
    // ** NOTE: This 'maven-3.6.3' Maven tool must be configured in the Jenkins Global Configuration.
    def mvnHome = tool 'maven-3.6.3'

    // holds reference to docker image
    def dockerImage
    // ip address of the docker private repository(nexus)
 
    def dockerImageTag = "michealng385/jenkinsexample${env.BUILD_NUMBER}"

    environment {
        DOCKERHUB_CREDENTIALS=credentials('jenkins-docker-hub')
    }
    
    stage('Clone Repo') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/michealng/jenkins-demo.git'
      // Get the Maven tool.
      // ** NOTE: This 'maven-3.6.3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'maven-3.6.3'
    }    
  
    stage('Build Project and Unit test') {
      // build project via maven
      sh "'${mvnHome}/bin/mvn' clean install"

//       post {
//           success {
//               junit '**/target/surefire-reports/TEST-*.xml'
//           }
//       }
    }
		
    stage('Build Docker Image') {
      // build docker image
      dockerImage = docker.build("michealng385/jenkinsexample:${env.BUILD_NUMBER}")
    }
   
    stage('Deploy Docker Image'){
      
      // deploy docker image to nexus
		
      echo "Docker Image Tag Name: ${dockerImageTag}"
	  
	  sh "docker stop jenkinsexample || true"
	  
	  sh "docker rm jenkinsexample || true"
	  
	  sh "docker run --name jenkinsexample -d -p 2222:2222 michealng385/jenkinsexample:${env.BUILD_NUMBER}"
	  
// 	  docker.withRegistry('https://registry.hub.docker.com', 'jenkins-docker-hub') {
//          dockerImage.push("${env.BUILD_NUMBER}")
//            dockerImage.push("latest")
//        }
      
    }

    stage('Push image to Docker hub') {

//         sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
//
//         sh 'docker push michealng385/jenkinsexample:${env.BUILD_NUMBER}'
//       withDockerRegistry([ credentialsId: "jenkins-docker-hub", url: "" ]) {
//         dockerImage.push()
//       }
        withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
            // some block
            sh "docker login -u michealng385 -p ${dockerhubpwd}"

            sh "docker push michealng385/jenkinsexample:${env.BUILD_NUMBER}"
        }
    }

}
