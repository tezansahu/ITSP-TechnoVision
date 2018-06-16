package technovision.itsp.studentapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Course_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_course_);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activty_course_);
        Intent intent = getIntent();
        final TextView usr_rollno = (TextView) findViewById(R.id.txtrollno);
        TextView usr_student_name = (TextView) findViewById(R.id.txtusername);
        TextView usr_branch = (TextView) findViewById(R.id.txtbranch);


        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("jsonstring"));
            usr_rollno.setText(jsonObject.getString("Roll_No"));
            usr_student_name.setText(jsonObject.getString("Student_Name"));
            usr_branch.setText(jsonObject.getString("Branch"));
            JSONArray jsonArray = jsonObject.getJSONArray("Reg_Course");

            final String[] courses = jsonObject.getJSONArray("Reg_Course").toString()
                    .replace("\"","")
                    .replace("[","")
                    .replace("]","")
                    .split(",");




        /*
        {
            "Roll_No": "170100035",
                "Student_Name": "Tezan Sahu",
                "Branch": "Mechanical",
                "Password": "tezan",
                "Image": "http://technovision.pythonanywhere.com/media/Student_Image/Tezan.jpg",
                "Reg_Course": [
            "CS101",
                    "MA106"
        ]
        }
     */


        ListView courselistview = (ListView) findViewById(R.id.course_list);
        ListAdapter courselistadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);
        courselistview.setAdapter(courselistadapter);

        courselistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Course_Activity.this, AttendanceActivity.class);
                intent.putExtra("course", courses[position]);
                intent.putExtra("roll_no",usr_rollno.getText().toString());
                startActivity(intent);

            }
        });

    }catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
