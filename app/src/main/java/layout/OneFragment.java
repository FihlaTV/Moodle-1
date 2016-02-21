package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nikhil.ayush.aditi.moodleana.CourseTab;
import nikhil.ayush.aditi.moodleana.MyApp_cookie;
import nikhil.ayush.aditi.moodleana.R;
import nikhil.ayush.aditi.moodleana.dummy;

import static android.location.Location.convert;


public class OneFragment extends Fragment {
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
        // Inflate the layout for this fragment

        View myFragmentView = inflater.inflate(R.layout.fragment_one, container, false);
        Bundle bundle = this.getArguments();
        String Name1 = bundle.getString("User");
        String Name2 = bundle.getString("Pass");
        ArrayList<String> list_all =bundle.getStringArrayList("String");
        ListView list=(ListView) myFragmentView.findViewById(R.id.listView) ;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                         list_all
                );
        System.out.println("yo boys");
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                //You could lookup by position, but "name" is more general)
                Toast.makeText(getActivity().getApplication(),"position selcted is "+position,Toast.LENGTH_SHORT).show();
                Intent coursePage=new Intent (getActivity().getApplicationContext(),CourseTab.class);
                coursePage.putExtra("Course Code",app_list.course_code.get(position));
                Intent intent = new Intent(getActivity(), CourseTab.class);
                startActivity(intent);
// all intents to be put here

            }
        });
        return myFragmentView;
    }


}