# MEI | CDN 2021-2022
Simple IRC - Using Java RMI

<br>

## Project Modules | Clean and Install maven dependencies

- Clone the project and open a terminal inside the project's directory (where the project modules mei_cdn_client, mei_cdn_server and mei_cdn_rmi_interfaces are located)

- Run the following commands to build the application (maven):
  
    > mvn clean install package

<br>

## Build and Execute the Server module:

- Make sure you keep on the same directory (where the pom.xml file of the main module is located)

- Run the following commands on another terminal window, on the same path:

    > cd Server/target/classes

    ~~*If compiling RMI clases is required:*<br>
    rmic -classpath ./target/classes/ -d ./target/classes/ estg.ipp.pt.mei_cdn_server.*server_implementation_class*~~

    > rmiregistry

- Run the following commands on the main terminal window to run the application:

    > cd Server/target/classes

    > java -Djava.security.policy=../../src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.*server_implementation_class*

    java -cp mei_cdn_server-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.security.policy=../src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.TestServerImpl

    java -cp ./mei_cdn_server/target/mei_cdn_server-1.0-SNAPSHOT.jar;./mei_cdn_rmi_interfaces/target/mei_cdn_rmi_interfaces-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false  -Djava.security.policy=./mei_cdn_server/src/main/resources/grant.policy -Djava.rmi.server.codebase=file://./mei_cdn_server/target/mei_cdn_server-1.0-SNAPSHOT.jar estg.ipp.pt.mei_cdn_server.TestServerImpl

<!-- <h2>Project Modules | Clean and Install maven dependencies</h2>

- Clone the project and open a terminal inside the project's directory (where the project modules Client, Server and RMIInterfaces are located)

- Run the following commands to build the application (maven):
  
    > mvn clean install package

<br>

<h2>Build and Execute the Server module:</h2>

- Make sure you keep on the same directory (where the pom.xml file of the main module is located)

- Run the following commands on another terminal window, on the same path:

    > cd Server/target/classes

    ~~*If compiling RMI clases is required:*<br>
    rmic -classpath ./target/classes/ -d ./target/classes/ estg.ipp.pt.mei_cdn_server.*server_implementation_class*~~

    > rmiregistry

- Run the following commands on the main terminal window to run the application:

    > cd Server/target/classes

    > java -Djava.security.policy=../../src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.*server_implementation_class*

    java -cp mei_cdn_server-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.security.policy=../src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.TestServerImpl

    java -cp ./mei_cdn_server/target/mei_cdn_server-1.0-SNAPSHOT.jar;./mei_cdn_rmi_interfaces/target/mei_cdn_rmi_interfaces-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false  -Djava.security.policy=./mei_cdn_server/src/main/resources/grant.policy -Djava.rmi.server.codebase=file://./mei_cdn_server/target/mei_cdn_server-1.0-SNAPSHOT.jar estg.ipp.pt.mei_cdn_server.TestServerImpl

<br>

<h2>Build and Execute (without being inside the generated classes path):</h2>

- Clone the project and open a terminal inside the project's directory (usually right before the 'src' directory)

- Run the following commands to build the application (maven):
  
    > mvn clean

    > mvn package

- Run the following commands on another terminal window, on the same path:

    > rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:/C:/*full path to project directory*/target/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.security.policy=./src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.*server_implementation_class* -->