package samsao.samsaoreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by cnagendra on 8/10/2016.
 */
public class ReportAdapter  extends BaseAdapter {

    private ArrayList<RepoData> repoList;
    private MainActivity caller;
    private Context context;

    public ReportAdapter(Context context, ArrayList<RepoData> repoList, MainActivity caller)
    {
        this.repoList = repoList;
        this.context = context;
        this.caller = caller;
    }

    @Override
    public int getCount()
    {
        return repoList.size();
    }

    @Override
    public RepoData getItem(int index)
    {
        return repoList.get(index);
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final RepoData repoData = getItem(position);
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.report_row, parent, false);
            holder = new ViewHolder();


            holder.fullName = (TextView) convertView.findViewById(R.id.full_name);
            holder.time = (TextView) convertView.findViewById(R.id.time_list);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fullName.setText(repoData.getFullName());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setTimeZone(TimeZone.getDefault());
        holder.time.setText(timeFormat.format(repoData.getLastUpdateDate()));

        return convertView;
    }

    static class ViewHolder
    {
        TextView fullName;
        TextView time;

    }
}
