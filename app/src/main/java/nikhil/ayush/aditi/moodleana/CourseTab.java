package nikhil.ayush.aditi.moodleana;

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
import java.util.List;

import layout.Course_Assignments;
import layout.Course_Grades;
import layout.Course_Overview;
import layout.Course_Resources;
import layout.Course_Threads;
import nikhil.ayush.aditi.moodleana.R;

public class CourseTab extends AppCompatActivity {

    MyApp_cookie cookie=new MyApp_cookie();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Course_Assignments Assignments=new Course_Assignments();
    Course_Grades Grades=new Course_Grades();
    Course_Resources Resources =new Course_Resources();
    Course_Threads Threads =new Course_Threads();
    Course_Overview Overview =new Course_Overview();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data=getIntent().getExtras();
        if(data == null) {
            return;
        }
        int pos=data.getInt("Course Code");
        String code=cookie.course_code.get(pos);


        setContentView(R.layout.activity_tab_view);
        String url="http://10.208.20.164:8000/courses/course.json/"+code+"/assignments";
        String url1="http://10.208.20.164:8000/courses/course.json/"+code+"/grade";
        final List<String> Assignment_name=new ArrayList<String>();
        final List<String> Assignment_created=new ArrayList<String>();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {JSONArray assign=response.getJSONArray("assignments");
                            for(int i=0;i<assign.length();i++)
                            {
                                Assignment_name.add(assign.getJSONObject(i).getString("name"));
                                Assignment_created.add(assign.getJSONObject(i).getString("created_at"));

                            }
//
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
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(json_ob);
        Bundle one =new Bundle();
        one.putStringArrayList("Name", (ArrayList<String>) Assignment_name);
        one.putStringArrayList("Created At", (ArrayList<String>) Assignment_created);
        Assignments.setArguments(one);
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        final List<String> Assignment_title=new ArrayList<String>();
        final List<String> Assignment_update=new ArrayList<String>();
        JsonObjectRequest json_thread = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {JSONArray assign=response.getJSONArray("course_threads");
                            for(int i=0;i<assign.length();i++)
                            {
                                Assignment_title.add(assign.getJSONObject(i).getString("name"));
                                Assignment_update.add(assign.getJSONObject(i).getString("updated_on"));

                            }
//
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
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(json_thread);
        Bundle two =new Bundle();
        one.putStringArrayList("Name", (ArrayList<String>) Assignment_name);
        one.putStringArrayList("Created At", (ArrayList<String>) Assignment_created);
        Threads.setArguments(two);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Course_Overview(), "Overview");
        adapter.addFragment(new Course_Assignments(), "Assignments");
        adapter.addFragment(new Course_Resources(), "Resources");
        adapter.addFragment(new Course_Threads(), "Threads");
        adapter.addFragment(new Course_Grades(), "Grades");
//        adapter.addFragment(new Course_Grades(), "Grades");
//        adapter.addFragment(new Course_Grades(), "Grades");
//        adapter.addFragment(new Course_Assignments(), "Assignments");
//        adapter.addFragment(new Course_Resources(), "Resources");
//        adapter.addFragment(new Course_Assignments(), "Assignments");
//        adapter.addFragment(new Course_Resources(), "Resources");
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
}