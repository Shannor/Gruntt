package comic.shannortrotty.gruntt.EventBusClasses;

import comic.shannortrotty.gruntt.models.ComicSpecifics;

/**
 * Created by shannortrotty on 2/16/17.
 */

public class SendComicDescriptionEvent {

    private ComicSpecifics comicSpecifics;

    public SendComicDescriptionEvent(ComicSpecifics comicSpecifics) {
        this.comicSpecifics = comicSpecifics;
    }

    public ComicSpecifics getComicSpecifics() {
        return comicSpecifics;
    }

    public void setComicSpecifics(ComicSpecifics comicSpecifics) {
        this.comicSpecifics = comicSpecifics;
    }
}
