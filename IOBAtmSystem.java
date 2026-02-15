package atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IOBAtmSystem {

    private static double balance = 10000;
    private static final String USERNAME = "gayathri";
    private static final String PIN = "1234";

    private static int[] xDots = new int[25];
    private static int[] yDots = new int[25];
    private static int[] speed = new int[25];

    public static void main(String[] args) {
        showLoginScreen();
    }

    // ================= LOGIN SCREEN =================
    public static void showLoginScreen() {

        JFrame frame = new JFrame("IOB ATM Login");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);
        frame.setContentPane(panel);


        // ===== IOB LOGO =====
        try {

            ImageIcon logoIcon = new ImageIcon(
                    IOBAtmSystem.class.getResource("/atm/iob_logo.png"));

            Image img = logoIcon.getImage()
                    .getScaledInstance(120, 120, Image.SCALE_SMOOTH);

            JLabel logoLabel = new JLabel(new ImageIcon(img));
            logoLabel.setBounds(340, 30, 120, 120);

            panel.add(logoLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== TITLE =====
        JLabel title = new JLabel("INDIAN OVERSEAS BANK", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 150, 800, 40);
        panel.add(title);

        // ===== GLASS CARD =====
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(220, 200, 360, 280);
        card.setBackground(new Color(255, 255, 255, 40));
        card.setOpaque(true);
        panel.add(card);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 40, 100, 30);
        card.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(130, 40, 180, 35);
        card.add(userField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(30, 100, 100, 30);
        card.add(pinLabel);

        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(130, 100, 180, 35);
        card.add(pinField);

        JButton loginBtn = createRoundedButton("Login");
        loginBtn.setBounds(80, 170, 200, 50);
        card.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pin = new String(pinField.getPassword());

            if (user.equals(USERNAME) && pin.equals(PIN)) {
                fadeOut(frame);
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or PIN");
            }
        });

        frame.setVisible(true);
    }

    // ================= DASHBOARD =================
    public static void showDashboard() {

        JFrame frame = new JFrame("IOB ATM Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);
        frame.setContentPane(panel);

        JLabel title = new JLabel("IOB ATM SERVICES", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 50, 800, 40);
        panel.add(title);

        JTextField amountField = new JTextField();
        amountField.setBounds(250, 120, 300, 40);
        panel.add(amountField);

        JButton depositBtn = createRoundedButton("Deposit");
        depositBtn.setBounds(300, 200, 200, 50);

        JButton withdrawBtn = createRoundedButton("Withdraw");
        withdrawBtn.setBounds(300, 270, 200, 50);

        JButton balanceBtn = createRoundedButton("Check Balance");
        balanceBtn.setBounds(300, 340, 200, 50);

        JButton logoutBtn = createRoundedButton("Logout");
        logoutBtn.setBounds(300, 410, 200, 50);

        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceBtn);
        panel.add(logoutBtn);

        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                balance += amount;
                JOptionPane.showMessageDialog(frame, "Deposited Successfully!");
                amountField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid amount");
            }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= balance) {
                    balance -= amount;
                    JOptionPane.showMessageDialog(frame, "Withdraw Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient Balance!");
                }
                amountField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid amount");
            }
        });

        balanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Current Balance: ₹ " + balance));

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        frame.setVisible(true);
    }

    // ================= ROUNDED PINK BUTTON =================
    private static JButton createRoundedButton(String text) {

        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(255, 105, 180));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(255, 20, 147));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(255, 105, 180));
            }
        });

        return btn;
    }

    // ================= FADE EFFECT =================
    private static void fadeOut(JFrame frame) {
        try {
            for (float i = 1.0f; i >= 0; i -= 0.05f) {
                frame.setOpacity(i);
                Thread.sleep(20);
            }
        } catch (Exception ignored) {
        }
        frame.dispose();
    }

    // ================= ANIMATED BACKGROUND =================
    static class GradientPanel extends JPanel {

        public GradientPanel() {
            for (int i = 0; i < xDots.length; i++) {
                xDots[i] = (int) (Math.random() * 800);
                yDots[i] = (int) (Math.random() * 600);
                speed[i] = 1 + (int) (Math.random() * 3);
            }

            Timer timer = new Timer(30, e -> {
                for (int i = 0; i < yDots.length; i++) {
                    yDots[i] += speed[i];
                    if (yDots[i] > 600) {
                        yDots[i] = 0;
                    }
                }
                repaint();
            });
            timer.start();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(75, 0, 130),
                    0, getHeight(), new Color(255, 105, 180));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 80));
            for (int i = 0; i < xDots.length; i++) {
                g2.fillOval(xDots[i], yDots[i], 8, 8);
            }
        }
    }
}
