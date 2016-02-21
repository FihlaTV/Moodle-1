package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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

import nikhil.ayush.aditi.moodleana.ExpandableListAdapter;
import nikhil.ayush.aditi.moodleana.MyApp_cookie;
import nikhil.ayush.aditi.moodleana.R;
import nikhil.ayush.aditi.moodleana.Tab_view;

public class TwoFragment extends Fragment{
    ExpandableListAdapter listAdapter;
   public static  ExpandableListView expListView;
    public static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    MyApp_cookie app_list=new MyApp_cookie();


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    private void prepareListData() {
        final int[] no_assign = {0};
        final JSONArray grades=new JSONArray();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        String url1="http://10.208.20.164:8000/default/grades.json";
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                        try
                        {
//                            int current_sem = response.getInt("current_sem");
//                            int current_year = response.getInt("current_year") ;
//                            JSONObject user = response.getJSONObject("user");
//                            JSONArray courses = response.getJSONArray("courses");
//                            System.out.println("sem :" +current_sem+" year: "+ current_year);
//                            String name = user.getString("first_name");
//                            for(int i=0;i<courses.length();i++)
//                            {    JSONObject course= courses.getJSONObject(i);
//                                int id = course.getInt("id");
//                                String name_course=course.getString("name") ;
//                                app_list.course_list.put(id,name_course);
//                                your_array_list.add(name_course);
                            JSONArray grades= response.getJSONArray("grades");
                            int i=0;

                            no_assign[0] =grades.length();
                            for(;i<no_assign[0];i++)
                            {   JSONObject grade_course=grades.getJSONObject(i);
                                int cour_id=grade_course.getInt("registered_course_id");
                                listDataHeader.add(app_list.course_code.get(cour_id)+" : "+ grade_course.getString("name"));//course code + Assignment name
                                List<String> expand = new ArrayList<String>();
                                float score=(float) grade_course.getInt("score");
                                float weightage=(float) grade_course.getInt("weightage");
                                float out_of =(float) grade_course.getInt("out_of");
                                expand.add("Name:    "+grade_course.getString("name"));
                                expand.add("Score:    "+Float.toString(score)+"/"+Float.toString(out_of));
                                expand.add("Weightage:    "+Float.toString(weightage));
                                expand.add("Absolute Marks:    "+Float.toString(score/out_of * weightage));

                                listDataChild.put(app_list.course_code.get(cour_id)+" : "+ grade_course.getString("name"), expand);
                                System.out.println("finallhfhfy"+app_list.course_code.get(cour_id)+" : "+ grade_course.getString("name"));
                            }
return;






                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),
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
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(json_ob);
        try {
            Thread.sleep(4000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Adding child data
//        listDataHeader.add("Top 250");
//        listDataHeader.add("Now Showing");
//        listDataHeader.add("Coming Soon..");
//
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemption");
//        top250.add("The Godfather");
//        top250.add("The Godfather: Part II");
//        top250.add("Pulp Fiction");
//        top250.add("The Good, the Bad and the Ugly");
//        top250.add("The Dark Knight");
//        top250.add("12 Angry Men");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");
        System.out.println(listDataChild.size());
        System.out.println(listDataHeader.size());
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_two, null);

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }


}