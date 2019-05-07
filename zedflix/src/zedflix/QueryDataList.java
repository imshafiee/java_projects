/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedflix;

/**
 *
 * @author Ashraful
 */
public class QueryDataList {
    int id, release;
    String name, genre, type, poster_url, movie_url;
    
    public QueryDataList(){}
    
    public QueryDataList(int id, String name, String genre, int release, String type, String poster_url, String movie_url) {
        this.genre = genre;
        this.id = id;
        this.name = name;
        this.release = release;
        this.type = type;
        this.poster_url = poster_url;
        this.movie_url = movie_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getMovie_url() {
        return movie_url;
    }

    public void setMovie_url(String movie_url) {
        this.movie_url = movie_url;
    }
    
    
    
    
}
