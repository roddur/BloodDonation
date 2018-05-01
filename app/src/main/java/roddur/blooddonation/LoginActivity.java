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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        /*forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(v -> {

        });*/

        login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
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
                            runOnUiThread(() -> {
                                TextView error = findViewById(R.id.error_message);
                                error.setVisibility(View.VISIBLE);
                            });

                        } else {
                            try {
                                //JSONArray array=new JSONArray(sb.toString());
                                //JSONObject data=array.getJSONObject(0);
                                JSONObject data = new JSONObject(sb.toString());
                                String firstname=data.getString("firstname");
                                String lastname=data.getString("lastname");
                                String email=data.getString("email");
                                String password=data.getString("password");
                                String date_ofbirth=data.getString("date_of_birth");
                                String mobile=data.getString("mobile");
                                String blood_group=data.getString("blood_group");
                                String last_donation=data.getString("last_donation");
                                String no_of_donation=data.getString("no_of_donation");
                                Intent i = new Intent(v.getContext(),HomeActivity.class);
                                i.putExtra("firstname",firstname);
                                i.putExtra("lastname",lastname);
                                i.putExtra("email",email);
                                i.putExtra("date_of_birth",date_ofbirth);
                                i.putExtra("password",password);
                                i.putExtra("mobile",mobile);
                                i.putExtra("blood",blood_group);
                                i.putExtra("last_donation",last_donation);
                                i.putExtra("no_of_donation",no_of_donation);

                                startActivity(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }
            }
            new DatabaseTask().execute();
        });

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(),RegistrationActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        phone.setText("");
        password.setText("");

        TextView error = findViewById(R.id.error_message);
        error.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onBackPressed(){
        System.exit(0);
    }
}
