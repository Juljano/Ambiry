package de.jukolai.ambiry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


public class NetworkChangeListener extends BroadcastReceiver {
    MainActivity mainActivity;
    ConstraintLayout constrainLayoutHome;
    BottomNavigationView bottomNavigationView;

    public NetworkChangeListener(Context context, Activity activity) {

        this.mainActivity = (MainActivity) activity;

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        constrainLayoutHome = mainActivity.findViewById(R.id.constrainLayoutHome);

        bottomNavigationView = mainActivity.findViewById(R.id.bottom_navigation);


        if (!ConnectionManager.isInternetAvailable(context)) {

            Snackbar.make(constrainLayoutHome, R.string.Offline, Snackbar.LENGTH_INDEFINITE).setAnchorView(bottomNavigationView).setBackgroundTint(context.getResources().getColor(R.color.blue)).setActionTextColor(mainActivity.getResources().getColor(R.color.white)).setAction("nochmal versuchen", new SnackListener()).show();


        }

    }

    public class SnackListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent refresh = new Intent(mainActivity, MainActivity.class);
            mainActivity.startActivity(refresh);
            mainActivity.finish();
        }
    }
}
