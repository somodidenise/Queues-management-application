package GUI;

import javax.swing.*;
import java.awt.*;

public class SimulationSetup {
    Font myFont = new Font("Courier",Font.ITALIC, 15);
    JFrame myFrame;
    JButton simulateButton, validateButton;
    JTextField nrClients, nrQueues, simulationInterval, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime;
    JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;
    JLabel failText, readyText;
    JComboBox<String> strategy;

    SimulationSetup(){
        myFrame = new JFrame("Queue Management System");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(360,650);
        myFrame.setLayout(null);
        myFrame.setLocationRelativeTo(null);
        myFrame.getContentPane().setBackground(new java.awt.Color(255, 204, 255));

        nrClients = new JTextField();
        nrClients.setBounds(200, 30, 100, 25);
        nrClients.setFont(myFont);
        myFrame.add(nrClients);

        nrQueues = new JTextField();
        nrQueues.setBounds(200, 75, 100, 25 );
        nrQueues.setFont(myFont);
        myFrame.add(nrQueues);

        simulationInterval = new JTextField();
        simulationInterval.setBounds(200, 120,100, 25);
        simulationInterval.setFont(myFont);
        myFrame.add(simulationInterval);

        minArrivalTime = new JTextField();
        minArrivalTime.setBounds(85, 210,60, 25);
        minArrivalTime.setFont(myFont);
        myFrame.add(minArrivalTime);

        maxArrivalTime = new JTextField();
        maxArrivalTime.setBounds(220, 210,60, 25);
        maxArrivalTime.setFont(myFont);
        myFrame.add(maxArrivalTime);

        minServiceTime = new JTextField();
        minServiceTime.setBounds(85, 300, 60, 25);
        minServiceTime.setFont(myFont);
        myFrame.add(minServiceTime);

        maxServiceTime = new JTextField();
        maxServiceTime.setBounds(220, 300, 60, 25);
        maxServiceTime.setFont(myFont);
        myFrame.add(maxServiceTime);

        label1 = new JLabel("Number of clients: ");
        label1.setBounds(50, 30,150,25);
        label1.setFont(myFont);
        myFrame.add(label1);

        label2 = new JLabel("Number of queues:  ");
        label2.setBounds(50, 75,150,25);
        label2.setFont(myFont);
        myFrame.add(label2);

        label3 = new JLabel("Simulation interval:  ");
        label3.setBounds(50, 120,150,25);
        label3.setFont(myFont);
        myFrame.add(label3);

        label4 = new JLabel("Arrival time:  ");
        label4.setBounds(120, 165,150,25);
        label4.setFont(myFont);
        myFrame.add(label4);

        label5 = new JLabel("Min:  ");
        label5.setBounds(50, 210,150,25);
        label5.setFont(myFont);
        myFrame.add(label5);

        label6 = new JLabel("Max:  ");
        label6.setBounds(180, 210,150,25);
        label6.setFont(myFont);
        myFrame.add(label6);

        label7 = new JLabel("Service time:  ");
        label7.setBounds(120, 255,150,25);
        label7.setFont(myFont);
        myFrame.add(label7);

        label8 = new JLabel("Min:  ");
        label8.setBounds(50, 300,150,25);
        label8.setFont(myFont);
        myFrame.add(label8);

        label9 = new JLabel("Max:  ");
        label9.setBounds(180, 300,150,25);
        label9.setFont(myFont);
        myFrame.add(label9);

        label10 = new JLabel("Choose Strategy:  ");
        label10.setBounds(110, 350,150,25);
        label10.setFont(myFont);
        myFrame.add(label10);

        String[] options = { "Shortest Queue", "Shortest Time"};
        strategy = new JComboBox<>(options);
        strategy.setBounds(100, 380,150, 25);
        strategy.setFont(myFont);
        myFrame.add(strategy);

        simulateButton = new JButton("Simulate");
        simulateButton.setFont(myFont);
        simulateButton.setFocusable(false);
        simulateButton.setBackground(new java.awt.Color(255,153,255));
        simulateButton.setBounds(120, 490,100, 50);
        myFrame.add(simulateButton);

        validateButton = new JButton("Validate Input Data");
        validateButton.setFont(myFont);
        validateButton.setFocusable(false);
        validateButton.setBackground(new java.awt.Color(255,153,255));
        validateButton.setBounds(70, 420,200, 50);
        myFrame.add(validateButton);

        failText = new JLabel("Input Data not Valid!!");
        failText.setBounds(95, 550, 150, 25);
        failText.setFont(myFont);
        failText.setVisible(false);
        myFrame.add(failText);

        readyText = new JLabel("Ready for simulation");
        readyText.setBounds(95, 550, 150, 25);
        readyText.setFont(myFont);
        readyText.setVisible(false);
        myFrame.add(readyText);

        myFrame.setVisible(true);
    }
    public void failMessage()
    {
        readyText.setVisible(false);
        failText.setVisible(true);
        myFrame.repaint();

    }
    public void successMessage()
    {
        failText.setVisible(false);
        readyText.setVisible(true);
        myFrame.repaint();
    }
}
