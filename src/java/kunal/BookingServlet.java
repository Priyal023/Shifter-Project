/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package kunal;

import databaseOperation.DatabaseActions;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author GHOST
 */
public class BookingServlet extends HttpServlet {

    
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
        {
           response.setContentType("text/html");
        PrintWriter out= response.getWriter();
        String orderId=request.getParameter("arg1");
        String customerName=request.getParameter("arg2");
        String customerAddress=request.getParameter("arg3");
        String riderName=request.getParameter("arg4");
        String vehicleType=request.getParameter("arg5");
        String items=request.getParameter("arg6");
        String pMode=request.getParameter("arg7");
        String pStatus=request.getParameter("arg8");
        int total=Integer.parseInt(request.getParameter("arg9"));
        String userID=request.getParameter("arg10");
        
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            DatabaseActions order = new DatabaseActions();
            boolean insertion=order.orderdataInsert(orderId, customerName, customerAddress, riderName,
                    vehicleType, items, pMode, pStatus, total, userID);
            jsonObject.put("insertion",insertion);
            jsonArray.put(jsonObject);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookingServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    } 