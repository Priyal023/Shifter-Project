/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package databaseOperation;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Math.log;
import java.sql.*;
import static org.apache.catalina.ha.session.DeltaSession.log;

/**
 *
 * @author GHOST
 */

public class DatabaseActions {
    public Connection con;

    public  DatabaseActions() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://localhost:3306/shifter?useSSL=false";
        String username = "root";
        String password = "123456";

        try {
            con = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public boolean dataInsert(String userName, String name, String number, String password) throws SQLException
    {
               boolean insertion = false;
        try {
            String sql = "INSERT INTO customers(UserName,Name,MobileNumber,Password) VALUES('" + userName + "','"
                    + name + "','" + number + "','" + password + "')";
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(sql);
            if (rs > 0) {
                insertion = true;
            } else {
                System.out.println("Insertion failed.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        con.close();
        return insertion;
    }
    public boolean orderdataInsert(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7,
            String arg8, int arg9, String arg10) throws SQLException
    {
               boolean insertion = false;
        try {
            System.out.println(arg1);
            System.out.println(arg2);
            System.out.println(arg3);
            System.out.println(arg4);
            System.out.println(arg5);
            System.out.println(arg6);
            System.out.println(arg7);
            System.out.println(arg8);
            System.out.println(arg9);
            System.out.println(arg10);
            String sql = "INSERT INTO orderTable(OrderId,customerName,customerAddress,riderName,vehicleType,goodsDelivered,"
                    + "paymentMode,paymentStatus,totalAmount_rs,UserName) VALUES('" + arg1 + "','"
                    + arg2 + "','" + arg3 +"','" + arg4 +"','" + arg5 +"','"+arg6+"','" + arg7 +"','" + arg8 
                    +"','" + arg9 + "','" + arg10 + "')";
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(sql);
            if (rs > 0) {
                insertion = true;
            } else {
                System.out.println("Insertion failed.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        con.close();
        return insertion;
    }
    public boolean userVerification(String id, String pass) throws SQLException {
        boolean verification = false;
        try {
           String ide = id;
            String password = pass;
            String sql = "SELECT UserName FROM customers WHERE UserName='" + ide + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            boolean userFound = rs.next();
            if (userFound) {
                sql = "SELECT UserName FROM customers WHERE Password='" + password + "'";
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                boolean passFound = rs.next();
                if (passFound) {
                    verification = true;
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        con.close();
        return verification;
    }
    public boolean deliveryPartnerRegistration(String partnerName, String phoneNumber, String emailAddress,
            String license, String govID, InputStream image, String vehicleNumber, InputStream vehicleProof,
            String insurance) throws SQLException, IOException 
    {
        try (
             PreparedStatement pstmt = con.prepareStatement(getInsertQuery())) {
            pstmt.setString(1, partnerName);
            pstmt.setString(2, phoneNumber);
            pstmt.setString(3, emailAddress);
            pstmt.setString(4, license);
            pstmt.setString(5, govID);
            pstmt.setBytes(6, inputStreamToByteArray(image));
            pstmt.setString(7, vehicleNumber);
            pstmt.setBytes( 8, inputStreamToByteArray(vehicleProof));
            pstmt.setString(9, insurance);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logError("Error registering delivery partner: " + e.getMessage());
            return false;
        } finally {
            closeResources(image, vehicleProof);
        }
    }

     private String getInsertQuery() {
        return "INSERT INTO `shifter`.`delivery_partners` (`name`, `mobile_number`, `email`, `driving_licence`, `gove_id`, `photo`, `vehicle_number`, `vehicle_registration_proof`, `insurance`) VALUES (?,?,?,?,?,?,?,?,?)";
    }


    /*public static void main(String[] args) {
        String filePath = "C:\\Users\\GHOST\\Documents\\IMG_20220522_194333 (1).jpg"; // Replace with your file path
        String filePatth = "C:\\Users\\GHOST\\Documents\\IMG_20220711_095735 (1).jpg";
        try (InputStream photo = new BufferedInputStream(new FileInputStream(filePath));
             InputStream signature = new BufferedInputStream(new FileInputStream(filePatth))) {
            boolean result = db.deliveryPartnerRegistration("Om Pandey", "1234567890", "ompa9010@gmail.com", "JNDFVNK", "FWGPP7627R", photo, "UP42S2289", signature, "59484SDCSD");
            if (result) {
                System.out.println("Success");
            }
        } catch (Exception e) {
            logError("Error registering delivery partner: " + e.getMessage());
        }
    }*/

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer))!= -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void logError(String message) {
        // Log the error using a logging framework
        System.err.println(message);
    }

    private static void closeResources(InputStream... streams) {
        for (InputStream stream : streams) {
            try {
                stream.close();
            } catch (IOException e) {
                // Log the error
            }
        }
    }
}