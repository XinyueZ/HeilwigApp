package wolf.de.justtestingsomestuff.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.Calendar;

import wolf.de.justtestingsomestuff.R;
import wolf.de.justtestingsomestuff.web.ExWebViewClient;


public class Essen_Fragment extends Fragment {

    Calendar calender = Calendar.getInstance();
    private WebView webView;
    private Bundle webViewBundle;
    String kw = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_web,
                container, false);

        kw = "kw" + calender.get(Calendar.WEEK_OF_YEAR);

        webView = (WebView) ll.findViewById(R.id.webView1);
        webView.setWebViewClient(new ExWebViewClient());

        if (webViewBundle == null) {
            webView.loadUrl("http://heilwig.de/images/hwg/mammas/" + kw + ".png");
        } else {
            webView.restoreState(webViewBundle);
        }

        return ll;

    }

    @Override
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }






}



