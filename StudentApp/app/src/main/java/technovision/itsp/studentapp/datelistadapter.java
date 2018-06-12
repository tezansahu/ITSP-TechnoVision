package technovision.itsp.studentapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class datelistadapter extends ArrayAdapter<attendancefordate>{
    private Context mcontext;
    private int mresource;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String attendance = getItem(position).getString();
        attendancefordate attendance_date = new attendancefordate(date,attendance);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresource,parent,false);
        TextView tvdate = (TextView) convertView.findViewById(R.id.attendance_course_date);
        TextView tvattendance = (TextView) convertView.findViewById(R.id.attendance_);
        tvdate.setText(date);
        tvattendance.setText(attendance);
        return  convertView;

    }

    public datelistadapter(@NonNull Context context, int resource, @NonNull ArrayList<attendancefordate> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource= resource;

    }
}
