package com.example.consultdoc.ui.home;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultdoc.MainActivity;
import com.example.consultdoc.R;
import com.example.consultdoc.docrecycle.RecyclerAdapter;
import com.example.consultdoc.docrecycle.RecyclerHelper;
import com.example.consultdoc.startup.LoginActivity;
import com.example.consultdoc.ui.gallery.DoctorsFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    LocationManager locationManager;

    ImageView imageView;


    TextView locationText;

    RecyclerView recyclerView;

    TextInputLayout searchLayout;

    RecyclerAdapter adapter;

    AutoCompleteTextView searchBox ;

    Context mContext;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.setSymptom(null);



        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        locationText = root.findViewById(R.id.location);



        locationText.setText(MainActivity.getLocalityValue());

        recyclerView = root.findViewById(R.id.doctors_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        recyclerView.setHasFixedSize(true);
        ArrayList<RecyclerHelper> recyclerHelpers = new ArrayList<>();

        recyclerHelpers.add(new RecyclerHelper(R.drawable.man,"Dr S."," dummy description"));
        recyclerHelpers.add(new RecyclerHelper(R.drawable.man,"Dr W."," dummy description"));
        recyclerHelpers.add(new RecyclerHelper(R.drawable.man,"Dr M."," dummy description"));
        recyclerHelpers.add(new RecyclerHelper(R.drawable.man,"Dr K."," dummy description"));

        RecyclerAdapter recyclerAdapter= new RecyclerAdapter(recyclerHelpers);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        searchLayout =root.findViewById(R.id.search_laout);

        searchBox = root.findViewById(R.id.search);

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_DONE){
                    if(textView.getText().toString().trim().equalsIgnoreCase("fever")){
                        MainActivity.setSymptom(textView.getText().toString());
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        DoctorsFragment NAME = new DoctorsFragment();
                        fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                        fragmentTransaction.commit();


                    }
                    else {
                        Toast toast = Toast.makeText(getContext(),"not available yet,Search for fever instead", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                return false;
            }
        });

//        String[] items = {
//                "fever",
//                "head ache"
//        };
//
//
//        if(mContext != null) {
//            ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.search_item,
//                    items
//            );
//
//            searchBox.setAdapter(arrayAdapter);
//
//        }

        imageView = root.findViewById(R.id.doc_icon_home);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DoctorsFragment NAME = new DoctorsFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();     }
        });


        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext= context;
    }


}

