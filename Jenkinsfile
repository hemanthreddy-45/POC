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

        stage('Build with Gradle') {
            steps {
                script {
                    sh './gradlew build'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }

        stage('Octopus Deploy') {
            steps {
                script {
                    def octopus = load 'octopus-deploy.groovy'
                    octopus.deploy()
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
