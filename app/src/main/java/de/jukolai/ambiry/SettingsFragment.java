package de.jukolai.ambiry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.preference, rootKey);


        findPreference("LogoutButton").setOnPreferenceClickListener(preference -> {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
            getActivity().finish();

            return false;
        });

        findPreference("FeedbackButton").setOnPreferenceClickListener(preference -> {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:feedback@janoroid.de"));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));

            return false;
        });

    }


}
