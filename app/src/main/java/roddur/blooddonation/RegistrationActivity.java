package roddur.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView error=findViewById(R.id.error);

        Button next=findViewById(R.id.next);
        next.setOnClickListener(v->{
            EditText firstname=findViewById(R.id.firstname);
            EditText lastname=findViewById(R.id.lastname);
            EditText email=findViewById(R.id.email);
            EditText password=findViewById(R.id.password2);
            EditText dateofbirth=findViewById(R.id.dateOfBirth);

            String date=dateofbirth.getText().toString().replaceAll(Pattern.quote("/"),"-");

            if(firstname.getText().toString().equals("")||lastname.getText().toString().equals("")||
                    email.getText().toString().equals("")||dateofbirth.getText().toString().equals("")||
                    password.getText().toString().equals("")){
                error.setText("*Some of the fields are empty");
                error.setVisibility(View.VISIBLE);
                return;

            } else if(password.getText().toString().length()<6){
                error.setText("*Password is too small");
                error.setVisibility(View.VISIBLE);
                return;

            } else if(email.getText().toString().indexOf('@')==-1){
                error.setText("*Not a valid Email address");
                error.setVisibility(View.VISIBLE);
                return;
            } else if(!(date.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))){
                error.setText("*Date format is wrong");
                error.setVisibility(View.VISIBLE);
                return;
            }

            Intent i = new Intent(v.getContext(),Registration2Activity.class);
            i.putExtra("firstname",firstname.getText().toString());
            i.putExtra("lastname",lastname.getText().toString());
            i.putExtra("email",email.getText().toString());
            i.putExtra("date_of_birth",dateofbirth.getText().toString());
            i.putExtra("password",password.getText().toString());
            startActivity(i);
        });

    }

}
