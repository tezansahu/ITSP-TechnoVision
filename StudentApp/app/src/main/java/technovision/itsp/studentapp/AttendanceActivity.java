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


        ListView attendanceview= (ListView) findViewById(R.id.attendance_list);
        ListAdapter attendanceviewadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, attend);
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
