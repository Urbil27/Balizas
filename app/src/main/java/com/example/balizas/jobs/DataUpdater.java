package com.example.balizas.jobs;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.RequiresApi;
import com.example.balizas.communication.euskalmet.ApiConnection;
import com.example.balizas.database.Baliza;
import com.example.balizas.fragmentoBalizas.BalizasViewModel;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataUpdater {
    private ScheduledExecutorService scheduleTaskExecutor;

    public void start(Context context) {
        HandlerThread updaterThread = new HandlerThread("Updater thread");
        updaterThread.start();
        Handler updaterHandler = new Handler(updaterThread.getLooper());
        BalizasViewModel balizasViewModel = new BalizasViewModel();
        ApiConnection apiConnection = new ApiConnection(context);
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                updaterHandler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        List<Baliza> balizas = balizasViewModel.getNowActivated();
                        for (Baliza baliza : balizas) {
                            apiConnection.getBalizaReading(baliza.id);
                        }
                    }
                });

            }
        }, 0, 5, TimeUnit.MINUTES);
    }
}
