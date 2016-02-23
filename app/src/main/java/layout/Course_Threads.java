package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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

import nikhil.ayush.aditi.moodleana.CourseTab;
import nikhil.ayush.aditi.moodleana.R;


public class Course_Threads extends Fragment{

    public Course_Threads() {
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
        View view = inflater.inflate(R.layout.fragment_course__threads, container, false);
        ListView ThreadLV = (ListView) view.findViewById(R.id.ThreadsLV);
        Bundle bundle = this.getArguments();
        ArrayList<String> titles = bundle.getStringArrayList("Name");
        ArrayList<String> times = bundle.getStringArrayList("Updated On");
//         arraylist
        CustomListAdapter new_adap = new CustomListAdapter((CourseTab)getActivity(), titles, times);
        ThreadLV.setAdapter(new_adap);
//        ThreadLV. itemclick listener.
        return view;
    }




    public void Post_thread(View view)
    {
        EditText title=(EditText) view.findViewById(R.id.title);
        EditText desc =(EditText) view.findViewById(R.id.description);
        String Title= String.valueOf(title.getText());
        if(Title.length()==0)
            return;//Display an error message
        String Description=String.valueOf(desc.getText());
        String url="http://10.208.20.164:8000/threads/new.json?title="+Title+"&description="+Description+"&course_code="+"cop290";
        // WE NEED TO CHANGE ABOVE LINE
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");


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
    }

}