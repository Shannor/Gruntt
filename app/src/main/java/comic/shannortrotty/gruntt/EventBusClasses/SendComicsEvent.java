package comic.shannortrotty.gruntt.EventBusClasses;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Comic;

/**
 * Created by shannortrotty on 2/15/17.
 */

public class SendComicsEvent {
    private List<Comic> comics;

    public SendComicsEvent(List<Comic> comics) {
        this.comics = comics;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}
