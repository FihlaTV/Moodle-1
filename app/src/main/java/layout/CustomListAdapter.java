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
public class CustomListAdapter extends BaseAdapter
{
    ArrayList<String> titles;
    ArrayList<String> times;
    Context context;
    ArrayList<Integer> Serial;

    private static LayoutInflater inflater = null;

    public CustomListAdapter(CourseTab c_Activity, ArrayList<String> titlelist, ArrayList<String> timelist)
    {
        titles = titlelist;
        context = c_Activity;
        times = timelist;
        for (int i = 0; i < titlelist.size(); i ++)
        {
            Serial.add(i, i+1);
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return titles.size();
    }

    public Object getItem(int position)
    {
        return  position;
    }

    public long getItemId(int position)
    {
        return position;
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
        onerow = inflater.inflate(R.layout.customListItem, null);
        holder.sno = (TextView) onerow.findViewById(R.id.SerialNo);
        holder.time = (TextView) onerow.findViewById(R.id.ATitle);
        holder.title = (TextView) onerow.findViewById(R.id.Time);
        holder.sno.setText(Serial.get(position));
        holder.time.setText(times.get(position));
        holder.title.setText(titles.get(position));
        onerow.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "You clicked row " + position, Toast.LENGTH_LONG).show();
            }

        });
        return  onerow;

    }

}
