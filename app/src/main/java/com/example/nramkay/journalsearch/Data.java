package com.example.nramkay.journalsearch;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private List<Article> item = new ArrayList<Article>();

    /**
     *
     * @return
     * The item
     */
    public List<Article> getItem() {
        return item;
    }

    /**
     *
     * @param item
     * The item
     */
    public void setItem(List<Article> item) {
        this.item = item;
    }

}