def utils //Global utils variable → accessible in all stages.

pipeline {
    agent any
    tools { maven 'maven-3.9' }
    stages {
        stage('init') {
            steps {
                script{
                    utils = load 'jenkins/utils.groovy'
                }
            }
        }
        stage('test') {
            steps {
                script {
                    utils.testApp()
                }
            }
        }

        stage('build') {
            steps {
                script {
                    echo 'Building the application....'
                    sh 'mvn clean package'
                }
            }
        }

        stage('deploy') {
            steps {
                script {
                    echo 'Deploying the application....'
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                            sh 'docker build -t "$DOCKER_USER/java-maven-app:latest" .'
                            sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                            sh 'docker push "$DOCKER_USER/java-maven-app:latest"'
                        }
                }
            }
        }
    }
}
