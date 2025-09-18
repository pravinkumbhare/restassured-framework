pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test -Dsurefire.useFile=false'
            }
        }

        stage('Allure Report') {
            steps {
                // Generate Allure report in Jenkins from allure-results
                allure([
                  includeProperties: false,
                  jdk: '',
                  results: [[path: 'allure-results']]
                ])

                // Archive the generated HTML report as artifact
                archiveArtifacts artifacts: 'allure-report/**/*', allowEmptyArchive: true

                // Publish HTML report in Jenkins UI
                publishHTML([
                    reportDir: 'allure-report',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report',
                    keepAll: true,
                    allowMissing: true
                ])
            }
        }

       stage('Extent Report') {
           steps {
               // Archive Extent report
               archiveArtifacts artifacts: 'target/extent-report/**/*', allowEmptyArchive: true

               // Publish Extent report in Jenkins UI
               publishHTML([
                   reportDir: 'target/extent-report',
                   reportFiles: 'index.html',
                   reportName: 'Extent Report',
                   keepAll: true,
                   allowMissing: true
               ])
           }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
