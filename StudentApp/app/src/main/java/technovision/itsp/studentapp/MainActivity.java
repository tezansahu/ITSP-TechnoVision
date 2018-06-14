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


    public  String url = "technovision.pythonanywhere.com/";
    public  String urlextension = "?format=json";


    public void clicklogin(View view)
    {
        Log.d("clicklogin_method","clicklogincalled");
        Ion.with(this)
                .load((url+"Student/"+urlextension).toString())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("ion debug","connection successful");
                        processdata(result);
                        }
                });
        }





        private void login(String password) {
        if (!password.equals("-1")) {
            EditText passwordview = (EditText) findViewById(R.id.login_password);
            String enteredpassword = passwordview.getText().toString();
            if (enteredpassword.equals(password)) {

                EditText login_username = (EditText) findViewById(R.id.login_username);
                Intent intent = new Intent(this, Course_Activity.class);
                intent.putExtra("username", login_username.getText().toString());
                startActivity(intent);
            }
            else {
                Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT);

            }
        }

        }



        private void processdata(String data) {
        try {
            EditText usernameview = (EditText) findViewById(R.id.login_username);
            String username = usernameview.getText().toString();
            Log.d("JSON","processdata method called");

            JSONArray jsonarray = new JSONArray(data);
            Log.d("JSON","creating json array");
            JSONObject J;


               for (int i = 0; i < jsonarray.length(); ++i)
               {
                   J = jsonarray.getJSONObject(i);
                    if (username == J.getString("Roll_No")) {
                       login(J.getString("Password"));
                       break;
                    }

               }

            Toast.makeText(this,"username not found",Toast.LENGTH_SHORT);
               login("-1");


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
