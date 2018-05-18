package com.fsryan.example.smcgradleplugin;

public class Main {

    public static void main(String[] args) {
        TurnstileContext tc = new TurnstileContext(new Turnstile() {

            @Override
            public void alarm() {
                System.out.println("Turnstile: alarm()");
            }

            @Override
            public void unlock() {
                System.out.println("Turnstile: unlock()");
            }

            @Override
            public void thankyou() {
                System.out.println("Turnstile: thankyou()");
            }

            @Override
            public void lock() {
                System.out.println("Turnstile: lock()");
            }
        });

        tc.enterStartState();
        tc.coin();
        tc.pass();
    }
}
