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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.MotorcycleMake;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author Matija
 */
public class RepositoryDBRider implements DBRepository<Rider> {

    @Override
    public List<Rider> getAll() throws Exception {
        try {
            Connection conn = DBConnectionFactory.getInstance().getConnection();
            List<Rider> riders = new ArrayList<>();
            String sql = "SELECT * FROM rider";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Rider rider = new Rider();
                rider.setID(rs.getLong("id"));
                rider.setFirstname(rs.getString("firstname"));
                rider.setSurname(rs.getString("surname"));
                rider.setNationality(rs.getString("nationality"));
                rider.setMotorcycle(MotorcycleMake.valueOf(rs.getString("motorcyclemake")));
                rider.setRacingNum(rs.getInt("racingnumber"));
                riders.add(rider);
            }
            rs.close();
            statement.close();
            return riders;
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryDBRider.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }

    }

    @Override
    public void add(Rider rider) throws Exception {
        try {

            Connection conn = DBConnectionFactory.getInstance().getConnection();
            int max = 0;
            String msg="";
            String sql = "select max(id) as maximum from rider";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                max = rs.getInt("maximum");
            }
            int id = ++max;
            rider.setID(Long.parseLong(String.valueOf(id)));
           
            
            sql = "insert into rider (id, firstname, surname, nationality, motorcyclemake, racingnumber, created_on) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id);
            ps.setString(2, rider.getFirstname());
            ps.setString(3, rider.getSurname());
            ps.setString(4, rider.getNationality());
            ps.setString(5, rider.getMotorcycle().toString());
            ps.setInt(6, rider.getRacingNum());
            ps.setDate(7, new java.sql.Date(new Date().getTime()));
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            throw new Exception("Rider could not be created!");

        }
    }

    @Override
    public void edit(Rider r) throws Exception {
        try {
            String sql = "UPDATE Rider SET "
                    + "firstname='" + r.getFirstname() + "', "
                    + "surname='" + r.getSurname() + "', "
                    + "nationality='" + r.getNationality() + "',"
                    + "motorcyclemake='" + r.getMotorcycle() + "',"
                    + "racingnumber=" + r.getRacingNum() + ", "
                    +"updated_on='"+new java.sql.Timestamp(new java.util.Date().getTime())+"' "
                    + "WHERE id=" + r.getID();
            System.out.println(sql);
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Update rider DB error: \n" + ex.getMessage());
        }
    }

    @Override
    public void delete(Rider r) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            String sql = "DELETE FROM race_item where riderid = "+r.getID();
            System.out.println(sql);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            sql = "DELETE FROM rider WHERE id=" + r.getID();
            System.out.println(sql);
            
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Delete rider DB error: \n" + ex.getMessage());
        }
    }

    @Override
    public List<Rider> getByCriteria(Object criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rider> getAll(Rider param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
