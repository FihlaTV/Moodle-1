package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import nikhil.ayush.aditi.moodleana.CourseTab;
import nikhil.ayush.aditi.moodleana.R;
import java.util.ArrayList;

/**
 * Created by jagdish on 2/21/2016.
 */

//this one is for list of threads in Course_threads fragment.
public class CustomAdapter_Thread extends BaseAdapter
{
    ArrayList<String> Titles;
    ArrayList<String> Times;
    Context context;
    ArrayList<Integer> Serial;

    private static LayoutInflater inflater = null;

    public CustomAdapter_Thread(CourseTab c_Activity, ArrayList<String> titlelist, ArrayList<String> timelist)
    {
        Titles = titlelist;
        context = c_Activity;
        Times = timelist;
        Serial = new ArrayList<Integer>();
        for (int i = 0; i < titlelist.size(); i ++)
        {
            Serial.add(i, i+1);
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return Titles.size(); // correct.
    }

    public Object getItem(int position)
    {
        return  position; // or return the tuple (titles[posn],time[posn],serial[posn].
    }

    public long getItemId(int position)
    {
        return position; // correct.
    }

    public class Holder
    {
        TextView sno;
        TextView title;
        TextView time;
    }


    public  View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View onerow;
        onerow = inflater.inflate(R.layout.customlistitem, parent, false);
        holder.sno = (TextView) onerow.findViewById(R.id.SerialNo);
        holder.title = (TextView) onerow.findViewById(R.id.ATitle);
        holder.time = (TextView) onerow.findViewById(R.id.Time);
        holder.sno.setText(String.valueOf(Serial.get(position)));
        holder.time.setText(Times.get(position));
        holder.title.setText(Titles.get(position));
//        onclick listener to be set in fragment code.
        return  onerow;

    }

}