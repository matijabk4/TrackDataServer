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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.MotorcycleMake;
import rs.ac.bg.fon.ps.domain.RaceItem;
import rs.ac.bg.fon.ps.domain.RacingTeam;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author Matija
 */
public class RepositoryDBTeam implements DBRepository<RacingTeam> {

    @Override
    public List<RacingTeam> getAll() throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            List<RacingTeam> teams = new ArrayList<>();
            String sql = "SELECT * FROM team";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                RacingTeam team = new RacingTeam();
                team.setId(rs.getInt("id"));
                team.setBudget(rs.getDouble("budget"));
                team.setGeneralManager(rs.getString("generalmanager"));
                team.setHeadquarters(rs.getString("headquarters"));
                team.setName(rs.getString("name"));
                team.setSponsor(rs.getString("sponsor"));
                teams.add(team);
            }
            rs.close();
            statement.close();
            return teams;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
    }

    @Override
    public void add(RacingTeam team) throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            String sql = "insert into team values (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, team.getId());
            ps.setString(2, team.getSponsor());
            ps.setDouble(3, team.getBudget());
            ps.setString(4, team.getName());
            ps.setString(5, team.getGeneralManager());
            ps.setString(6, team.getHeadquarters());
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Team could not be created!");

        }
    }

    @Override
    public void edit(RacingTeam r) throws Exception {
        try {
            String sql = "UPDATE team SET "
                    + "sponsor='" + r.getSponsor() + "', "
                    + "budget='" + r.getBudget() + "', "
                    + "name='" + r.getName() + "',"
                    + "generalmanager='" + r.getGeneralManager() + "',"
                    + "headquarters='" + r.getHeadquarters() + "' "
                    + "WHERE id=" + r.getId();
            System.out.println(sql);
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Update team DB error: \n" + ex.getMessage());
        }
    }

    @Override
    public void delete(RacingTeam r) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
             String sql = "DELETE FROM race_item where teamid = "+r.getId();
            System.out.println(sql);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            sql = "DELETE FROM team WHERE id=" + r.getId();
            System.out.println(sql);
            
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Delete team DB error: \n" + ex.getMessage());
        }
    }


    @Override
    public List<RacingTeam> getByCriteria(Object criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RacingTeam> getAll(RacingTeam param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
