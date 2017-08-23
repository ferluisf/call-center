package com.am.call.model;

import com.am.call.dispatcher.EmployeeRunnable;

import java.util.concurrent.*;

/**
 * It maintains the general state of the call center, the employee runnables and its queues. It have the logic to
 * assign a call to the corresponding employee.
 */
public class CallCenter {

    private Semaphore freeEmployees;

    private int operatorsFree;
    private int supervisorsFree;
    private int directorsFree;

    private LinkedBlockingQueue<Call> operatorQueue = new LinkedBlockingQueue<Call>();
    private LinkedBlockingQueue<Call> supervisorQueue = new LinkedBlockingQueue<Call>();
    private LinkedBlockingQueue<Call> directorQueue = new LinkedBlockingQueue<Call>();

    private ExecutorService operatorExecutor;
    private ExecutorService supervisorExecutor;
    private ExecutorService directorExecutor;

    public CallCenter(int numberOfOperators, int numberOfSupervisors, int numberOfDirectors){
        this.operatorExecutor = createEmployeeExecutor(numberOfOperators);
        this.supervisorExecutor = createEmployeeExecutor(numberOfOperators);
        this.directorExecutor = createEmployeeExecutor(numberOfOperators);

        this.operatorsFree = numberOfOperators;
        this.supervisorsFree = numberOfSupervisors;
        this.directorsFree = numberOfDirectors;

        this.freeEmployees = new Semaphore(numberOfOperators + numberOfSupervisors + numberOfDirectors);

        for(int i=0; i < numberOfOperators; i++){
            operatorExecutor.submit(new EmployeeRunnable(new Operator(i, this), operatorQueue));
        }
        for(int i=0; i < numberOfSupervisors; i++){
            supervisorExecutor.submit(new EmployeeRunnable(new Supervisor(1000+i, this), supervisorQueue));
        }
        for(int i=0; i < numberOfDirectors; i++){
            directorExecutor.submit(new EmployeeRunnable(new Director(9000+i, this), directorQueue));
        }

        System.out.println("Call center started with: " + this.getFreeEmployeesString());
    }

    private ThreadPoolExecutor createEmployeeExecutor(int numberOfEmployees) {
        // SynchronousQueue as we will have exactly 'numberOfEmployees' threads without queue.
        return new ThreadPoolExecutor(numberOfEmployees, numberOfEmployees,0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
    }


    /**
     * Acquire a free employee if there is any available. Otherwise wait till any become available. Then dispatch the call to the employee.
     * @param call to be dispatched
     */
    public void waitFreeEmployeeAndDispatchCall(Call call) throws InterruptedException {
        System.out.println("Trying to acquire employee and dispatch call " + call.getId());
        freeEmployees.acquire();
        dispatchCall(call);
    }

    public int getNumberOfFreeEmployees() {
        return freeEmployees.availablePermits();
    }

    /**
     * Assign the call to a free employee in the following priority order: operator, supervisor, director.
     * @param call to be asigned
     */
    private synchronized void dispatchCall(Call call) throws InterruptedException {
        if(this.operatorsFree > 0){
            // Operator free. Assign call to operator
            this.operatorsFree--;
            System.out.println("Assigning call " + call.getId() + " to operator. " + getFreeEmployeesString());
            operatorQueue.put(call);
        } else {
            if(this.supervisorsFree > 0) {
                // Supervisor free. Assign call to supervisor
                this.supervisorsFree--;
                System.out.println("Assigning call " + call.getId() + " to supervisor. " + getFreeEmployeesString());
                supervisorQueue.put(call);
            } else {
                if(this.directorsFree > 0){
                    // Director free. Assign call to director
                    this.directorsFree--;
                    System.out.println("Assigning call " + call.getId() + " to director. " + getFreeEmployeesString());
                    directorQueue.put(call);
                } else {
                    //No employee free. Something went wrong
                    throw new RuntimeException("Going to dispatch call but no employee free");
                }
            }
        }
    }

    private String getFreeEmployeesString(){
        return "Free employees -> Total: " + (operatorsFree + supervisorsFree + directorsFree) + " | Operators: " + operatorsFree +
                " | Supervisors: " + supervisorsFree + " | Directors: " + directorsFree;
    }

    public void releaseOperator(){
        freeOperator();
        freeEmployees.release();
        System.out.println("Operator free. " + getFreeEmployeesString());
    }

    private synchronized void freeOperator() {
        operatorsFree++;
    }

    public void releaseSupervisor(){
        freeSupervisor();
        freeEmployees.release();
        System.out.println("Supervisor free. " + getFreeEmployeesString());
    }

    private synchronized void freeSupervisor() {
        supervisorsFree++;

    }

    public void releaseDirector() {
        freeDirector();
        freeEmployees.release();
        System.out.println("Director released. " + getFreeEmployeesString());
    }

    private synchronized void freeDirector() {
        directorsFree++;
    }
}
