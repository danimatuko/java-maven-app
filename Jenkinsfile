pipeline {
    agent any
    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Git branch to build')
        choice(name: 'ENV', choices: ['staging', 'prod'], description: 'Deployment environment')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests before deploy?')
    }
    stages {
        stage('Build') {
            steps {
                echo "Building branch ${params.BRANCH}"
            }
        }
        stage('Test') {
            when { expression { params.RUN_TESTS } }
            steps {
                echo "Running tests..."
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying to ${params.ENV}"
            }
        }
    }
}
