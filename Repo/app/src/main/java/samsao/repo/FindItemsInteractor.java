package samsao.repo;

import java.util.List;

/**
 * Created by cnagendra on 8/9/2016.
 */
public interface FindItemsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}
