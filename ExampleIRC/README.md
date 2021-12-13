# Java-RMI-Chat
A Chat application using java remote method invocation

> cd src

> java -Djava.security.policy=security.policy -cp ../bin/server server.ChatServer

> java -cp ../bin/client;../bin/server; client.ClientRMIGUI

---

### Features:  
- The appplication follows a hub and spoke topology, with the server as the hub.
- Clients logon to the system with a username
- Clients can send a normal chat message(broadcast to all clients)
- Clients can send a private message to one or more clients  
- Server maintains a user list, which is displayed in client GUI
- Online user list is updated on all clients when users join or leave the chat room  