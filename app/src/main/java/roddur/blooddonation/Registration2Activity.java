package roddur.blooddonation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

public class Registration2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        Spinner spinner = findViewById(R.id.blood);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button create = findViewById(R.id.create_btn);
        create.setOnClickListener(v -> {
            Intent i = getIntent();
            String firstname = i.getExtras().getString("firstname");
            String lastname = i.getExtras().getString("lastname");
            String email = i.getExtras().getString("email");
            String date_ofbirth = i.getExtras().getString("date_of_birth");
            String password = i.getExtras().getString("password");

            String mobile = ((EditText) findViewById(R.id.mobile)).getText().toString();
            String blood_group = spinner.getSelectedItem().toString().replaceAll(Pattern.quote("+"),"1")
                    .replaceAll(Pattern.quote("-"),"0");
            String last_donation = ((EditText) findViewById(R.id.lastdonation)).getText().toString().
                    replaceAll(Pattern.quote("/"),"-");
            String no_of_donation = ((EditText) findViewById(R.id.no_of_donation)).getText().toString();
            TextView error=findViewById(R.id.errors);
            if(mobile.equals("") || blood_group.equals("") || last_donation.equals("")){
                error.setText("*Some of the fields are empty");
                error.setVisibility(View.VISIBLE);
                return;
            } else if(!last_donation.equals("0") && !(last_donation.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))){
                error.setText("*Date format is wrong");
                error.setVisibility(View.VISIBLE);
                return;
            } else if(mobile.length()!=10){
                error.setText("*Mobile numbers must be of 10 characters");
                error.setVisibility(View.VISIBLE);
                return;
            }

            String link = "http://10.0.2.2/BloodDomain/create.php?firstname=" + firstname + "&lastname=" + lastname +
                    "&email=" + email + "&password=" + password + "&date_of_birth=" + date_ofbirth + "&mobile=" + mobile +
                    "&blood_group=" + blood_group + "&last_donation=" + last_donation + "&no_of_donation=" + no_of_donation;

            class DatabaseTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    try {

                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(link));
                        HttpResponse response = client.execute(request);

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(response.getEntity().getContent()));

                        StringBuffer sb = new StringBuffer("");
                        String line;

                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();

                        if (sb.toString().equals("")) {
                            Intent i = new Intent(Registration2Activity.this, HomeActivity.class);
                            i.putExtra("firstname", firstname);
                            i.putExtra("lastname", lastname);
                            i.putExtra("email", email);
                            i.putExtra("date_of_birth", date_ofbirth);
                            i.putExtra("password", password);
                            i.putExtra("mobile", mobile);
                            i.putExtra("blood", blood_group);
                            i.putExtra("last_donation", last_donation);
                            i.putExtra("no_of_donation", no_of_donation);

                            startActivity(i);

                        } else {
                            error.setText("*Sorry, this number is being used");
                            error.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }
            new DatabaseTask().execute();

        });
    }
}
