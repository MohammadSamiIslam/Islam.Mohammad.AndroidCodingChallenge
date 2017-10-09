package com.example.msi.islammohammadandroidcodingchallenge;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ItemArrayAdapter adapterBooks;
    ArrayList<BookInfo> booksList;
    private Context appContext;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner sp = (Spinner) findViewById(R.id.spinner_load);
        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        listView = (ListView) findViewById(R.id.listView1);
        new MainActivity.getBooks(this).execute("");
    }

    private class getBooks extends AsyncTask<String, Void, String> {
        String page = "";
        private Context mContext;
        JSONArray resultSet = null;

        public getBooks(Context context){
            mContext = context;
        }

        public Context getmContext() {
            return mContext;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... arg0) {
            // Initialize a new RequestQueue instance
            RequestQueue requestQueue = Volley.newRequestQueue(appContext);
            String url = "http://de-coding-test.s3.amazonaws.com/books.json";
            JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            int len = response.length();
                            booksList = new ArrayList<BookInfo>(len);
                            try{
                                for(int i=0;i<len;i++){
                                    JSONObject book = response.getJSONObject(i);
                                    String title = book.getString("title");
                                    String bookImage = book.getString("imageURL");
                                    booksList.add(i, new BookInfo(title, bookImage));
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                            adapterBooks = new ItemArrayAdapter(MainActivity.this, R.layout.itemarrayadapter, booksList);
                            listView.setAdapter(adapterBooks);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            error.printStackTrace();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            requestQueue.add(jsonArrayRequest);

            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }

    public class ItemArrayAdapter extends ArrayAdapter<BookInfo> {
        public ItemArrayAdapter(Context context, int textViewResourceId, ArrayList<BookInfo> books) {
            super(context, textViewResourceId, books);
            this.context = context;
            this.values = books;
            // TODO Auto-generated constructor stub
        }

        public int getCount() {
            return values.size();
        }

        public long getItemId(int position) {
            return position;
        }

        private final Context context;
        private final List<BookInfo> values;

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View rowView = inflater.inflate(
                    R.layout.itemarrayadapter, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            WebView webView = (WebView) rowView.findViewById(R.id.webview);

            textView.setText(values.get(position).getTitle()
                    .toString());
            webView.loadUrl(values.get(position).getBookImage()
                    .toString());

            return rowView;
        }
    }
}
