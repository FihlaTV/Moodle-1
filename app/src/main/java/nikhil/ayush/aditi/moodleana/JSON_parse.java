package nikhil.ayush.aditi.moodleana;

import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by Ayush on 20-02-2016.
 */
public class JSON_parse
{
    public static boolean login_res(String response)
    {try{
        JSONObject obj=new JSONObject(response) ;
        String success = obj.getString("success");
        if(success.equals("true"))
        {
           return true;
        }

    }catch (org.json.JSONException e)
    {
        e.printStackTrace();
    }
return false;
    }


}
