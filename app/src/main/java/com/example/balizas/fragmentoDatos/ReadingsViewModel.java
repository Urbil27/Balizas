package com.example.balizas.fragmentoDatos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.balizas.MainActivity;
import com.example.balizas.database.Reading;

import java.util.List;

public class ReadingsViewModel extends ViewModel {
    public LiveData<List<Reading>> getReadings() {return MainActivity.db.readingDao().getAll(); }
}
