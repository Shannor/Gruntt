package comic.shannortrotty.gruntt.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class Genre implements Parcelable{

    private String genre;
    private String genreLink;

    public Genre(){
        //Default Constructor
    }

    public Genre(String name, String genreLink){
        this.genre = name;
        this.genreLink = genreLink;
    }


    //Code to handle Parcelable
    protected Genre(Parcel in) {
        genre = in.readString();
        genreLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(genre);
        dest.writeString(genreLink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };



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
