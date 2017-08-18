package comic.shannortrotty.gruntt.presenter;

import android.content.Context;

import java.util.List;

import comic.shannortrotty.gruntt.classes.Comic;
import comic.shannortrotty.gruntt.services.APIError;
import comic.shannortrotty.gruntt.utils.Constants;
import comic.shannortrotty.gruntt.utils.RequestType;
import comic.shannortrotty.gruntt.model.NetworkModel;
import comic.shannortrotty.gruntt.view.GenericView;
import retrofit2.Call;

/**
 * Created by shannortrotty on 3/18/17.
 */

public class AllComicsPresenter implements ComicPresenter, NetworkModel.OnResponseListListener<Comic> {

    private GenericView<Comic> genericView;
    private NetworkModel networkModel;
    private Context mContext;
    private boolean canceled;
    private Call call;
    private boolean finished;

    public AllComicsPresenter(Context context, GenericView<Comic> genericView, NetworkModel networkModel) {
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
        if(requestType.getType() == RequestType.Type.LOAD){
            networkModel.getAllComics(
                    requestType.getExtras().get(Constants.SOURCE_TAG),
                    this);
        }else{
            //Throw Error
        }
    }

    @Override
    public void onDestroy() {
        genericView = null;
        networkModel = null;
        mContext = null;
    }

    @Override
    public void setRequestListCall(Call<List<Comic>> call) {
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
    public void onAPIFailure(Throwable throwable) {

    }

    @Override
    public void onListFailure(APIError error) {

    }

    @Override
    public void onListSuccess(List<Comic> items) {
        if(!canceled){
            genericView.setItems(items);
            genericView.hideLoading();
            finished = true;
        }
    }
}
