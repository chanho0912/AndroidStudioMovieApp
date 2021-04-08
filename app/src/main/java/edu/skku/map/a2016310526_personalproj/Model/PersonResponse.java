package edu.skku.map.a2016310526_personalproj.Model;

import java.util.List;

public class PersonResponse {
    private int pages;

    private int total_results;

    private int total_pages;

    private List<PersonResponseResults> results;

    public PersonResponse() {
    }

    public PersonResponse(int pages, int total_results, int total_pages, List<PersonResponseResults> results) {
        this.pages = pages;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<PersonResponseResults> getResults() {
        return results;
    }

    public void setResults(List<PersonResponseResults> results) {
        this.results = results;
    }
}
