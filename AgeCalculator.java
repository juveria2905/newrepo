import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgeCalculator {

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String inputType = args[0];
        String dateStr = args[1];
        String referenceDateStr = args[2];
        String dateFormat = args[3];
        String dlc = args[4];

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.replace("dlc", dlc));
            Date date = sdf.parse(dateStr);
            Date referenceDate = sdf.parse(referenceDateStr);

            if (inputType.equals("DOB")) {
                int[] age = calculateAge(date, referenceDate);
                System.out.println("Age: " + age[0] + " years, " + age[1] + " months, " + age[2] + " days");
            } else if (inputType.equals("AGE")) {
                String[] dateParts = dateStr.split(dlc);
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                Date dob = determineDob(year, month, day, referenceDate, dlc);
                System.out.println("DOB: " + sdf.format(dob));
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
    }

    public static int[] calculateAge(Date dob, Date referenceDate) {
        long ageInMillis = referenceDate.getTime() - dob.getTime();
        long ageInDays = ageInMillis / (24 * 60 * 60 * 1000);
        int years = (int) (ageInDays / 365);
        int months = (int) ((ageInDays % 365) / 30);
        int days = (int) ((ageInDays % 365) % 30);
        return new int[] {years, months, days};
    }

    public static Date determineDob(int year, int month, int day, Date referenceDate, String dlc) {
        Date dob = new Date(referenceDate.getTime());
        dob.setYear(referenceDate.getYear() - year);
        dob.setMonth(referenceDate.getMonth() - month);
        dob.setDate(referenceDate.getDate() - day);
        return dob;
    }
}