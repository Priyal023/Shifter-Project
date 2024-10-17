/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package kunal;

import databaseOperation.DatabaseActions;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GHOST
 */
public class RegisterUser extends HttpServlet {
public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
{
     response.setContentType("text/html");
        PrintWriter out= response.getWriter();
        String userName = request.getParameter("username").trim();
        String name = request.getParameter("name").trim();
        String mobileNumber = request.getParameter("number");
        String password = request.getParameter("password").trim();
        String confirmpassword = request.getParameter("cpassword").trim();
        if(userName.equals(name))
        {
       
            out.println("<script>");
            out.println("alert('User name and name can not be same!!!');");
            out.println("</script>");
            RequestDispatcher rs=request.getRequestDispatcher("register.html");
            rs.include(request, response);
        }
        else if(password.equals(confirmpassword))
                {
                    try {
                        DatabaseActions db= new DatabaseActions();
                       
                        boolean check = db.dataInsert(userName, name, mobileNumber, password);
                        if(check)
                        {
                            out.println("<script>");
                            out.println("alert('Your user id:"+userName+
                                    "   Thankyou for registering!!!');");
                            out.println("</script>");
                            response.sendRedirect("index.html");
                        }
                        else{
                            out.println("<script>");
                            out.println("alert('Recod not inserted!!!');");
                            out.println("</script>");
                            RequestDispatcher rs=request.getRequestDispatcher("register.html");
                            rs.include(request, response);
                        }
                    } catch (Exception e) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Error occurred while inserting data!!!');");
                        out.println("</script>");
                        RequestDispatcher rs=request.getRequestDispatcher("register.html");
                        rs.include(request, response);
                    }
                    
        RequestDispatcher rs=request.getRequestDispatcher("index.html");
            rs.include(request, response);
        }
        else{
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Password not matched!!!');");
                     out.println("</script>");
                     RequestDispatcher rs=request.getRequestDispatcher("register.html");
                     rs.include(request, response);
        }
        
    }
}

