package comic.shannortrotty.gruntt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

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
    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;
        Genre genre = (Genre) obj;
        // field comparison
        return Objects.equals(this.genre, genre.getGenre());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + genre + '\'' +
                '}';
    }
}
