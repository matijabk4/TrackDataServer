/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Race;
import rs.ac.bg.fon.ps.domain.RaceItem;
import rs.ac.bg.fon.ps.domain.RacingTeam;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.threads.ProcessRequests;

/**
 *
 * @author Matija
 */
public class Server {

    public void startServer() {
        try {
            
            
            ServerSocket serverSocket = new ServerSocket(9000);
            while(true){
            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Connected!");
            handleClient(socket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void handleClient(Socket socket) throws Exception {
        ProcessRequests ps = new ProcessRequests(socket);
        ps.start();
    }

}
