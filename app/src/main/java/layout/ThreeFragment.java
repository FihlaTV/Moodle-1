package layout;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nikhil.ayush.aditi.moodleana.R;

public class ThreeFragment extends Fragment{
public TextView msg ;
    public ThreeFragment() {
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
        View ret_view = inflater.inflate(R.layout.fragment_three, container, false);
        msg=(TextView) ret_view.findViewById(R.id.three);
        Bundle bundle = this.getArguments();
        ArrayList<String > list =bundle.getStringArrayList("noti");
        if(list.isEmpty())
        {msg.setText("No New Notifications");

        }
        return ret_view;


    }

}