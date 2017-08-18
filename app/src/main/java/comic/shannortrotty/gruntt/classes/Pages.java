package comic.shannortrotty.gruntt.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shannortrotty on 3/30/17.
 */

public class Pages {
    @SerializedName("totalPages")
    private int totalPageNumber;
    @SerializedName("urls")
    private List<String> pageUrls;

    public Pages(int totalPageNumber, List<String> pageUrls) {
        this.totalPageNumber = totalPageNumber;
        this.pageUrls = pageUrls;
    }

    public Pages() {
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public void setPageUrls(List<String> pageUrls) {
        this.pageUrls = pageUrls;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public List<String> getPageUrls() {
        return pageUrls;
    }
}
