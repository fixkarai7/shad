import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

class Item {
    String id;
    String name;
    int quantity;
    double price;

    Item(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}

public class InventoryManagementGUI extends JFrame {

    JTextField idField, nameField, qtyField, priceField;
    JTable table;
    DefaultTableModel model;
    ArrayList<Item> inventory = new ArrayList<>();

    public InventoryManagementGUI() {

        setTitle("Inventory Management System");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== COLORS =====
        Color bgColor = new Color(245, 247, 250);
        Color headerColor = new Color(33, 45, 62);
        Color addColor = new Color(46, 204, 113);
        Color updateColor = new Color(52, 152, 219);
        Color deleteColor = new Color(231, 76, 60);
        Color clearColor = new Color(149, 165, 166);

        // ===== HEADER =====
        JLabel title = new JLabel("Inventory Management System", JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(headerColor);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setPreferredSize(new Dimension(100, 50));
        add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        formPanel.setBackground(bgColor);

        idField = new JTextField();
        nameField = new JTextField();
        qtyField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Item ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Item Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(qtyField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(bgColor);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        styleButton(addBtn, addColor);
        styleButton(updateBtn, updateColor);
        styleButton(deleteBtn, deleteColor);
        styleButton(clearBtn, clearColor);

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Item ID", "Name", "Quantity", "Price"}, 0);

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader th = table.getTableHeader();
        th.setBackground(headerColor);
        th.setForeground(Color.WHITE);
        th.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(bgColor);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        addBtn.addActionListener(e -> addItem());
        updateBtn.addActionListener(e -> updateItem());
        deleteBtn.addActionListener(e -> deleteItem());
        clearBtn.addActionListener(e -> clearFields());

        // Table click → auto-fill
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                idField.setText(model.getValueAt(row, 0).toString());
                nameField.setText(model.getValueAt(row, 1).toString());
                qtyField.setText(model.getValueAt(row, 2).toString());
                priceField.setText(model.getValueAt(row, 3).toString());
            }
        });

        setVisible(true);
    }

    // ===== BUTTON STYLE METHOD =====
    void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(100, 35));
    }

    // ===== CRUD =====
    void addItem() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            int qty = Integer.parseInt(qtyField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            model.addRow(new Object[]{id, name, qty, price});
            inventory.add(new Item(id, name, qty, price));
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    void updateItem() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.setValueAt(idField.getText(), row, 0);
        model.setValueAt(nameField.getText(), row, 1);
        model.setValueAt(qtyField.getText(), row, 2);
        model.setValueAt(priceField.getText(), row, 3);
        clearFields();
    }

    void deleteItem() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.removeRow(row);
        inventory.remove(row);
        clearFields();
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        new InventoryManagementGUI();
    }
}
