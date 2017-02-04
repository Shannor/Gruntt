package comic.shannortrotty.gruntt.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class Genre implements Parcelable{

    private String name;
    private String genreLink;

    public Genre(){
        //Default Constructor
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }

    //Code to handle Parcelable
    protected Genre(Parcel in) {
        name = in.readString();
        genreLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
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

}
