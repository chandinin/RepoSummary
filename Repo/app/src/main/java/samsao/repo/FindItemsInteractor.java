package samsao.repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnagendra on 8/9/2016.
 */
public interface FindItemsInteractor {

    interface OnFinishedListener {
        void onFinished(ArrayList<RepoSummary> items);
    }

    void findItems(OnFinishedListener listener);
}
