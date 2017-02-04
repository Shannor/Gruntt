package comic.shannortrotty.gruntt.services;

import com.squareup.otto.Bus;

/**
 * Created by shannortrotty on 2/3/17.
 */

public class OttoBus {
    private static Bus mBus = null;

    public static Bus getInstance(){
        if(mBus ==null){
            mBus = new Bus();
        }
        return mBus;
    }
}
