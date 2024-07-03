pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "your-docker-image-name"
        SONARQUBE_SERVER_URL = "http://sonarqube-soau2w14.ldapowner.opsera.io"
        SONARQUBE_TOKEN = "HEnqq0Zw3c" // Directly using the provided token
        OCTOPUS_SERVER = "https://octopus-test.opsera.io"
        OCTOPUS_API_KEY = "JMLK3JPHLP5YPMHIGGFZKJEO8G" // Directly using the provided API key
        OCTOPUS_PROJECT = "YourOctopusProject"
        OCTOPUS_ENVIRONMENT = "Production"
        OCTOPUS_RELEASE_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://your-repo-url.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'chmod +x build.sh'
                    sh './build.sh'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh """
                        docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} .
                    """
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh """
                        sonar-scanner \
                        -Dsonar.projectKey=your-project-key \
                        -Dsonar.sources=. \
                        -Dsonar.host.url=${SONARQUBE_SERVER_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN}
                    """
                }
            }
        }

        stage('Octopus Deploy') {
            steps {
                script {
                    sh """
                        docker tag ${DOCKER_IMAGE}:${env.BUILD_NUMBER} your-registry/${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                        docker push your-registry/${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                    """

                    sh """
                        curl -X POST ${OCTOPUS_SERVER}/api/releases \
                        -H "X-Octopus-ApiKey: ${OCTOPUS_API_KEY}" \
                        -H "Content-Type: application/json" \
                        -d '{
                            "ProjectId": "${OCTOPUS_PROJECT}",
                            "Version": "${OCTOPUS_RELEASE_VERSION}",
                            "ReleaseNotes": "Release created by Jenkins",
                            "SelectedPackages": [{
                                "StepName": "Deploy Step",
                                "Version": "${env.BUILD_NUMBER}"
                            }]
                        }'

                        curl -X POST ${OCTOPUS_SERVER}/api/deployments \
                        -H "X-Octopus-ApiKey: ${OCTOPUS_API_KEY}" \
                        -H "Content-Type: application/json" \
                        -d '{
                            "ReleaseId": "Release-ID-from-above-step",
                            "EnvironmentId": "${OCTOPUS_ENVIRONMENT}"
                        }'
                    """
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
