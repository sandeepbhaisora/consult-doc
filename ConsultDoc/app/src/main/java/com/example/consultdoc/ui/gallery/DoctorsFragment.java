package com.example.consultdoc.ui.gallery;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.consultdoc.MainActivity;
import com.example.consultdoc.R;
import com.example.consultdoc.ui.home.HomeFragment;

public class DoctorsFragment extends Fragment {

    private DoctorViewModel doctorViewModel;

    Dialog consultDialogue;

    TextView symptom ;

    CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doctorViewModel =
                ViewModelProviders.of(this).get(DoctorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_consult, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        doctorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        symptom =root.findViewById(R.id.symptom);
        if(MainActivity.getSymptom() != null){
            String symptoms = "(" + MainActivity.getSymptom() + ")";
            symptom.setText(symptoms);
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                MainActivity.setSymptom(null);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment NAME = new HomeFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();


            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        cardView = root.findViewById(R.id.doctor_consult);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultDialogue = new Dialog(getContext());
                TextView textClose;
                Button consultNow;
                consultDialogue.setContentView(R.layout.consult_pop_up);
                textClose = consultDialogue.findViewById(R.id.close_pop_up);
                consultNow = consultDialogue.findViewById(R.id.consult_now_button);
                textClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        consultDialogue.dismiss();
                    }
                });
                consultNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getContext(),"Appointment fixed",Toast.LENGTH_LONG);
                        toast.show();
                        consultDialogue.dismiss();
                    }
                });
                consultDialogue.show();
            }
        });


        return root;
    }
}