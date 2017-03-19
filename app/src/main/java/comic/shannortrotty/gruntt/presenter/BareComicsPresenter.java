package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import java.util.List;

import comic.shannortrotty.gruntt.classes.BareComicsCategory;
import comic.shannortrotty.gruntt.classes.Chapter;
import comic.shannortrotty.gruntt.classes.Constants;
import comic.shannortrotty.gruntt.classes.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/18/17.
 */

public class BareComicsPresenter implements  GenericPresenter, NetworkModel.OnResponseListListener<BareComicsCategory> {

    private GenericView<BareComicsCategory> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished;

    public BareComicsPresenter(Context context, GenericView<BareComicsCategory> genericView, NetworkModel networkModel) {
        this.mContext = context;
        this.genericView = genericView;
        this.networkModel = networkModel;
        this.finished = false;
        this.canceled = false;
    }

    @Override
    public void startRequest(RequestType requestType) {
        genericView.showLoading();
        finished = false;
        canceled = false;
        if(requestType.getType() == RequestType.Type.ALLCOMICS){
            networkModel.getAllComics(this);
        }else{
            //Throw Error
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
    }

    @Override
    public void setRequestListCall(Call<List<BareComicsCategory>> call) {
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
    public void onListFailed(Throwable throwable) {

    }

    @Override
    public void onListFinished(List<BareComicsCategory> items) {
        if(!canceled){
            genericView.setItems(items);
            genericView.hideLoading();
            finished = true;
        }
    }
}
