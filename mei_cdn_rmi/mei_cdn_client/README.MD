# MEI | CDN 2021-2022
Simple IRC Client - Using Java RMI

## It's possible to locally Build and Execute the client module individually, though the mei_cdn_rmi_interfaces dist .jar file is required::

- Make sure you keep on the same directory (where the pom.xml file is located)

- Run the following commands to run the application: 

    > java -cp ./target/mei_cdn_client-1.0-SNAPSHOT.jar;../mei_cdn_rmi_interfaces/target/mei_cdn_mei_cdn_rmi_interfaces-1.0-SNAPSHOT.jar; <br>&emsp;-Djava.rmi.server.useCodebaseOnly=false <br>&emsp;-Djava.rmi.server.codebase=file:./target/mei_cdn_client-1.0-SNAPSHOT.jar <br>&emsp;-Djava.security.policy=./target/classes/grant.policy *client_implementation_class*