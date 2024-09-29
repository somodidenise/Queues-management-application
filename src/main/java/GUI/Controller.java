package GUI;

import BussinessLogic.SimulationManager;
import Model.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener {
    SimulationSetup simulationSetup;
    boolean validation = false;
    public Controller(){

        this.simulationSetup = new SimulationSetup();
        addActionListeners();
        addMouseListeners();
    }
    public void addActionListeners(){
        this.simulationSetup.simulateButton.addActionListener(this);
        this.simulationSetup.validateButton.addActionListener(this);
    }

    public void addMouseListeners(){

        this.simulationSetup.nrClients.addActionListener(this);
        this.simulationSetup.nrQueues.addActionListener(this);
        this.simulationSetup.simulationInterval.addActionListener(this);
        this.simulationSetup.minArrivalTime.addActionListener(this);
        this.simulationSetup.maxArrivalTime.addActionListener(this);
        this.simulationSetup.minServiceTime.addActionListener(this);
        this.simulationSetup.maxServiceTime.addActionListener(this);
        this.simulationSetup.strategy.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ev) {

        if(ev.getSource() == simulationSetup.validateButton) {
            if (!simulationSetup.nrClients.getText().matches("\\d+") || !simulationSetup.nrQueues.getText().matches("\\d+") || !simulationSetup.simulationInterval.getText().matches("\\d+")
                    || !simulationSetup.minArrivalTime.getText().matches("\\d+") || !simulationSetup.maxArrivalTime.getText().matches("\\d+") || !simulationSetup.minServiceTime.getText().matches("\\d+")
                    || !simulationSetup.maxServiceTime.getText().matches("\\d+")) {
                this.simulationSetup.failMessage();
                validation = false;
            } else {
                this.simulationSetup.successMessage();
                validation = true;
            }
        }
        else if(ev.getSource() == simulationSetup.simulateButton){
                if(validation == false)
                {
                    this.simulationSetup.failMessage();
                }
                else {
                    int nrCllients = Integer.parseInt(simulationSetup.nrClients.getText());
                    int nrQueues = Integer.parseInt(simulationSetup.nrQueues.getText());
                    int simulationTime = Integer.parseInt(simulationSetup.simulationInterval.getText());
                    int minArrivalTime = Integer.parseInt(simulationSetup.minArrivalTime.getText());
                    int maxArrivalTime = Integer.parseInt(simulationSetup.maxArrivalTime.getText());
                    int minServiceTime = Integer.parseInt(simulationSetup.minServiceTime.getText());
                    int maxServiceTime = Integer.parseInt(simulationSetup.maxServiceTime.getText());
                    String strategy = (String) simulationSetup.strategy.getSelectedItem();
                    SimulationManager simulationManager = new SimulationManager(nrCllients,nrQueues,simulationTime,minArrivalTime,maxArrivalTime,minServiceTime,maxServiceTime,strategy);
                }

            }

    }


}

