package comic.shannortrotty.gruntt.presenter;

import comic.shannortrotty.gruntt.classes.ComicSpecifics;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.DescriptionView;

/**
 * Created by shannortrotty on 2/28/17.
 */

public class DescriptionPresentor implements GenericNetworkPresenter, NetworkModel.OnItemFinishedListener{

    private DescriptionView descriptionView;
    private NetworkModel networkModel;

    public DescriptionPresentor(DescriptionView descriptionView, NetworkModel networkModel) {
        this.descriptionView = descriptionView;
        this.networkModel = networkModel;
    }

    @Override
    public void onDestroy() {
        descriptionView = null;
    }

    @Override
    public void onFinishedRequest() {

    }

    @Override
    public void onItemFinished(ComicSpecifics item) {
        descriptionView.setDescription(item);
    }

    @Override
    public void startRequest(RequestType requestType) {
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
