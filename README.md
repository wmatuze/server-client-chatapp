#Client-Server Chat Application
The client-server chat application is a simple networked messaging system that allows multiple users to engage in real-time communication. The application consists of two main components: the server and the client.

Server:

    The server component is responsible for handling incoming client connections and managing communication between clients.
    Upon starting, the server creates a listening socket on a specified port (e.g., port 5000) to accept incoming connections
    from clients.
    When a client connects, the server accepts the connection, assigns a unique username to the client, and creates a new thread
    to handle the client's communication.
    Each client thread listens for messages from its respective client and broadcasts those messages to all other connected
    clients, allowing for group chat functionality.
    When a client disconnects, the server closes the corresponding socket and removes the client from the list of active clients.

Client:

    The client component represents an individual user who wants to participate in the chat.
    When the client program starts, it connects to the server using the server's IP address and port number.
    Upon connecting, the client is prompted to enter a unique username.
    The client then runs two threads: one for sending messages to the server and another for receiving messages from the server.
    The user can type messages on the console, and the client sends them to the server, which then relays the message to all
    other connected clients.
    At the same time, the client constantly listens for messages from the server and displays them on the console.

Communication Flow:

    Multiple clients connect to the server and provide unique usernames.
    Clients can send messages to the server.
    The server relays the messages to all other clients except the sender.
    Clients display received messages on their console, creating a real-time chat experience.
    With this client-server chat application, multiple users can simultaneously communicate with each other in a chat room-like
    environment, making it a basic yet functional example of networked messaging.
