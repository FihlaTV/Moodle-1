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
        final Intent mainIntent = new Intent(this, Tab_view.class);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("yo", "why this ... working");
                        System.out.println(response.toString());
                        boolean b= J_object.login_res(response.toString());
                       if(b) {
                           message.setText("Success");

        mainIntent.putExtra("User", Username);
        mainIntent.putExtra("Pass", Password);
        startActivity(mainIntent);

                       }
                        else
                           message.setText("faliure");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        Toast.makeText(LoginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("yo", "why this ... working");
                        System.out.println(response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        Toast.makeText(LoginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
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
