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
import rs.ac.bg.fon.ps.domain.MotorcycleMake;
import rs.ac.bg.fon.ps.domain.Race;
import rs.ac.bg.fon.ps.domain.RaceItem;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author Matija
 */
public class RepositoryDBRace implements DBRepository<Race> {

    @Override
    public List<Race> getAll() throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            List<Race> races = new ArrayList<>();
            String sql = "SELECT * FROM race";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy.");
            while (rs.next()) {
                Race race = new Race();
                race.setId(rs.getInt("id"));
                race.setTrack(rs.getString("track"));
                race.setDate(rs.getDate("date"));
                race.setName(rs.getString("name"));
                race.setTotalLaps(rs.getInt("totallaps"));
                races.add(race);
            }
            rs.close();
            statement.close();
            return races;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public void add(Race race) throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            String sql = "insert into race (track,date,name,totallaps,created_on) values (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, race.getTrack());
            ps.setDate(2, new java.sql.Date(race.getDate().getTime()));
            ps.setString(3, race.getName());
            ps.setInt(4, race.getTotalLaps());
            ps.setTimestamp(5, race.getCreatedOn());
            ps.executeUpdate();
            ResultSet rsKey = ps.getGeneratedKeys();
            if (rsKey.next()) {
                int id = rsKey.getInt(1);
                race.setId(id);
                sql = "insert into race_item(id,racenumber,riderid,startposition,teamid) values (?,?,?,?,?) ";
                ps = conn.prepareStatement(sql);
                for (RaceItem item : race.getItems()) {
                    ps.setInt(1, race.getId());
                    ps.setInt(2, item.getRaceNumber());
                    ps.setLong(3, item.getRider().getID());
                    ps.setInt(4, item.getStartPosition());
                    ps.setInt(5, item.getTeam().getId());
                    ps.executeUpdate();
                }
            } else {
                throw new Exception("Race id is not generated!");
            }
            ps.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Race could not be created!");

        }
    }

    @Override
    public void edit(Race r) throws Exception {
         try {
            String sql = "UPDATE race SET "
                    + "track='" + r.getTrack()+ "', "
                    + "date='" +new java.sql.Date(r.getDate().getTime())+ "', "
                    + "name='" + r.getName()+ "',"
                    + "totallaps='" + r.getTotalLaps()+ "',"
                    + "updated_on='" + r.getUpdatedOn() + "' "
                    + "WHERE id=" + r.getId();
            System.out.println(sql);
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Update race DB error: \n" + ex.getMessage());
        }
    }

    @Override
    public void delete(Race param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Race> getByCriteria(Object criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void update(Object rr) throws Exception {
        try {
            int raceId = (int) rr;

            Connection conn = DBConnectionFactory.getInstance().getConnection();

            String sql = "update race set updated_on=? where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));

            ps.setInt(2, raceId);

            int rows = ps.executeUpdate();
            System.out.println(rows);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public List<Race> getAll(Race param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
