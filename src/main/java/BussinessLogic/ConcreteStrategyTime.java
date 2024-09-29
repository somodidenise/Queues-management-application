package BussinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server rightServer = servers.get(0);
        int index = 0;
        for(int i=0; i<servers.size(); i++)
        {
            Server server = servers.get(i);
            if(server.getWaitingPeriod() < rightServer.getWaitingPeriod()){
                index = i;
                rightServer = server;
            }
        }
        rightServer.addTask(t, index);
    }
}
