package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.classes.ComicSpecifics;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.DescriptionView;
import comic.shannortrotty.gruntt.view.GenericView;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class ItemPresenter<T> implements GenericNetworkPresenter, NetworkModel.OnFinishedItemListener<T> {

    private GenericView<T> genericView;
    private NetworkModel networkModel;

    public ItemPresenter(GenericView<T> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
    }

    @Override
    public void onDestroy() {
        genericView = null;
    }

    @Override
    public void onItemFinished(T item) {
        genericView.setItem(item);
        genericView.hideLoading();
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        if(requestType.getType() == RequestType.Type.COMICSDESCRIPTION){
            networkModel.getComicDescription(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    this
            );
        }else{
//          Throw some error here
        }
    }
}
