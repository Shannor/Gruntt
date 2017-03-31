package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.PopularComic;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 2/28/17.
 * Handles General List Types.
 */

public class PopularComicsPresenter implements ComicDetialPresenter, NetworkModel.OnResponseListListener<PopularComic> {
    private GenericView<PopularComic> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished;

    public PopularComicsPresenter(Context context, GenericView<PopularComic> genericView, NetworkModel networkModel) {
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.mContext = context;
        this.canceled = false;
        this.finished = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        finished = false;
        if(requestType.getType() == RequestType.Type.LOAD) {
            networkModel.getPopularComics(
                    requestType.getExtras().get(Constants.PAGE_NUMBER),
                    this);
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void setRequestListCall(Call<List<PopularComic>> call) {
        this.call = call;
    }


    @Override
    public void onListFailed(Throwable throwable) {

    }

    @Override
    public void cancelRequest() {
        if(!finished){
            this.canceled = true;
            this.call.cancel();
        }

    }

    @Override
    public void onListSuccess(List<PopularComic> items) {
        if(!canceled){
            genericView.setItems(items);
            genericView.hideLoading();
            finished =true;
        }

    }
}
