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
import android.text.Html;
import android.widget.ListView;

import java.util.ArrayList;


public class AssignDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle data=getIntent().getExtras();
        if(data == null)
        {
            return;
        }

        int id = data.getInt("Assgt No");
        String assgt_det = "http://10.208.20.164:8000/courses/assignment.json/" + id;

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
//                            populate static fields.
                            String create_at = assgt.getString("created_at");
                            TextView as_Create = (TextView) findViewById(R.id.createAssgt);
                            as_Create.setText(create_at);

                            String dead= assgt.getString("deadline");
                            TextView deadline = (TextView) findViewById(R.id.deadline);
                            deadline.setText(dead);

                            OtherWorks timerem = new OtherWorks();
                            String time_rem = timerem.time_left(dead);
                            TextView time_reim_Tv = (TextView) findViewById(R.id.time_rem);
                            time_reim_Tv.setText(time_rem);

                            int latedays = assgt.getInt("late_days_allowed");
                            TextView late = (TextView) findViewById(R.id.latedays);
                            late.setText("" + latedays + " days");
                            description=assgt.getString("description");
                            TextView details=(TextView) findViewById(R.id.details);
                            details.setText(Html.fromHtml(description));

                            ArrayList<String> sub_name = new ArrayList<>();
                            ArrayList<String> sub_time = new ArrayList<>();

                            JSONArray subm = response.getJSONArray("submissions");
                            System.out.println(subm);
                            for (int i = 0; i < subm.length(); i ++)
                            {
                                JSONObject submit = subm.getJSONObject(i);
                                sub_name.add(submit.getString("name"));
                                sub_time.add(submit.getString("created_at"));
                            }
                            ListView submissions = (ListView) findViewById(R.id.sublist);
                            System.out.println(sub_name);
                            CustomAdapter_Thread adap = new CustomAdapter_Thread(getApplicationContext(), sub_name, sub_time);
                            submissions.setAdapter(adap);

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.assign_details, menu);
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
