package nikhil.ayush.aditi.moodleana;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;



public class LoginPage extends AppCompatActivity {

    private Context context;

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
        Typeface typeFace=FontLoader.getTypeFace(this,"Pacifico");
        if(typeFace!=null) myTextView.setTypeface(typeFace);


    }

    public void  grow1(View view){
        TextView Username = (TextView)findViewById(R.id.user);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow);
        Username.startAnimation(animation1);
    }


    public void  grow2(View view){
        TextView Password = (TextView)findViewById(R.id.pass);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow);
        Password.startAnimation(animation1);
    }


    public void change(View view){
        final EditText User = (EditText) findViewById(R.id.Username);
        final EditText Pass = (EditText) findViewById(R.id.password);
        String Username = User.getText().toString();
        String Password = Pass.getText().toString();
        Intent mainIntent = new Intent(this,Tab_view.class);
        mainIntent.putExtra("User", Username);
        mainIntent.putExtra("Pass", Password);
        startActivity(mainIntent);
    }
}
