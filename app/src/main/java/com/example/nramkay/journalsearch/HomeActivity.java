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
    List<String> documentTypes = new ArrayList<>();
    AutoCompleteTextView analyticSearch;
    AutoCompleteTextView collectiveSearch;
    AutoCompleteTextView subordinateSearch;
    AutoCompleteTextView authorSearch;
    AutoCompleteTextView publisherSearch;
    AutoCompleteTextView documentSearch;
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
        GetHandles();
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



        convertFromJson();
    }

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
            documentTypes.add(a.getDocumentTypeTypeDeDocument());
        }
    }

    private void GetHandles(){
        analyticSearch = (AutoCompleteTextView) findViewById(R.id.analyticSearch);
        collectiveSearch = (AutoCompleteTextView) findViewById(R.id.collectiveSearch);
        subordinateSearch = (AutoCompleteTextView) findViewById(R.id.subordinateSearch);
        authorSearch = (AutoCompleteTextView) findViewById(R.id.authorSearch);
        publisherSearch = (AutoCompleteTextView) findViewById(R.id.publisherSearch);
        documentSearch = (AutoCompleteTextView) findViewById(R.id.documentTypeSearch);
    }

    private void SetAdapters(){
        analyticSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, analyticTitles));
        collectiveSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, collectiveTitles));
        subordinateSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subordinateTitles));
        authorSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, authors));
        publisherSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, publishers));
        documentSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, documentTypes));
    }

    private void SetItemClickListeners(){
        analyticSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String target = analyticSearch.getText().toString();
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
    }
}
