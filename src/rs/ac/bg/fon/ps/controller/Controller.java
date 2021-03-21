/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.domain.Race;
import rs.ac.bg.fon.ps.domain.RaceItem;
import rs.ac.bg.fon.ps.domain.RacingTeam;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.form.FrmMain;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operation.rider.DeleteRider;
import rs.ac.bg.fon.ps.operation.rider.DeleteTeam;
import rs.ac.bg.fon.ps.operation.rider.SaveRider;
import rs.ac.bg.fon.ps.operation.rider.SaveTeam;
import rs.ac.bg.fon.ps.operation.rider.ShowAllRiders;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DBRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBRace;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBRaceItem;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBRider;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBTeam;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBUser;
import rs.ac.bg.fon.ps.threads.ProcessRequests;
import rs.ac.bg.fon.ps.threads.ServerThread;

/**
 *
 * @author Matija
 */
public class Controller {

    private static Controller controller;
    private final Repository repositoryUser;
    private final Repository repositoryRider;
    private final Repository repositoryTeam;
    private final Repository repositoryRaceItem;
    private static User currentUser;
    private final Repository repositoryRace;
    private final Repository repositoryGeneric;
    List<ProcessRequests> usersThreadsList;
    List<User> activeUsers;
    ServerThread st;
    private final FrmMain frmMain;

    private Controller() {
        repositoryUser = new RepositoryDBUser();
        repositoryRider = new RepositoryDBRider();
        repositoryTeam = new RepositoryDBTeam();
        repositoryRace = new RepositoryDBRace();
        repositoryRaceItem = new RepositoryDBRaceItem();
        currentUser = new User();
        this.repositoryGeneric = new RepositoryDBGeneric();
        usersThreadsList = new ArrayList<>();
        activeUsers = new ArrayList<>();
        frmMain = new FrmMain();
        addActionListener();
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public User login(String username, String password) throws Exception {
        List<User> users = repositoryUser.getAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (!activeUsers.contains(user)) {
                    activeUsers.add(user);
                    return user;
                } else {
                    throw new Exception("User already logged in!");
                }
            }
        }
        throw new Exception("Unknown user!");
    }

    public void addRider(Rider rider) throws Exception {
        /*((DBRepository) repositoryGeneric).connect();
        try {
            repositoryGeneric.add(rider);
            ((DBRepository) repositoryGeneric).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryGeneric).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }*/
        AbstractGenericOperation operation = new SaveRider();
        operation.execute(rider);
    }

    public List<Rider> getAllRiders() throws Exception {
        List<Rider> riders = null;
        //((DBRepository) repositoryRider).connect();
        try {
            riders = repositoryRider.getAll();
            //((DBRepository) repositoryRider).commit();
        } catch (Exception e) {
            e.printStackTrace();
            //((DBRepository) repositoryRider).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        return riders;
        /*AbstractGenericOperation operation = new ShowAllRiders();
        operation.execute(new Rider());
        return riders;*/
    }

    public void deleteRider(Rider r) throws Exception {
        ((DBRepository) repositoryRider).connect();
        try {
            repositoryRider.delete(r);
            ((DBRepository) repositoryRider).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRider).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        /*AbstractGenericOperation operation = new DeleteRider();
        operation.execute(r);*/
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void editRider(Rider rider) throws Exception {
        ((DBRepository) repositoryRider).connect();
        try {
            ((DBRepository) repositoryRider).edit(rider);
            ((DBRepository) repositoryRider).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRider).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void addTeam(RacingTeam team) throws Exception {
        /*((DBRepository) repositoryTeam).connect();
        try {
            repositoryTeam.add(team);
            ((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }*/
        AbstractGenericOperation operation = new SaveTeam();
        operation.execute(team);
    }

    public List<RacingTeam> getAllTeams() throws Exception {
        List<RacingTeam> teams = null;
        //((DBRepository) repositoryTeam).connect();
        try {
            teams = repositoryTeam.getAll();
            //((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            //((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        return teams;
    }

    public void deleteTeam(RacingTeam team) throws Exception {
        ((DBRepository) repositoryTeam).connect();
        try {
            repositoryTeam.delete(team);
            ((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        /*AbstractGenericOperation operation = new DeleteTeam();
        operation.execute(team);*/
    }

    public void editTeam(RacingTeam team) throws Exception {
        ((DBRepository) repositoryTeam).connect();
        try {
            ((DBRepository) repositoryTeam).edit(team);
            ((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void addRace(Race race) throws Exception {
        ((DBRepository) repositoryRace).connect();
        try {
            repositoryRace.add(race);
            ((DBRepository) repositoryRace).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRace).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public List<Race> getAllRaces() throws Exception {
        List<Race> races = null;
        //((DBRepository) repositoryTeam).connect();
        try {
            races = repositoryRace.getAll();
            //((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            //((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        return races;
    }

    public List<RaceItem> getRaceItems(int raceId) throws Exception {
        List<RaceItem> raceItems = null;
        //((DBRepository) repositoryTeam).connect();
        try {
            raceItems = repositoryRaceItem.getByCriteria(raceId);
            //((DBRepository) repositoryTeam).commit();
        } catch (Exception e) {
            e.printStackTrace();
            //((DBRepository) repositoryTeam).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
        return raceItems;
    }

    public void saveRaceResult(List<RaceItem> raceItemsForSave) throws Exception {

        ((DBRepository) repositoryRaceItem).connect();
        try {
            for (RaceItem r : raceItemsForSave) {
                repositoryRaceItem.update(r);
                ((DBRepository) repositoryRaceItem).commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRaceItem).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void updateRaceInfo(int id) throws Exception {
        ((DBRepository) repositoryRace).connect();
        try {
            repositoryRace.update(id);
            ((DBRepository) repositoryRace).commit();

        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRace).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void editRace(Race race) throws Exception {
        ((DBRepository) repositoryRace).connect();
        try {
            ((DBRepository) repositoryRace).edit(race);
            ((DBRepository) repositoryRace).commit();
        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRace).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void editRaceItems(Race race, List<RaceItem> items) throws Exception {
        ((DBRepository) repositoryRaceItem).connect();
        if (!items.isEmpty()) {
            ((DBRepository) repositoryRaceItem).delete(items.get(0));
            ((DBRepository) repositoryRaceItem).commit();
        } else {
            RaceItem r = new RaceItem();
            r.setRace(race);
            ((DBRepository) repositoryRaceItem).delete(r);
            ((DBRepository) repositoryRaceItem).commit();
            return;
        }
        try {
            for (RaceItem r1 : race.getItems()) {
                r1.setRace(race);
                ((DBRepository) repositoryRaceItem).edit(r1);
                ((DBRepository) repositoryRaceItem).commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ((DBRepository) repositoryRaceItem).rollback();
            throw e;
        } finally {
            //((DBRepository) repositoryRider).disconnect();
        }
    }

    public void startServer(FrmMain mainForm) {
        Controller.getInstance().getActiveUsers().clear();
        st = new ServerThread(mainForm);
        st.start();
    }

    public void stopServer() {
        for (ProcessRequests processRequests : usersThreadsList) {
            try {
                processRequests.logout();
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        st.stopServer();
    }

    public void addUser(ProcessRequests pr) {
        usersThreadsList.add(pr);
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void addActionListener() {
                
        frmMain.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                frmMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                try {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?\n Server will shut down automatically.", "TrackData exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Controller.getInstance().stopServer();
                        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

}
