package de.jukolai.ambiry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    TextView textviewGreeting;
    ImageButton imageButtonSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textviewGreeting = view.findViewById(R.id.textViewWelcome);
        imageButtonSettings = view.findViewById(R.id.imageButtonSettings);
        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());


        imageButtonSettings.setOnClickListener(v -> {
            SettingsFragment settingFragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, settingFragment);
            fragmentTransaction.commit();

        });


        setWelcomeText();



        return view;
    }

    private void setWelcomeText() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {

            textviewGreeting.setText(R.string.goodMorning);

        } else if (timeOfDay >= 12 && timeOfDay < 18) {

            textviewGreeting.setText(R.string.goodDay);

        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            textviewGreeting.setText(R.string.goodEvening);

        }

    }

}