package BussinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private Strategy strategy;
    private final Lock lock = new ReentrantLock();
    public Scheduler(int maxNoServers, SelectionPolicy policy){
        this.maxNoServers = maxNoServers;
        this.servers = new ArrayList<>();
        for(int i=0; i<this.maxNoServers; i++)
        {
            Server server = new Server();
            this.servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
        changeStrategy(policy);
    }
    public void changeStrategy(SelectionPolicy policy){
        lock.lock();
        try {
            if (policy == SelectionPolicy.SHORTEST_QUEUE) {
                this.strategy = new ConcreteStrategyQueue();
            }
            if (policy == SelectionPolicy.SHORTEST_TIME) {
                this.strategy = new ConcreteStrategyTime();
            }
        }finally{
            lock.unlock();
        }
    }

    public void dispatchTask(Task t){
        lock.lock();
        try {
            this.strategy.addTask(this.servers, t);
        }finally {
            lock.unlock();
        }

    }
    public List<Server> getServers(){
        return new ArrayList<>(servers);
    }
    public int countClients(){
        lock.lock();
        try {
            int count = 0;
            for (Server server : servers) {
                count += server.getTasks().length;
            }
            return count;
        }finally {
            lock.unlock();
        }
    }

}
