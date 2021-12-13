# MEI | CDN 2021-2022
Simple IRC Client - Using Java RMI

<br>

## It's possible to Build and Execute the server module individually:

- Make sure you keep on the same directory (where the pom.xml file is located)

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:./target/mei_cdn_client-1.0-SNAPSHOT.jar -Djava.security.policy=./src/main/resources/grant.policy estg.ipp.pt.mei_cdn_client.*client_implementation_class*