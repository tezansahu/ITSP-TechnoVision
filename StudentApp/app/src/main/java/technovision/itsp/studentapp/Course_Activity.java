package technovision.itsp.studentapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Course_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_course_);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activty_course_);
        Intent intent = getIntent();
        TextView usr_rollno = (TextView) findViewById(R.id.txtrollno);
        usr_rollno.setText(intent.getStringExtra("username"));



        final String[] courses = {"ma105","ma106","ma108","ma106","ma108","ma106","ma108","ma106","ma108","ma106","ma108","ma106","ma108","ma106","ma108"};
        //will load "courses" later from server


        ListView courselistview= (ListView) findViewById(R.id.course_list);
        ListAdapter courselistadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, courses);
        courselistview.setAdapter(courselistadapter);

        courselistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Course_Activity.this,AttendanceActivity.class);
                intent.putExtra("course",courses[position]);
                startActivity(intent);

            }
        });






    }
}
