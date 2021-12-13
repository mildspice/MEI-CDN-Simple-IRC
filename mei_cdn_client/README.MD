# MEI | CDN 2021-2022
Simple IRC Client - Using Java RMI

<h2>Build and Execute:</h2>

- Clone the project and open a terminal inside the project's source code directory (usually named 'src')


- Run the following commands:

    > rmiregistry

    > java -Djava.security.policy=./main/resources/grant.policy estg.ipp.pt.code_mei_client

# MEI | CDN 2021-2022
Simple IRC Client - Using Java RMI

<h2>Build and Execute:</h2>

- Clone the project and open a terminal inside the project's directory (usually right before the 'src' directory)

- Run the following commands to build the application (maven):
  
    > mvn clean

    > mvn package

- Run the following commands on another terminal window, on the same path:

    > cd target/classes

    ~~*If compiling RMI clases is required:*<br>
    rmic -classpath ./target/classes/ -d ./target/classes/ estg.ipp.pt.mei_cdn_client.*client_implementation_class*~~

    > rmiregistry

- Run the following commands to run the application:

    > cd target/classes

    > java -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=../../src/main/resources/grant.policy estg.ipp.pt.mei_cdn_client.*client_implementation_class*

<br>

<h2>Build and Execute (without being inside the generated classes path):</h2>

- Clone the project and open a terminal inside the project's directory (usually right before the 'src' directory)

- Run the following commands to build the application (maven):
  
    > mvn clean

    > mvn package

- Run the following commands on another terminal window, on the same path:

    > rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:/C:/*full path to project directory*/target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.security.policy=./src/main/resources/grant.policy estg.ipp.pt.mei_cdn_client.*client_implementation_class*