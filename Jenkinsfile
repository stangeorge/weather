pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(url: 'https://github.com/stangeorge/weather', branch: 'master', poll: true)
        echo 'Checkout complete'
      }
    }
    stage('Gradle build') {
      steps {
          sh './gradlew clean'
      }
    }
  }
  environment {
    dev = 'dev'
  }
}