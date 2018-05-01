package roddur.blooddonation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    public static String user_mobile;

    static TextView dlast_donation;
    static TextView dno_of_donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i=getIntent();
        String firstname=i.getExtras().getString("firstname");
        String lastname=i.getExtras().getString("lastname");
        String email=i.getExtras().getString("email");
        String date_ofbirth=i.getExtras().getString("date_of_birth");
        String password=i.getExtras().getString("password");
        String mobile=i.getExtras().getString("mobile");
        String blood_group=i.getExtras().getString("blood").
                replaceAll("0","-").replaceAll("1","+");
        String last_donation=i.getExtras().getString("last_donation");
        Log.v("sex",last_donation);
        String no_of_donation=i.getExtras().getString("no_of_donation");

        user_mobile=mobile;

        TextView dname=findViewById(R.id.dname);
        dname.setText(firstname+" "+lastname);
        TextView dblood=findViewById(R.id.dblood);
        dblood.setText(blood_group);
        TextView dmobile=findViewById(R.id.dmobile);
        dmobile.setText(mobile);
        TextView demail=findViewById(R.id.demail);
        demail.setText(email);
        dlast_donation=findViewById(R.id.dlastdonation);
        dlast_donation.setText(last_donation);
        dno_of_donation=findViewById(R.id.dnoofdonations);
        dno_of_donation.setText(no_of_donation);

        Button update_donation = findViewById(R.id.update_donation);
        update_donation.setOnClickListener(v->{
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });

        Button search=findViewById(R.id.search_blood);
        search.setOnClickListener(v->{
            Intent intent=new Intent(v.getContext(), SearchActivity.class);
            startActivity(intent);
        });

        Button logout=findViewById(R.id.logout);
        logout.setOnClickListener(v->{
            Intent homeIntent=new Intent(v.getContext(),LoginActivity.class);
            startActivity(homeIntent);
        });

        Button info=findViewById(R.id.info);
        info.setOnClickListener(v->{
            Intent infoIntent=new Intent(v.getContext(),InfoActivity.class);
            startActivity(infoIntent);
        });

    }
}
