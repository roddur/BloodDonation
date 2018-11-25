package roddur.blooddonation;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.regex.Pattern;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent i=getIntent();
        String mobile=i.getExtras().getString("mobile");

        class DatabaseTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String link = "http://10.0.2.2/BloodDomain/history.php?mobile=" + mobile;

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

                    if (sb.toString().equals("")) {
                        runOnUiThread(() -> {
                            //TextView error = findViewById(R.id.error_message);
                            //error.setVisibility(View.VISIBLE);
                        });

                    } else {
                        try {
                            JSONArray array = new JSONArray(sb.toString());
                            runOnUiThread(()->{
                                LinearLayout results2 = findViewById(R.id.results2);
                                TextView ttv = new TextView(HistoryActivity.this);
                                ttv.setText("Donated " + array.length()+" time/s");
                                ttv.setTextColor(Color.WHITE);
                                results2.addView(ttv);
                            });
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject data = array.getJSONObject(i);

                                String date = data.getString("date");

                                runOnUiThread(() -> {
                                    LinearLayout results2 = findViewById(R.id.results2);
                                    TextView tv = new TextView(HistoryActivity.this);
                                    tv.setText("Donated on "+date);
                                    tv.setTextColor(Color.WHITE);
                                    results2.addView(tv);
                                    View v = new View(HistoryActivity.this);
                                    v.setLayoutParams(new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            5
                                    ));
                                    v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                                    results2.addView(v);
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        new DatabaseTask().execute();
    }
}
