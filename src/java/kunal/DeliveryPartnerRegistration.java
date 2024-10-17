package kunal;

import databaseOperation.DatabaseActions;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class DeliveryPartnerRegistration extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String SUCCESS_PAGE = "success.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out= response.getWriter();
        response.setContentType("text/html");
        String driverName = request.getParameter("name");
        String mobileNumber = request.getParameter("mobile");
        String email = request.getParameter("email");
        String drivingLicence = request.getParameter("drivingLicence");
        String governmentId = request.getParameter("goveID");
        String vehicleRtoNumber = request.getParameter("vehicleNumber");
        String insuranceId = request.getParameter("vehicleInsurance");

        Part photoPart = request.getPart("photo");
        Part vehicleRegistrationProofPart = request.getPart("vehicleRegistrationProof");

        if (photoPart!= null && vehicleRegistrationProofPart!= null) {
            InputStream photoIS = photoPart.getInputStream();
            InputStream vehicleRegistrationProofIS = vehicleRegistrationProofPart.getInputStream();
            BufferedInputStream img = new BufferedInputStream(photoIS);
            BufferedInputStream vhimg = new BufferedInputStream(vehicleRegistrationProofIS);

            try {
                DatabaseActions db = new DatabaseActions();
                boolean insertion = db.deliveryPartnerRegistration(driverName, mobileNumber, email,
                        drivingLicence, governmentId, photoIS, vehicleRtoNumber, vehicleRegistrationProofIS, insuranceId);

                if (insertion) {
                    /*byte[] imageData = readInputStream(img);
        byte[] vehicleRegistrationProofData = readInputStream(vhimg);
        // Write the image data to the output stream
        response.getOutputStream().write("image:".getBytes());
                    response.getOutputStream().write(imageData);
                    response.getOutputStream().write("\nVehicle Registration Proof:".getBytes());
                    response.getOutputStream().write(vehicleRegistrationProofData);*/
                    //write confirmation code
                } else {
                    response.sendRedirect(ERROR_PAGE);
                }
            } catch (Exception e) {
                Logger.getLogger(DeliveryPartnerRegistration.class.getName()).log(Level.SEVERE, null, e);
                response.sendRedirect(ERROR_PAGE);
            }
        } else {
            response.sendRedirect(ERROR_PAGE);
        }
    }
    private byte[] readInputStream(InputStream is) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = is.read(buffer))!= -1) {
        bos.write(buffer, 0, bytesRead);
    }
    return bos.toByteArray();
}
}