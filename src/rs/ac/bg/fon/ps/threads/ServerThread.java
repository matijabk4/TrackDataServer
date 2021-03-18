/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.form.FrmMain;

/**
 *
 * @author Matija
 */
public class ServerThread extends Thread {

    FrmMain mainForm;
    boolean kraj = false;
    ServerSocket ss;
    ProcessRequests pr;

    public ServerThread(FrmMain mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(9000);
            mainForm.setSuccess();
            while (!kraj) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = ss.accept();

                System.out.println("Connected!");
                handleClient(clientSocket);
                Controller.getInstance().addUser(pr);
            }
        } catch (Exception ex) {
            //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            mainForm.setFailure();
            System.out.println("Server stopped!");
        }
    }

    private void handleClient(Socket socket) throws Exception {
        pr = new ProcessRequests(socket);
        pr.start();
    }

    public void stopServer() {
        kraj = true;
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
