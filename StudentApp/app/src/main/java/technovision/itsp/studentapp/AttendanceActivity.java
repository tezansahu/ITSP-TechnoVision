package technovision.itsp.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent = getIntent();
        String course = intent.getStringExtra("course");
        final String roll_no = intent.getStringExtra("roll_no");
        TextView coursenameview= (TextView) findViewById(R.id.coursename);
        coursenameview.setText(course);

        String url = "https://technovision.pythonanywhere.com/";
        String urlextension = "/?format=json";

        Ion.with(this)
                .load((url + course + urlextension))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("ion debug", "connection successful");
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            JSONObject J=null;

                            for (int i = 0; i < jsonArray.length(); ++i) {
                                J = jsonArray.getJSONObject(i);
                                if (roll_no.equals(J.getString("student"))) {
                                    Log.d("JSONobject","username matched");
                                    break;
                                }

                            }
                            Log.d("JSOn",J.toString());


                            ArrayList<attendancefordate> datelist = new ArrayList<>();


                                String datesstring = J.getString("Dates").replace("[u","").replace(", u","");
                                String attendancestring = J.getString("Attendance").replace("[u","").replace(", u","");
                                int counter=0;
                                String string=" ";
                                int length=0;
                               ArrayList<String> datestrings = new ArrayList<>();
                                ArrayList<String> attendancestrings = new ArrayList<>();
                                for (String x : datesstring.split(""))
                                {
                                    if (x.equals("'")&&counter==1)
                                    {
                                            counter--;
                                            datestrings.add(string);
                                            length++;
                                            string=" ";

                                        }
                                        else if(x.equals("'")&&counter==0)
                                        {
                                            counter++;
                                        }
                                        else
                                        {string=string.concat(x);}

                                        }

                                        string=" ";
                                for (String x : attendancestring.split(""))
                                {
                                    if (x.equals("'")&&counter==1)
                                    {
                                        counter--;
                                        attendancestrings.add(string);
                                        string=" ";

                                    } else if(x.equals("'")&&counter==0) {
                                        counter++;
                                    } else
                                    {string=string.concat(x);}

                                }
                                 for (int i=0;i<length;++i)
                                 {
                                attendancefordate date = new attendancefordate(datestrings.get(i),attendancestrings.get(i));
                                datelist.add(date);

                                }



                            datelistadapter adapter = new datelistadapter(AttendanceActivity.this, R.layout.adapter_view_attendance, datelist);
                            ListView attendanceview = (ListView) findViewById(R.id.attendance_list);
                            attendanceview.setAdapter(adapter);






                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }




                    }
                });


    }
}



   /*
    void processdata(String data)

    {
        final String[] attend = {"A", "N"};
        //will load "courses" later from server


        attendancefordate date = new attendancefordate("03-10-1999", "P");
        attendancefordate date1 = new attendancefordate("06-10-1999", "P");
        attendancefordate date2 = new attendancefordate("09-10-1999", "A");


        ArrayList<attendancefordate> datelist = new ArrayList<>();
        datelist.add(date);
        datelist.add(date1);
        datelist.add(date2);


        datelistadapter adapter = new datelistadapter(this, R.layout.adapter_view_attendance, datelist);
        ListView attendanceview = (ListView) findViewById(R.id.attendance_list);
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


    }
    */