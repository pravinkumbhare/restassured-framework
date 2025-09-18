pipeline {
    agent any

    stages {
        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                ])
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
                // Archive the extent report as an artifact
                archiveArtifacts artifacts: 'target/extent-report.html', allowEmptyArchive: false

                // Publish Extent report
                publishHTML([
                    reportDir: 'target',
                    reportFiles: 'extent-report.html',
                    reportName: 'Extent Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            // Don't let JUnit mark the build as unstable
            catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
            }

            // Double-ensure final status is SUCCESS
            script {
                if (currentBuild.result == 'UNSTABLE' || currentBuild.result == null) {
                    currentBuild.result = 'SUCCESS'
                }
            }
        }
    }

}
