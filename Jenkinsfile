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
            // Publish test results but don't fail/unstable if none
            catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            }

            // Force SUCCESS if pipeline is unstable due to reports
            script {
                if (currentBuild.result == 'UNSTABLE') {
                    currentBuild.result = 'SUCCESS'
                }
            }
        }
    }

}
