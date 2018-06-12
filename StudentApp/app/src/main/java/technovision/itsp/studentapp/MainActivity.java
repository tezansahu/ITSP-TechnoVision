package technovision.itsp.studentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clicklogin(View view) {
        if (logincheck()){
            EditText login_username = (EditText) findViewById(R.id.login_username);
            Intent intent = new Intent(this,Course_Activity.class);
            intent.putExtra("username",login_username.getText().toString());
            startActivity(intent);
            }
        }

    private boolean logincheck() {
        return true;
    }
}
