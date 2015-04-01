package net.viotech.jettyservlet;

import android.os.AsyncTask;

public class RunServerTask extends AsyncTask<Void, Integer, Void> {

    private final JettyServerRunner runner;

    public RunServerTask() {
        this.runner = new JettyServerRunner();
    }

    @Override
    protected Void doInBackground(Void... args) {
        try {
            runner.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void stop() {
        try {
            runner.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}