import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Contact {
    String name;
    String phone;
    String email;

    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

public class ContactManagerProGUI extends JFrame {

    JTextField nameField, phoneField, emailField;
    JTable table;
    DefaultTableModel model;

    ArrayList<Contact> contacts = new ArrayList<>();

    public ContactManagerProGUI() {

        setTitle("Contact Management System");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== LAYOUT =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====

        addBtn.addActionListener(e -> addContact());
        updateBtn.addActionListener(e -> updateContact());
        deleteBtn.addActionListener(e -> deleteContact());
        clearBtn.addActionListener(e -> clearFields());

        // ===== TABLE CLICK EVENT =====
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                nameField.setText(model.getValueAt(row, 0).toString());
                phoneField.setText(model.getValueAt(row, 1).toString());
                emailField.setText(model.getValueAt(row, 2).toString());
            }
        });

        setVisible(true);
    }

    // ===== CRUD METHODS =====

    void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        Contact c = new Contact(name, phone, email);
        contacts.add(c);
        model.addRow(new Object[]{name, phone, email});

        clearFields();
    }

    void updateContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a contact to update");
            return;
        }

        model.setValueAt(nameField.getText(), row, 0);
        model.setValueAt(phoneField.getText(), row, 1);
        model.setValueAt(emailField.getText(), row, 2);

        JOptionPane.showMessageDialog(this, "Contact Updated");
        clearFields();
    }

    void deleteContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a contact to delete");
            return;
        }

        model.removeRow(row);
        contacts.remove(row);

        JOptionPane.showMessageDialog(this, "Contact Deleted");
        clearFields();
    }

    void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        new ContactManagerProGUI();
    }
}
