package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import dao.ExpenseDAO;
import model.Expense;
import java.util.List;

public class ExpenseTrackerUI extends JFrame {
    private final int userId;

    public ExpenseTrackerUI(int userId) {
        this.userId = userId;
        setTitle("Expense Tracker");
        setSize(1150, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Premium Background Color
        getContentPane().setBackground(new Color(30, 30, 46)); // Copilot-inspired shade

        // Glassmorphism Panel
        JPanel capsulePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Reduced translucency
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
        JLabel title = createStyledLabel("Expense Tracker", 30);

        JLabel amountLabel = createStyledLabel("Amount:", 22);
        JLabel categoryLabel = createStyledLabel("Category:", 22);
        JLabel descriptionLabel = createStyledLabel("Description:", 22);

        JTextField amountField = createGlowingTextField();
        JTextField categoryField = createGlowingTextField();
        JTextField descriptionField = createGlowingTextField();

        JButton addExpenseBtn = createInteractiveButton("Add Expense", new Color(0, 153, 51));
        JButton totalExpensesBtn = createInteractiveButton("Show Total Expenses", new Color(0, 102, 204));
        JButton logoutBtn = createInteractiveButton("Log Out", new Color(204, 0, 0));

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        capsulePanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        capsulePanel.add(amountLabel, gbc);
        gbc.gridx = 1;
        capsulePanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        capsulePanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        capsulePanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        capsulePanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        capsulePanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        capsulePanel.add(addExpenseBtn, gbc);

        gbc.gridy++;
        capsulePanel.add(totalExpensesBtn, gbc);

        gbc.gridy++;
        capsulePanel.add(logoutBtn, gbc);

        // Centered Wrapper Panel
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(capsulePanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Button Actions
        addExpenseBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                String description = descriptionField.getText();

                ExpenseDAO expenseDAO = new ExpenseDAO();
                boolean success = expenseDAO.addExpense(new Expense(userId, amount, category, description));

                JOptionPane.showMessageDialog(this, success ? "✅ Expense added!" : "❌ Failed to add expense.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid amount. Please enter a numeric value.");
            }
        });

        totalExpensesBtn.addActionListener(e -> {
            ExpenseDAO expenseDAO = new ExpenseDAO();
            List<Expense> expenses = expenseDAO.getAllExpenses(userId);
            StringBuilder expenseList = new StringBuilder("💰 Your Expenses:\n");

            double totalExpenses = 0;
            for (Expense expense : expenses) {
                expenseList.append("📌 Amount: $").append(expense.getAmount())
                        .append(", Category: ").append(expense.getCategory())
                        .append(", Description: ").append(expense.getDescription())
                        .append("\n");
                totalExpenses += expense.getAmount();
            }

            expenseList.append("\n💰 Total Expenses: $").append(totalExpenses);
            JOptionPane.showMessageDialog(this, expenseList.toString());
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        setVisible(true);
    }

    private JLabel createStyledLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, size));
        label.setForeground(new Color(242, 242, 242)); // Off-white for sharper contrast
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }

    private JTextField createGlowingTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("SansSerif", Font.PLAIN, 18));
        field.setBackground(new Color(255, 255, 255, 230));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return field;
    }

    private JButton createInteractiveButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
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
        new ExpenseTrackerUI(1);
    }
}