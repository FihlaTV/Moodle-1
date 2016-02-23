package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import nikhil.ayush.aditi.moodleana.thread_detail;
import nikhil.ayush.aditi.moodleana.R;
import java.util.ArrayList;

/**
 * Created by jagdish on 2/23/2016.
 */

//this is for list of comments on thread_detail.

public class CustomListAdapter extends BaseAdapter
{
    ArrayList<String> Texts;
    ArrayList<String> Times;
    Context context;
    ArrayList<String> Users;

    private static LayoutInflater inflater = null;

    public CustomListAdapter(Context c_Activity, ArrayList<String> textlist, ArrayList<String> timelist, ArrayList<String> userlist)
    {
        Texts = textlist;
        Times = timelist;
        Users = userlist;
        context = c_Activity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return Texts.size(); // correct.
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
        TextView user;
        TextView text;
        TextView time;
    }


    public  View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View onerow;
        onerow = inflater.inflate(R.layout.comment_item, parent, false);
        holder.user = (TextView) onerow.findViewById(R.id.User_comm);
        holder.text = (TextView) onerow.findViewById(R.id.Comment);
        holder.time = (TextView) onerow.findViewById(R.id.CTime);
        holder.text.setText(Texts.get(position));
        holder.time.setText(Times.get(position));
        holder.user.setText(Users.get(position));
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