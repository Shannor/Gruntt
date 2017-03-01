package comic.shannortrotty.gruntt.presenter;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.ComicView;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ComicPresenter implements NetworkPresenter, NetworkModel.OnFinishedComicListener {
    private ComicView comicView;
    private NetworkModel networkModel;

    public ComicPresenter(ComicView comicView, NetworkModel networkModel){
        this.comicView = comicView;
        this.networkModel = networkModel;
    }

    @Override
    public void onFinishedRequest() {

    }

    @Override
    public void startRequest(RequestType requestType) {
        //TODO: Change this, Pass in param or something else
        if(requestType.getType() == RequestType.Type.POPULARCOMICS){
            networkModel.getPopularComics(
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this);
        }
    }

    @Override
    public void onDestroy() {
        comicView = null;
    }

    @Override
    public void onListFinished(List<Comic> items) {
        comicView.setItems(items);
    }
}
