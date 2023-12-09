package com.example.androidrssreaderjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidrssreaderjava.model.Rss;
import com.example.androidrssreaderjava.repository.MainRepository;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;

    public MainViewModel(){
        mainRepository = new MainRepository();
    }

    public LiveData<Rss> getRSS() {
        return mainRepository.getRssMutableLiveData();
    }

    public  LiveData<String> getError(){
        return mainRepository.getError();
    }

    public void clear(){
         mainRepository.clear();
    }
}
