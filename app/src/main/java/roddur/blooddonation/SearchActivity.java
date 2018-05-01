package roddur.blooddonation;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        LinearLayout results=findViewById(R.id.results);

        Spinner spinner = findViewById(R.id.blood_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button searchDonors=findViewById(R.id.search_btn);
        searchDonors.setOnClickListener(view-> {
            results.removeAllViews();
            View v = new View(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    5
            ));
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));
            results.addView(v);

            class DatabaseTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    String blood = spinner.getSelectedItem().toString().replaceAll(Pattern.quote("+"),"1")
                            .replaceAll(Pattern.quote("-"),"0");
                    try {
                        String link = "http://10.0.2.2/BloodDomain/search.php?blood=" + blood;

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
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject data = array.getJSONObject(i);

                                    String firstname = data.getString("firstname");
                                    String lastname = data.getString("lastname");
                                    String mobile = data.getString("mobile");
                                    //String blood_group=data.getString("blood_group");
                                    //String no_of_donation=data.getString("no_of_donation");

                                    runOnUiThread(() -> {
                                        LinearLayout results = findViewById(R.id.results);
                                        TextView tv = new TextView(SearchActivity.this);
                                        tv.setText(firstname + " " + lastname);
                                        results.addView(tv);
                                        TextView tv2 = new TextView(SearchActivity.this);
                                        tv2.setText(mobile);
                                        results.addView(tv2);
                                        View v = new View(SearchActivity.this);
                                        v.setLayoutParams(new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                5
                                        ));
                                        v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                                        results.addView(v);
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
        });

    }
}

