package wolf.de.justtestingsomestuff.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import wolf.de.justtestingsomestuff.R;
import wolf.de.justtestingsomestuff.fragments.Einstellungen_Fragment;
import wolf.de.justtestingsomestuff.fragments.Essen_Fragment;
import wolf.de.justtestingsomestuff.fragments.Exams_Fragment;
import wolf.de.justtestingsomestuff.fragments.Ha_Fragment;
import wolf.de.justtestingsomestuff.fragments.Vplan_Fragment;
import wolf.de.justtestingsomestuff.fragments.Web_Fragment;

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

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String wasdanndasteht;
    private String aversion = "0.1.9-2";
    private String data = "{'id':'ID','method':'authenticate','params':{'user':'HWG', 'password':'WMS4', 'client':'HeilwigAppBeta0168'},'jsonrpc':'2.0'}";
    private String wfragment;
    private static final String TAG = "MainActivity";
    private static final String URL = "http://www.yourdomain.com:80";
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);


        // Drawer, navigation-view
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem ) {
                menuItem.setChecked( true );
                mDrawerLayout.closeDrawer(GravityCompat.START);
                setTitle(menuItem.getTitle());
                selectItem(menuItem.getItemId());
                return true;
            }
        } );
        mNavigationView.getLayoutParams().width = calculateDrawerWidth();


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


    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case R.id.drawer_website:
                fragment = new Web_Fragment();
                wfragment = "web";
                break;
            case  R.id.drawer_plan:
                fragment = new Vplan_Fragment();
                wfragment = "vplan";
               // fragment = new testlogin_Fragment();
                break;
            case  R.id.drawer_tasks_system:
                fragment = new Ha_Fragment();
                wfragment = "ha";
                break;
            case  R.id.drawer_class_jobs:
                fragment = new Exams_Fragment();
                wfragment = "exams";
                break;
            case  R.id.drawer_canteen:
                fragment = new Essen_Fragment();
                wfragment = "essen";
                break;
            case  R.id.drawer_settings:
                fragment = new Einstellungen_Fragment();
                break;

        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }

        final String PREFS_NAME = "hwgprefs";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("firststart", true)) {

            new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                    .setTitle(R.string.hello_title)
                    .setMessage(getString(R.string.hello_message) + aversion + ".")
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

    @Override
    public void onBackPressed() {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavigationView);
        if(drawerOpen) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }
}