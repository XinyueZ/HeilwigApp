package wolf.de.justtestingsomestuff.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.content.SharedPreferences;


import java.util.Calendar;

import wolf.de.justtestingsomestuff.R;



public class Einstellungen_Fragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "hwgprefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_web,
                container, false);



        return ll;

    }

    @Override
    public void onPause() {
        super.onPause();

    }


   /* public void clear()
    {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();
    }*/



}



