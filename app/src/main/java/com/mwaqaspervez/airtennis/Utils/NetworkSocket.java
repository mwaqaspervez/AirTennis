package com.mwaqaspervez.airtennis.Utils;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class NetworkSocket {


    private static NetworkSocket instance = null;
    private static Socket mSocket;

    public static NetworkSocket getInstance() throws URISyntaxException {
        if (instance == null) {
            instance = new NetworkSocket();
            mSocket = IO.socket("http://192.168.0.108:4050");
        }
        return instance;

    }

    public synchronized Socket getSocket() throws URISyntaxException {
        return mSocket;
    }

}
