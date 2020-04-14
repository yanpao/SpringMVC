package com.yanpao.Service;


public class RunnableDemo implements Runnable {

    private Thread t;
    private String threadName;

    public RunnableDemo(String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void start () {
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
        System.out.println("Starting " +  threadName + ";ID: "+ t.getId() );
    }

    public void run() {
        System.out.println("Running " +  threadName + ";ID: "+ t.getId());
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ";ID: "+ t.getId() + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + ";ID: "+ t.getId()+ " interrupted.");
        }
        System.out.println("Thread " +  threadName + ";ID: "+ t.getId()+ " exiting.");
    }
}
