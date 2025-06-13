package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

public class Dashboard extends JFrame {
    public Dashboard(int userId) {
        setTitle("Dashboard");
        setSize(1150, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Premium Background Color (No Image)
        getContentPane().setBackground(new Color(28, 28, 36)); // Sleek Dark Gray-Blue

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
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel title = new JLabel("Welcome to Your Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JButton expenseTrackerBtn = createInteractiveButton("Go to Expense Tracker", new Color(0, 102, 204));

        // Layout Positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        capsulePanel.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        capsulePanel.add(expenseTrackerBtn, gbc);

        // Centered Wrapper Panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(capsulePanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Button Action
        expenseTrackerBtn.addActionListener(e -> {
            dispose();
            new ExpenseTrackerUI(userId);
        });

        setVisible(true);
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