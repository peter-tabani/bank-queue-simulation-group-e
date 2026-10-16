import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/** A single-teller, first-come-first-served bank queue simulation. */
public class BankQueueSimulation {
    private final JFrame frame = new JFrame("Bank Queue Simulation");
    private JTextField customersField;
    private JTextField minArrivalField;
    private JTextField maxArrivalField;
    private JTextField minServiceField;
    private JTextField maxServiceField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankQueueSimulation().showSetup());
    }

    private void showSetup() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel page = new JPanel(new BorderLayout(0, 22));
        page.setBorder(new EmptyBorder(28, 34, 28, 34));
        page.setBackground(Color.WHITE);

        JLabel title = new JLabel("Bank Queue Simulation Setup");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        page.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 12, 14));
        form.setBackground(Color.WHITE);
        customersField = addField(form, "Total customers", "100");
        minArrivalField = addField(form, "Minimum interarrival (min)", "1");
        maxArrivalField = addField(form, "Maximum interarrival (min)", "5");
        minServiceField = addField(form, "Minimum service (min)", "1");
        maxServiceField = addField(form, "Maximum service (min)", "6");
        page.add(form, BorderLayout.CENTER);

        JButton run = new JButton("Run Simulation");
        run.setBackground(new Color(35, 91, 151));
        run.setForeground(Color.WHITE);
        run.addActionListener(event -> runSimulation());
        page.add(run, BorderLayout.SOUTH);

        frame.setContentPane(page);
        frame.setSize(500, 370);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTextField addField(JPanel form, String label, String value) {
        form.add(new JLabel(label));
        JTextField field = new JTextField(value);
        form.add(field);
        return field;
    }

    private void runSimulation() {
        try {
            int count = Integer.parseInt(customersField.getText().trim());
            int minArrival = Integer.parseInt(minArrivalField.getText().trim());
            int maxArrival = Integer.parseInt(maxArrivalField.getText().trim());
            int minService = Integer.parseInt(minServiceField.getText().trim());
            int maxService = Integer.parseInt(maxServiceField.getText().trim());
            if (count < 1 || count > 10000 || minArrival < 1 || minService < 1
                    || maxArrival < minArrival || maxService < minService
                    || maxArrival > 10000 || maxService > 10000) {
                throw new NumberFormatException();
            }

            Random random = new Random();
            Object[][] rows = new Object[count][8];
            int arrival = 0, previousDeparture = 0;
            int totalService = 0, totalWait = 0, totalSystem = 0, totalIdle = 0, waited = 0;

            for (int i = 0; i < count; i++) {
                int interarrival = random.nextInt(maxArrival - minArrival + 1) + minArrival;
                int service = random.nextInt(maxService - minService + 1) + minService;
                arrival += interarrival;
                int start = Math.max(arrival, previousDeparture);
                int wait = start - arrival;
                int departure = start + service;
                int inSystem = departure - arrival;

                totalIdle += Math.max(0, arrival - previousDeparture);
                totalService += service;
                totalWait += wait;
                totalSystem += inSystem;
                if (wait > 0) waited++;
                rows[i] = new Object[]{i + 1, interarrival, arrival, service,
                        start, wait, departure, inSystem};
                previousDeparture = departure;
            }
            showResults(rows, count, totalService, totalWait, totalSystem, totalIdle, waited);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Enter whole numbers from 1 to 10,000; each minimum must be no greater than its maximum.",
                    "Check the inputs", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResults(Object[][] rows, int count, int totalService, int totalWait,
                             int totalSystem, int totalIdle, int waited) {
        JDialog results = new JDialog(frame, "Simulation Results", false);
        results.setLayout(new BorderLayout(0, 12));

        JTextArea summary = new JTextArea(String.format(
                "BANK QUEUE PERFORMANCE — %d CUSTOMERS, ONE TELLER%n" +
                "Average service: %.2f min     Average waiting: %.2f min     Average time in system: %.2f min%n" +
                "Customers who waited: %d (%.2f%%)     Total teller idle time: %d min",
                count, totalService / (double) count, totalWait / (double) count,
                totalSystem / (double) count, waited, waited * 100.0 / count, totalIdle));
        summary.setEditable(false);
        summary.setFont(new Font("SansSerif", Font.PLAIN, 15));
        summary.setBackground(new Color(235, 244, 252));
        summary.setBorder(new EmptyBorder(18, 20, 18, 20));
        results.add(summary, BorderLayout.NORTH);

        String[] columns = {"Customer", "Interarrival", "Arrival", "Service",
                "Start", "Wait", "Departure", "In system"};
        JTable table = new JTable(rows, columns);
        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        results.add(new JScrollPane(table), BorderLayout.CENTER);

        results.setSize(1050, 650);
        results.setLocationRelativeTo(frame);
        results.setVisible(true);
    }
}
