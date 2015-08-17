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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Article> data = new ArrayList<>();
    List<String> jsonString = new ArrayList<>();
    List<String> analyticTitles = new ArrayList<>();
    List<String> collectiveTitles = new ArrayList<>();
    List<String> subordinateTitles = new ArrayList<>();
    List<String> authors = new ArrayList<>();
    List<String> publishers = new ArrayList<>();
    List<String> documentTypes = new ArrayList<>();
    File file;

    //http://donnees.ec.gc.ca/data/managementoversight/systems/environment-canada-lists-of-peer-reviewed-scientific-and-technical-publications/environment-canada-list-of-2013-peer-reviewed-scientific-and-technical-publications/Environment_Canada_2013_scientific_and_technical_publications_list.json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/e_canada_2013.txt";
        file = new File(destPath);

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
        InitializeData();
        Gson gson = new Gson();
        for(String s : jsonString){
            data.add(gson.fromJson(s, Article.class));
        }
        for(Article a : data){
            analyticTitles.add(a.getTitleAnalyticTitreAnalytique());
            collectiveTitles.add(a.getTitleCollectiveTitreCollectif());
            subordinateTitles.add(a.getTitleSubordinateTitreSubalterne());
            authors.add(a.getAuthorsAuteurs());
            publishers.add(a.getPublisherMaisonDDition());
            documentTypes.add(a.getDocumentTypeTypeDeDocument());
        }
        Log.i("home", data.get(2).getAuthorsAuteurs());

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

    private void InitializeData(){
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"1-2\", \"Title, Analytic / Titre, Analytique\": \"\\\"The Rainmakers\\\": the Meteorological Service of Canada and post-war weather modification research\", \"Page end / Derni\\u00e8re Page\": \"37\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"5\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.7202/1013979ar\", \"Title, Collective / Titre, Collectif\": \"Scientia Canadensis\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"35\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Wallace, M.L.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"16-year simulation of arctic black carbon: Transport, source contribution, and sensitivity analysis on deposition\", \"Page end / Derni\\u00e8re Page\": \"964\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"943\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1029/2012JD017774\", \"Title, Collective / Titre, Collectif\": \"Journal of Geophysical Research: Atmospheres\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"118\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Sharma S., Ishizawa M., Chan D., Lavoue D., Andrews E., Eleftheriadis K., Maksyutov S.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"3d flow and sediment dynamics in a laboratory channel bend with and without stream barbs\", \"Page end / Derni\\u00e8re Page\": \"166\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"154\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1061/(ASCE)HY.1943-7900.0000655\", \"Title, Collective / Titre, Collectif\": \"Journal of Hydraulic Engineering\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"139\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Jamieson E.C., Rennie C.D., Townsend R.D.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"10\", \"Title, Analytic / Titre, Analytique\": \"A 4-year study of avian influenza virus prevalence and subtype diversity in ducks of Newfoundland, Canada\", \"Page end / Derni\\u00e8re Page\": \"708\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"701\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1139/cjm-2013-0507\", \"Title, Collective / Titre, Collectif\": \"Canadian Journal of Microbiology\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"59\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Huang Y., Wille M., Dobbin A., Robertson G.J., Ryan P., Ojkic D., Whitney H., Lang A.S.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"9\", \"Title, Analytic / Titre, Analytique\": \"A Canadian application of one health: Integration of salmonella data from various canadian surveillance programs (2005-2010)\", \"Page end / Derni\\u00e8re Page\": \"756\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"747\", \"Document Type / Type de document\": \"Review\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1089/fpd.2012.1438\", \"Title, Collective / Titre, Collectif\": \"Foodborne Pathogens and Disease\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"10\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Parmley E.J., Pintar K., Majowicz S., Avery B., Cook A., Jokinen C., Gannon V., Lapen D.R., Topp E., Edge T.A., Gilmour M., Pollari F., Reid-Smith R., Irwin R.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"3\", \"Title, Analytic / Titre, Analytique\": \"A Canadian viewpoint on data, information and uncertainty in the context of prediction in ungauged basins\", \"Page end / Derni\\u00e8re Page\": \"429\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"419\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.2166/nh.2012.055\", \"Title, Collective / Titre, Collectif\": \"Hydrology Research\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"44\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Spence C., Burn D.H., Davison B., Hutchinson D., Ouarda T.B.M.J., St-Hilaire A., Weber F., Whitfield P.H.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"1\", \"Title, Analytic / Titre, Analytique\": \"A checklist of bats (Mammalia: Chiroptera) from Lao PDR\", \"Page end / Derni\\u00e8re Page\": \"260\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"193\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.3161/150811013X667993\", \"Title, Collective / Titre, Collectif\": \"Acta Chiropterologica\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"15\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Thomas N.M., Duckworth J.W., Douangboubpha B., Williams M., Francis C.M.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"A coagulant survey for chemically enhanced primary treatment of synthetic CSOs\", \"Page end / Derni\\u00e8re Page\": \"\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"\", \"Document Type / Type de document\": \"Review\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1007/s11270-012-1414-z\", \"Title, Collective / Titre, Collectif\": \"Water, Air, and Soil Pollution\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"224\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Exall K., Marsalek J.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"A cohort study of intra-urban variations in volatile organic compounds and mortality, Toronto, Canada\", \"Page end / Derni\\u00e8re Page\": \"39\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"30\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1016/j.envpol.2012.12.022\", \"Title, Collective / Titre, Collectif\": \"Environmental Pollution\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"183\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Villeneuve P.J., Jerrett M., Su J., Burnett R.T., Chen H., Brook J., Wheeler A.J., Cakmak S., Goldberg M.S.\"}");

    }

    private void GetHandles(){

    }
}
