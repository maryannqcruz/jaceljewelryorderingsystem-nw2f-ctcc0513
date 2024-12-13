import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

class ViewOrders extends JFrame {

    JLabel titleLabel;
    JTable ordersTable;
    JScrollPane scrollPane;
    DefaultTableModel tableModel;
    JButton backButton;
    Font font = new Font("Times New Roman", Font.PLAIN, 16);

    public ViewOrders(Queue<Order> sharedOrderQueue) {
        setTitle("View Orders");
        setSize(600, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        titleLabel = new JLabel("Order List");
        titleLabel.setBounds(250, 20, 200, 30);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(titleLabel);

        String[] columnNames = { "Name", "Address", "Jewelry", "Quantity", "Payment Method", "Total Amount" };
        tableModel = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(tableModel);
        scrollPane = new JScrollPane(ordersTable);
        setFont(font);
        scrollPane.setBounds(50, 70, 500, 300);
        add(scrollPane);

        backButton = new JButton("Back");
        backButton.setBounds(250, 400, 100, 40);
        backButton.setFont(font);
        backButton.setBackground(new Color(255, 182, 193));
        add(backButton);

        loadOrders(sharedOrderQueue);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new JewelOrdering(sharedOrderQueue));
            }
        });

        setVisible(true);
    }

    private void loadOrders(Queue<Order> sharedOrderQueue) {
        for (Order order : sharedOrderQueue) {
            Object[] row = {
                    order.name,
                    order.address,
                    order.jewelryType,
                    order.quantity,
                    order.paymentMethod,
                    order.totalAmount
            };
            tableModel.addRow(row);
        }
    }
}
