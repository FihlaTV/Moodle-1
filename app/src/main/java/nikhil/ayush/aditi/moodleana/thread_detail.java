package nikhil.ayush.aditi.moodleana;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

import layout.CustomListAdapter;

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
        String thread_url="http://10.208.20.164:8000/threads/thread.json/" + ID;

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
                            System.out.println(thread);
                            String title_thr = thread.getString("title");
                            TextView title = (TextView) findViewById(R.id.thread_title);
                            title.setText(title_thr);

//                            String thr_user =
                            String code = course.getString("code");
                            TextView codeview = (TextView) findViewById(R.id.Course);
                            codeview.setText(code);

                            String create = thread.getString("created_at");
                            TextView createview = (TextView) findViewById(R.id.create);
                            createview.setText("Created At " + create);

                            String update = thread.getString("updated_at");
                            TextView updateview = (TextView) findViewById(R.id.update);
                            updateview.setText("Updated At: " + update);

                            String desc = thread.getString("description");
                            TextView desc_view = (TextView) findViewById(R.id.desc);
                            desc_view.setText(desc);


                            JSONArray comments = response.getJSONArray("comments");
                            JSONArray comment_users = response.getJSONArray("comment_users");

                            ArrayList<String> comment_text = new ArrayList<>();
                            ArrayList<String> comm_user = new ArrayList<>();
                            JSONArray arr=response.getJSONArray("times_readable");
//                            List<String> x = (List<String>) response.get("times_readable");
                            System.out.println(arr);
                            ArrayList<String> times_Read = new ArrayList<>();


                            for(int i=0;i<comments.length();i++)
                            {    JSONObject comment= comments.getJSONObject(i);
                                JSONObject comment_user = comment_users.getJSONObject(i);
                               // String xtime = xy[i];
//                                u have the user and comment details and time of post.
//                                TODO : parse times_readable.
                                comment_text.add(comment.getString("description"));
                                comm_user.add(comment_user.getString("first_name") + " " + comment_user.getString("last_name"));
                                times_Read.add(arr.getString(i));

                            }
                            ListView comment_List = (ListView) findViewById(R.id.commentList);
                            Context c = getApplicationContext();
                            CustomListAdapter adap = new CustomListAdapter(c ,comment_text, times_Read, comm_user);
                            comment_List.setAdapter(adap);
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
    }

    public void PostComment(View view)
    {
//        comment on a thread.
    }


}
