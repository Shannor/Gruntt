package comic.shannortrotty.gruntt.models;



import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by shannortrotty on 2/4/17.
 * Class taking advantage of RxJava and RxAndroid
 * Used as a Bus system. Will handle sending of Comics.
 * Singleton Class
 */

public class ComicEventBus {
        private static ComicEventBus instance;
        private PublishSubject<List<Comic>> comicSubject = PublishSubject.create();
        private PublishSubject<List<Chapter>> comicChapters = PublishSubject.create();

        private  ComicEventBus(){
            //Default Constructor
        }

        public static ComicEventBus getInstance() {
            if (instance == null) {
                instance = new ComicEventBus();
            }
            return instance;
        }

        public void passComics(List<Comic> comics){
            comicSubject.onNext(comics);
        }
        public void passChapters(List<Chapter> chapters){ comicChapters.onNext(chapters);}
        /**
         * Subscribe to this Observable. On event, return the list of comics provided
         */
        public Observable<List<Comic>> getComicObservable() {
            return comicSubject;
        }
        public Observable<List<Chapter>> getChapterObservable() { return comicChapters; }
}