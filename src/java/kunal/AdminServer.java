/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package kunal;

import databaseOperation.AdminDatabaseServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import static java.lang.System.out;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GHOST
 */
public class AdminServer extends HttpServlet 
{
    public void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException
    {
        String uid=req.getParameter("username");
        String pass=req.getParameter("password");
        ResultSet rs = null;
        resp.setContentType("text/html");
        PrintWriter out= resp.getWriter();
        
     try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/shifter?useSSL=false";
            String username = "root";
            String password = "123456";
            Connection con = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");
            String query="select *from adminuser where userName='"+uid+"'";
            Statement stmt = con.createStatement();
             rs=stmt.executeQuery(query);
            boolean uidFound=rs.next();
            if(uidFound)
            {
                query="select *from adminuser where password='"+pass+"'";
                rs=stmt.executeQuery(query);
                boolean passFound=rs.next();
                if(passFound)
                {
                    System.out.println("Logged In");
                    Cookie adminCookie = new Cookie("adminName", uid);
                    adminCookie.setMaxAge(60*5); // 5 minute
                    resp.addCookie(adminCookie);
                    resp.sendRedirect("adminModule.html");
                }
                else
                {
                    out.println("<script>");
                    out.println("alert('Incorrect UserID or Password');");
                    out.println("</script>");
                    HttpSession session = req.getSession();
                    RequestDispatcher re= req.getRequestDispatcher("adminModule.html");
                    re.include(req, resp);
                }
            }
            else
                {
                    out.println("<script>");
                    out.println("alert('Incorrect UserID or Password');");
                    out.println("</script>");
                    HttpSession session = req.getSession();
                    RequestDispatcher re= req.getRequestDispatcher("adminModule.html");
                    re.include(req, resp);
                }
            }
            catch(SQLException e)
            {
                
            System.out.println("Error: " + e.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminDatabaseServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }

