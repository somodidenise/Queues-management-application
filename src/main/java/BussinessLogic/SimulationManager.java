package BussinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import  java.io.FileWriter;
import  java.io.IOException;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    private List<Integer> indexesOfServers = new ArrayList<>();
    public int totalServiceTime = 0;
    public int totalWaitingTime = 0;
    public List<Integer> clientsPerHour = new ArrayList<>();
    FileWriter myWriter;

    private volatile int currentTime;
    public SimulationManager(int nrClients, int nrQueues, int simulationTime, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, String strategy){
        this.numberOfClients = nrClients;
        this.numberOfServers = nrQueues;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minServiceTime;
        this.maxProcessingTime = maxServiceTime;
        this.timeLimit = simulationTime;
        if(strategy.equals("Shortest Queue"))
        {
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
        }
        else{
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        }
        this.scheduler = new Scheduler(this.numberOfServers, selectionPolicy);
        generateNRandomTasks();
        this.frame = new SimulationFrame(nrClients, nrQueues, generatedTasks);
        try {
            this.myWriter = new FileWriter("log.txt");
        } catch (IOException e) {
            System.out.println("An error occured!");
        }
        Thread thread = new Thread(this);
        thread.start();
    }
    private void generateNRandomTasks(){
        Random rand = new Random();
        this.generatedTasks = new ArrayList<>();
        for(int i=0; i<numberOfClients; i++)
        {
            int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = rand.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            int index = i+1;
            generatedTasks.add(new Task(arrivalTime, serviceTime, index));
        }

    }
    @Override
    public void  run() {
        currentTime = 0;
        while(currentTime <= timeLimit) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("Time ").append(currentTime).append("\n");
            logBuilder.append("Waiting clients: ");

                for (Task task : generatedTasks) {
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(task);
                        int index = task.getIndexOfServer();
                       // this.scheduler.getServers().get(index).processNextTask();
                        int open = this.scheduler.getServers().get(index).isOpen();
                        frame.updateTask(task, open);
                    } else if (task.getArrivalTime() < currentTime) {
                            this.totalServiceTime++;
                        }
                        else {
                                 logBuilder.append(task.toString()).append("; ");
                             }
                }
                logBuilder.append("\n");
                for(int i=0; i<scheduler.getServers().size(); i++){
                    logBuilder.append("Queue ").append(i+1).append(": ");
                    Server server = this.scheduler.getServers().get(i);
                    if(server.getTasks().length == 0){
                        logBuilder.append("closed ");
                    }
                    for(Task task : server.getTasks()) {
                        if(!server.getFirstTask().equals(task)) {
                            this.totalWaitingTime++;
                        }
                        frame.updateServiceTime(task);
                        logBuilder.append(task.toString() + " ");
                    }
                    server.processNextTask();

                }
            frame.updateTime(currentTime);
            int countClients = scheduler.countClients();
            clientsPerHour.add(countClients);
            synchronized (myWriter){
                try {
                    myWriter.write(logBuilder.toString() + "\n");
                    myWriter.flush();
                } catch (IOException e){
                    System.out.println("error");
                    break;
                }
            }
            try {
                Thread.sleep(1000);
                currentTime++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        int peakHour = 0;
        for(int i=1; i<clientsPerHour.size(); i++)
        {
            if(clientsPerHour.get(i) > clientsPerHour.get(peakHour))
            {
                 peakHour = i;
            }
        }
        double avgTime = (double) totalWaitingTime / numberOfClients;
        double avgService = (double) totalServiceTime / numberOfClients;
        frame.updateResults(avgTime, avgService, peakHour);

                synchronized (myWriter) {
                    try {
                        myWriter.write("Average Time: " + avgTime);
                        myWriter.flush();
                        myWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        }

}

