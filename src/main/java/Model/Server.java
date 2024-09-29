package Model;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Flow;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private volatile AtomicInteger waitingPeriod;
    public final Object lock = new Object();
    private boolean taskInProgress = false;
    public Server(){
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }
    public void addTask(Task newTask, int indexOfServer){
        try{
            newTask.setIndexOfServer(indexOfServer);
            this.tasks.put(newTask);
            this.waitingPeriod.addAndGet(newTask.getServiceTime());
        }catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.out.println("Server interrupted");
        }
    }
    public void run(){
       while(!Thread.currentThread().isInterrupted()) {
           synchronized (lock) {
               try {
                   while (!taskInProgress && !tasks.isEmpty()) {
                       lock.wait();
                   }
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                   System.out.println("error");
                   return;
               }
           if (!tasks.isEmpty()) {
               Task currentTask = tasks.peek();
               if (currentTask.getServiceTime() >= 0) {
                   currentTask.decrementServiceTime();
                   this.waitingPeriod.addAndGet(-1);
               }
               if (currentTask.getServiceTime() == -1) {
                   tasks.poll();
               }
               taskInProgress = false;
               lock.notifyAll();
           }
       }

       }
    }
    public void processNextTask(){
        synchronized (lock){
            taskInProgress = true;
            lock.notify();
        }
    }
    public Task[] getTasks(){
        return this.tasks.toArray(new Task[0]);
    }
    public Task getFirstTask(){
        return this.tasks.peek();
    }
    public int getWaitingPeriod() {
        return this.waitingPeriod.get();
    }
    public int isOpen(){
        if(tasks.size() == 1){
            return 1;
        }
        else{
            return 0;
        }
    }
}
