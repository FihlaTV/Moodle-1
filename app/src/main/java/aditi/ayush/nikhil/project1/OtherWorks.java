package aditi.ayush.nikhil.project1;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nikhil on 24/02/16.
 */

public class OtherWorks
{/** A modular way to handle all the methods not related to moodle **/
    public String time_left(String deadline)
    {
        /** This function returns the time remaining from deadline. **/
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date curr_date = dateFormat.parse(current);
            long curr_sec = curr_date.getTime();
            Date dead_date = dateFormat.parse(deadline);
            long dead_sec = dead_date.getTime();
            long time_rem = dead_sec - curr_sec;
            time_rem = time_rem/1000;
            String ans = "";
            if (time_rem >= 86400)
            {
                long days_rem = time_rem/86400;
                ans += days_rem + " days, ";
                time_rem = time_rem%86400;
            }
            if (time_rem >= 3600)
            {
                ans += (time_rem/3600);
            }
            else
            {
                ans += "00";
            }
            ans += ":";
            time_rem = time_rem%3600;
            if (time_rem >= 60)
            {
                ans += (time_rem/60);
            }
            else
                ans += "00";
            ans += ":";
            time_rem = time_rem%60;
            ans += time_rem;

            return ans;

        }
        catch(ParseException p)
        {
            Log.i("other", "parse error");
            return "";
        }
    }

}
