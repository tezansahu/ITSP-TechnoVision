package technovision.itsp.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.JSONArrayBody;
import com.koushikdutta.async.parser.JSONObjectParser;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public String url = "https://technovision.pythonanywhere.com/";
    public String urlextension = "?format=json";


    public void clicklogin(View view) {
        Log.d("clicklogin_method", "clicklogincalled");


        Ion.with(this)
                .load((url + "Student/" + urlextension).toString())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("ion debug", "connection successful");
                        processdata(result);
                    }
                });
    }


    private void login(JSONObject jsonObject) throws JSONException {

        if (!jsonObject.isNull("Password")) {
            String password = jsonObject.getString("Password");
            Log.d("JSONobject","inside login");
            EditText passwordview = (EditText) findViewById(R.id.login_password);
            String enteredpassword = passwordview.getText().toString();
            if (enteredpassword.equals(password)) {
                Log.d("JSONobject","password matched");

                EditText login_username = (EditText) findViewById(R.id.login_username);
                String jsonstring = jsonObject.toString();
                Intent intent = new Intent(this, Course_Activity.class);
                intent.putExtra("jsonstring", jsonstring);
                Log.d("JSONobject","activity going to start");
                startActivity(intent);
                Log.d("JSONobject","activity started");
            }
            else {
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();

            }
        }

    }

   /* private void login(String password) {
        if (!password.equals("-1")) {
            Log.d("JSONobject","inside login");
            EditText passwordview = (EditText) findViewById(R.id.login_password);
            String enteredpassword = passwordview.getText().toString();
            if (enteredpassword.equals(password)) {
                Log.d("JSONobject","password matched");

                EditText login_username = (EditText) findViewById(R.id.login_username);
                Intent intent = new Intent(this, Course_Activity.class);
                intent.putExtra("username", login_username.getText().toString());
                Log.d("JSONobject","activity going to start");
                startActivity(intent);
                Log.d("JSONobject","activity started");
            }
            else {
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();

            }
        }

    }


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


    private void processdata(String data) {
        try {
            EditText usernameview = (EditText) findViewById(R.id.login_username);
            String username = usernameview.getText().toString();
            Log.d("JSON", "processdata method called");
            int c= 0;


            JSONArray jsonarray = new JSONArray(data);
            Log.d("JSON", "creating json array");
            JSONObject J;


            for (int i = 0; i < jsonarray.length(); ++i) {
                J = jsonarray.getJSONObject(i);
                if (username.equals(J.getString("Roll_No"))) {
                    Log.d("JSONobject","username matched");
                    login(J);
                    c=1;
                    break;
                }

            }
            if (c==0)
            { Toast.makeText(this, "username not found", Toast.LENGTH_SHORT).show(); }


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }


    public JSONArray Stringtojsonarray(String string) {
        if (string.equals(null)) {
            Log.d("stringtojsonarray", "string parameter is empty");
            return null;
        } else {
            JSONArray jsonArray = null;

            int counter = 0;//counter for "{"
            String stringtojson = "";


            for (String x : string.split("")) {
                if (!x.equals("[")) {
                    if (counter == 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(stringtojson);
                            jsonArray.put(jsonObject);
                            stringtojson = "";

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Stringtojsonarray", "string to json object");
                        }
                    }

                    if (x.equals("{")) {
                        counter++;
                    } else if (x.equals("}")) {
                        counter--;
                    }

                    stringtojson.concat(x);
                }
            }
            return jsonArray;
        }
    }
}
