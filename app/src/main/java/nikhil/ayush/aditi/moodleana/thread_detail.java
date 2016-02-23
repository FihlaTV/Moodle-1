package nikhil.ayush.aditi.moodleana;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class thread_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data=getIntent().getExtras();
        if(data == null)
        {
            return;
        }
//        need thread id to get details.
        int ID = data.getInt("ID");
        System.out.println("Thread with ID " + ID);
        String thread_url="http://10.208.20.164:8000/threads/thread.json" + ID;

        JsonObjectRequest thread_json = new JsonObjectRequest (Request.Method.GET, thread_url,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("yo", "why this ... working");
//                     System.out.println(response.toString());

                        try
                        {
                            JSONObject course = response.getJSONObject("course");
                            JSONObject thread = response.getJSONObject("thread");
//                            populate static fields.
                            String title_thr = thread.getString("title");
                            TextView title = (TextView) findViewById(R.id.thread_title);
                            title.setText(title_thr);

                            int user_no = thread.getInt("user_id");
//                            user name needed. HOW?

                            JSONArray comments = response.getJSONArray("comments");
                            JSONArray comment_users = response.getJSONArray("comment_users");
                            List<String> times_read = (ArrayList<String>)response.get("times_readable");
                            for(int i=0;i<comments.length();i++)
                            {    JSONObject comment= comments.getJSONObject(i);
                                JSONObject comment_user = comment_users.getJSONObject(i);
                                String time_comm = times_read.get(i);
//                                u have the user and comment details and time of post.

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
                        Log.i("thread", "thread details request error");
                    }
                });
        Volley.newRequestQueue(this).add(thread_json);




        setContentView(R.layout.activity_thread_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public  void PostComment(View view)
    {
        EditText comment = (EditText) findViewById(R.id.add_comm);
        String comm_text = comment.getText().toString();
        if (comm_text != "")
        {
//            post a comment
//            add to list view?
        }
    }


}
