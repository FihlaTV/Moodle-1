package nikhil.ayush.aditi.moodleana;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;

/**
 * Created by Ayush on 20-02-2016.
 * This has been made to handle cookies and also to store a global list of courses and their codes.
 */
public class MyApp_cookie extends Application
{

    CookieManager cookieManage;
   public static HashMap<Integer,String> course_list=new HashMap<Integer,String>();
    public static HashMap<Integer,String> course_code=new HashMap<Integer,String>();

    public void onCreate() {
        cookieManage= new CookieManager();
        CookieHandler.setDefault(cookieManage);
        super.onCreate();
//        courses=new ArrayList<>();
        //urls=new String[10];


    }



}
