/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.Operation;
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

/**
 *
 * @author Matija
 */
public class ProcessRequests extends Thread {

    Socket socket;
    Sender sender;
    Receiver receiver;
    boolean kraj=false;

    public ProcessRequests(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {

        while (!kraj) {
            try {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    switch (request.getOperation()) {
                        case LOGIN:
                            User user = (User) request.getArgument();
                            response.setResult(Controller.getInstance().login(user.getUsername(), user.getPassword()));
                            break;
                        case GET_ALL_RIDERS:
                            response.setResult(Controller.getInstance().getAllRiders());
                            break;
                        case GET_ALL_TEAMS:
                            response.setResult(Controller.getInstance().getAllTeams());
                            break;
                        case GET_ALL_RACES:
                            response.setResult(Controller.getInstance().getAllRaces());
                            break;
                        case ADD_RIDER:
                            Rider riderAdd = (Rider) request.getArgument();
                            Controller.getInstance().addRider(riderAdd);
                            response.setResult(riderAdd);
                            break;
                        case EDIT_RIDER:
                            Rider riderEdit = (Rider) request.getArgument();
                            Controller.getInstance().editRider(riderEdit);
                            break;
                        case DELETE_RIDER:
                            Rider riderDelete = (Rider) request.getArgument();
                            Controller.getInstance().deleteRider(riderDelete);
                            break;
                        case ADD_TEAM:
                            RacingTeam teamAdd = (RacingTeam) request.getArgument();
                            Controller.getInstance().addTeam(teamAdd);
                            break;
                        case DELETE_TEAM:
                            RacingTeam teamDelete = (RacingTeam) request.getArgument();
                            Controller.getInstance().deleteTeam(teamDelete);
                            break;
                        case EDIT_TEAM:
                            RacingTeam teamEdit = (RacingTeam) request.getArgument();
                            Controller.getInstance().editTeam(teamEdit);
                            break;
                        case ADD_RACE:
                            Race raceAdd = (Race) request.getArgument();
                            Controller.getInstance().addRace(raceAdd);
                            response.setResult(raceAdd);
                            break;
                        case EDIT_RACE:
                            Race raceEdit = (Race) request.getArgument();
                            Controller.getInstance().editRace(raceEdit);
                            break;
                        case GET_RACE_ITEMS:
                            int raceId = (int) request.getArgument();
                            response.setResult(Controller.getInstance().getRaceItems(raceId));
                            break;
                        case SAVE_RACE_RESULT:
                            List<RaceItem> raceItemsForSave = (List<RaceItem>) request.getArgument();
                            Controller.getInstance().saveRaceResult(raceItemsForSave);
                            break;
                        case UPDATE_RACE_INFO:
                            int id = (int) request.getArgument();
                            Controller.getInstance().updateRaceInfo(id);
                            break;
                        case EDIT_RACE_ITEMS:
                            Race r = (Race) request.getArgument();
                            List<RaceItem> raceItems = (List<RaceItem>) request.getArgument1();
                            Controller.getInstance().editRaceItems(r, raceItems);
                            break;
                        case LOGOUT:
                            User u = (User) request.getArgument();
                            Controller.getInstance().getActiveUsers().remove(u);
                            System.out.println(u.getUsername()+" left.");
                            break;
                        case LOGOUT_ALL:
                            
                            break;
                        case DELETE_RACE:
                            Race raceDelete = (Race) request.getArgument();
                            Controller.getInstance().deleteRace(raceDelete);
                            
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setException(e);
                }
                sender.send(response);
            } catch (Exception ex) {
                //Logger.getLogger(ProcessRequests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Response logout() throws Exception{
      
        Response response = new Response();
        response.setResult(Operation.LOGOUT_ALL);
        sender.send(response);
        kraj=true;
        return response;
    }

}
