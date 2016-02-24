package aditi.ayush.nikhil.project1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;



public class OneFragment extends Fragment {
    /** A class for the fragment containing all courses **/
    MyApp_cookie app_list=new MyApp_cookie();

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate the layout for the fragment containing all courses **/

        View myFragmentView = inflater.inflate(R.layout.fragment_one, container, false);
        Bundle bundle = this.getArguments();
        String Name1 = bundle.getString("User");
        String Name2 = bundle.getString("Pass");
        ArrayList<String> list_all =bundle.getStringArrayList("String");
        ListView list=(ListView) myFragmentView.findViewById(R.id.listView) ;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity().getApplicationContext(),
                        (R.layout.list_view_item),
                        list_all
                );
        System.out.println("onCreate of OneFragment called");
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                //You could lookup by position, but "name" is more general)
//                Toast.makeText(getActivity().getBaseContext(), "Loading Course Page...It may take time",Toast.LENGTH_LONG).show();
                Intent coursePage = new Intent(getActivity().getApplicationContext(), CourseTab.class);

                System.out.println("current position(onefragment): " + (position + 1) + " " + app_list.course_code.get(position + 1));
                coursePage.putExtra("Course Code", app_list.course_code.get(position + 1));
//                Intent intent = new Intent(getActivity(), CourseTab.class);
               startActivity(coursePage);
// all intents to be put here

            }
        });
        return myFragmentView;
    }

}