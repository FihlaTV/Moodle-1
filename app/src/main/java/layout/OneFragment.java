package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;

import nikhil.ayush.aditi.moodleana.CourseTab;
import nikhil.ayush.aditi.moodleana.R;


public class OneFragment extends Fragment {

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
        final TextView Team  = (TextView) myFragmentView.findViewById(R.id.Name3);
        Team.setText(Name1);
        final TextView Team1  = (TextView) myFragmentView.findViewById(R.id.Name4);
        Team1.setText(Name2);

        return myFragmentView;
    }


}