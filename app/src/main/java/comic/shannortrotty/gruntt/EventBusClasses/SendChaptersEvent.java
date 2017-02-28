package comic.shannortrotty.gruntt.EventBusClasses;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Chapter;

/**
 * Created by shannortrotty on 2/15/17.
 */

public class SendChaptersEvent {
    private List<Chapter> chapters;

    public SendChaptersEvent(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

}
