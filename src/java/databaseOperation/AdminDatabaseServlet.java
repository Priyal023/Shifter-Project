package databaseOperation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String detail=req.getParameter("arg");
     
            System.out.println("Argument: " + detail);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/shifter?useSSL=false";
            String username = "root";
            String password = "123456";
            con = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");
            if(detail.equals("usersInfo")){
            String query = "SELECT * FROM customers";
            pstmt = con.prepareStatement(query);

            // Execute query
            rs = pstmt.executeQuery();

            // Create JSON response
            resp.setContentType("application/json");
            try (PrintWriter out = resp.getWriter()) {
                JSONArray jsonArray = new JSONArray();
                int rowCount = 0;
    while (rs.next()) {
        rowCount++;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", rs.getString("UserName"));
        jsonObject.put("Name", rs.getString("Name"));
        jsonObject.put("MobileNumber", rs.getString("MobileNumber"));
        jsonObject.put("Password", rs.getString("Password"));
        jsonArray.put(jsonObject);
    }
    
    System.out.println("Number of rows processed: " + rowCount);
    System.out.println("Recived Argument is: " + detail);
                out.println(jsonArray.toString());
                
            }
            }
            else if(detail.equals("clientsInfo")){
                String query = "SELECT * FROM delivery_partners";
    pstmt = con.prepareStatement(query);

    // Execute query
    rs = pstmt.executeQuery();

    // Create JSON response
    resp.setContentType("application/json");
    try (PrintWriter out = resp.getWriter()) {
        JSONArray jsonArray = new JSONArray();
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name", rs.getString("name"));
            jsonObject.put("MobileNumber", rs.getString("mobile_number"));
            jsonObject.put("Email", rs.getString("email"));
            jsonObject.put("driving_licence",rs.getString("driving_licence"));
            jsonObject.put("gove_id",rs.getString("gove_id"));
            jsonObject.put("photo",rs.getBlob("photo"));
            jsonObject.put("vehicle_number",rs.getString("vehicle_number"));
            jsonObject.put("vehicle_registration_proof",rs.getBlob("vehicle_registration_proof"));
            jsonObject.put("insurance",rs.getString("insurance"));
            jsonArray.put(jsonObject);
        }
        System.out.println("Number of rows processed: " + rowCount);
        System.out.println("Received Argument is: " + detail);
        out.println(jsonArray.toString());
    }
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminDatabaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs!= null) rs.close();
                if (pstmt!= null) pstmt.close();
                if (con!= null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        
    }
}
