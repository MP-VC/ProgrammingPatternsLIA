package vanier.com.programmingpatternslia.DataBase.Conference;

import java.io.IOError;
import java.sql.*;
import java.util.ArrayList;

public class ConferenceDAO {

    String url = "jdbc:sqlite:DataBase.db";

    public void addConference(Conference object){
        String sql = "INSERT INTO Conference(Title, Capacity, C_Date, Start_Time, End_Time, Location) VALUES(?, ?, ?, ?, ?, ?)";
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
        String sql = "UPDATE Conference SET " + columnName + " = ? WHERE C_ID = ?";
        try(Connection con = DriverManager.getConnection(url);
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            if(columnName.equalsIgnoreCase("capacity")){
                int newValueInt = Integer.parseInt(newValue);
                pstmt.setInt(1, newValueInt);
            }
            else{
                pstmt.setString(1, newValue);
            }
            pstmt.setInt(2, c_id);

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            System.err.println("Database Update Error:" + e.getMessage());

        }

    }

    public ArrayList<Conference> getAllConferences(){
        String sql = "SELECT * FROM Conference";
        ArrayList<Conference> conferences = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("C_ID");
                String title = rs.getString("Title");
                int capacity = rs.getInt("Capacity");
                String c_date = rs.getString("C_Date");
                String start_Time = rs.getString("Start_Time");
                String end_Time = rs.getString("End_Time");
                String location = rs.getString("Location");
                Conference c = new Conference(title,capacity,c_date, start_Time, end_Time, location);
                conferences.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Database Selecting Error:" + e.getMessage());
        }

        return conferences;
    }

    public int retrieveID(String title) {
        String sql = "SELECT C_ID FROM Conference WHERE Title = ?";
        int c_id = 0;
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, title);

            try(ResultSet rs  = pstmt.executeQuery()){
                while(rs.next()){
                    c_id = rs.getInt("C_ID");
                }
            }
            catch(SQLException e){
                System.err.println("Database Connection Error:" + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database Selecting Error:" + e.getMessage());
        }
        return c_id;
    }


    public Conference findByID(int id){
        String sql = "SELECT * FROM Conference WHERE C_ID = ?";
        Conference c = null;
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);

           try( ResultSet rs = pstmt.executeQuery();) {
               while (rs.next()) {
                   String title = rs.getString("Title");
                   int capacity = rs.getInt("Capacity");
                   String c_date = rs.getString("C_Date");
                   String start_Time = rs.getString("Start_Time");
                   String end_Time = rs.getString("End_Time");
                   String location = rs.getString("Location");

                   c = new Conference(id, title, capacity, c_date, start_Time, end_Time, location);
               }
           }
        } catch (SQLException e) {
            System.err.println("Database Selecting Error:" + e.getMessage());
        }
        return c;
    }
}

