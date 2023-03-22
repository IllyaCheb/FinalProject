package com.example.animefinalappproject;

// Illya Chebotaryov

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private EditText etRequest;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<AnimeModel> animeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRequest = findViewById(R.id.et_request);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String request = etRequest.getText().toString();
                if (!request.isEmpty()) {
                    new FetchAnimeDataTask().execute(request);
                    hideKeyboard();
                }
            }
        });

        animeList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter(animeList, this));


    }

    public void launchSecondActivity(AnimeModel anime) {
        Intent intent = new Intent(this, ModelDetailPage.class);
        intent.putExtra("title", anime.getTitle());
        intent.putExtra("synopsis", anime.getSynopsis());
        intent.putExtra("image_url", anime.getImageUrl());
        startActivity(intent);
    }



    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etRequest.getWindowToken(), 0);
    }
    // This is the main method which makes the up function by callin the API and retrieving the info
    private class FetchAnimeDataTask extends AsyncTask<String, Void, ArrayList<AnimeModel>> {

        //Progress Bar
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        //Main Code which makes the call to API and stores JSON objects
        @Override
        protected ArrayList<AnimeModel> doInBackground(String... params) {

            String searchQuery = params[0];
            String apiUrl = "https://api.jikan.moe/v4/anime?q=" + searchQuery + "&sfw"; // the api format for Jikan
            ArrayList<AnimeModel> animeList = new ArrayList<>();

            try { //connect to online
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                //Being able to read the format that was return through stringBuilder
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                //Log.d("Test", sb.toString());

                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray animeArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < animeArray.length(); i++) { //Loop through the array of data returned
                    JSONObject anime = animeArray.getJSONObject(i);
                    AnimeModel animeModel = new AnimeModel("random", "random", "random");//intialize each object with AnimeModel
                    animeModel.setTitle(anime.getString("title_english")); // This gets the title
                    animeModel.setSynopsis(anime.getString("synopsis")); // This gets synopsis
                    JSONObject images = anime.getJSONObject("images");// in order to ge to the specific url need to go though images and jpg
                    JSONObject jpg = images.getJSONObject("jpg");
                    String imageUrl = jpg.getString("image_url");//this gets the image
                    animeModel.setImageUrl(imageUrl);
                    animeList.add(animeModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return animeList;
        }

        //After getting all the info either we will get our scrolling list of anime or an error
        @Override
        protected void onPostExecute(ArrayList<AnimeModel> animeList) {
            progressBar.setVisibility(View.GONE);
            if (animeList.isEmpty()) {
                Toast.makeText(MainActivity.this, "No results found.", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.this.animeList.clear();
                MainActivity.this.animeList.addAll(animeList);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
