package com.example;

public class App {
    public String getGreeting() {
        return "Hello, Jenkins CI/CD!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}