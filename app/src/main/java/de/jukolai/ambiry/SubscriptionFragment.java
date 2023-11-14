package de.jukolai.ambiry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class SubscriptionFragment extends Fragment {

    RecyclerView recyclerview_Subscription;
    TextView textviewEmpty;
    ImageView imageViewEmpty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);

        recyclerview_Subscription = view.findViewById(R.id.recyclerview_Subscription);
        textviewEmpty = view.findViewById(R.id.empty_view);
        imageViewEmpty = view.findViewById(R.id.imageView_empty);
        recyclerview_Subscription = view.findViewById(R.id.recyclerview_Subscription);


        return view;
    }
}