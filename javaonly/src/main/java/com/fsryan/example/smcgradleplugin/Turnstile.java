package com.fsryan.example.smcgradleplugin;

public interface Turnstile {
    void alarm();
    void unlock();
    void thankyou();
    void lock();
}
