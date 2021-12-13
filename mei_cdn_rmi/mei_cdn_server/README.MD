# MEI | CDN 2021-2022
Simple IRC Server - Using Java RMI

<br>

## It's possible to Build and Execute the server module individually:

- Make sure you keep on the same directory (where the pom.xml file is located)

- Run the following commands on another terminal window, on the same path:

    ~~*If compiling RMI clases is required:*<br>
    rmic -classpath ./target/classes/ -d ./target/classes/ estg.ipp.pt.mei_cdn_server.*server_implementation_class*~~

    > rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:./target/mei_cdn_server-1.0-SNAPSHOT.jar -Djava.security.policy=./src/main/resources/grant.policy estg.ipp.pt.mei_cdn_server.*server_implementation_class*