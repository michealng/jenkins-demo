pipeline {
  // This pipeline requires the following plugins:
  // * Git: https://plugins.jenkins.io/git/
  // * Workflow Aggregator: https://plugins.jenkins.io/workflow-aggregator/
  // * JUnit: https://plugins.jenkins.io/junit/
  agent 'any'

  environment {
    mvnHome = tool 'maven-3.6.3'
    dockerImageTag = "michealng385/jenkinsexample${env.BUILD_NUMBER}"
    DOCKERHUB_CREDENTIALS=credentials('jenkins-docker-hub')
  }
  
  stages {
    stage('Clone Repo') {
      steps {
        git 'https://github.com/michealng/jenkins-demo.git'
      }
    }
    stage('Build Project and Unit test') {
      steps {
        sh(script: "'${mvnHome}/bin/mvn' clean install") 

      }
    }
    stage('Package') {
      steps {
          sh "docker build -t michealng385/jenkinsexample:${env.BUILD_NUMBER} ."
        // docker.build("michealng385/jenkinsexample:${env.BUILD_NUMBER}")
      }
    }
    stage('Deploy Docker Image') {
      steps {
          echo "Docker Image Tag Name: ${dockerImageTag}"
	  
	      sh "docker stop jenkinsexample || true"
	  
	      sh "docker rm jenkinsexample || true"
	  
	      sh "docker run --name jenkinsexample -d -p 2222:2222 michealng385/jenkinsexample:${env.BUILD_NUMBER}"
      }
    }
    stage('Push image to Docker hub') {
      steps {
          withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
            // some block
            sh "docker login -u michealng385 -p ${dockerhubpwd}"

            sh "docker push michealng385/jenkinsexample:${env.BUILD_NUMBER}"
          }
      }
    }
    
  }
  post {
    always {
      junit(testResults: 'target/surefire-reports/*.xml', allowEmptyResults : true)
    }
  }
}