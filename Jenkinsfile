env.DOCKERHUB_USERNAME = 'esswhye'
env.project = 'arvent-order'
env.dockerproject = 'arvent_order'
env.dockerportest = '1111:12142'
//${dockerproject}
/*
    Not a very CI/CD Way due to limitation of server  i have (Only using 2 server in 1 environment)
*/
node{
    /*stage('Git') {
                steps {
                    step([$class: 'WsCleanup'])
                    checkout scm
                }
            }
    */
    stage('Clone Sources')
    {
     checkout scm
    }

    stage("Unit Test & Build") {
              //An Example
              //sh 'mvn test'
              sh 'mvn clean install'
             try{
              //sh 'docker login'
              sh 'docker build -t ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER} -f DockerfileTest .'
              }
              catch(e){
                sh 'docker image rm -f ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER}'
               }
            }
    stage("Integration Test") {
          try {
            //Removing the server in order to test (CPU limitation and ports)
            sh "docker service rm ${dockerproject} || true"


            sh "docker rm -f ${project} || true"
            sh "docker run -d --name=${project} --network arvent_backend ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER}"
          }
          catch(e) {
            error "Integration Test failed"
          }finally {
            sh "docker rm -f ${project} || true"
            sh "docker ps -aq | xargs docker rm || true"
            sh "docker images -aq -f dangling=true | xargs docker rmi || true "
          }
        }

       // stage("Build") {
       //       sh "docker build -t ${DOCKERHUB_USERNAME}/arvent-gateway:${BUILD_NUMBER} -f DockerfileTest  ."
       //     }
      stage("Publish") {
            withDockerRegistry([credentialsId: 'DockerHub']) {
                //sh "docker push ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER}"
                sh "docker image tag ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER} ${DOCKERHUB_USERNAME}/${project}:latest"
                sh "docker push ${DOCKERHUB_USERNAME}/${project}:latest"

              }
              sh "docker image rm ${DOCKERHUB_USERNAME}/${project}:${BUILD_NUMBER}"
              sh "docker image rm ${DOCKERHUB_USERNAME}/${project}:latest"

            }
       stage("Deploy"){
            sh "docker stack deploy arvent --compose-file=./docker-compose.yml --with-registry-auth "
       }
  }