package net.viotech.jettyservlet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private RunServerTask task;
    private Boolean isRunning = false;
    private Button btnJetty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJetty = (Button) findViewById(R.id.button);
        btnJetty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    stopServer();
                } else {
                    startServer();
                }
                isRunning = !isRunning;
                updateStatus();
            }
        });
    }

    private void updateStatus() {
        btnJetty.setText(isRunning ? "Stop Jetty" : "Start Jetty");
    }

    private void startServer() {
        task = new RunServerTask();
        task.execute();
        Toast.makeText(getApplicationContext(), "Jetty server is now running", Toast.LENGTH_SHORT).show();
    }

    private void stopServer() {
        task.stop();
        Toast.makeText(getApplicationContext(), "Jetty server stopped", Toast.LENGTH_SHORT).show();
    }
}
