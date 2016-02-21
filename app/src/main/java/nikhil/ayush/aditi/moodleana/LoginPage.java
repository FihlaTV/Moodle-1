package nikhil.ayush.aditi.moodleana;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;


public class LoginPage extends AppCompatActivity {

    private Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public final JSON_parse J_object = new JSON_parse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView myTextView = (TextView) findViewById(R.id.Login);
        Typeface typeFace = FontLoader.getTypeFace(this, "Pacifico");
        if (typeFace != null) myTextView.setTypeface(typeFace);
        CookieManager cookieManage = new CookieManager();
        CookieHandler.setDefault(cookieManage);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void grow1(View view) {
        TextView Username = (TextView) findViewById(R.id.user);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow);
        Username.startAnimation(animation1);
    }


    public void grow2(View view) {
        TextView Password = (TextView) findViewById(R.id.pass);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow);
        Password.startAnimation(animation1);
    }


    public void login(View view) throws JSONException {
        final EditText User = (EditText) findViewById(R.id.Username);
        final EditText Pass = (EditText) findViewById(R.id.password);
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault( manager  );
        final String Username = User.getText().toString();
        final String Password = Pass.getText().toString();  Log.i("yo", "why this working");
        final TextView message = (TextView) findViewById(R.id.message);
        String url = "http://10.208.20.164:8000/default/login.json?userid="+Username+"&password="+Password;
        String url1="http://10.208.20.164:8000/courses/list.json";
        String url_1="http://10.208.20.164:8000/default/login.json?userid=cs1110200&password=john";
        final Intent mainIntent = new Intent(this, Tab_view.class);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_1, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    boolean success = response.getBoolean("success");
                  //  String email = response.getString("email");
                    JSONObject user = response.getJSONObject("user");
                    String entry = user.getString("entry_no");
                    String email = user.getString("email");

                    String jsonResponse = "";
                    jsonResponse += "Name: " + entry + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    if(success)
                    {
                    mainIntent.putExtra("User", Username);
                    mainIntent.putExtra("Pass", Password);
                        message.setText("Success");
                    Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT).show();
                        startActivity(mainIntent);



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

        Volley.newRequestQueue(this).add(jsonObjReq);


    }
//10.192.55.191
    //10.208.20.164
    public void login1(View view) throws JSONException {
        final EditText User = (EditText) findViewById(R.id.Username);
        final EditText Pass = (EditText) findViewById(R.id.password);
//        CookieManager manager = new CookieManager();
//        CookieHandler.setDefault( manager  );
        String Username = User.getText().toString();
        String Password = Pass.getText().toString();  Log.i("yo", "why this working");
        final TextView message = (TextView) findViewById(R.id.message);
        String url = "http://10.208.20.164:8000/default/login.json?userid="+Username+"&password="+Password;
        String url1="http://10.208.20.164:8000/courses/list.json";
        JsonObjectRequest json_ob = new JsonObjectRequest (Request.Method.GET, url1,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("yo", "why this ... working");
///                        System.out.println(response.toString());

                       try
                       {
                           int current_sem = response.getInt("current_sem");
                           int current_year = response.getInt("current_year") ;
                           JSONObject user = response.getJSONObject("user");
                           JSONArray courses = response.getJSONArray("courses");
                           System.out.println("sem :" +current_sem+" year: "+ current_year);
                           String name = user.getString("first_name");
                           for(int i=0;i<courses.length();i++)
                           {    JSONObject course= courses.getJSONObject(i);
                           }

                       } catch (JSONException e)
                       {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        Toast.makeText(LoginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(json_ob);
      /*  Intent mainIntent = new Intent(this, Tab_view.class);
        mainIntent.putExtra("User", Username);
        mainIntent.putExtra("Pass", Password);
        startActivity(mainIntent); */
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nikhil.ayush.aditi.moodleana/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LoginPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://nikhil.ayush.aditi.moodleana/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
