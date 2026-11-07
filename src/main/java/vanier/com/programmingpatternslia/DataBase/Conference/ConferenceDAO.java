package vanier.com.programmingpatternslia.DataBase.Conference;

import vanier.com.programmingpatternslia.Model.Conference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConferenceDAO {

    String url = "jdbc:sqlite:DataBase.db";

    public void addConference(Conference object){
        String sql = "INSERT INTO Conference(Title, Capacity, C_Date, Start_Time, End_time, Location) VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection con = DriverManager.getConnection(url);
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setString(1, object.getTitle());
            pstmt.setInt(2, object.getCapacity());
            pstmt.setString(3, object.getC_date());
            pstmt.setString(4, object.getStart_time());
            pstmt.setString(5, object.getEnd_time());
            pstmt.setString(6, object.getLocation());
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            System.err.println("Database Insertion Error:" + e.getMessage());

        }
    }

    public void removeConference(int c_id){
        String sql = "DELETE FROM Conference WHERE c_id = ?";
        try(Connection con = DriverManager.getConnection(url);
            PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, c_id);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Database Deletion Error:" + e.getMessage());
        }

    }

    public void updateConference(int c_id, String columnName, String newValue){
        String sql = "UPDATE Conference SET ? = ? WHERE C_ID = ?";
        try(Connection con = DriverManager.getConnection(url);
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, columnName);
            if(columnName.equalsIgnoreCase("capacity")){
                int newValueInt = Integer.parseInt(newValue);
                pstmt.setInt(2, newValueInt);
            }
            else{
                pstmt.setString(2, newValue);
            }
            pstmt.setInt(3, c_id);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            System.err.println("Database Update Error:" + e.getMessage());

        }

    }
}
