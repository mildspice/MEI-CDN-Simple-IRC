# MEI | CDN 2021-2022
Simple IRC Server - Using Java RMI

## It's possible to locally Build and Execute the server module individually, though the mei_cdn_rmi_interfaces dist .jar file is required:

- Make sure you keep on the same directory (where the pom.xml file is located)

- ~~Run the following commands on another terminal window, on the same path:~~ *DEPRECATED: the server itself is now responsible for starting the RMI registry*

    ~~*If compiling RMI clases is required:*<br> 
    rmic -classpath ./target/classes/ -d ./target/classes/ estg.ipp.pt.mei_cdn_server.*server_implementation_class*~~

    ~~rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false~~

- Run the following commands to run the application:

    > java -cp ./target/mei_cdn_server-1.0-SNAPSHOT.jar;../mei_cdn_rmi_interfaces/target/mei_cdn_mei_cdn_rmi_interfaces-1.0-SNAPSHOT.jar; <br>&emsp;-Djava.rmi.server.useCodebaseOnly=false <br>&emsp;-Djava.rmi.server.codebase="file:./target/mei_cdn_server-1.0-SNAPSHOT.jar file:../mei_cdn_rmi_interfaces/target/mei_cdn_mei_cdn_rmi_interfaces-1.0-SNAPSHOT.jar" <br>&emsp;-Djava.security.policy=./target/classes/grant.policy *server_implementation_class*