package wolf.de.justtestingsomestuff.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
/*import android.util.Log;

import org.apache.http.HttpEntity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;*/


import wolf.de.justtestingsomestuff.R;



public class Vplan_Fragment extends Fragment {


    private WebView webView;
    private Bundle webViewBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_web,
                container, false);

        webView = (WebView) ll.findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClient());

        if (webViewBundle == null) {
            webView.loadUrl("http://littleprogramms.pfweb.eu/stundenplan/get.php?sitename=Vertretungsplan&client=android");
        } else {
            webView.restoreState(webViewBundle);
        }

       // reqlogin();

        return ll;

    }

    /*void reqlogin() {

    String apiUrl = "https://arche.webuntis.com/WebUntis/jsonrpc.do?school=SCHULNAME"; //Changes here
    JSONObject jsonParams = new JSONObject();
    JSONObject params1 = new JSONObject();
    String jsonstring;


    HttpClient client = new DefaultHttpClient();

    // Prepare a request object
    HttpPost post = new HttpPost(apiUrl);
    post.setHeader("Content-type","application/json");
    try

    {
        params1.put("user", "HWG");
        params1.put("password", "WMS4");
        params1.put("client", "HeilwigApp"); //You can change the name

        jsonParams.put("id", "ID");
        jsonParams.put("method", "authenticate");
        jsonParams.put("params", params1);
        jsonParams.put("jsonrpc", "2.0");

        StringEntity se = new StringEntity(jsonParams.toString());
        jsonstring = jsonParams.toString();
        post.setHeader("Content-length", "" + se.getContentLength());
        post.setEntity(se);

    }

    catch(
    JSONException e1
    )

    {
        e1.printStackTrace();
    }

    catch(
    UnsupportedEncodingException e
    )

    {
        e.printStackTrace();
    }
    // Execute the request
    try

    {
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
          /*  new MaterialDialog.Builder(this)
                    .title("HeilwigApp - Willkommen")
                    .content(result)
                    .positiveText("Ok")
                            //.negativeText("Nein")
                            //.theme(Theme.DARK)
                            // .neutralText("More info")
                    .show();*/
       // }

/*
    }

    catch(
    ClientProtocolException e
    )

    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    catch(
    IOException e
    )

    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

}
*/
    @Override
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }






}



