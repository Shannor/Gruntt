package comic.shannortrotty.gruntt.EventBusClasses;

import java.util.List;

/**
 * Created by shannortrotty on 2/27/17.
 */

public class SendChapterPagesEvent {
    private List<String> chapterPagesLinks;

    public SendChapterPagesEvent(List<String> chapterPagesLinks) {
        this.chapterPagesLinks = chapterPagesLinks;
    }

    public List<String> getChapterPagesLinks() {
        return this.chapterPagesLinks;
    }

    public void setChapterPagesLinks(List<String> chapterPagesLinks) {
        this.chapterPagesLinks = chapterPagesLinks;
    }
}
