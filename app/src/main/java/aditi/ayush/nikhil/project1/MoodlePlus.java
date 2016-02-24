package aditi.ayush.nikhil.project1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodlePlus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int splash_time = 2000;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodle_plus);

        TextView myTextView = (TextView) findViewById(R.id.Text);
        Typeface typeFace = FontLoader.getTypeFace(this, "Pacifico");
        if (typeFace != null) myTextView.setTypeface(typeFace);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MoodlePlus.this, Login.class);
                MoodlePlus.this.startActivity(mainIntent);
                MoodlePlus.this.finish();
            }
        }, splash_time);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            String url_out="http://10.208.20.164:8000/default/logout.json";
            JsonObjectRequest logout=new JsonObjectRequest(Request.Method.GET,
                    url_out, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, response.toString());

                    try {
                        // Parsing json object response
                        // response will be a json object
                        int noti_count=response.getInt("noti_count");
                        if(noti_count==4)
                        {
                            Toast.makeText(getApplicationContext(), "Logout Succesful", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),Login.class);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    //  hidepDialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Tag", "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    // hide the progress dialog
                    // hidepDialog();
                }
            });

            Volley.newRequestQueue(this).add(logout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}