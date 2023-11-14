package de.jukolai.ambiry;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // BottomNavigationMenu
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.home:

                fragment = new HomeFragment();
                break;

            case R.id.search:

                fragment = new SearchFragment();
                break;

            case R.id.library:

                fragment = new LibraryFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("HomeFragment").commit();

        return true;
    };
    NetworkChangeListener networkChangeListener = new NetworkChangeListener(MainActivity.this, this);
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Object> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        // if savedinstacestate is null, then show the HomeFragment
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        }

    }

    @Override
    protected void onStart() {

        new getDataFromDatabase().execute();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);

        super.onStart();

    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    //get the Links from Firebase
    private class getDataFromDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            firebaseDatabase = FirebaseDatabase.getInstance("https://ambiry-default-rtdb.europe-west1.firebasedatabase.app");
            databaseReference = firebaseDatabase.getReference();


            // Read from the database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    links = new ArrayList<>();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        links.add(String.valueOf(dsp.getValue())); //add links into the arraylist


                    }
                    Log.d("TAG", "Value is: " + links.get(3));
                    onPostExecute(links);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

            return null;
        }

        protected void onPostExecute(ArrayList getArrayListLinks) {

            new Parser(MainActivity.this).execute(getArrayListLinks);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
