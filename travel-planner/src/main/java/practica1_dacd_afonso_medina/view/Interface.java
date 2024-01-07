package practica1_dacd_afonso_medina.view;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;


public class Interface extends JFrame {
    private WeatherCalculator weatherCalculator;
    private BookingCalculator bookingCalculator;
    private JComboBox<String> islandsComboBox;
    private JDateChooser checkInDateChooser;
    private JDateChooser checkOutDateChooser;
    private JButton confirmButton;
    private JTextArea resultTextArea;

    public Interface(WeatherCalculator weatherCalculator, BookingCalculator bookingCalculator) {
        this.weatherCalculator = weatherCalculator;
        this.bookingCalculator = bookingCalculator;
        initialize();
    }

    public void initialize() {
        setTitle("Travel Planner");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        setVisible(true);
    }

    private void createUI() {
        createInputPanel();
        addActionListeners();
    }

    private void createInputPanel() {
        Font font = new Font("Verdana", Font.PLAIN, 16);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230));
        JPanel inputPanel = createInputComponents(font);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
    }


    private JPanel createInputComponents(Font font) {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(135, 206, 250));
        JLabel titleLabel = new JLabel("Travel Planner");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel islandLabel = new JLabel("Select an island:");
        islandLabel.setFont(font);
        islandsComboBox = new JComboBox<>(new String[]{"GranCanaria", "Tenerife", "LaPalma", "LaGomera", "ElHierro", "Lanzarote", "Fuerteventura", "LaGraciosa"});
        islandsComboBox.setFont(font);
        JLabel checkInLabel = new JLabel("Select check-in date:");
        checkInLabel.setFont(font);
        checkInDateChooser = new JDateChooser();
        checkInDateChooser.setFont(font);
        JLabel checkOutLabel = new JLabel("Select check-out date (max. 5 days):");
        checkOutLabel.setFont(font);
        checkOutDateChooser = new JDateChooser();
        checkOutDateChooser.setFont(font);
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(font);
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setFont(font);
        inputPanel.add(new JLabel());
        inputPanel.add(titleLabel);
        inputPanel.add(islandLabel);
        inputPanel.add(islandsComboBox);
        inputPanel.add(checkInLabel);
        inputPanel.add(checkInDateChooser);
        inputPanel.add(checkOutLabel);
        inputPanel.add(checkOutDateChooser);
        inputPanel.add(new JLabel()); // Empty space
        inputPanel.add(confirmButton);

        return inputPanel;
    }

    private void addActionListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleConfirmButtonAction();
            }
        });
    }

    private void handleConfirmButtonAction() {
        String selectedIsland = (String) islandsComboBox.getSelectedItem();
        String checkInDate = formatDate(checkInDateChooser);
        String checkOutDate = formatDate(checkOutDateChooser);

        String weatherResult = weatherCalculator.weatherCalculator(checkInDate, checkOutDate, selectedIsland);
        String bookingResult = bookingCalculator.bookingCalculator(checkInDate, checkOutDate, selectedIsland);

        resultTextArea.setText("This is the proposal for your trip to the selected island: " + selectedIsland +
                " on the selected check-in: " + checkInDate + " and check-out: " + checkOutDate + ":\n\n" +
                "Weather Prediction:\n" + weatherResult + "\n\nBooking Detaitracls:\n" + bookingResult);
    }

    private String formatDate(JDateChooser dateChooser) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateChooser.getDate());
    }

}