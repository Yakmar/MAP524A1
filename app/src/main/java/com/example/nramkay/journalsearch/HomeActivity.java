package com.example.nramkay.journalsearch;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class HomeActivity extends AppCompatActivity {

    //http://donnees.ec.gc.ca/data/managementoversight/systems/environment-canada-lists-of-peer-reviewed-scientific-and-technical-publications/environment-canada-list-of-2013-peer-reviewed-scientific-and-technical-publications/Environment_Canada_2013_scientific_and_technical_publications_list.json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/e_canada_2013.txt";
        File file = new File(destPath);

        if(!file.exists()) {
            String url = "http://donnees.ec.gc.ca/data/managementoversight/systems/environment-canada-lists-of-peer-reviewed-scientific-and-technical-publications/environment-canada-list-of-2013-peer-reviewed-scientific-and-technical-publications/Environment_Canada_2013_scientific_and_technical_publications_list.json";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading the proper data.");
            request.setTitle("Downloading Data");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "e_canada_2013.txt");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }

        Gson gson = new Gson();
        try{
            BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/e_canada_2013.txt"));
            Data obj = gson.fromJson(br, Data.class);

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
