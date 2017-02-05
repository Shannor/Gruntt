package comic.shannortrotty.gruntt.models;



import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by shannortrotty on 2/4/17.
 * Class taking advantage of RxJava and RxAndroid
 * Used as a Bus system. Will handle sending of Comics.
 */

public class ComicEventBus {
        private static ComicEventBus instance;

        private PublishSubject<List<Comic>> subject = PublishSubject.create();

        public static ComicEventBus getInstance() {
            if (instance == null) {
                instance = new ComicEventBus();
            }
            return instance;
        }

        public void passComics(List<Comic> comics){
            subject.onNext(comics);
        }

        /**
         * Subscribe to this Observable. On event, do something e.g. replace a fragment
         */
        public Observable<List<Comic>> getStringObservable() {
            return subject;
        }
}