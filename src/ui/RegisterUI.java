package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import dao.UserDAO;

public class RegisterUI extends JFrame {
    public RegisterUI() {
        setTitle("Register");
        setSize(1150, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background Image
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\LENOVO\\OneDrive\\Desktop\\14.jpg"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Translucent Capsule Panel
        JPanel capsulePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                g2.setColor(new Color(255, 255, 255, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        capsulePanel.setOpaque(false);
        capsulePanel.setLayout(new GridBagLayout());
        capsulePanel.setBorder(new EmptyBorder(35, 40, 35, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel title = new JLabel("Create Your Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        usernameLabel.setForeground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        passwordLabel.setForeground(Color.WHITE);

        JTextField usernameField = createGlowingTextField();
        JPasswordField passwordField = createGlowingPasswordField();

        JButton registerBtn = createInteractiveButton("Register", new Color(0, 102, 204));
        JButton loginRedirectBtn = createInteractiveButton("Go to Login Page", new Color(0, 153, 0));

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        capsulePanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        capsulePanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        capsulePanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        capsulePanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        capsulePanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        capsulePanel.add(registerBtn, gbc);

        gbc.gridy++;
        capsulePanel.add(loginRedirectBtn, gbc);

        // Capsule is aligned right
        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.add(capsulePanel);
        background.add(rightWrapper, BorderLayout.EAST);

        // Button Actions
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Both fields are required.");
                return;
            }

            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.registerUser(username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Registration successful!");
                dispose();
                new LoginUI();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Registration failed. Try again.");
            }
        });

        loginRedirectBtn.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        setVisible(true);
    }

    private JTextField createGlowingTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("SansSerif", Font.PLAIN, 18));
        field.setBackground(new Color(255, 255, 255, 230));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        return field;
    }

    private JPasswordField createGlowingPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setFont(new Font("SansSerif", Font.PLAIN, 18));
        field.setBackground(new Color(255, 255, 255, 230));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        return field;
    }

    private JButton createInteractiveButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bg.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new RegisterUI();
    }
}