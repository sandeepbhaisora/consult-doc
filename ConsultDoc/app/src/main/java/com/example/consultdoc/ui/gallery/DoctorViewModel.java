package com.example.consultdoc.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoctorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DoctorViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Consult fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}