package comic.shannortrotty.gruntt.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class Genre{

    private String genre;
    private String genreLink;

    public Genre(){
        //Default Constructor
    }

    public Genre(String name, String genreLink){
        this.genre = name;
        this.genreLink = genreLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String name) {
        this.genre = name;
    }

    public String getGenreLink() {
        return genreLink;
    }

    public void setGenreLink(String genreLink) {
        this.genreLink = genreLink;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + genre + '\'' +
                '}';
    }
}
