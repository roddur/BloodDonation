package roddur.blooddonation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    EditText password;
    TextView forgot;
    Button signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone =  findViewById(R.id.phone);
        password = findViewById(R.id.password);

        forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                class DatabaseTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        String phone_no= phone.getText().toString();
                        String pass= password.getText().toString();
                        try{
                            String link = "http://10.0.2.2/BloodDomain/auth.php?mobile="+phone_no+"&password="+pass;

                            URL url = new URL(link);
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

                            if(sb.toString().equals("")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView error = findViewById(R.id.error_message);
                                        error.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else {
                                Intent i = new Intent(v.getContext(),HomeActivity.class);
                                startActivity(i);
                            }

                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }
                new DatabaseTask().execute();
            }
        });

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}
