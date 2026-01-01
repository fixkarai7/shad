import javax.swing.*;
import java.awt.*;

public class LibraryApp extends JFrame {

    JTextField userField, passField;
    JTextField titleField, authorField, categoryField;
    JTextArea output;

    public LibraryApp() {

        Database.init();

        setTitle("Library Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bg = new Color(245, 247, 250);
        Color header = new Color(52, 73, 94);

        // Header
        JLabel title = new JLabel("Library Management System", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(header);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(100, 50));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(bg);

        // Login
        JPanel login = new JPanel();
        userField = new JTextField(10);
        passField = new JTextField(10);
        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");

        login.add(new JLabel("User"));
        login.add(userField);
        login.add(new JLabel("Pass"));
        login.add(passField);
        login.add(loginBtn);
        login.add(regBtn);

        // Book
        JPanel book = new JPanel();
        titleField = new JTextField(8);
        authorField = new JTextField(8);
        categoryField = new JTextField(8);
        JButton addBook = new JButton("Add Book");
        JButton recommend = new JButton("Recommend");

        book.add(new JLabel("Title"));
        book.add(titleField);
        book.add(new JLabel("Author"));
        book.add(authorField);
        book.add(new JLabel("Category"));
        book.add(categoryField);
        book.add(addBook);
        book.add(recommend);

        // Output
        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);

        panel.add(login);
        panel.add(book);
        panel.add(scroll);

        add(panel);

        // Actions
        loginBtn.addActionListener(e ->
                output.setText(UserService.login(
                        userField.getText(), passField.getText())
                        ? "Login Successful"
                        : "Login Failed")
        );

        regBtn.addActionListener(e ->
                output.setText(UserService.register(
                        userField.getText(), passField.getText())
                        ? "User Registered"
                        : "User Exists")
        );

        addBook.addActionListener(e -> {
            BookService.addBook(
                    titleField.getText(),
                    authorField.getText(),
                    categoryField.getText());
            output.setText("Book Added");
        });

        recommend.addActionListener(e -> {
            output.setText("Recommended Books:\n");
            for (String b : BookService.getRecommendations(categoryField.getText())) {
                output.append(b + "\n");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryApp();import javax.swing.*;
import java.awt.*;

public class LibraryApp extends JFrame {

    JTextField userField, passField;
    JTextField titleField, authorField, categoryField;
    JTextArea output;

    public LibraryApp() {

        Database.init();

        setTitle("Library Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bg = new Color(245, 247, 250);
        Color header = new Color(52, 73, 94);

        // Header
        JLabel title = new JLabel("Library Management System", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(header);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(100, 50));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(bg);

        // Login
        JPanel login = new JPanel();
        userField = new JTextField(10);
        passField = new JTextField(10);
        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");

        login.add(new JLabel("User"));
        login.add(userField);
        login.add(new JLabel("Pass"));
        login.add(passField);
        login.add(loginBtn);
        login.add(regBtn);

        // Book
        JPanel book = new JPanel();
        titleField = new JTextField(8);
        authorField = new JTextField(8);
        categoryField = new JTextField(8);
        JButton addBook = new JButton("Add Book");
        JButton recommend = new JButton("Recommend");

        book.add(new JLabel("Title"));
        book.add(titleField);
        book.add(new JLabel("Author"));
        book.add(authorField);
        book.add(new JLabel("Category"));
        book.add(categoryField);
        book.add(addBook);
        book.add(recommend);

        // Output
        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);

        panel.add(login);
        panel.add(book);
        panel.add(scroll);

        add(panel);

        // Actions
        loginBtn.addActionListener(e ->
                output.setText(UserService.login(
                        userField.getText(), passField.getText())
                        ? "Login Successful"
                        : "Login Failed")
        );

        regBtn.addActionListener(e ->
                output.setText(UserService.register(
                        userField.getText(), passField.getText())
                        ? "User Registered"
                        : "User Exists")
        );

        addBook.addActionListener(e -> {
            BookService.addBook(
                    titleField.getText(),
                    authorField.getText(),
                    categoryField.getText());
            output.setText("Book Added");
        });

        recommend.addActionListener(e -> {
            output.setText("Recommended Books:\n");
            for (String b : BookService.getRecommendations(categoryField.getText())) {
                output.append(b + "\n");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryApp();
    }
}

    }
}
