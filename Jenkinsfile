pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(url: 'https://github.com/stangeorge/weather', branch: 'master', poll: true)
      }
    }
    stage('') {
      steps {
        echo 'Checkout complete'
      }
    }
  }
  environment {
    dev = 'dev'
  }
}