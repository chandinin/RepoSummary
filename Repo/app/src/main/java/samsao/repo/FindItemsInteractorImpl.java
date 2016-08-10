package samsao.repo;

import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.util.Log;
import cz.msebera.android.httpclient.Header;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by cnagendra on 8/9/2016.
 */
public class FindItemsInteractorImpl implements FindItemsInteractor{

    public static final String REST_SERVICE_URL = "https://api.github.com/users/samsao/repos";
    ArrayList<RepoSummary> repoSummary;



    @Override public void findItems(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                new RetrieveSamsaoRepoTask().execute();
                listener.onFinished(repoSummary);
            }
        }, 2000);
    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9",
                "Item 10"
        );
    }

    public class RetrieveSamsaoRepoTask extends AsyncTask<Void, Void, ArrayList<RepoSummary>>
    {
        private Exception exception;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<RepoSummary> doInBackground(Void... arg0) {
            // Do some validation here
        String finalJson;
            try {
                URL url = new URL("https://api.github.com/users/samsao/repos");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                    try {
                        InputStream inputstream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        finalJson = stringBuilder.toString();
                        Type listType = new TypeToken<ArrayList<RepoSummary>>(){}.getType();
                        ArrayList<RepoSummary> summary = new GsonBuilder().create().fromJson(finalJson, listType);



/*                        Gson gson = new Gson();
                        RepoSummary summary = gson.fromJson(finalJson, RepoSummary.class);*/

           /*             JSONObject jsonObject = new JSONObject(finalJson);
                        JsonArray jsonArray = jsonObject.
                                String fullName = jsonObject.getString("full_name");
                        String lastUpdate = jsonObject.getString("updated_at");
                        String language = jsonObject.getString("language");
                        String forkCount = jsonObject.getString("forks_count");
                        String defaultBranch = jsonObject.getString("default_branch");*/

                        return summary;
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<RepoSummary> response) {

        if(response != null)
        {
            repoSummary.addAll(response);
        }

        }
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        }
        catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }

        return null;

    }
/*
    private ArrayList<RepoSummary> getRepoSummary(){
        ArrayList<RepoSummary> repoSummary = new ArrayList<RepoSummary>();

        HttpGet getRequest = new HttpGet(url);


        return repoSummary;
    }
*/


}
