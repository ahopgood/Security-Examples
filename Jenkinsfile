pipeline {
	agent  { label 'java11' }
	stages {
	    stage('build') {
            git credentialsId: 'github_token', url: 'https://github.com/ahopgood/Security-Examples.git', branch: '${BRANCH_NAME}'
            sh 'mvn --version'
            sh 'mvn clean install'
	    }
    }
    post {
    	always {
        }
    }
}