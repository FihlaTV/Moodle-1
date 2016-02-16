package nikhil.ayush.aditi.moodleana;

/**
 * Created by Nikhil on 15/02/16.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;


public class Font {
//    public static final Font  PROXIMA_NOVA    = new Font("ProximaNovaRegular.otf");
    public static final Font  Pacifico = new Font("Pacifico.ttf");
    private final String      assetName;
    private volatile Typeface typeface;

    private Font(String assetName) {
        this.assetName = assetName;
    }

    public void apply(Context context, TextView textView) {
        if (typeface == null) {
            synchronized (this) {
                if (typeface == null) {
                    typeface = Typeface.createFromAsset(context.getAssets(), assetName);
                }
            }
        }
        textView.setTypeface(typeface);
    }
}