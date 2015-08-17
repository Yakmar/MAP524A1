package com.example.nramkay.journalsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("Issue / Num\u00e9ro")
    @Expose
    private String Issue;
    @SerializedName("Title, Analytic / Titre, Analytique")
    @Expose
    private String TitleAnalytic;
    @SerializedName("Page end / Derni\u00e8re Page")
    @Expose
    private String PageEnd;
    @SerializedName("URL / Adresse URL ")
    @Expose
    private String URL;
    @SerializedName("Publisher / Maison d'\u00e9dition")
    @Expose
    private String Publisher;
    @SerializedName("City / Ville")
    @Expose
    private String City;
    @SerializedName("Year / Ann\u00e9e")
    @Expose
    private String Year;
    @SerializedName("Page start / Premi\u00e8re Page")
    @Expose
    private String PageStart;
    @SerializedName("Document Type / Type de document")
    @Expose
    private String DocumentType;
    @SerializedName("DOI / Identificateur d'objet num\u00e9rique")
    @Expose
    private String DOI;
    @SerializedName("Title, Collective / Titre, Collectif")
    @Expose
    private String TitleCollective;
    @SerializedName("Editors / \u00c9diteurs")
    @Expose
    private String Editors;
    @SerializedName("Title - Subordinate / Titre - Subalterne")
    @Expose
    private String TitleSubordinate;
    @SerializedName("Volume - Chapter / Volume - Chapitre ")
    @Expose
    private String VolumeChapter;
    @SerializedName("\ufeff\"Authors / Auteurs\"")
    @Expose
    private String Authors;

    /**
     *
     * @return
     * The Issue
     */
    public String getIssue() {
        return Issue;
    }

    /**
     *
     * @param IssueNumRo
     * The Issue / Numéro
     */
    public void setIssue(String IssueNumRo) {
        this.Issue = IssueNumRo;
    }

    /**
     *
     * @return
     * The TitleAnalytic
     */
    public String getTitleAnalytic() {
        return TitleAnalytic;
    }

    /**
     *
     * @param TitleAnalyticTitreAnalytique
     * The Title, Analytic / Titre, Analytique
     */
    public void setTitleAnalytic(String TitleAnalyticTitreAnalytique) {
        this.TitleAnalytic = TitleAnalyticTitreAnalytique;
    }

    /**
     *
     * @return
     * The PageEnd
     */
    public String getPageEnd() {
        return PageEnd;
    }

    /**
     *
     * @param PageEndDerniRePage
     * The Page end / Dernière Page
     */
    public void setPageEnd(String PageEndDerniRePage) {
        this.PageEnd = PageEndDerniRePage;
    }

    /**
     *
     * @return
     * The URL
     */
    public String getURL() {
        return URL;
    }

    /**
     *
     * @param URL
     * The URL / Adresse URL
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     *
     * @return
     * The Publisher
     */
    public String getPublisher() {
        return Publisher;
    }

    /**
     *
     * @param PublisherMaisonDDition
     * The Publisher / Maison d'édition
     */
    public void setPublisher(String PublisherMaisonDDition) {
        this.Publisher = PublisherMaisonDDition;
    }

    /**
     *
     * @return
     * The City
     */
    public String getCity() {
        return City;
    }

    /**
     *
     * @param CityVille
     * The City / Ville
     */
    public void setCity(String CityVille) {
        this.City = CityVille;
    }

    /**
     *
     * @return
     * The Year
     */
    public String getYear() {
        return Year;
    }

    /**
     *
     * @param YearAnnE
     * The Year / Année
     */
    public void setYear(String YearAnnE) {
        this.Year = YearAnnE;
    }

    /**
     *
     * @return
     * The PageStart
     */
    public String getPageStart() {
        return PageStart;
    }

    /**
     *
     * @param PageStartPremiRePage
     * The Page start / Première Page
     */
    public void setPageStart(String PageStartPremiRePage) {
        this.PageStart = PageStartPremiRePage;
    }

    /**
     *
     * @return
     * The DocumentType
     */
    public String getDocumentType() {
        return DocumentType;
    }

    /**
     *
     * @param DocumentTypeTypeDeDocument
     * The Document Type / Type de document
     */
    public void setDocumentType(String DocumentTypeTypeDeDocument) {
        this.DocumentType = DocumentTypeTypeDeDocument;
    }

    /**
     *
     * @return
     * The DOI
     */
    public String getDOI() {
        return DOI;
    }

    /**
     *
     * @param DOI
     * The DOI / Identificateur d'objet numérique
     */
    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    /**
     *
     * @return
     * The TitleCollective
     */
    public String getTitleCollective() {
        return TitleCollective;
    }

    /**
     *
     * @param TitleCollectiveTitreCollectif
     * The Title, Collective / Titre, Collectif
     */
    public void setTitleCollective(String TitleCollectiveTitreCollectif) {
        this.TitleCollective = TitleCollectiveTitreCollectif;
    }

    /**
     *
     * @return
     * The Editors
     */
    public String getEditors() {
        return Editors;
    }

    /**
     *
     * @param EditorsDiteurs
     * The Editors / Éditeurs
     */
    public void setEditors(String EditorsDiteurs) {
        this.Editors = EditorsDiteurs;
    }

    /**
     *
     * @return
     * The TitleSubordinate
     */
    public String getTitleSubordinate() {
        return TitleSubordinate;
    }

    /**
     *
     * @param TitleSubordinateTitreSubalterne
     * The Title - Subordinate / Titre - Subalterne
     */
    public void setTitleSubordinate(String TitleSubordinateTitreSubalterne) {
        this.TitleSubordinate = TitleSubordinateTitreSubalterne;
    }

    /**
     *
     * @return
     * The VolumeChapter
     */
    public String getVolumeChapter() {
        return VolumeChapter;
    }

    /**
     *
     * @param VolumeChapterVolumeChapitre
     * The Volume - Chapter / Volume - Chapitre
     */
    public void setVolumeChapter(String VolumeChapterVolumeChapitre) {
        this.VolumeChapter = VolumeChapterVolumeChapitre;
    }

    /**
     *
     * @return
     * The Authors
     */
    public String getAuthors() {
        return Authors;
    }

    /**
     *
     * @param AuthorsAuteurs
     * The ﻿"Authors / Auteurs"
     */
    public void setAuthors(String AuthorsAuteurs) {
        this.Authors = AuthorsAuteurs;
    }

}
