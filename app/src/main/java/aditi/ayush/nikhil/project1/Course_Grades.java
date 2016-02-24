package aditi.ayush.nikhil.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course_Grades extends Fragment
{    ExpandableListAdapter listAdapter;
    public static ExpandableListView expListView;
    public static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public Course_Grades()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void prepareListData(ArrayList<String> name,ArrayList<Integer> Score,ArrayList<Integer> Out_of,ArrayList<Integer> Weightage)
    {   listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        for(int i=0;i<name.size();i++)
        {
            List<String> expand =new ArrayList<String>();
            float a=Score.get(i),b=Out_of.get(i),c=Weightage.get(i);
            expand.add("Score:     "+Float.toString(a));
            expand.add("Out of:    "+Float.toString(b));
            expand.add("Weightage: "+Float.toString(c));
            expand.add("Absolute Marks"+Float.toString(a*c/b));
            listDataHeader.add(name.get(i));
            listDataChild.put(name.get(i), expand);
        }
        System.out.println("yotry:"+listDataChild.size());
        System.out.println(listDataHeader.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // it will receive bundles....then fill the expandable list view

        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_course__grades, container, false);
         Bundle bundle = this.getArguments();
        if(bundle==null)
        {
            return rootView;
        }
        System.out.println("Recieved at grades: " + bundle);
        ArrayList<String> Name = bundle.getStringArrayList("Name");
        ArrayList<Integer> Score = bundle.getIntegerArrayList("Score");
        ArrayList<Integer> Out_of = bundle.getIntegerArrayList("Out_of");
        ArrayList<Integer> Weightage = bundle.getIntegerArrayList("Weightage");
        expListView = (ExpandableListView) rootView.findViewById(R.id.course_grades_exp);
        prepareListData(Name,Score,Out_of,Weightage);
        if(Name.size()==0)
        {   System.out.println(":(");
            return rootView;
        }

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
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
        {
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
//              Toast.makeText(getActivity().getApplicationContext(), "Child of expanded list clicked " + listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
//                Intent assgtPage = new Intent(getActivity().getApplicationContext(), Assign_details.class);
//                assgtPage.putExtra("Assgt No", A_ID.get(groupPosition));
//                startActivity(assgtPage);
//              assgt number needed to get details.
                return false;
            }
        });


        return rootView;
    }

}
