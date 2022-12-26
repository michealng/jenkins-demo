def artifact
pipeline {
	// configure environmental variables
	environment {
	    registry = "https://registry.hub.docker.com"
	    registryCredentials = "docker"
	}

	// instruct jenkins to allocate executor and workspace for entire pipeline
    agent any

    stages {
    	// compile and generate single executable jar with all dependencies
		stage('Build') {
            steps {
                sh 'mvn install'
            }
        }
        // build docker image of an application
// 		stage('Package') {
//             steps {
//                 script {
//                     artifact = docker.build("praveenellaiyan/jenkins-springboot-app:myapp")
//                 }
//             }
//         }
//         // push built docker image to docker hub
// 		stage('Publish') {
//             steps {
//                 script {
//                     docker.withRegistry(registry, registryCredentials) {
//       					artifact.push()
//     				}
//                 }
//             }
//         }
    }
}