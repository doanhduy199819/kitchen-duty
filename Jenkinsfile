pipeline {
    agent any
    tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "Maven"
    }
    stages {
        stage('Build') {
            steps {
                 sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
