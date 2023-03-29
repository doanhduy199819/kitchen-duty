pipeline {
    agent any
    tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "Maven"
            jdk "Java 8"
    }
    stages {
        stage('Build') {
            steps {
                 sh 'echo $JAVA_HOME'
                 sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
