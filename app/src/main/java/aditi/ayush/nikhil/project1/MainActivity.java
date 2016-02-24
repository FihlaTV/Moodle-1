package aditi.ayush.nikhil.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;


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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long mRequestStartTime;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String Username;
    private  String Password;
    MyApp_cookie app_list=new MyApp_cookie();
//    final HashMap<Integer,String> hm =new HashMap<Integer,String>() ;

    OneFragment One = new OneFragment();
    TwoFragment Two =new TwoFragment();
    ThreeFragment Three =new ThreeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int current_year,current_sem;
        final List<String> your_array_list = new ArrayList<String>();

        Bundle registrationData = getIntent().getExtras();
        if(registrationData == null) {
            return;
        }

        Username = registrationData.getString("User");
        Password = registrationData.getString("Pass");

        mRequestStartTime = System.currentTimeMillis();
        String url = "http://10.208.20.164:8000/default/login.json?userid="+Username+"&password="+Password;
        UpdateCourses();

        UpdateNotif();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void UpdateCourses()
    {// to update the course list
        final List<String> your_array_list = new ArrayList<String>();
        String url1="http://10.208.20.164:8000/courses/list.json";
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {Log.i("yo", "why this ... working");
//                    System.out.println(response.toString());
                        try
                        {
//                            System.out.println("Tabview pe " + response);
                            int current_sem = response.getInt("current_sem");
                            int current_year = response.getInt("current_year");
                            JSONObject user = response.getJSONObject("user");
                            JSONArray courses = response.getJSONArray("courses");
                            System.out.println("sem :" +current_sem+" year: "+ current_year);
                            String name = user.getString("first_name");
                            for(int i=0;i<courses.length();i++)
                            {    JSONObject course= courses.getJSONObject(i);
                                int id = course.getInt("id");
                                String name_course=course.getString("name") ;
                                app_list.course_list.put(id,name_course);
                                app_list.course_code.put(id,course.getString("code"));
                                your_array_list.add(name_course);
                            }
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                            System.out.println("Response time for one is=="+ totalRequestTime );
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
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_ob);
        Bundle bundle = new Bundle();
        bundle.putString("User", Username);
        bundle.putString("Pass", Password);
        bundle.putStringArrayList("String", (ArrayList<String>) your_array_list);
        One.setArguments(bundle);

    }

    public void UpdateNotif()
    {// to update the notification fragment
        String url2="http://10.208.20.164:8000/default/notifications.json" ;
        final List<String> noti_text = new ArrayList<String>();
        final List<String> noti_time = new ArrayList<String>();
        mRequestStartTime = System.currentTimeMillis();
        JsonObjectRequest json_not = new JsonObjectRequest (Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {
//                            no array named notification. :/
                            JSONArray noti=response.getJSONArray("notifications");
                            for(int i=0;i<noti.length();i++)
                            {

                                noti_text.add(noti.getJSONObject(i).getString("description"));
// TODO: change this description, thread id & course code is clickable.
                                noti_time.add(noti.getJSONObject(i).getString("created_at"));
//                                TODO: current - created at.
                            }
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                            System.out.println("Response time for three is=="+ totalRequestTime );

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
                        //       Toast.makeText(Tab_view.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_not);
        System.out.println("here");
        Bundle bundle3 = new Bundle();

        //bundle.putString("User", Username);
        //bundle.putString("Pass", Password);
        bundle3.putStringArrayList("noti_text", (ArrayList<String>) noti_text);
        bundle3.putStringArrayList("noti_time", (ArrayList<String>) noti_time);
        Three.setArguments(bundle3);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(One, "Courses");
        adapter.addFragment(new TwoFragment(), "Grades");
        adapter.addFragment(Three, "Notifications");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            if (viewPager.getCurrentItem() != 0)
            {
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
                viewPager.setCurrentItem(0);
                tabLayout.setupWithViewPager(viewPager);
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
            }

        }
        else if (id == R.id.nav_gallery)
        {
            if (viewPager.getCurrentItem() != 1)
            {
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
                viewPager.setCurrentItem(1);
                tabLayout.setupWithViewPager(viewPager);
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
            }
        }
        else if (id == R.id.nav_slideshow)
        {
            if (viewPager.getCurrentItem() != 2)
            {
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
                viewPager.setCurrentItem(2);
                tabLayout.setupWithViewPager(viewPager);
                System.out.println(tabLayout.getSelectedTabPosition() + " : Selected tab");
            }
        }
        else if (id == R.id.nav_logout)
        {
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


        }
        else if (id == R.id.nav_send)
        {

        }
//        else if (id = R.id.nav_)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
