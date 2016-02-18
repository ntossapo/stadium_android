package com.tossapon.stadiumfinder.App;

import android.util.Log;

import com.tossapon.stadiumfinder.Network.Server;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by benvo_000 on 16/2/2559.
 */
public class SocketIO {
    private static Socket ourInstance;
    private static final String TAG = "SocketIO";

    public static Socket getInstance()
    {
        if (ourInstance == null){
            try {
                ourInstance = IO.socket(Server.SOCKETIO);
                ourInstance.connect();
                Log.d(TAG, "instance initializer: initial ok" );
            } catch (URISyntaxException e) {
                Log.d(TAG, "instance initializer: " + e.getMessage());
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
