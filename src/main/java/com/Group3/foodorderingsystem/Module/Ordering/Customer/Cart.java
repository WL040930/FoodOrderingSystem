package com.Group3.foodorderingsystem.Module.Ordering.Customer;

import javax.swing.*;
import java.awt.*;

public class Cart extends JFrame {
    public Cart() {
        setTitle("Cart");
        setSize(500, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));
        initUI();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void initUI() {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        addItem(itemsPanel, "Caramel Macchiato, Hot, Colombia Beans", "14.00", 1);
        addItem(itemsPanel, "Curry Chicken Pastry", "6.00", 1);
        addItem(itemsPanel, "Doughnut", "5.00", 1);
        addItem(itemsPanel, "Curry Chicken Pastry", "6.00", 1);
        addItem(itemsPanel, "Curry Chicken Pastry", "6.00", 1);
        addItem(itemsPanel, "Curry Chicken Pastry", "6.00", 1);

        add(createSummaryPanel(), BorderLayout.SOUTH);
    }

    private void addItem(JPanel panel, String name, String price, int quantity) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel quantityLabel = new JLabel(String.valueOf(quantity) + "x");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel priceLabel = new JLabel("RM " + price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        itemPanel.add(quantityLabel, BorderLayout.WEST);
        itemPanel.add(nameLabel, BorderLayout.CENTER);
        itemPanel.add(priceLabel, BorderLayout.EAST);

        panel.add(itemPanel);
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new GridLayout(0, 2, 10, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        summaryPanel.setBackground(Color.LIGHT_GRAY);

        summaryPanel.add(new JLabel("Subtotal:"));
        summaryPanel.add(new JLabel("RM 25.00"));
        summaryPanel.add(new JLabel("Delivery Fee:"));
        summaryPanel.add(new JLabel("RM 4.99"));
        summaryPanel.add(new JLabel("Discount:"));
        summaryPanel.add(new JLabel("-RM 8.75"));
        summaryPanel.add(new JLabel("Container/Processing Fee:"));
        summaryPanel.add(new JLabel("RM 2.00"));
        summaryPanel.add(new JLabel("Total (incl. VAT):"));
        summaryPanel.add(new JLabel("RM 23.24"));

        JButton reviewButton = new JButton("Review payment and address");
        reviewButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Go to payment and address review."));
        summaryPanel.add(reviewButton);

        return summaryPanel;
    }

    public static void main(String[] args) {
        new Cart();
    }
}

