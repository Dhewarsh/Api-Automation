pipeline {
    agent any
    
    tools {
        maven 'M3'
        jdk 'JDK11'
    }
    
    triggers {
        // Run daily at 1 AM
        cron('0 1 * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh 'mvn clean test'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Test execution failed: ${e.message}")
                    }
                }
            }
        }
        
        stage('Generate and Send Report') {
            steps {
                script {
                    // Archive the test results
                    junit '**/target/surefire-reports/TEST-*.xml'
                    
                    // Archive the HTML reports
                    archiveArtifacts artifacts: '**/target/surefire-reports/**/*.*', fingerprint: true
                    
                    // Send email with test results
                    emailext body: '''${SCRIPT, template="groovy-html.template"}''',
                        attachLog: true,
                        attachmentsPattern: '**/target/surefire-reports/emailable-report.html',
                        mimeType: 'text/html',
                        subject: "API Test Report - ${BUILD_NUMBER}",
                        to: '${DEFAULT_RECIPIENTS}'
                }
            }
        }
    }
    
    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        failure {
            // Send notification on failure
            emailext body: "Pipeline failed. Please check the build log at ${BUILD_URL}",
                subject: "Pipeline Failed - ${BUILD_NUMBER}",
                to: '${DEFAULT_RECIPIENTS}'
        }
    }
}
