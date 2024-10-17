/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kunal;
import databaseOperation.DatabaseActions;
import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet{
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter out=response.getWriter();
        String userName=request.getParameter("username");
        String password=request.getParameter("password");
        DatabaseActions db = null;
        try {
            db = new DatabaseActions();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean verification=false;
        try {
            verification=db.userVerification(userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(verification)
        {
            Cookie userCookie = new Cookie("username", userName);
            Cookie loginCookie = new Cookie("login", "1");
        userCookie.setMaxAge(60*5); // 5 minute
        loginCookie.setMaxAge(60*60);
        response.addCookie(loginCookie);
        response.addCookie(userCookie);
    response.sendRedirect("index.html"); 
        }
        else{
            out.println("<script>");
            out.println("alert('Incorrect UserID or Password');");
            out.println("</script>");
            HttpSession session = request.getSession();
    response.addCookie(new Cookie("isLoggedIn", "false"));
            RequestDispatcher rs= request.getRequestDispatcher("login.html");
                rs.include(request, response);
        }
   }
    
}