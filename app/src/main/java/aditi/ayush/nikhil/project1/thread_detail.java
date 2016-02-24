package aditi.ayush.nikhil.project1;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.util.Log;
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
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.android.volley.VolleyLog;

public class thread_detail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_detail);

        Bundle data=getIntent().getExtras();
        if(data == null)
        {
            return;
        }
//        need thread id to get details.
        ID = data.getInt("ID");
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
                            List<String> x = (List<String>)response.get("times_readable");
                            System.out.println(x);
                            ArrayList<String> times_Read = new ArrayList<>();


                            for(int i=0;i<comments.length();i++)
                            {    JSONObject comment= comments.getJSONObject(i);
                                JSONObject comment_user = comment_users.getJSONObject(i);
                                String xtime = x.get(i);
//                                u have the user and comment details and time of post.
//                                TODO : parse times_readable.
                                comment_text.add(comment.getString("description"));
                                comm_user.add(comment_user.getString("first_name") + " " + comment_user.getString("last_name"));
                                times_Read.add(xtime);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void PostComment(View view)
    {
        EditText txt = (EditText) findViewById(R.id.add_comm);
        String comm = txt.getText().toString();
        if (comm.length() == 0)
            Toast.makeText(getApplicationContext(),"Comment cannot be empty" ,Toast.LENGTH_SHORT).show();
        else
        {
            String post_comm = "http://10.208.20.164:8000/threads/post_comment.json?thread_id=" + ID  + "&description=" + comm;
            JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                    post_comm, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, response.toString());

                    try {
                        // Parsing json object response
                        // response will be a json object
                        boolean success = response.getBoolean("success");
                        //  String email = response.getString("email");
                        if(success)
                        {
                            Toast.makeText(getApplicationContext(),"Comment posted successfully!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Tag", "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(jsoncomm);
            UpdateComments();

        }

    }

    private  void UpdateComments()
    {
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

    }

    public void Logout(View view)
    {
        String logout = "http://10.208.20.164:8000/default/logout.json";
        JsonObjectRequest json_not = new JsonObjectRequest (Request.Method.GET, logout,null,
                new Response.Listener<JSONObject>()
                {
                    @Override

                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "Logout response");
                        ///                        System.out.println(response.toString());

                        try
                        {
                            System.out.println("Logout response: " + response);
                            Integer noti_count = response.getInt("noti_count");
                            if (noti_count != null)
                            {
//                                intent, to login page.
                                Intent logPage = new Intent(getApplicationContext(), Login.class);
                                Toast.makeText(getApplicationContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(logPage);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),	"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        //       Toast.makeText(Tab_view.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_not);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.thread_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
