package GUI;

import Model.Server;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import  java.util.List;

import static java.lang.Boolean.TRUE;

public class SimulationFrame {
    Font myFont = new Font("Courier",Font.ITALIC, 15);
    int nrClients;
    int nrQueues;
    List<Task> generatedTasks;
    JFrame myFrame;
    JPanel mainPanel;
    JPanel panel1, panel2, panel3;
    int timeCount = 0;
    JLabel time, avgWaitingTime, avgServiceTime, peakHour;
    JLabel secTime, res1, res2, res3;
    JLabel waitingClients;
    ImageIcon stickMan = new ImageIcon("stickman.png");
    ImageIcon closed = new ImageIcon("close.png");
    ImageIcon opened = new ImageIcon("open.png");
    JLabel[] clients;
    JPanel[] queues;
    JLabel[] openOrClose;

    public SimulationFrame(int nrClients, int nrQueues, List<Task> generatedTasks){
        this.nrClients = nrClients;
        clients = new JLabel[nrClients];
        this.nrQueues = nrQueues;
        queues = new JPanel[nrQueues];
        openOrClose = new JLabel[nrQueues];
        this.generatedTasks = generatedTasks;
        myFrame = new JFrame("Real Time Simulation");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(820,800);
        myFrame.setLayout(null);
        myFrame.setLocationRelativeTo(null);
        myFrame.getContentPane().setBackground(new java.awt.Color(255, 204, 255));

        panel1 = new JPanel();
        panel1.setBackground(new java.awt.Color(255, 153, 255));
        panel1.setBounds(0,0, 820, 50);
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 50,805, 550);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5,5,5,5);
        int rows = (int)Math.sqrt(nrQueues);
        int cols = (int)Math.ceil((double) nrQueues/rows);
        cons.weightx = 1.0 / cols;
        cons.weighty = 1.0 / rows;

        time = new JLabel("Time: ");
        time.setFont(myFont);
        panel1.add(time);

        secTime = new JLabel(Integer.toString(timeCount));
        secTime.setFont(myFont);
        panel1.add(secTime);

        avgWaitingTime = new JLabel("            Avg Waiting Time: ");
        avgWaitingTime.setFont(myFont);
        panel1.add(avgWaitingTime);

        res1 = new JLabel("  ");
        res1.setFont(myFont);
        panel1.add(res1);

        avgServiceTime = new JLabel( "            Avg Service Time: ");
        avgServiceTime.setFont(myFont);
        panel1.add(avgServiceTime);

        res2 = new JLabel("  ");
        res2.setFont(myFont);
        panel1.add(res2);

        peakHour = new JLabel("                   Peak Hour: ");
        peakHour.setFont(myFont);
        panel1.add(peakHour);

        res3 = new JLabel("  ");
        res3.setFont(myFont);
        panel1.add(res3);

        myFrame.add(panel1);

        panel2 = new JPanel();
        panel2.setBackground(new java.awt.Color(255, 155, 180));
        panel2.setBounds(0,600, 820, 40);
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

        waitingClients = new JLabel("Waiting Clients:");
        waitingClients.setFont(myFont);
        panel2.add(waitingClients);
        myFrame.add(panel2);

        panel3 = new JPanel();
        panel3.setBackground(new java.awt.Color(255, 153, 255));
        panel3.setBounds(0,640,820,180);
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
       // myFrame.add(panel3);

        Image image = stickMan.getImage();
        Image newImage = image.getScaledInstance(30,30, Image.SCALE_SMOOTH);
        stickMan = new ImageIcon(newImage);

        image = closed.getImage();
        newImage = image.getScaledInstance(40,40, Image.SCALE_SMOOTH);
        closed = new ImageIcon(newImage);

        image = opened.getImage();
        newImage = image.getScaledInstance(30,30, Image.SCALE_SMOOTH);
        opened = new ImageIcon(newImage);

        for(int i=0; i<nrClients; i++)
        {
            int arrivalTime = generatedTasks.get(i).getArrivalTime();
            int serviceTime = generatedTasks.get(i).getServiceTime();
            int key = generatedTasks.get(i).getKey();
            String string = "("+Integer.toString(key)+","+Integer.toString(arrivalTime)+","+Integer.toString(serviceTime)+")";
            clients[i] = new JLabel(string);
            clients[i].setFont(myFont);
            clients[i].setIcon(stickMan);
            panel3.add(clients[i]);

        }
        myFrame.add(panel3);

        for(int i=0; i<nrQueues; i++)
        {
            queues[i] = new JPanel();
            queues[i].setBackground(new java.awt.Color(197, 148, 245, 255));
            queues[i].setLayout(new BoxLayout(queues[i], BoxLayout.Y_AXIS));
            queues[i].setBorder(BorderFactory.createLineBorder(Color.black));
            openOrClose[i] = new JLabel(closed);
            queues[i].add(openOrClose[i]);
            cons.gridx = i % cols;
            cons.gridy = i / cols;
            cons.gridwidth = 1;
            cons.gridheight = 1;

            mainPanel.add(queues[i], cons);
        }

        myFrame.add(mainPanel);

        myFrame.setVisible(true);
    }

    public void updateTime(int count){
        secTime.setText(Integer.toString(count));
        myFrame.repaint();
    }
    public void updateTask(Task task, int open){
        int key = task.getKey() - 1;
        int indexOfServer = task.getIndexOfServer();
        if(open == 1)
        {
            openOrClose[indexOfServer].setIcon(opened);
        }
        queues[indexOfServer].add(clients[key]);
        myFrame.repaint();
    }
    public void updateServiceTime(Task task){
        int key = task.getKey() - 1;
        int indexOfServer = task.getIndexOfServer();
        int arrivalTime = task.getArrivalTime();
        int serviceTime = task.getServiceTime();
        String string = "(" + Integer.toString(key + 1) + "," + Integer.toString(arrivalTime) + "," + Integer.toString(serviceTime) + ")";
        clients[key].setText(string);
        if (task.isReady() == TRUE) {
            queues[indexOfServer].remove(clients[key]);
        }
        myFrame.repaint();

    }

    public void updateResults(double avgTime, double avgService, int peakHour )
    {
        res1.setText(String.format("%.2f", avgTime));
        res2.setText(String.format("%.2f", avgService));
        res3.setText(Integer.toString(peakHour) + ":00");
        myFrame.repaint();
    }
}
