import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

class Order {
    String name;
    String address;
    String jewelryType;
    int quantity;
    String paymentMethod;
    double totalAmount;

    public Order(String name, String address, String jewelryType, int quantity, String paymentMethod,
            double totalAmount) {
        this.name = name;
        this.address = address;
        this.jewelryType = jewelryType;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }
}

public class JewelOrdering extends JFrame {

    JLabel nameLabel, addressLabel, jewelryLabel, quantityLabel, paymentLabel;
    JTextField nameTextField, addressTextField, quantityTextField;
    JComboBox<String> jewelryComboBox, paymentComboBox;
    JButton orderButton, viewOrdersButton;

    Font font = new Font("Times New Roman", Font.PLAIN, 18);

    String[] jewelryOptions = { "Gold Rings", "Diamond Rings", "Silver Rings", "Gemstone Rings", "Gold Chains",
            "Diamond Pendants", "Pearl Necklaces", "Silver Necklaces", "Gold Bracelets", "Charm Bracelets",
            "Diamond Bracelets", "Beaded Bracelets", "Stud Earrings (Diamond)", "Hoop Earrings", "Drop Earrings",
            "Silver Earrings", "Gold Watches", "Diamond Watches", "Fashion Watches (Silver)" };
    double[] jewelryPrices = { 5500, 10000, 3000, 20000, 5000, 15000, 5000, 1500, 4000, 1500, 15000, 1500, 5000, 5500,
            7000, 1300, 30000, 50000, 2500 };
    String[] paymentOptions = { "Cash", "E-Wallet" };

    Queue<Order> orderQueue;

    public JewelOrdering(Queue<Order> sharedOrderQueue) {
        this.orderQueue = sharedOrderQueue;

        setTitle("Jacel Luxe Jewelry");
        setSize(1000, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(70, 83, 80, 25);
        nameLabel.setFont(font);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(145, 75, 280, 40);
        nameTextField.setFont(font);
        add(nameTextField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(70, 143, 80, 25);
        addressLabel.setFont(font);
        add(addressLabel);

        addressTextField = new JTextField();
        addressTextField.setBounds(145, 133, 280, 40);
        addressTextField.setFont(font);
        add(addressTextField);

        jewelryLabel = new JLabel("Jewelry:");
        jewelryLabel.setBounds(70, 203, 80, 25);
        jewelryLabel.setFont(font);
        add(jewelryLabel);

        jewelryComboBox = new JComboBox<>(jewelryOptions);
        jewelryComboBox.setBounds(145, 195, 280, 40);
        jewelryComboBox.setFont(font);
        add(jewelryComboBox);

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(70, 263, 80, 25);
        quantityLabel.setFont(font);
        add(quantityLabel);

        quantityTextField = new JTextField();
        quantityTextField.setBounds(145, 255, 280, 40);
        quantityTextField.setFont(font);
        add(quantityTextField);

        paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setBounds(70, 322, 150, 25);
        paymentLabel.setFont(font);
        add(paymentLabel);

        paymentComboBox = new JComboBox<>(paymentOptions);
        paymentComboBox.setBounds(205, 315, 219, 40);
        paymentComboBox.setFont(font);
        add(paymentComboBox);

        orderButton = new JButton("Order");
        orderButton.setBackground(new Color(160, 32, 240));
        orderButton.setBounds(70, 380, 120, 45);
        orderButton.setFont(font);
        add(orderButton);

        viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.setBackground(new Color(173, 216, 230));
        viewOrdersButton.setBounds(295, 380, 130, 45);
        viewOrdersButton.setFont(font);
        add(viewOrdersButton);

        // Right panel for background image
        JPanel panelRight = new JPanel();
        panelRight.setLayout(null);
        panelRight.setBounds(500, 0, 500, 500);
        add(panelRight);

        JLabel jewelryLogo = new JLabel();
        jewelryLogo.setIcon(new ImageIcon("JewelryOrderingSystem/src/download (8).jpg"));
        jewelryLogo.setBounds(0, 0, 500, 500); // Adjust the bounds as needed for the image size
        panelRight.add(jewelryLogo);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String address = addressTextField.getText();
                String jewelryChoice = (String) jewelryComboBox.getSelectedItem();
                String quantityText = quantityTextField.getText();

                if (quantityText.isEmpty() || !quantityText.matches("\\d+") || Integer.parseInt(quantityText) <= 0) {
                    JOptionPane.showMessageDialog(null, "Please input a valid quantity!", "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int quantity = Integer.parseInt(quantityText);
                    int paymentIndex = paymentComboBox.getSelectedIndex();

                    double totalAmount = calculateTotalAmount(jewelryChoice, quantity);

                    Order order = new Order(name, address, jewelryChoice, quantity, paymentOptions[paymentIndex],
                            totalAmount);
                    orderQueue.offer(order);
                    JOptionPane.showMessageDialog(null,
                            "Order placed successfully. Total Amount: \u20B1" + totalAmount);
                    clearFields();
                }
            }
        });

        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new ViewOrders(orderQueue));
            }
        });

        setVisible(true);
    }

    private double calculateTotalAmount(String selectedJewelry, int quantity) {
        double price = 0;
        for (int i = 0; i < jewelryOptions.length; i++) {
            if (jewelryOptions[i].equals(selectedJewelry)) {
                price = jewelryPrices[i];
                break;
            }
        }
        return price * quantity;
    }

    private void clearFields() {
        nameTextField.setText("");
        addressTextField.setText("");
        quantityTextField.setText("");
        jewelryComboBox.setSelectedIndex(0);
        paymentComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JewelOrdering(new LinkedList<>()));
    }
}