import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {

    JFrame frame;
    JTextArea chatArea;
    JTextField input;
    DefaultListModel<String> userModel;
    JList<String> userList;

    DataInputStream in;
    DataOutputStream out;

    public ChatClientGUI() {

        frame = new JFrame("💬 Java Chat");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ----- Chat Area -----
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // ----- User List -----
        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        userList.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane userPane = new JScrollPane(userList);
        userPane.setPreferredSize(new Dimension(150, 0));

        // ----- Input -----
        input = new JTextField();
        JButton sendBtn = new JButton("Send");
        JButton fileBtn = new JButton("📎");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.add(sendBtn);
        actions.add(fileBtn);
        bottom.add(actions, BorderLayout.EAST);

        // ----- Layout -----
        frame.add(userPane, BorderLayout.WEST);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMessage());
        fileBtn.addActionListener(e -> sendFile());

        frame.setVisible(true);

        connect();
    }

    void connect() {
        try {
            Socket socket = new Socket("localhost", 1234);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String name = JOptionPane.showInputDialog("Enter your name:");
            out.writeUTF(name);

            new Thread(this::receive).start();

        } catch (Exception e) {
            chatArea.append("Connection failed\n");
        }
    }

    void sendMessage() {
        try {
            out.writeUTF("MSG");
            out.writeUTF(input.getText());
            out.flush();
            input.setText("");
        } catch (Exception ignored) {}
    }

    void sendFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = fis.readAllBytes();

                out.writeUTF("FILE");
                out.writeUTF(file.getName());
                out.writeLong(data.length);
                out.write(data);
                out.flush();

                chatArea.append("📤 File sent: " + file.getName() + "\n");
            } catch (Exception ignored) {}
        }
    }

    void receive() {
        try {
            while (true) {
                String type = in.readUTF();

                if (type.equals("MSG")) {
                    chatArea.append(in.readUTF() + "\n");

                } else if (type.equals("USER_LIST")) {
                    userModel.clear();
                    int count = in.readInt();
                    for (int i = 0; i < count; i++) {
                        userModel.addElement(in.readUTF());
                    }

                } else if (type.equals("FILE")) {
                    String name = in.readUTF();
                    long size = in.readLong();
                    byte[] data = new byte[(int) size];
                    in.readFully(data);

                    FileOutputStream fos = new FileOutputStream("received_" + name);
                    fos.write(data);
                    fos.close();

                    chatArea.append("📥 File received: " + name + "\n");
                }
            }
        } catch (Exception e) {
            chatArea.append("Disconnected\n");
        }
    }

    public static void main(String[] args) {
        new ChatClientGUI();
    }
}
