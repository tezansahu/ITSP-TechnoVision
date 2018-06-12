package technovision.itsp.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent = getIntent();
        String course = intent.getStringExtra("course");
        TextView coursenameview= (TextView) findViewById(R.id.coursename);
        coursenameview.setText(course);

        final String[] attend = {"A","N"};
        //will load "courses" later from server
        attendancefordate date = new attendancefordate("03-10-1999","P");
        attendancefordate date1 = new attendancefordate("06-10-1999","P");
        attendancefordate date2 = new attendancefordate("09-10-1999","A");
        ArrayList<attendancefordate> datelist = new ArrayList<>();
        datelist.add(date);
        datelist.add(date1);
        datelist.add(date2);
        datelistadapter adapter = new datelistadapter(this,R.layout.adapter_view_attendance,datelist);






       ListView attendanceview= (ListView) findViewById(R.id.attendance_list);
       attendanceview.setAdapter(adapter);
       /* ListAdapter attendanceviewadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, attend);
        attendanceview.setAdapter(attendanceviewadapter);

       /* attendanceview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Course_Activity.this,AttendanceActivity.class);
                intent.putExtra("course",co[position]);
                startActivity(intent);

            }
        });

       */
    }
}
