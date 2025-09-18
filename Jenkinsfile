pipeline {
    agent any

    stages {
        stage('Build & Test') {
            steps {
                // Run Maven tests, but don't fail the build if tests fail
                bat 'mvn clean test -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Allure Report') {
            steps {
                // Generate Allure report
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                ])
                // Publish HTML report
                publishHTML([
                    reportDir: 'allure-report',
                    reportFiles: 'index.html',
                    reportName: 'Allure Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }

        stage('Extent Report') {
            steps {
                // Archive Extent report artifact
                archiveArtifacts artifacts: 'target/extent-report.html', allowEmptyArchive: true
                // Publish Extent HTML report
                publishHTML([
                    reportDir: 'target',
                    reportFiles: 'extent-report.html',
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])
            }
        }

        stage('Publish Test Results') {
            steps {
                // Capture JUnit results safely
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo "Pipeline finished. Reports have been published."
        }
    }
}
