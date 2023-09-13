package com.example.main_project_final.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class NetworkUtil implements Serializable {
    private final Socket socket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public NetworkUtil(String s, int port) throws IOException {
        this.socket = new Socket(s,port);
        oos = new ObjectOutputStream(socket.getOutputStream());//output stream should be opened first
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkUtil(Socket s) throws IOException {
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readUnshared();//when we read we also have to return it
    }

    public void write(Object o) throws IOException {
        oos.writeUnshared(o);//when we write we don't have to return anything

    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
    }
}

