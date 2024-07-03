pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myapp:${env.BUILD_ID}"
        SONARQUBE_SERVER = "http://sonarqube-soau2w14.ldapowner.opsera.io/"
        OCTOPUS_SERVER = "https://octopus-test.opsera.io/"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/hemanthreddy-45/POC.git'
            }
        }

        
    }

    post {
        always {
            cleanWs()
        }
    }
}
