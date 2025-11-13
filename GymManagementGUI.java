import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Member {
    String id, name, membership, payment;
    int age;

    Member(String id, String name, int age, String membership, String payment) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.membership = membership;
        this.payment = payment;
    }
}

public class GymManagementGUI extends JFrame {

    // Storage
    private ArrayList<Member> members = new ArrayList<>();
    private DefaultTableModel tableModel;

    // Login Panel components
    private JPanel loginPanel, mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Member form components
    private JTextField idField, nameField, ageField, membershipField, paymentField;

    public GymManagementGUI() {
        setTitle("Gym Membership Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Initialize panels
        loginPanel = createLoginPanel();
        mainPanel = createMainPanel();

        add(loginPanel, "Login");
        add(mainPanel, "Main");

        showLogin();
    }

    // ------------------ LOGIN PANEL ------------------
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(220, 240, 250));

        JLabel title = new JLabel("GYM LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(30, 144, 255));
        loginBtn.setForeground(Color.white);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (user.equals("admin") && pass.equals("123")) {
                showMain();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials! Try again.");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy++; panel.add(userLabel, gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(passLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);
        gbc.gridy++; panel.add(loginBtn, gbc);

        return panel;
    }

    // ------------------ MAIN PANEL ------------------
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);

        JLabel header = new JLabel("Gym Membership Management", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(header, BorderLayout.NORTH);

        // Table for viewing members
        String[] columns = {"ID", "Name", "Age", "Membership", "Payment"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form Panel
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Member Details"));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        membershipField = new JTextField();
        paymentField = new JTextField();

        form.add(new JLabel("Member ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Age:")); form.add(ageField);
        form.add(new JLabel("Membership Type:")); form.add(membershipField);
        form.add(new JLabel("Payment Status:")); form.add(paymentField);

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton viewBtn = new JButton("View All");
        JButton logoutBtn = new JButton("Logout");

        addBtn.addActionListener(e -> addMember());
        viewBtn.addActionListener(e -> viewMembers());
        updateBtn.addActionListener(e -> updateMember());
        deleteBtn.addActionListener(e -> deleteMember());
        logoutBtn.addActionListener(e -> showLogin());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(logoutBtn);

        panel.add(form, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ------------------ METHODS ------------------
    private void addMember() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String membership = membershipField.getText();
            String payment = paymentField.getText();

            members.add(new Member(id, name, age, membership, payment));
            JOptionPane.showMessageDialog(this, "Member Added Successfully!");
            clearFields();
            viewMembers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid details!");
        }
    }

    private void viewMembers() {
        tableModel.setRowCount(0);
        for (Member m : members) {
            tableModel.addRow(new Object[]{m.id, m.name, m.age, m.membership, m.payment});
        }
    }

    private void updateMember() {
        String id = idField.getText();
        boolean found = false;
        for (Member m : members) {
            if (m.id.equalsIgnoreCase(id)) {
                m.name = nameField.getText();
                m.age = Integer.parseInt(ageField.getText());
                m.membership = membershipField.getText();
                m.payment = paymentField.getText();
                JOptionPane.showMessageDialog(this, "Member Updated Successfully!");
                found = true;
                break;
            }
        }
        if (!found)
            JOptionPane.showMessageDialog(this, "Member ID Not Found!");
        viewMembers();
    }

    private void deleteMember() {
        String id = idField.getText();
        boolean removed = members.removeIf(m -> m.id.equalsIgnoreCase(id));
        if (removed)
            JOptionPane.showMessageDialog(this, "Member Deleted Successfully!");
        else
            JOptionPane.showMessageDialog(this, "Member ID Not Found!");
        viewMembers();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        membershipField.setText("");
        paymentField.setText("");
    }

    // ------------------ PANEL SWITCHING ------------------
    private void showLogin() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Login");
    }

    private void showMain() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Main");
    }

    // ------------------ MAIN ------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GymManagementGUI().setVisible(true);
        });
    }
}
