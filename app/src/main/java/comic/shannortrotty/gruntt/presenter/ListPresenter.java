package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 * Handles General List Types.
 */

public class ListPresenter<T> implements GenericPresenter, NetworkModel.OnResponseListListener<T> {
    private GenericView<T> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished = false;

    public ListPresenter(Context context,GenericView<T> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = context;
        this.canceled = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        finished = false;
        if(requestType.getType() == RequestType.Type.POPULARCOMICS){
            networkModel.getPopularComics(
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this);
        }else if (requestType.getType() == RequestType.Type.PAGES){
            networkModel.getComicPages(
                    requestType.getExtras().get(Constants.COMIC_LINK),
                    requestType.getExtras().get(Constants.CHAPTER_NUMBER),
                    this
            );
        }else if(requestType.getType() == RequestType.Type.ALLCOMICS){
            networkModel.getAllComics(this);
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void onCanceledRequest(Call<T> call) {
        this.call = call;
    }

    @Override
    public void cancelRequest() {
        if(!finished){
            this.canceled = true;
            this.call.cancel();
        }

    }

    @Override
    public void onListFinished(List<T> items) {
        if(!canceled){
            genericView.setItems(items);
            genericView.hideLoading();
            finished =true;
        }

    }
}
