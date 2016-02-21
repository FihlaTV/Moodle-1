package nikhil.ayush.aditi.moodleana;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayush on 21-02-2016.
 */
public class dummy extends AppCompatActivity
{/* class created for intents only to be used later */

    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        Bundle registrationData = getIntent().getExtras();
        if(registrationData == null)
        {
            return;
        }
        String course_code=registrationData.getString("Course Code");
        /////////////////////////////////////////////////////////////////////////////
        String url1="http://10.208.20.164:8000/courses/course.json/"+course_code+"/assignment";
       final  List<String> assign=new ArrayList<String>();
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {
                        JSONArray assignm=response.getJSONArray("assignments");
                            for(int i=0;i<assignm.length();i++)
                            {   JSONObject temp=assignm.getJSONObject(i);
                                assign.add(temp.getString("name"));

                            }



                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        Toast.makeText(dummy.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_ob);

        ///////////////////////////////////////////////////////////////////////////////
        String url2="http://10.208.20.164:8000/courses/course.json/"+course_code+"/assignment";
        final  List<String> assign_=new ArrayList<String>();
        JsonObjectRequest json_obdsd = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {
                            JSONArray assignm=response.getJSONArray("assignments");
                            for(int i=0;i<assignm.length();i++)
                            {   JSONObject temp=assignm.getJSONObject(i);
                                assign.add(temp.getString("name"));

                            }



                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        Toast.makeText(dummy.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_ob);


        /////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////

    }

}
