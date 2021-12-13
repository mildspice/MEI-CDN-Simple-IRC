# MEI | CDN 2021-2022
Simple IRC Client - Using Java RMI

<h2>Project Modules | Clean and Install maven dependencies</h2>

- Clone the project and open a terminal inside the project directory (where the pom.xml file is located)

- Run the following commands to build the application (maven):
  
    > mvn clean install package

<br>

<h2>Build and Execute the client module:</h2>

- Make sure you keep on the same directory (where the pom.xml file is located)

- Run the following commands on the main terminal window to run the application:

    > cd target/classes

    > java -Djava.security.policy=grant.policy estg.ipp.pt.mei_cdn_client.*client_implementation_class*

    Example:
    > java -Djava.security.policy=grant.policy estg.ipp.pt.mei_cdn_client.TestClient

<br>

<h2>Build and Execute (without being inside the generated classes path):</h2>

- Make sure you keep on the same directory (where the pom.xml file is located)

- Run the following commands on another terminal window, on the same path:

    > rmiregistry -J-Djava.rmi.client.useCodebaseOnly=false

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.rmi.client.useCodebaseOnly=false -Djava.rmi.client.codebase=file:/C:/*full path to project directory*/target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.security.policy=./src/main/resources/grant.policy estg.ipp.pt.mei_cdn_client.*client_implementation_class*