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
    public boolean b1=false;
    public boolean b2=false;
    public boolean b3=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data=getIntent().getExtras();
        if(data == null) {
            return;
        }
        String code=data.getString("Course Code");

        System.out.println("onCreate of CourseTab called, code:" + code);

        setContentView(R.layout.activity_course_tab);
        String url="http://10.208.20.164:8000/courses/course.json/"+code+"/assignments";
        String url1="http://10.208.20.164:8000/courses/course.json/"+code+"/grade";
        String url2 = "http://10.208.20.164:8000/courses/course.json/"+code+"/threads";
        final List<String> Assignment_name=new ArrayList<String>();
        final List<String> Assignment_created=new ArrayList<String>();
        final List<String> Assignment_Deadline=new ArrayList<String>();
        final List<Integer> Assgt_No = new ArrayList<Integer>();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // for course assignamnets
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

        //////////////////////////////////////////////////////////////////////////////////////////////////////
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
///                        System.out.println(response.toString());
                        try
                        {JSONArray assign=response.getJSONArray("course_threads");
//                      System.out.println("JSON for threads: " + assign);
                        for(int i=0;i<assign.length();i++)
                            {Thread_title.add(assign.getJSONObject(i).getString("title"));
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


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        // object for course grades
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
                    {
                        Log.i("yo", "why this ...grades..... working");
///                        System.out.println(response.toString());

                        try
                        {JSONArray grades=response.getJSONArray("grades");
                            for(int i=0;i<grades.length();i++)
                            {  Name_assign.add(grades.getJSONObject(i).getString("name"));
                                int x =grades.getJSONObject(i).getInt("Score");
                                float y=(float) x;
                                Assignment_Score.add( x);
                                Out_of.add(grades.getJSONObject(i).getInt("out_of"));
                                Weightage.add(grades.getJSONObject(i).getInt("weightage"));

                                //Assignment_update.add(assign.getJSONObject(i).getString("updated_on"));

                            }
//
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        b3=true;
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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
//        try {
//            Thread.sleep(4500);                 //1000 milliseconds is one second.
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
        float tme=System.currentTimeMillis();
        //while(!(b1&&b2&&b3))
         try {
            Thread.sleep(7500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
       // }
//        System.out.println("Runs now "+ (System.currentTimeMillis()-tme));
        Bundle one =new Bundle();
        one.putStringArrayList("Name", (ArrayList<String>) Assignment_name);
        one.putStringArrayList("Created At", (ArrayList<String>) Assignment_created);
        one.putStringArrayList("deadline", (ArrayList<String>) Assignment_Deadline);
        one.putIntegerArrayList("A_ID", (ArrayList<Integer>) Assgt_No);
        Assignments.setArguments(one);

        Bundle two =new Bundle();
        two.putStringArrayList("Name", (ArrayList<String>) Thread_title);
        two.putStringArrayList("Updated On", (ArrayList<String>) Thread_update);
        two.putIntegerArrayList("ID", (ArrayList<Integer>) ThreadID);
        two.putString("Course Code", code);
        Threads.setArguments(two);

        Bundle three =new Bundle();
        three.putStringArrayList("Name",(ArrayList<String>) Name_assign);
        three.putIntegerArrayList("Score", (ArrayList<Integer>) Assignment_Score);
        three.putIntegerArrayList("Out_of", (ArrayList<Integer>) Out_of);
        three.putIntegerArrayList("Weightage", (ArrayList<Integer>) Weightage);
        Grades.setArguments(three);

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
        adapter.addFragment(Overview, "Overview");
        adapter.addFragment(Assignments, "Assignments");
        adapter.addFragment(Resources, "Resources");
        adapter.addFragment(Threads, "Threads");
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
}