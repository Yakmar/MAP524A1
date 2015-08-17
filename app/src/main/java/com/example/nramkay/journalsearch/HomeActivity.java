package com.example.nramkay.journalsearch;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Gson gson = new Gson();
    List<Article> data = new ArrayList<>();
    List<String> jsonString = new ArrayList<>();
    List<String> analyticTitles = new ArrayList<>();
    List<String> collectiveTitles = new ArrayList<>();
    List<String> subordinateTitles = new ArrayList<>();
    List<String> authors = new ArrayList<>();
    List<String> publishers = new ArrayList<>();
    List<String> editors = new ArrayList<>();
    AutoCompleteTextView analyticSearch;
    AutoCompleteTextView collectiveSearch;
    AutoCompleteTextView subordinateSearch;
    AutoCompleteTextView authorSearch;
    AutoCompleteTextView publisherSearch;
    AutoCompleteTextView editorSearch;
    File file;

    //http://donnees.ec.gc.ca/data/managementoversight/systems/environment-canada-lists-of-peer-reviewed-scientific-and-technical-publications/environment-canada-list-of-2013-peer-reviewed-scientific-and-technical-publications/Environment_Canada_2013_scientific_and_technical_publications_list.json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String destPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/e_canada_2013.txt";
        file = new File(destPath);

        /*This piece of code I left in despite not actually using the file at all.
        * I wanted to demonstrate that I know how to download directly to the device using DownloadManager.
        * Unfortunately, my attempts to properly parse the text file lead me to do some manual workarounds.*/
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
        GetHandles();
        ClearFields();
        SetAdapters();
        SetItemClickListeners();

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

    /*This was the worst part of the assignment for me. Up to the night before the due date
    * I couldn't find a solution for parsing the JSON text file provided by the government website.
    * I ran into many problems that I will highlight in my documentation.
    *
    * My solution was to isolate the object strings manually (this is bad I know) in order to continue working
    * on the actual app functionality. Up until this point I had not been able to work with an Article Object at all.
    * After consulting with Brad Hoover & Stephen Ruthland I ended up keeping this solution as any work arounds would
    * require too much time to reach a realistic submission date.*/
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
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"A comparative toxicogenomic investigation of oil sand water and processed water in rainbow trout hepatocytes\", \"Page end / Derni\\u00e8re Page\": \"323\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"309\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1007/s00244-013-9888-2\", \"Title, Collective / Titre, Collectif\": \"Archives of Environmental Contamination and Toxicology\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"65\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Gagne F., Andre C., Turcotte P., Gagnon C., Sherry J., Talbot A.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"3\", \"Title, Analytic / Titre, Analytique\": \"A comparison of rainfall-runoff modelling approaches for estimating impacts of rural land management on flood flows\", \"Page end / Derni\\u00e8re Page\": \"483\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"467\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.2166/nh.2013.034\", \"Title, Collective / Titre, Collectif\": \"Hydrology Research\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"44\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Bulygina N., McIntyre N., Wheater H.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"10\", \"Title, Analytic / Titre, Analytique\": \"A comparison of the canadian global and regional meteorological ensemble prediction systems for short-term hydrological forecasting\", \"Page end / Derni\\u00e8re Page\": \"3476\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"3462\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1175/MWR-D-12-00206.1\", \"Title, Collective / Titre, Collectif\": \"Monthly Weather Review\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"141\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Abaza M., Anctil F., Fortin V., Turcotte R.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"A first estimate for Canada of the number of birds killed by colliding with building windows [Premi\\u00e8re estimation canadienne du nombre d'oiseaux morts par collision avec les fen\\u00eatres de b\\u00e2timents]\", \"Page end / Derni\\u00e8re Page\": \"\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.5751/ACE-00568-080206\", \"Title, Collective / Titre, Collectif\": \"Avian Conservation and Ecology\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"8\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Machtans C.S., Wedeles C.H.R., Bayne E.M.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"3\", \"Title, Analytic / Titre, Analytique\": \"A framework for assessing cumulative effects in watersheds: An introduction to Canadian case studies\", \"Page end / Derni\\u00e8re Page\": \"369\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"363\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1002/ieam.1418\", \"Title, Collective / Titre, Collectif\": \"Integrated Environmental Assessment and Management\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"9\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Dube, M., P. Duniker, L. Greig, M. Servos, M. McMaster, M. Carver, B. Noble, H. Schreier, L. Jackson and K. Munkittrick\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"6\", \"Title, Analytic / Titre, Analytique\": \"A global climatology of baroclinically influenced tropical cyclogenesis\", \"Page end / Derni\\u00e8re Page\": \"1989\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"1963\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1175/MWR-D-12-00186.1\", \"Title, Collective / Titre, Collectif\": \"Monthly Weather Review\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"141\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Mctaggart-Cowan R., Galarneau Jr. T.J., Bosart L.F., Moore R.W., Martius O.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"22\", \"Title, Analytic / Titre, Analytique\": \"A global ozone climatology from ozone soundings via trajectory mapping: A stratospheric perspective\", \"Page end / Derni\\u00e8re Page\": \"11464\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"11441\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.5194/acp-13-11441-2013\", \"Title, Collective / Titre, Collectif\": \"Atmospheric Chemistry and Physics\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"13\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Liu J., Tarasick D.W., Fioletov V.E., McLinden C., Zhao T., Gong S., Sioris C., Jin J.J., Liu G., Moeini O.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"A global tropospheric ozone climatology from trajectory-mapped ozone soundings \", \"Page end / Derni\\u00e8re Page\": \"10675\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"10659\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.5194/acp-13-10659-2013\", \"Title, Collective / Titre, Collectif\": \"Atmospheric Chemistry and Physics\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"13\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Liu, G., Liu, J., Tarasick, D.W., Fioletov, V.E., Jin, J.J, Moeini, O., Liu, X., Sioris, C.E., and Osman, M.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"1\", \"Title, Analytic / Titre, Analytique\": \"A high-resolution canadian lightning climatology\", \"Page end / Derni\\u00e8re Page\": \"59\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"50\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1080/07055900.2012.755946\", \"Title, Collective / Titre, Collectif\": \"Atmosphere - Ocean\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"51\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Shephard M.W., Morris R., Burrows W.R., Welsh L.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"3\", \"Title, Analytic / Titre, Analytique\": \"A hybrid zone between Canada geese (Branta canadensis) and Cackling geese (B. hutchinsii)\", \"Page end / Derni\\u00e8re Page\": \"500\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"487\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1525/auk.2013.12196\", \"Title, Collective / Titre, Collectif\": \"Auk\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"130\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Leafloor J.O., Moore J.A., Scribner K.T.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Arctic terrestrial biodiversity monitoring plan\", \"Page end / Derni\\u00e8re Page\": \"164\", \"URL / Adresse URL   \": \"http://www.caff.is/monitoring-series/256-arctic-terrestrial-biodiversity-monitoring-plan\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"1\", \"Document Type / Type de document\": \"Report\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"CAFF Monitoring Series Report\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"7\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Christensen, T., J. Payne, M. Doyle, G. Ibarguchi, J.J. Taylor, N.M. Schmidt, M. Gill, M. Svoboda, M. Aronsson, C. Behe, C. Buddle, C. Cuyler, A.M. Fosaa, A.D. Fox, S. Heidmarsson, P. Henning Krogh, J. Madsen, D. McLennan, J. Nymand, C. Rosa, J. Salmela, R. Shuchman, M. Soloviev and M. Wedege\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"An Analysis of Spatial and Temporal Trends and Patterns in Western Canadian Runoff: A CROCWR Component\", \"Page end / Derni\\u00e8re Page\": \"56\", \"URL / Adresse URL   \": \"http://www.19thnrb.com/docs/19thNRB_Proceedings_Web2013-9-19.pdf\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"45\", \"Document Type / Type de document\": \"Conference Proceedings\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Proceedings of the 19th International Northern Research Basins Symposium and Workshop\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Bawden, A.J., D.H. Burn and T.D. Prowse\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"23\", \"Title, Analytic / Titre, Analytique\": \"An energetic perspective on hydrological cycle changes in the Geoengineering Model Intercomparison Project\", \"Page end / Derni\\u00e8re Page\": \"13102\", \"URL / Adresse URL   \": \"http://onlinelibrary.wiley.com/doi/10.1002/2013JD020502/abstract\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"13087\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1002/2013JD020502\", \"Title, Collective / Titre, Collectif\": \"Journal of Geophysical Research D: Atmospheres\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"118\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Kravitz B., Rasch P.J., Forster P.M., Andrews T., Cole J.N.S., Irvine P.J., Ji D., Kristjansson J.E., Moore J.C., Muri H., Niemeier U., Robock A., Singh B., Tilmes S. Watanabe S., Yoon J.-H.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Analysis of present day and future OH and methane lifetime in the ACCMIP simulations\", \"Page end / Derni\\u00e8re Page\": \"2587\", \"URL / Adresse URL   \": \"http://www.atmos-chem-phys.net/13/2563/2013/acp-13-2563-2013.html\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"2563\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.5194/acp-13-2563-2013\", \"Title, Collective / Titre, Collectif\": \"Atmospheric Chemistry and Physics\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"13\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"A. Voulgarakis, V. Naik, J.-F. Lamarque, D. T. Shindell, P. J. Young, M. J. Prather, O. Wild, R. D. Field, D. Bergmann, P. Cameron-Smith, I. Cionni, W. J. Collins, S. B. Dals\\u00f8ren, R. M. Doherty, V. Eyring, G. Faluvegi, G. A. Folberth, L. W. Horowitz, B. Josse, I. A. MacKenzie, T. Nagashima, D. A. Plummer, M. Righi, S. T. Rumbold, D. S. Stevenson, S. A. Strode, K. Sudo, S. Szopa, and G. Zeng \"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Analytical delta-four-stream doubling-adding method for radiative transfer parameterizations\", \"Page end / Derni\\u00e8re Page\": \"808\", \"URL / Adresse URL   \": \"http://dx.doi.org/10.1175/JAS-D-12-0122.1\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"794\", \"Document Type / Type de document\": \"\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"American Meteorological Society\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"70\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Feng Zhang, Zhongping Shen, Jiangnan Li, Xiuji Zhou, and Leiming Ma\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Benthic Community Ecotoxicology\", \"Page end / Derni\\u00e8re Page\": \"180\", \"URL / Adresse URL   \": \"http://www.springer.com/gp/book/9789400750401\", \"Publisher / Maison d'\\u00e9dition\": \"Springer Publishing\", \"City / Ville\": \"London, UK\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"169\", \"Document Type / Type de document\": \"Book Chapter\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"In J.F. F\\u00e9rard and C. Blaise (eds.), Encyclopedia of Aquatic Ecotoxicology. Springer Publishers, London, UK.\", \"Editors / \\u00c9diteurs\": \"J.F. F\\u00e9rard and C. Blaise\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Grapentine, L.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"Biomonitoring for the 21st Century: new perspectives in an age of globalisation and emerging environmental threats\", \"Page end / Derni\\u00e8re Page\": \"174\", \"URL / Adresse URL   \": \"http://www.limnetica.com/fulltext/Limnetica_32v2_2013.pdf\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"159\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Limnetica\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"32\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Woodward, G., C. Gray and D.J. Baird\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Canadian Arctic Contaminants Assessment Report (CACAR) III - Mercury\", \"Page end / Derni\\u00e8re Page\": \"276\", \"URL / Adresse URL   \": \"http://publications.gc.ca/site/eng/457558/publication.html\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"1\", \"Document Type / Type de document\": \"Report\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Canadian Arctic Contaminants Assessment Report (CACAR) III\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Braune, B. and J. Chetelat\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"2\", \"Title, Analytic / Titre, Analytique\": \"Changes in Abundance and Distribution of Pelagic Cormorants Nesting on Triangle Island, British Columbia, 1949-2010\", \"Page end / Derni\\u00e8re Page\": \"166\", \"URL / Adresse URL   \": \"http://www.wildlifebc.org/pdfs/Pelagic%20Cormorants%20Triangle%20Island%20BC.pdf\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"147\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Wildlife Afield\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"8\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Rodway. M.S., K.R. Summers, J.M. Hipfner, J.C. van Rooyen and R.W. Campbell\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Chapter 1. Introduction\", \"Page end / Derni\\u00e8re Page\": \"17\", \"URL / Adresse URL   \": \"http://www.cripe.ca/Freeze-up_Book.html\", \"Publisher / Maison d'\\u00e9dition\": \"Committee on River Ice Processes and the Environment\", \"City / Ville\": \"Edmonton\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"1\", \"Document Type / Type de document\": \"Book Chapter\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"In S. Beltaos (eds.), River Ice Formation. Committe on River Ice Processes and the Environment, CGU-HS, Edmonton. .\", \"Editors / \\u00c9diteurs\": \"Beltaos, S.\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Burrell, B.C. and S. Beltaos\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Chapter 7. Freezeup jamming and formation of ice cover. \", \"Page end / Derni\\u00e8re Page\": \"296\", \"URL / Adresse URL   \": \"http://www.cripe.ca/Freeze-up_Book.html\", \"Publisher / Maison d'\\u00e9dition\": \"Committee on River Ice Processes and the Environment\", \"City / Ville\": \"Edmonton\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"181\", \"Document Type / Type de document\": \"Book Chapter\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"In S. Beltaos (ed.), River Ice Formation. Committee on River Ice Processes and the Environment, CGU-HS, Edmonton.\", \"Editors / \\u00c9diteurs\": \"Beltaos, S.\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Beltaos, S.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Chapter 8. Thermal growth of ice cover\", \"Page end / Derni\\u00e8re Page\": \"296\", \"URL / Adresse URL   \": \"http://www.cripe.ca/Freeze-up_Book.html\", \"Publisher / Maison d'\\u00e9dition\": \"Committee on River Ice Processes and the Environment\", \"City / Ville\": \"Edmonton\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"257\", \"Document Type / Type de document\": \"Book Chapter\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"In S. Beltaos (ed.), River Ice Formation. Committe on River Ice Processes and the Environment, CGU-HS, Edmonton.\", \"Editors / \\u00c9diteurs\": \"Beltaos, S.\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Ashton, G.D. and S. Beltaos\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"3\", \"Title, Analytic / Titre, Analytique\": \"Dendrohydroclimate reconstructions of July-August runoff for two nival-regime rivers in west central British Columbia\", \"Page end / Derni\\u00e8re Page\": \"420\", \"URL / Adresse URL   \": \"http://onlinelibrary.wiley.com/doi/10.1002/hyp.9257/abstract\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"405\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"10.1002/hyp.9257\", \"Title, Collective / Titre, Collectif\": \"Hydrological Processes\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"27\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Starheim C.C.A., Smith D.J., Prowse T.D.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Ecotoxicogenomics\", \"Page end / Derni\\u00e8re Page\": \"362\", \"URL / Adresse URL   \": \"http://link.springer.com/referenceworkentry/10.1007/978-94-007-5704-2_34\", \"Publisher / Maison d'\\u00e9dition\": \"Springer Publishing\", \"City / Ville\": \"London,UK.\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"353\", \"Document Type / Type de document\": \"Book Chapter\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Encyclopedia of Aquatic Ecotoxicology F\\u00e9rard J.-F. & Blaise C. Eds.\", \"Editors / \\u00c9diteurs\": \" F\\u00e9rard J.-F. & Blaise C. \", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Osachoff, H.L. and van Aggelen, G.C.\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"\", \"Title, Analytic / Titre, Analytique\": \"Effect of anthropogenic land-use and land-cover changes on climate and land carbon storage in CMIP5 projections for the twenty-first century\", \"Page end / Derni\\u00e8re Page\": \"6881\", \"URL / Adresse URL   \": \"\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"6859\", \"Document Type / Type de document\": \"\", \"DOI / Identificateur d'objet num\\u00e9rique\": \" http://dx.doi.org/10.1175/JCLI-D-12-00623.1\", \"Title, Collective / Titre, Collectif\": \"American Meteorological Society\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"26\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"V. Brovkin, L. Boysen, V. K. Arora, J. P. Boisier, P. Cadule, L. Chini, M. Claussen, P. Friedlingstein, V. Gayler, B. J. J. M. van den Hurk, G. C. Hurtt, C. D. Jones, E. Kato, N. de Noblet-Ducoudr\\u00e9, F. Pacifico, J. Pongratz, and M. Weiss\"}");
        jsonString.add("{\"Issue / Num\\u00e9ro\": \"1\", \"Title, Analytic / Titre, Analytique\": \"Flight times and abundance of three shorebird species staging near Chickney Channel, James Bay, Ontario, summer 2012\", \"Page end / Derni\\u00e8re Page\": \"23\", \"URL / Adresse URL   \": \"http://www.ofo.ca/site/download/id/2\", \"Publisher / Maison d'\\u00e9dition\": \"\", \"City / Ville\": \"\", \"Year / Ann\\u00e9e\": \"2013\", \"Page start / Premi\\u00e8re Page\": \"10\", \"Document Type / Type de document\": \"Article\", \"DOI / Identificateur d'objet num\\u00e9rique\": \"\", \"Title, Collective / Titre, Collectif\": \"Ontario Birds\", \"Editors / \\u00c9diteurs\": \"\", \"Title - Subordinate / Titre - Subalterne\": \"\", \"Volume - Chapter / Volume - Chapitre \": \"31\", \"\\ufeff\\\"Authors / Auteurs\\\"\": \"Friis, C., K.G. Burrell and S. Mackenzie\"}");

        convertFromJson();
    }

    //Accepts a ListArray<String> full of JSON strings.
    //Using Gson, it converts each string into an Article object and
    //adds them to a List of Articles.
    //
    //The function also creates Lists of all the possible search terms
    //for each category (Titles, Authors, Publishers, etc..).
    private void convertFromJson(){
        for(String s : jsonString){
            data.add(gson.fromJson(s, Article.class));
        }
        for(Article a : data){
            analyticTitles.add(a.getTitleAnalyticTitreAnalytique());
            collectiveTitles.add(a.getTitleCollectiveTitreCollectif());
            subordinateTitles.add(a.getTitleSubordinateTitreSubalterne());
            authors.add(a.getAuthorsAuteurs());
            publishers.add(a.getPublisherMaisonDDition());
            editors.add(a.getEditorsDiteurs());
        }
    }

    //Adds all the proper handles for each piece of the view using the correct ids
    private void GetHandles(){
        analyticSearch = (AutoCompleteTextView) findViewById(R.id.analyticSearch);
        collectiveSearch = (AutoCompleteTextView) findViewById(R.id.collectiveSearch);
        subordinateSearch = (AutoCompleteTextView) findViewById(R.id.subordinateSearch);
        authorSearch = (AutoCompleteTextView) findViewById(R.id.authorSearch);
        publisherSearch = (AutoCompleteTextView) findViewById(R.id.publisherSearch);
        editorSearch = (AutoCompleteTextView) findViewById(R.id.editorSearch);
    }

    /*Some of the terms are so long it is annoying to have to delete them if you want a different
    * article. */
    private void ClearFields(){
        analyticSearch.setText("");
        collectiveSearch.setText("");
        authorSearch.setText("");
        publisherSearch.setText("");
        editorSearch.setText("");
    }

    /*Sets all the proper adapters to each of the different AutoCompleteTextViews.*/
    private void SetAdapters(){
        analyticSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, analyticTitles));
        collectiveSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collectiveTitles));
        subordinateSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subordinateTitles));
        authorSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, authors));
        publisherSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, publishers));
        editorSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, editors));
    }

    /*Set listeners to each AutoCompleteTextView that, on click, will start
    * a DetailActivity by loading the Intent with the correct JSON string of
    * the term searched. We do this by cross-referencing the search term with
    * the position in the ListArray. Then grabbing the correct JSON string and giving
    * it to the DetailActivity.*/
    private void SetItemClickListeners(){
        analyticSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = analyticSearch.getText().toString();
                analyticSearch.setText("");
                int i =0;
                for(String s : analyticTitles){
                    if(s.compareTo(target) == 0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Log.i("home", "" + i);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });

        collectiveSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = collectiveSearch.getText().toString();
                collectiveSearch.setText("");
                int i = 0;
                for(String s : collectiveTitles){
                    if(s.compareTo(target)==0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });

        subordinateSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = subordinateSearch.getText().toString();
                subordinateSearch.setText("");
                int i = 0;
                for(String s : subordinateTitles){
                    if(s.compareTo(target)==0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });

        authorSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = authorSearch.getText().toString();
                authorSearch.setText("");
                int i = 0;
                for(String s : authors){
                    if(s.compareTo(target)==0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });

        publisherSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = publisherSearch.getText().toString();
                publisherSearch.setText("");
                int i = 0;
                for(String s : publishers){
                    if(s.compareTo(target)==0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });

        editorSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = editorSearch.getText().toString();
                editorSearch.setText("");
                int i = 0;
                for(String s : editors){
                    if(s.compareTo(target)==0){
                        break;
                    }
                    i++;
                }
                String article = gson.toJson(data.get(i));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });
    }
}
