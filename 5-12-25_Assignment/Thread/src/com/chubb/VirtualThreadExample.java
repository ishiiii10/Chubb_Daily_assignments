package com.chubb;

public class VirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread virtualThread =
            Thread.ofVirtual().name("MyVirtualThread").start(() -> {
                System.out.println("Virtual thread running: " +
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

        virtualThread.join(); // Wait for the thread to finish
        System.out.println("Virtual thread finished.");
    }
}