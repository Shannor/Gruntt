package comic.shannortrotty.gruntt.classes;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shannortrotty on 3/14/17.
 */

public class AllComicsResponse implements Parent<BareComics> {

    @SerializedName("category")
    String category;
    @SerializedName("comic_list")
    List<BareComics> comicsList;

    public AllComicsResponse(String category, List<BareComics> comicsList) {
        this.category = category;
        this.comicsList = comicsList;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BareComics> getComicsList() {
        return comicsList;
    }

    public void setComicsList(List<BareComics> comicsList) {
        this.comicsList = comicsList;
    }

    @Override
    public String toString() {
        return "AllComicsResponse{" +
                "category='" + category + '\'' +
                ", comicsList=" + comicsList +
                '}';
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public List<BareComics> getChildList() {
        return this.comicsList;
    }
}
