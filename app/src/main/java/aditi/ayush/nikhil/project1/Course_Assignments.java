package aditi.ayush.nikhil.project1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;


import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course_Assignments extends Fragment{

    ExpandableListAdapter listAdapter;
    public static ExpandableListView expListView;
    public static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<Integer> A_ID;
    MyApp_cookie app_list=new MyApp_cookie();
    public CourseTab activity = (CourseTab) getActivity();

    public Course_Assignments() {
        /** Required empty public constructor **/
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    public void prepareListData(ArrayList<String> name,ArrayList<String> time)
    {
        /** **/
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        System.out.println("ttt"+name.size()+" : "+time.size());
        for(int i=0;i<name.size();i++)
        {
            List<String> expand =new ArrayList<String>();
            expand.add(""+(i+1)+":"+name.get(i));
            System.out.println(i+name.get(i));
            expand.add("Time Remaining:"+time.get(i));
            listDataHeader.add("Assignment "+i);
            listDataChild.put("Assignment " + i, expand);
        }
        System.out.println("yotry:"+listDataChild.size());
        System.out.println(listDataHeader.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_two, null);
        ListView Assgt_lv = (ListView) rootView.findViewById(R.id.AssgtCustomList);
        Bundle bundle = this.getArguments();
        if(bundle==null)
        {   System.out.println(":(");
            return rootView;
        }
        System.out.println("Recieved at Assgts: " + bundle);
        ArrayList<String> Name = bundle.getStringArrayList("Name");
        ArrayList<String> time = bundle.getStringArrayList("deadline");
        A_ID = bundle.getIntegerArrayList("A_ID");
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        prepareListData(Name,time);

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
//                Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent assgtPage = new Intent(getActivity().getApplicationContext(), AssignDetails.class);
                assgtPage.putExtra("Assgt No", A_ID.get(groupPosition));
                startActivity(assgtPage);
                return false;
            }
        });
        return rootView;
    }

}
