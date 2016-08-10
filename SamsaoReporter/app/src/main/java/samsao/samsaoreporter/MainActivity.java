package samsao.samsaoreporter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RepoData> repoList;
    private ReportAdapter adapter;
    private ProgressDialog progressDialog;
    private int prevItem = 0;
    private boolean scrollingUp = true;
    private long timeLimit = 0;
    private boolean endOfRecords = false;
    private String repoID;
    private int startRecord = 0;
    private final int mMaxRecords = 20;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RepoListView listView = (RepoListView) findViewById(R.id.repo_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        startRecord = 0;
        repoList = new ArrayList<>();
        repoList.clear();
        adapter = new ReportAdapter(this, repoList, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(
                new AbsListView.OnScrollListener()
                {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState)
                    {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount)
                    {
                        if(prevItem != firstVisibleItem)
                        {
                            if(prevItem < firstVisibleItem)
                            {
                                scrollingUp = false;
                            }
                            else
                            {
                                scrollingUp = true;
                            }
                            prevItem = firstVisibleItem;
                        }
                    }
                });

        listView.setOnOverScrollListener(
                new RepoListViewOverScrollListener()
                {
                    @Override
                    public void onOverScrolled(int scrollX, int scrollY,
                                               boolean clampedX, boolean clampedY)
                    {
                        if(!scrollingUp && (System.currentTimeMillis() - timeLimit > 1000)
                                && !endOfRecords)
                        {
                            timeLimit = System.currentTimeMillis();
                            new RetrieveReportTask().execute();
                        }
                    }
                });

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position,
                                            long id)
                    {
                        // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_box);

                        // set the custom dialog components - text and button
                        TextView language = (TextView) dialog.findViewById(R.id.language);
                        language.setText("Language: " +repoList.get(position).getLanguage());

                        TextView defaultBranch = (TextView) dialog.findViewById(R.id.default_branch);
                        defaultBranch.setText("Default Branch: " +repoList.get(position).getDefaultBranch());

                        TextView forkCount = (TextView) dialog.findViewById(R.id.fork_count);
                        forkCount.setText("Fork Count: " +repoList.get(position).getForkCount());

                        Button dialogButton = (Button) dialog.findViewById(R.id.button_ok);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

        //retrieve the github repo info in the background thread
        new RetrieveReportTask().execute();
    }


    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    public class RetrieveReportTask extends AsyncTask<Void, Void, ArrayList<RepoData>>
    {
        ArrayList<RepoData> result = new ArrayList<>();

        @Override
        protected void onPreExecute()
        {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<RepoData> doInBackground(Void... arg0)
        {
            result = GetSamsaoRepoList();
            return result;
        }

        private ArrayList<RepoData> GetSamsaoRepoList()
        {
            try
            {
                String finalJson;
                try {
                    //url to get all the public repositories on github in json format
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
                        Type listType = new TypeToken<ArrayList<RepoData>>(){}.getType();
                        //Convert from json to java object
                        result = new GsonBuilder().create().fromJson(finalJson, listType);
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
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            return result;
        }

        protected void onPostExecute(ArrayList<RepoData> result)
        {
            if(result != null)
            {
                if(result != null)
                {
                    startRecord += mMaxRecords;
                    repoList.addAll(result);
                    adapter.notifyDataSetChanged();
                    endOfRecords = true;
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.no_record,Toast.LENGTH_SHORT).show();
                }
            }

            if(progressDialog != null)
            {
                progressDialog.dismiss();
            }
        }
    }
}
