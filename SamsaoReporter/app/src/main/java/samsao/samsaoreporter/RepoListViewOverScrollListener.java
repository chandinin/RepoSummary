package samsao.samsaoreporter;

/**
 * Created by cnagendra on 8/10/2016.
 */
public interface RepoListViewOverScrollListener {
    /**
     * Called when the list is over scrolled
     */
    void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                        boolean clampedY);
}

