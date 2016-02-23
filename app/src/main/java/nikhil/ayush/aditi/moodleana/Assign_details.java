package nikhil.ayush.aditi.moodleana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Assign_details extends AppCompatActivity {
    public String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_details);
        Bundle data=getIntent().getExtras();
        if(data == null)
        {
            return;
        }
        int id = data.getInt("Assgt No");
        String assgt_det = "http://10.208.20.164:8000/courses/assignment.json/" + id;

        setContentView(R.layout.activity_assign_details);
        JsonObjectRequest assgt_json = new JsonObjectRequest (Request.Method.GET, assgt_det,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("yo", "why this ... working");
//                     System.out.println(response.toString());

                        try
                        {
                            JSONObject assgt = response.getJSONObject("assignment");
                            JSONObject registered = response.getJSONObject("registered");
                            JSONObject course = response.getJSONObject("course");
                            JSONArray subm = response.
                                    getJSONArray("submissions");
//                            populate static fields.
                            String create_at = assgt.getString("created_at");
                            TextView as_Create = (TextView) findViewById(R.id.createAssgt);
                            as_Create.setText(create_at);

                            String dead= assgt.getString("deadline");
                            TextView deadline = (TextView) findViewById(R.id.deadline);
                            deadline.setText(dead);

                            int latedays = assgt.getInt("late_days_allowed");
                            TextView late = (TextView) findViewById(R.id.latedays);
                            late.setText("" + latedays + " days");
                            description=assgt.getString("description");
                            TextView details=(TextView) findViewById(R.id.details);
                            details.setText(Html.fromHtml(description));

                        }
                        catch (JSONException e)
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
                        Log.i("assgt", "assgt details request error");
                    }
                });

        Volley.newRequestQueue(this).add(assgt_json);


    }
}
