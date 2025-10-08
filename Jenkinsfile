#!/usr/bin/env groovy

// Load the shared library; 'shared-lib' is the Name set in Jenkins → Global Pipeline Libraries
@Library('shared-lib') _

pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    stages {
        stage('Build JAR') {
            steps {
                buildJar()   // step provided by shared library
            }
        }

        stage('Build Image') {
            steps {
                buildImage() // step provided by shared library
            }
        }

        stage('Deploy') {
            steps {
                deployApp()  // step provided by shared library
            }
        }
    }
}
