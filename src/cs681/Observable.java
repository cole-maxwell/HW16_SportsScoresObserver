package cs681;

import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Observable 
{

    private LinkedList<Observer> observers = new LinkedList<Observer>();
    private AtomicBoolean changed = new AtomicBoolean(false);
    private ReentrantLock lockObs = new ReentrantLock();

    public Observable() {};

    public Observer getObserver(){
        return this.observers.get(0);
    }

    public void addObserver(Observer o) {
        if (observers.contains(o))
            System.out.println("Observer already added");
        else {
            lockObs.lock();
            observers.add(o);
            lockObs.unlock();
        }
    };

    public void deleteObserver(Observer o) {
        if (observers.contains(o))
            observers.remove(o);
        else
            System.out.println("Observer doesn't exist, so it cannot be removed.");
        
    };

    protected void setChanged() {
        this.changed.set(true);
    };

    protected void clearChanged() {
        this.changed.set(false);
    };

    public boolean hasChanged() {
        return this.changed.get();
    };

    public void notifyObservers(Object obj) {
        lockObs.lock();
        synchronized(this) {
            if( hasChanged() ){
                for (Observer o : observers) {
                    System.out.println("Updated with: " + o);
                }
                this.clearChanged();
            }
        }
        lockObs.unlock();
    }
}

