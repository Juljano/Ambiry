package de.jukolai.ambiry;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageButton createPlaylistImageButton;
    RecyclerView recyclerView;
    TextView textviewEmpty;
    ImageView imageViewEmpty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        createPlaylistImageButton = view.findViewById(R.id.imageButtonCreatePlaylist);
        recyclerView = view.findViewById(R.id.recyclerviewPlaylist);
        textviewEmpty = view.findViewById(R.id.empty_view);
        imageViewEmpty = view.findViewById(R.id.imageView_empty);


        // Recylerview
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        createPlaylistImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPlaylist();
            }
        });


        return view;
    }

    private void createPlaylist() {

        View view = getLayoutInflater().inflate(R.layout.dialogplaylistlayout, null);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);
        dialog.show();

        Button cancelButton;
        Button doneButton;
        EditText editTextName;
        Spinner spinner;


        cancelButton = dialog.findViewById(R.id.cancelButton);
        doneButton = dialog.findViewById(R.id.DoneButton);
        spinner = dialog.findViewById(R.id.spinner);


        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<>();
        categories.add("Öffentlich");
        categories.add("Privat");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        doneButton.setOnClickListener(v -> Toast.makeText(getActivity(), "Erstellt!", Toast.LENGTH_SHORT).show());


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(getActivity(), position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}