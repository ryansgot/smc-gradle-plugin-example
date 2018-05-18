package com.fsryan.example.smcgradleplugin.android;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ScrollView console;
    private TextView consoleText;

    private TurnstileContext turnstileContext;

    private Handler appendHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        console = findViewById(R.id.console);
        consoleText = findViewById(R.id.console_text);

        appendHandler = new Handler();
        turnstileContext = new TurnstileContext(new Turnstile() {
            @Override
            public void alarm() {
                appendToConsole("ALARM!!!");
            }

            @Override
            public void unlock() {
                appendToConsole("Unlocked--you may proceed");
            }

            @Override
            public void thankyou() {
                appendToConsole("Thank you!");
            }

            @Override
            public void lock() {
                appendToConsole("Locked--you shall not pass");
            }
        });
        turnstileContext.enterStartState();
    }

    @SuppressWarnings("unused")
    public void onCoinClicked(View v) {
        turnstileContext.coin();
    }

    @SuppressWarnings("unused")
    public void onPassClicked(View v) {
        turnstileContext.pass();
    }

    void appendToConsole(String str) {
        if (consoleText.getText().length() == 0) {
            consoleText.setText("> " + str);
            return;
        }

        consoleText.append("\n> " + str);
        appendHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                console.fullScroll(View.FOCUS_DOWN);
            }
        },
        150);
    }

}
