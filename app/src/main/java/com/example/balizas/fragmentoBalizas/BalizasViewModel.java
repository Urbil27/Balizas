package com.example.balizas.fragmentoBalizas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.balizas.MainActivity;
import com.example.balizas.database.AppDatabase;
import com.example.balizas.database.Baliza;

import java.util.List;

public class BalizasViewModel extends ViewModel {
    public LiveData<List<Baliza>> getBalizas() {
        return MainActivity.db.balizaDao().getAll();
    }
    public LiveData<List<Baliza>> getActivatedBalizas() {
        return MainActivity.db.balizaDao().getActivated();
    }
}
