package nikhil.ayush.aditi.moodleana;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import layout.OneFragment;
import layout.ThreeFragment;
import layout.TwoFragment;

public class Tab_view extends AppCompatActivity {
    private long mRequestStartTime;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String Username;
    MyApp_cookie app_list=new MyApp_cookie();
//    final HashMap<Integer,String> hm =new HashMap<Integer,String>() ;

    OneFragment One = new OneFragment();
    TwoFragment two =new TwoFragment();
    ThreeFragment three =new ThreeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        final int current_year,current_sem;
        final List<String> your_array_list = new ArrayList<String>();

        Bundle registrationData = getIntent().getExtras();
        if(registrationData == null) {
            return;
        }

        Username = registrationData.getString("User");
        String Password = registrationData.getString("Pass");
        setContentView(R.layout.activity_tab_view);
        mRequestStartTime = System.currentTimeMillis();
        String url = "http://10.208.20.164:8000/default/login.json?userid="+Username+"&password="+Password;
        String url1="http://10.208.20.164:8000/courses/list.json";
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("yo", "why this ... working");
//                     System.out.println(response.toString());

                        try
                        {
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
                        Toast.makeText(Tab_view.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_ob);


        Bundle bundle = new Bundle();
        bundle.putString("User", Username);
        bundle.putString("Pass", Password);
        bundle.putStringArrayList("String", (ArrayList<String>) your_array_list);
        One.setArguments(bundle);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String url2="http://10.208.20.164:8000/default/notifications.json" ;
        final List<String> noti_array_list = new ArrayList<String>();
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
                            JSONArray noti=response.getJSONArray("notification");
                            for(int i=0;i<noti.length();i++)
                            {
                                noti_array_list.add(noti.get(i).toString());
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
                        Toast.makeText(Tab_view.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_not);
        System.out.println("here");
        Bundle bundle3 = new Bundle();

        //bundle.putString("User", Username);
        //bundle.putString("Pass", Password);
        bundle3.putStringArrayList("noti", (ArrayList<String>) noti_array_list);
        three.setArguments(bundle3);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        ///4 se c delayw2

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager)
    {//Defines the number pf tabs
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("Abt to delay");
        adapter.addFragment(One, "BLAH-BLAH-BLAH-BLAH-BLAH");
        adapter.addFragment(new TwoFragment(), "Grades");
        adapter.addFragment(three, "Notifications");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter

     {  // provides fragments required from the view page
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
            //secomd argument defines the title being passed
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}