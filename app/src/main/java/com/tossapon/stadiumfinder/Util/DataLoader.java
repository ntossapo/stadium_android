package com.tossapon.stadiumfinder.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import com.tossapon.stadiumfinder.AppModel.Response;

/**
 * Created by Tossapon on 22/12/2558.
 */
public class DataLoader {
    private static DataLoader ourInstance = null;
    public static DataLoader getInstance() {
        return ourInstance;
    }
    public static boolean isNull(){return ourInstance==null;}

    private Runnable runnable;
    private Activity activity;
    private ProgressDialog progressDialog;
    private String title = "Loading";
    private String detail = "New Loading";
    public static Response res = null;

    private DataLoader(Activity act, Runnable run) throws Exception {
        if(run != null)
            runnable = run;
        else
            throw new Exception("Runable can't be null");

        if(act != null)
            activity = act;
        else
            throw new Exception("Activity can't be null");
    }

    public DataLoader setTitle(String title){
        this.title = title;
        return this;
    }

    public DataLoader setDetail(String detail){
        this.detail = detail;
        return this;
    }

    public void run(){
        progressDialog = ProgressDialog.show(activity, title, detail);
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                try {
                    (new Thread(runnable)).join();
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Response getResponse(){
        return res;
    }

    private void stop() throws Exception {
        if(progressDialog != null)
            progressDialog.dismiss();
        else
            throw new Exception("progress bar cannot be null");
    }

    public static class Factory{
        public static void Build(Activity act, Runnable run){
            try {
                ourInstance = new DataLoader(act, run);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
