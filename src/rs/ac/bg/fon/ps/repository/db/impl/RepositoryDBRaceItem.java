/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.Race;
import rs.ac.bg.fon.ps.domain.RaceItem;
import rs.ac.bg.fon.ps.domain.RacingTeam;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author Matija
 */
public class RepositoryDBRaceItem implements DBRepository<RaceItem> {

    @Override
    public List<RaceItem> getAll() throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            List<RaceItem> raceItems = new ArrayList<>();
            String sql = "SELECT * FROM race_item";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                RaceItem raceItem = new RaceItem();
                Race r = new Race();
                r.setId(rs.getInt("id"));
                raceItem.setRace(r);

                raceItem.setRaceNumber(rs.getInt("racenumber"));

                Rider rdr = new Rider();
                rdr.setID(rs.getLong("id"));
                raceItem.setRider(rdr);

                raceItem.setStartPosition(rs.getInt("startposition"));
                raceItem.setNumberOfLaps(rs.getInt("numberoflaps"));
                raceItem.setAverageSpeed(rs.getDouble("averagespeed"));
                raceItem.setTopSpeed(rs.getDouble("topspeed"));
                raceItem.setFastestLap(rs.getTime("fastestlap"));
                raceItem.setFinishPosition(rs.getInt("finishposition"));

                RacingTeam team = new RacingTeam();
                team.setId(rs.getInt("teamid"));
                raceItem.setTeam(team);

                raceItems.add(raceItem);
            }
            rs.close();
            statement.close();
            return raceItems;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public void add(RaceItem param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(RaceItem r) throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();

            String sql = "INSERT INTO race_item(id,racenumber,riderid,startposition,teamid) VALUES ((SELECT id FROM race WHERE id=?),?,(SELECT id FROM rider WHERE id=?),?,(SELECT id FROM team WHERE id=?))";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, r.getRace().getId());
            ps.setInt(2, r.getRaceNumber());
            ps.setLong(3, r.getRider().getID());
            ps.setInt(4, r.getStartPosition());
            ps.setInt(5, r.getTeam().getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Race item could not be deleted!");

        }
    }

    @Override
    public void delete(RaceItem r) throws Exception {
        if (r.getRace() != null) {
            try {

                String sql = "DELETE FROM race_item WHERE id=" + r.getRace().getId();
                System.out.println(sql);
                Connection connection = DBConnectionFactory.getInstance().getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Delete race item DB error: \n" + ex.getMessage());
            }
        }

    }

    @Override
    public List<RaceItem> getByCriteria(Object criteria) throws Exception {
        try {
            int id = Integer.parseInt(criteria.toString());
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            List<RaceItem> raceItems = new ArrayList<>();
            String sql = "SELECT ri.id,ri.racenumber,ri.riderid,ri.startposition,ri.numberoflaps,ri.averagespeed,ri.topspeed,ri.fastestlap,ri.finishposition,ri.teamid,r.firstname,r.surname,t.name FROM race_item ri JOIN rider r ON ri.riderid=r.id  JOIN team t ON t.id=ri.teamid WHERE ri.id=" + id + " ORDER BY finishposition ASC";
            System.out.println(sql);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                RaceItem raceItem = new RaceItem();
                Race r = new Race();
                r.setId(rs.getInt("id"));
                raceItem.setRace(r);

                raceItem.setRaceNumber(rs.getInt("racenumber"));

                Rider rdr = new Rider();
                rdr.setID(rs.getLong("riderid"));
                rdr.setFirstname(rs.getString("firstname"));
                rdr.setSurname(rs.getString("surname"));
                raceItem.setRider(rdr);

                raceItem.setStartPosition(rs.getInt("startposition"));
                raceItem.setNumberOfLaps(rs.getInt("numberoflaps"));
                raceItem.setAverageSpeed(rs.getDouble("averagespeed"));
                raceItem.setTopSpeed(rs.getDouble("topspeed"));
                raceItem.setFastestLap(rs.getTime("fastestlap"));
                raceItem.setFinishPosition(rs.getInt("finishposition"));

                RacingTeam team = new RacingTeam();
                team.setId(rs.getInt("teamid"));
                team.setName(rs.getString("name"));
                raceItem.setTeam(team);

                raceItems.add(raceItem);
            }
            rs.close();
            statement.close();
            return raceItems;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public void update(Object rr) throws Exception {
        try {
            RaceItem r = (RaceItem) rr;

            Connection conn = DBConnectionFactory.getInstance().getConnection();

            String sql = "update race_item set numberoflaps=?, averagespeed=?, topspeed=?, fastestlap=?, finishposition=? where id=? and riderid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getNumberOfLaps());
            System.out.println(r.getRace().getId() + "\n" + r.getRider().getID() + "\n" + r.getNumberOfLaps() + " " + r.getAverageSpeed() + " " + r.getTopSpeed() + " " + r.getFinishPosition() + " " + r.getFastestLap());
            System.out.println("RIDERID " + r.getRider().getID());
            ps.setDouble(2, r.getAverageSpeed());
            ps.setDouble(3, r.getTopSpeed());
            ps.setTime(4, r.getFastestLap());
            ps.setInt(5, r.getFinishPosition());

            ps.setInt(6, r.getRace().getId());
            ps.setLong(7, r.getRider().getID());
            int rows = ps.executeUpdate();
            System.out.println(rows);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public List<RaceItem> getAll(RaceItem param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
