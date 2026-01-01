import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Chat Server Started...");

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    static void broadcastUserList() {
        try {
            for (ClientHandler c : clients) {
                c.out.writeUTF("USER_LIST");
                c.out.writeInt(clients.size());
                for (ClientHandler u : clients) {
                    c.out.writeUTF(u.username);
                }
                c.out.flush();
            }
        } catch (Exception ignored) {}
    }

    static class ClientHandler implements Runnable {

        Socket socket;
        DataInputStream in;
        DataOutputStream out;
        String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                username = in.readUTF();
                broadcast("🟢 " + username + " joined");
                broadcastUserList();

                while (true) {
                    String type = in.readUTF();

                    if (type.equals("MSG")) {
                        broadcast(username + ": " + in.readUTF());

                    } else if (type.equals("FILE")) {
                        String name = in.readUTF();
                        long size = in.readLong();
                        byte[] data = new byte[(int) size];
                        in.readFully(data);

                        for (ClientHandler c : clients) {
                            c.out.writeUTF("FILE");
                            c.out.writeUTF(name);
                            c.out.writeLong(size);
                            c.out.write(data);
                            c.out.flush();
                        }
                    }
                }

            } catch (Exception e) {
                clients.remove(this);
                broadcast("🔴 " + username + " left");
                broadcastUserList();
            }
        }

        void broadcast(String msg) {
            for (ClientHandler c : clients) {
                try {
                    c.out.writeUTF("MSG");
                    c.out.writeUTF(msg);
                    c.out.flush();
                } catch (Exception ignored) {}
            }
        }
    }
}
