package aditi.ayush.nikhil.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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




public class TwoFragment extends Fragment{
    ExpandableListAdapter listAdapter;
    public static  ExpandableListView expListView;
    public static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    MyApp_cookie app_list=new MyApp_cookie();
    private long mRequestStartTime;



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
        mRequestStartTime = System.currentTimeMillis();
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
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                            System.out.println("Response time for one is=="+ totalRequestTime );






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
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


        System.out.println("yo:"+listDataChild.size());
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
            public void onGroupExpand(int groupPosition)
            {
                //    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
                //    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //        Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }


}