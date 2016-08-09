package samsao.repo;

import java.util.List;

/**
 * Created by cnagendra on 8/9/2016.
 */
public interface MainView {
    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String message);
}
