package com.tossapon.stadiumfinder.Network;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by benvo_000 on 23/2/2559.
 */
public class RealTimeStadiumPeopleSocketIO {
    private static Socket ourInstance;
    private static final String TAG = "NotificationSocketIO";

    public static Socket getInstance() {
        if (ourInstance == null){
            try {
                ourInstance = IO.socket(Server.STADIUM_SOCKET);
                ourInstance.connect();
            } catch (URISyntaxException e) {
            }
        }
        ourInstance.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: " + "connect error" + args[0]);
            }
        });
        ourInstance.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: " + "time out" + args[0]);
            }
        });
        return ourInstance;
    }
}
