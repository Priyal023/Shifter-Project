import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTest {
    private static final String DBURL = "jdbc:mysql://localhost:3306/shifter";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public boolean deliveryPartnerRegistration(String partnerName, String phoneNumber, String emailAddress,
            String license, String govID, InputStream image, String vehicleNumber, InputStream vehicleProof,
            String insurance) throws SQLException, IOException {
        try (Connection con = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
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


    public static void main(String[] args) {
        String filePath = "C:\\Users\\GHOST\\Documents\\IMG_20220522_194333 (1).jpg"; // Replace with your file path
        String filePatth = "C:\\Users\\GHOST\\Documents\\IMG_20220711_095735 (1).jpg";
        DatabaseTest db = new DatabaseTest();
        try (InputStream photo = new BufferedInputStream(new FileInputStream(filePath));
             InputStream signature = new BufferedInputStream(new FileInputStream(filePatth))) {
            boolean result = db.deliveryPartnerRegistration("Om Pandey", "1234567890", "ompa9010@gmail.com", "JNDFVNK", "FWGPP7627R", photo, "UP42S2289", signature, "59484SDCSD");
            if (result) {
                System.out.println("Success");
            }
        } catch (Exception e) {
            logError("Error registering delivery partner: " + e.getMessage());
        }
    }

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