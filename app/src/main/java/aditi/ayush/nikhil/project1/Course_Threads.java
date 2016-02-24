package aditi.ayush.nikhil.project1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.widget.AdapterView;




public class Course_Threads extends Fragment{

    String coursecode;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();
    ListView ThreadLV ;
    CustomAdapter_Thread new_adap;
    EditText title;
    EditText desc;

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
        final View view = inflater.inflate(R.layout.fragment_course__threads, container, false);
        ThreadLV = (ListView) view.findViewById(R.id.commentList);

        Button post=(Button) view.findViewById(R.id.post_comment);

        Bundle bundle = this.getArguments();
        coursecode = bundle.getString("Course Code");
        titles = bundle.getStringArrayList("Name");
        times = bundle.getStringArrayList("Updated On");

//         arraylist
        final ArrayList<Integer> ids = bundle.getIntegerArrayList("ID");
        new_adap = new CustomAdapter_Thread(getActivity().getApplicationContext(), titles, times);
        ThreadLV.setAdapter(new_adap);
        ThreadLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent threadPage = new Intent(getActivity(), thread_detail.class);
                threadPage.putExtra("ID", ids.get(position));
                startActivity(threadPage);
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = (EditText) view.findViewById(R.id.title);
                desc = (EditText) view.findViewById(R.id.description);
                String Title = String.valueOf(title.getText());
                if (Title.length() == 0)
                    return;//Display an error message
                String Description = String.valueOf(desc.getText());
                String url = "http://10.208.20.164:8000/threads/new.json?title=" + Title + "&description=" + Description + "&course_code=" + coursecode;
                System.out.println(url);
                titles.add(title.getText().toString());
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                times.add(formattedDate);
                JsonObjectRequest json_ob = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.i("yo", "why this ... working");
                                try {
                                    System.out.println(Boolean.toString(response.getBoolean("success")));
                                    if (response.getBoolean("success")) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Thread created successfully", Toast.LENGTH_SHORT).show();
                                        new_adap = new CustomAdapter_Thread(getActivity().getApplicationContext(), titles, times);
                                        ThreadLV.setAdapter(new_adap);

// Fragment currentFragment = getFragmentManager().findFragmentByTag("Threads");
//                                        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
//                                        fragTransaction.detach(currentFragment);
//                                        ((CourseTab) getActivity()).UpdateThreads();
//                                        try
//                                        {
//                                            Thread.sleep(1000);                 //1000 milliseconds is one second.
//                                        }
//                                        catch(InterruptedException ex)
//                                        {
//                                            Thread.currentThread().interrupt();
//                                        }
//                                        fragTransaction.attach(currentFragment);
//                                        fragTransaction.commit();
                                    }
                                } catch (JSONException e) {
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
            }
        });
        return view;
    }

}
