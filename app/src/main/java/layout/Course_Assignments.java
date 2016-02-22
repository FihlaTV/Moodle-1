package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;

import nikhil.ayush.aditi.moodleana.CourseTab;
import nikhil.ayush.aditi.moodleana.R;
import android.widget.ListView;

import java.util.ArrayList;

public class Course_Assignments extends Fragment{

    ListView Alv;
//    Context context;
//    CourseTab activity is the context for these fragments.
    public CourseTab activity = (CourseTab) getActivity();

    public Course_Assignments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        context = this;
//        Alv = (ListView)findViewById(R.id.AssgtCustomList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course__assignments, container, false);
        ListView Assgt_lv = (ListView) view.findViewById(R.id.AssgtCustomList);
        Bundle bundle = this.getArguments();
        ArrayList<String> titles = bundle.getStringArrayList("Name");
        ArrayList<String> times = bundle.getStringArrayList("Created At");
//         arraylist
        CustomListAdapter new_adap = new CustomListAdapter((CourseTab)getActivity(), titles, times);
            Assgt_lv.setAdapter(new_adap);
        return view;
    }

}