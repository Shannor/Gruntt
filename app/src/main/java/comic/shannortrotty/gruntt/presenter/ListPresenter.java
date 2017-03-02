package comic.shannortrotty.gruntt.presenter;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
/**
 * Created by shannortrotty on 2/28/17.
 * Handles General List Types.
 */

public class ListPresenter<T> implements GenericNetworkPresenter, NetworkModel.OnResponseListListener<T> {
    private GenericView<T> genericView;
    private NetworkModel networkModel;

    public ListPresenter(GenericView<T> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        if(requestType.getType() == RequestType.Type.POPULARCOMICS){
            networkModel.getPopularComics(
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this);
        }else if (requestType.getType() == RequestType.Type.CHAPTERS){
            networkModel.getChapters(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    this);
        }else if (requestType.getType() == RequestType.Type.PAGES){
            networkModel.getComicPages(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    requestType.getExtras().get(Constants.CHAPTER_NUMBER),
                    this
            );
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void onListFinished(List<T> items) {
        genericView.setItems(items);
        genericView.hideLoading();
    }

    @Override
    public void onNextStep(List<T> items) {
        genericView.setItems(items);
    }
}
