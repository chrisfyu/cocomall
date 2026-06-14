pipeline {
  agent {
    node {
      label 'maven'
    }
  }

  environment {
    DOCKER_CREDENTIAL_ID = 'dockerhub-id'
    GITHUB_CREDENTIAL_ID = 'github-id'
    KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
    REGISTRY = 'docker.io'
    DOCKERHUB_NAMESPACE = 'chrisfyu1'
    GITHUB_ACCOUNT = 'chrisfyu@hotmail.com'
    SONAR_CREDENTIAL_ID = 'sonar-qube'
    BRANCH_NAME = 'dev'
  }

  stages {
    stage('pull code') {
      steps {
        git(credentialsId: 'github-id', url: 'https://github.com/chrisfyu/cocomall.git', branch: 'dev', changelog: true, poll: false)
        sh 'echo Building $PROJECT_NAME Version: $PROJECT_VERSION, pushing it into $REGISTRY repository.'
        sh "echo 正在完整编译项目"
        container ('maven') {
          sh "mvn clean install -Dmaven.test.skip=true -gs `pwd`/mvn-settings.xml"
        }
      }
    }

    // stage('sonarqube analysis') {
    //   steps {
    //     container ('maven') {
    //       withCredentials([string(credentialsId: "$SONAR_CREDENTIAL_ID", variable: 'SONAR_TOKEN')]) {
    //         withSonarQubeEnv('sonar') {
    //           sh "echo current folder: `pwd`"
    //           sh "mvn sonar:sonar -gs `pwd`/mvn-settings.xml -Dsonar.branch=$BRANCH_NAME -Dsonar.login=$SONAR_TOKEN"
    //         }
    //       }
    //       timeout(time: 1, unit: 'HOURS') {
    //         waitForQualityGate abortPipeline: true
    //       }
    //     }
    //   }
    // }

    stage ('build image & push snapshot') {
      steps {
        container ('maven') {
          sh 'mvn  -Dmaven.test.skip=true -gs `pwd`/mvn-settings.xml clean package'
          sh 'cd $PROJECT_NAME && docker build -f Dockerfile -t $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER .'
          withCredentials([usernamePassword(passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,credentialsId : "$DOCKER_CREDENTIAL_ID" ,)]) {
              sh 'echo "$DOCKER_PASSWORD" | docker login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin'
              sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER'
          }
        }
      }
    }

    stage('push latest'){
      when{
       branch 'dev'
      }
      steps{
        container ('maven') {
          sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest '
          sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest '
        }
      }
    }

    stage('deploy to k8s') {
      when{
        branch 'dev'
      }
      steps {
        input(id: 'deploy-to-dev-$PROJECT_NAME', message: 'Deploy $PROJECT_NAME to k8s clusters?')
        kubernetesDeploy(configs: '$PROJECT_NAME/deploy/**', enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
      }
    }

    stage('publish with tag'){
      when{
        expression{
          return params.PROJECT_VERSION =~ /v.*/
        }
      }
      steps {
        container ('maven') {
          input(id: 'release-image-with-tag', message: 'release image with tag?')
            withCredentials([usernamePassword(credentialsId: "$GITHUB_CREDENTIAL_ID", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
              sh 'git config --global user.email "chrisfyu@hotmail.com" '
              sh 'git config --global user.name "yufei" '
              sh 'git tag -a $PROJECT_VERSION -m "$PROJECT_VERSION" '
              sh 'git push http://$GIT_USERNAME:$GIT_PASSWORD@github.com/$GITHUB_ACCOUNT/cocomall.git --tags --ipv4'
            }
          sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
          sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
        }
      }
    }
  }

  parameters {
    string(name: 'PROJECT_VERSION', defaultValue: 'v0.0Beta', description: '')
    string(name: 'PROJECT_NAME', defaultValue: 'cocomall-gateway', description: '')
  }
}