package wolf.de.justtestingsomestuff.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import wolf.de.justtestingsomestuff.R;
import wolf.de.justtestingsomestuff.adapter.DrawerAdapter;
import wolf.de.justtestingsomestuff.fragments.Essen_Fragment;
import wolf.de.justtestingsomestuff.fragments.Exams_Fragment;
import wolf.de.justtestingsomestuff.fragments.Ha_Fragment;
import wolf.de.justtestingsomestuff.fragments.Vplan_Fragment;
import wolf.de.justtestingsomestuff.fragments.Web_Fragment;
import wolf.de.justtestingsomestuff.ui.Items;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;*/
//import org.apache.http.client.HttpClient;
/*import org.apache.http.HttpEntity;
import org.apache.http.entity.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.client.methods.HttpPost;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
*/



public class MainActivity extends ActionBarActivity {

    private String[] mDrawerTitles;
    private TypedArray mDrawerIcons;
    private ArrayList<Items> drawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String wasdanndasteht;
    private String aversion = "0.1.9-2";
    private String data = "{'id':'ID','method':'authenticate','params':{'user':'HWG', 'password':'WMS4', 'client':'HeilwigAppBeta0168'},'jsonrpc':'2.0'}";
    private String wfragment;
    private static final String TAG = "MainActivity";
    private static final String URL = "http://www.yourdomain.com:80";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);

        mDrawerTitles = getResources().getStringArray(R.array.drawer_titles);
        mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        drawerItems = new ArrayList<Items>();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        for (int i = 0; i < mDrawerTitles.length; i++) {
            drawerItems.add(new Items(mDrawerTitles[i], mDrawerIcons.getResourceId(i, -(i + 1))));
        }



        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), drawerItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header,
                mDrawerList, false);

        final ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer,
                mDrawerList, false);

        // Give your Toolbar a subtitle!
        /* mToolbar.setSubtitle("Subtitle"); */

        mDrawerList.addHeaderView(header, null, true); // true = clickable
        mDrawerList.addFooterView(footer, null, true); // true = clickable

        //Set width of drawer
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        lp.width = calculateDrawerWidth();
        mDrawerList.setLayoutParams(lp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                //Account
                //fragment= new Start_Fragment();
                //fragment = new Web_Fragment();
                break;
            case 1:
                fragment = new Web_Fragment();
                wfragment = "web";
                break;
            case 2:
                fragment = new Vplan_Fragment();
                wfragment = "vplan";
               // fragment = new testlogin_Fragment();
                break;
            case 3:
                fragment = new Ha_Fragment();
                wfragment = "ha";
                break;
            case 4:
                fragment = new Exams_Fragment();
                wfragment = "exams";
                break;
            case 5:
                fragment = new Essen_Fragment();
                wfragment = "essen";
                break;
            /*case 6:
                fragment = new Einstellungen_Fragment();
                break;*/

        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerTitles[position - 1]);
        mDrawerLayout.closeDrawer(mDrawerList);

        updateView(position, position, true);

        final String PREFS_NAME = "hwgprefs";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("firststart", true)) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.hello_title)
                    .setMessage(getString(R.string.hello_message) + aversion)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();


            // record the fact that the app has been started at least once
            settings.edit().putBoolean("firststart", false).commit();
        }


        if (settings.getBoolean("firststart", false)) {

            //Hier wird die Login Request an den Server gemacht



          /*  String apiUrl = "https://arche.webuntis.com/WebUntis/jsonrpc.do?school=SCHULNAME"; //Changes here
            JSONObject jsonParams = new JSONObject();
            JSONObject params1 = new JSONObject();
            String jsonstring;


            HttpClient client = new DefaultHttpClient();

            // Prepare a request object
            HttpPost post = new HttpPost(apiUrl);
            post.setHeader("Content-type", "application/json");
            try {
                params1.put("user", "HWG");
                params1.put("password", "WMS4");
                params1.put("client", "HeilwigApp"); //You can change the name

                jsonParams.put("id", "ID");
                jsonParams.put("method", "authenticate");
                jsonParams.put("params", params1);
                jsonParams.put("jsonrpc", "2.0");

                StringEntity se = new StringEntity(jsonParams.toString());
                jsonstring = jsonParams.toString();
                post.setHeader("Content-length",""+se.getContentLength());
                post.setEntity(se);

            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // Execute the request
            try {
                HttpResponse response = client.execute(post);
                Log.d("log_response: ", response.getStatusLine().toString());

                // Get hold the response entity
                HttpEntity entity = response.getEntity();

                // if the response does not enclose the entity, there is no need
                // to worry about it

                if (entity != null) {
                    // a simple JSON Response read
                    InputStream instream = entity.getContent();
                    String result;

                    // convert content of response to bufferedreader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            instream.close();
                        } catch (IOException exp) {
                            exp.printStackTrace();
                        }
                    }
                    result = sb.toString();
                    Log.d("Result of the Request: ", result);
                    tresp.setText(result);
                }


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
*/
            //return "OK";
            settings.edit().putBoolean("firststart", false).commit();
        }








    }

    /*public void postData() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://stundenplan.hamburg.de/WebUntis/jsonrpc.do?school=hh5888");
        String result = "";

        switch (wfragment) {
            case "web":

            case "vplan":

            case "ha":

            case "exams":

            case "essen":

        }
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://example.com/data/feedmepostrequests.php");

        // set values you'd like to send
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("name", "test"));
        pairs.add(new BasicNameValuePair("comment", "comment2"));
        pairs.add(new BasicNameValuePair("article_id", "articleID2"));

        // you probably won't need these two lines below, but I have to work with web service
        // which has very annoying caching issues, so I usually pass some random argument
        // to avoid possible failure
        double r = Math.random();
        pairs.add(new BasicNameValuePair("rand", "r"+r));

        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            // set ResponseHandler to handle String responses
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(post, responseHandler);
            Log.v("HttpPost", "Response: " + response);
            if (response.contains("SUCCESS")){
                // express your joy here!
            } else {
                // pop a sad Toast message here...
            }
        } catch (Exception e) {}
    }*/

    @Override
    public void setTitle(CharSequence title) {
        //mTitle = "HeilwigApp";
        getSupportActionBar().setTitle("HeilwigApp");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int calculateDrawerWidth() {
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        Display display = getWindowManager().getDefaultDisplay();
        int width;
        int height;
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();  // deprecated
            height = display.getHeight();  // deprecated
        }
        return width - actionBarHeight;
    }

    private void updateView(int position, int counter, boolean visible) {

        View v = mDrawerList.getChildAt(position -
                mDrawerList.getFirstVisiblePosition());
        TextView someText = (TextView) v.findViewById(R.id.item_new);
        Resources res = getResources();
        String articlesFound = "";

        switch (position) {
            case 1:
               /* articlesFound = res.getQuantityString(R.plurals.numberOfNewArticles, counter, counter);
                someText.setBackgroundResource(R.drawable.new_apps);
                break;*/
            case 2:
               /* articlesFound = res.getQuantityString(R.plurals.numberOfNewArticles, counter, counter);
                someText.setBackgroundResource(R.drawable.new_sales);
                break;*/
            case 3:
                /*articlesFound = res.getQuantityString(R.plurals.numberOfNewArticles, counter, counter);
                someText.setBackgroundResource(R.drawable.new_blog);
                break;
              */
            case 4:
                /*articlesFound = res.getQuantityString(R.plurals.numberOfNewArticles, counter, counter);
                someText.setBackgroundResource(R.drawable.new_bookmark);
                break;*/
            case 5:
                /*articlesFound = res.getQuantityString(R.plurals.numberOfNewArticles, counter, counter);
                someText.setBackgroundResource(R.drawable.new_community);
                break;*/
        }

        someText.setText(articlesFound);
        if (visible) someText.setVisibility(View.VISIBLE);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }



}