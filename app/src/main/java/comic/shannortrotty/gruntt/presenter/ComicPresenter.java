package comic.shannortrotty.gruntt.presenter;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.BasicView;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ComicPresenter implements NetworkPresenter, NetworkModel.OnFinishedComicListener {
    private BasicView basicView;
    private NetworkModel networkModel;

    public ComicPresenter(BasicView basicView, NetworkModel networkModel){
        this.basicView = basicView;
        this.networkModel = networkModel;
    }

    @Override
    public void onFinishedRequest() {

    }

    @Override
    public void startRequest() {
        networkModel.getPopularComics("1",this);
    }

    @Override
    public void onListFinished(List<Comic> items) {
        basicView.setItems(items);
    }
}
