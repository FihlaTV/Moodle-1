package nikhil.ayush.aditi.moodleana;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by Ayush on 20-02-2016.
 */
public class MyApp_cookie extends Application
{    CookieManager cookieManage;
    public void onCreate() {
        cookieManage= new CookieManager();
        CookieHandler.setDefault(cookieManage);
        super.onCreate();
//        courses=new ArrayList<>();
        //urls=new String[10];


    }



}
