package com.tossapon.stadiumfinder.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Network.LocationSocketIO;
import com.tossapon.stadiumfinder.Network.NotificationSocketIO;
import com.tossapon.stadiumfinder.Network.Server;
import com.tossapon.stadiumfinder.Util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by benvo_000 on 21/2/2559.
 */
public class NotificationService extends IntentService{

    private static final String TAG = "NotificationService";
    String userId = null;
    String blockedUser = null;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Server.NOTIFICATION_SOCKET);
        } catch (URISyntaxException e) {
            Log.d(TAG, "instance initializer: " + e.getMessage());
        }
    }

    public NotificationService() {
        super("com.tossapon.stadiumfinder.Services.NotificationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            userId = FileUtil.readFile(getApplicationContext(), "currentUser");
            File f = new File(getFilesDir().getPath() + "/blockFriend" + AppUser.getInstance().getFacebook_id());
            if(f.exists()) {
                blockedUser = FileUtil.readFile(getApplicationContext(), "blockFriend" + userId);
            }else{
                blockedUser = "[]";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: " + userId);
        mSocket.on(userId, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    blockedUser = FileUtil.readFile(getApplicationContext(), "blockFriend" + userId);
                    JSONObject jsonObject = new JSONObject((String) args[0]);
                    Log.d(TAG, "call: " + args[0]);
                    if(!blockedUser.contains(jsonObject.getString("fromUserId"))) {
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationService.this)
                                .setSmallIcon(R.drawable.icon)
                                .setContentTitle(jsonObject.getString("fromUser") + " ชวนคุณ")
                                .setContentText(jsonObject.getString("data"));
                        NotificationManager mNotifyMgr =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        mNotifyMgr.notify(001, mBuilder.build());
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "call: " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mSocket.connect();
        Log.d(TAG, "onCreate: " + mSocket.connected());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mSocket.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
