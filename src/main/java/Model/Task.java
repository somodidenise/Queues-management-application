package Model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Task {
    private int arrivalTime;
    private int serviceTime;
    private int key;
    private  int indexOfServer;
    public Task(int arrivalTime, int serviceTime, int key){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.key = key;
    }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getServiceTime(){
        return this.serviceTime;
    }
    public int getKey(){
        return this.key;
    }

    public void setIndexOfServer(int indexOfServer) {
        this.indexOfServer = indexOfServer;
    }

    public int getIndexOfServer(){
        return this.indexOfServer;
    }

    public String toString(){
        String s;
        s = "(" + this.key + "," + this.arrivalTime + "," + this.serviceTime + ")";
        return s;
    }
    public void decrementServiceTime(){
        if(this.serviceTime >= 0) {
            this.serviceTime--;
        }
    }
    public  boolean isReady(){
        if(this.serviceTime == 0)
        {
            return TRUE;
        }
        else{
            return FALSE;
        }
    }

}
