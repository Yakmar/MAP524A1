package com.example.nramkay.journalsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    Gson gson = new Gson();
    Article article;
    TextView analyticTitle, collectiveTitle, subordinateTitle, author, publisher,
        editor, city, issue, pageEnd, pageStart, year, url, documentType, doi,
        volumeChapter;
    Button website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Check for the proper extra in the intent, making sure it is valid (not null).
        //if it does exist we then turn the JSON string back into an Article object to be
        //used by the activity.
        if(getIntent().getStringExtra("article") != null){
            article = gson.fromJson(getIntent().getStringExtra("article"), Article.class);
        }
        GetHandles();
        FillTextViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    //All the proper handles for the views are gotten using the proper ids.
    private void GetHandles(){
        analyticTitle = (TextView) findViewById(R.id.detailAnalyticTitle);
        collectiveTitle = (TextView) findViewById(R.id.detailCollectiveTitle);
        subordinateTitle = (TextView) findViewById(R.id.detailSubordinateTitle);
        author = (TextView) findViewById(R.id.detailAuthor);
        publisher = (TextView) findViewById(R.id.detailPublisher);
        editor = (TextView) findViewById(R.id.detailEditor);
        city = (TextView) findViewById(R.id.detailCity);
        pageStart = (TextView) findViewById(R.id.detailPageStart);
        pageEnd = (TextView) findViewById(R.id.detailPageEnd);
        issue = (TextView) findViewById(R.id.detailIssue);
        year = (TextView) findViewById(R.id.detailYear);
        doi = (TextView) findViewById(R.id.detailDOI);
        documentType = (TextView) findViewById(R.id.detailDocType);
        volumeChapter = (TextView) findViewById(R.id.detailVolume);
        website = (Button) findViewById(R.id.urlButton);
        if(article.getURLAdresseURL().equals("")){
            website.setVisibility(View.GONE);
        }
    }

    //Using the handles we've gotten, we fill the textViews with information from the
    //article object. I've also added the one OnClickListener in this view.
    private void FillTextViews(){
        analyticTitle.setText(article.getTitleAnalyticTitreAnalytique());
        collectiveTitle.setText(article.getTitleCollectiveTitreCollectif());
        subordinateTitle.setText(article.getTitleSubordinateTitreSubalterne());
        author.setText(article.getAuthorsAuteurs());
        publisher.setText(article.getPublisherMaisonDDition());
        editor.setText(article.getEditorsDiteurs());
        city.setText(article.getCityVille());
        pageStart.setText(article.getPageStartPremiRePage());
        pageEnd.setText(article.getPageEndDerniRePage());
        issue.setText(article.getIssueNumRo());
        year.setText(article.getYearAnnE());
        doi.setText(article.getDOIIdentificateurDObjetNumRique());
        documentType.setText(article.getDocumentTypeTypeDeDocument());
        volumeChapter.setText(article.getVolumeChapterVolumeChapitre());

        //On clicking the button we add the url as a string to an intent,
        //then start the intent loading up our WebViewActivity.
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", article.getURLAdresseURL().trim());
                startActivity(intent);
            }
        });
    }
}
