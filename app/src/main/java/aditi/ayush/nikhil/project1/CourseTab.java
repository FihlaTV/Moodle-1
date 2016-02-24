package aditi.ayush.nikhil.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;


public class CourseTab extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MyApp_cookie cookie=new MyApp_cookie();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Course_Assignments Assignments=new Course_Assignments();
    Course_Grades Grades=new Course_Grades();
    Course_Resources Resources =new Course_Resources();
    Course_Threads Threads =new Course_Threads();
    Course_Overview Overview =new Course_Overview();
    public boolean b1=false;
    public boolean b2=false;
    public boolean b3=false;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tab);
        Toast.makeText(this, "Loading Course Page...It may take time",Toast.LENGTH_LONG).show();
        Bundle data=getIntent().getExtras();
        if(data == null) {
            return;
        }
        code=data.getString("Course Code");

        System.out.println("onCreate of CourseTab called, code:" + code);

        setContentView(R.layout.activity_course_tab);
        UpdateAssgt();
        UpdateGrades();
        UpdateThreads();

        float tme=System.currentTimeMillis();
        //while(!(b1&&b2&&b3))


        System.out.println("bundle setting");
        try
        {
            Thread.sleep(200);                 //1000 milliseconds is one second.
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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

    public void UpdateAssgt()
    {
        String url="http://10.208.20.164:8000/courses/course.json/"+code+"/assignments";
        final List<String> Assignment_created=new ArrayList<String>();
        final List<String> Assignment_name=new ArrayList<String>();
        final List<String> Assignment_Deadline=new ArrayList<String>();
        final List<Integer> Assgt_No = new ArrayList<Integer>();
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {Log.i("yo", "why this ... working");
///                System.out.println(response.toString());
                        try
                        {JSONArray assign=response.getJSONArray("assignments");
                            for(int i=0;i<assign.length();i++)
                            {   Assignment_name.add(assign.getJSONObject(i).getString("name"));
                                Assignment_created.add(assign.getJSONObject(i).getString("created_at"));
                                Assignment_Deadline.add(assign.getJSONObject(i).getString("deadline"));
                                Assgt_No.add(assign.getJSONObject(i).getInt("id"));
                                b1=true;
                            }
                            Bundle one =new Bundle();
                            one.putStringArrayList("Name", (ArrayList<String>) Assignment_name);
                            one.putStringArrayList("Created At", (ArrayList<String>) Assignment_created);
                            one.putStringArrayList("deadline", (ArrayList<String>) Assignment_Deadline);
                            one.putIntegerArrayList("A_ID", (ArrayList<Integer>) Assgt_No);
                            Assignments.setArguments(one);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {Log.i("yo", "why this not working");
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(json_ob);

    }

    public  void UpdateGrades()
    {
        String url1="http://10.208.20.164:8000/courses/course.json/"+code+"/grades";
        final List<String> Name_assign=new ArrayList<String>();
        final List<Integer> Assignment_Score=new ArrayList<Integer>();
        //final List<Integer> Course_id=new ArrayList<Integer>();
        final List<Integer> Out_of=new ArrayList<Integer>();
        final List<Integer> Weightage=new ArrayList<Integer>();
        JsonObjectRequest json_grade = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {Log.i("yo", "why this ...grades..... working");
///                        System.out.println(response.toString());
                        try
                        {JSONArray grades=response.getJSONArray("grades");
                            for(int i=0;i<grades.length();i++)
                            {
                                Name_assign.add(grades.getJSONObject(i).getString("name"));
                                int x =grades.getJSONObject(i).getInt("score");
//                                float y=(float) x;
                                Assignment_Score.add( x);
                                Out_of.add(grades.getJSONObject(i).getInt("out_of"));
                                Weightage.add(grades.getJSONObject(i).getInt("weightage"));
                                //Assignment_update.add(assign.getJSONObject(i).getString("updated_on"));

                            }
                            Bundle three =new Bundle();
                            three.putStringArrayList("Name", (ArrayList<String>) Name_assign);
                            three.putIntegerArrayList("Score", (ArrayList<Integer>) Assignment_Score);
                            three.putIntegerArrayList("Out_of", (ArrayList<Integer>) Out_of);
                            three.putIntegerArrayList("Weightage", (ArrayList<Integer>) Weightage);
                            System.out.println("Sending bundle: " + three);
                            Grades.setArguments(three);
//
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        b3=true;System.out.println("bundle set oof");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(json_grade);




    }

    public  void UpdateThreads()
    {
        String url2 = "http://10.208.20.164:8000/courses/course.json/"+code+"/threads";
        // object for course threads
        final List<String> Thread_title=new ArrayList<String>();
        final List<String> Thread_update=new ArrayList<String>();
        final List<Integer> ThreadID = new ArrayList<Integer>();
        JsonObjectRequest json_thread = new JsonObjectRequest (Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>()
                {@Override
                 public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                       System.out.println(response.toString());
                        try
                        {JSONArray assign=response.getJSONArray("course_threads");
//                     System.out.println("JSON for threads: " + assign);
                            for(int i=0;i<assign.length();i++) {
                                Thread_title.add(assign.getJSONObject(i).getString("title"));
                                Thread_update.add(assign.getJSONObject(i).getString("created_at"));
                                ThreadID.add(assign.getJSONObject(i).getInt("id"));
                            }
//
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        b2=true;

                        Bundle two =new Bundle();
                        two.putStringArrayList("Name", (ArrayList<String>) Thread_title);
                        two.putStringArrayList("Updated On", (ArrayList<String>) Thread_update);
                        two.putIntegerArrayList("ID", (ArrayList<Integer>) ThreadID);
                        two.putString("Course Code", code);

                        Threads.setArguments(two);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(json_thread);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Overview, "Overview");
        adapter.addFragment(Resources, "Resources");
        adapter.addFragment(Threads, "Threads");
        adapter.addFragment(Assignments, "Assignments");
        adapter.addFragment(Grades, "Grades");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
        getMenuInflater().inflate(R.menu.course_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            String url_out="http://10.208.20.164:8000/default/logout.json";
            JsonObjectRequest logout=new JsonObjectRequest(Request.Method.GET,
                    url_out, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, response.toString());

                    try {
                        // Parsing json object response
                        // response will be a json object
                        int noti_count=response.getInt("noti_count");
                        if(noti_count==4)
                        {
                            Toast.makeText(getApplicationContext(),"Logout Succesful",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),Login.class);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    //  hidepDialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Tag", "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    // hide the progress dialog
                    // hidepDialog();
                }
            });

            Volley.newRequestQueue(this).add(logout);
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
