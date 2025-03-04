package bookLibrary;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AID {
	
    // Method to convert java.sql.Date to String
    public static String dateToString(Date date) {
        if (date == null) {
            return null; // Handle null date input
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Specify the desired format
        return sdf.format(date);
    }

    // Method to convert String to java.sql.Date
    public static Date stringToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null; // Handle null or empty string input
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Specify the expected format
        try {
            java.util.Date utilDate = sdf.parse(dateString); // Convert String to java.util.Date
            return new Date(utilDate.getTime()); // Convert java.util.Date to java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing exception
        }
    }


}
