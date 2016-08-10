package samsao.samsaoreporter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by cnagendra on 8/10/2016.
 */
public class RepoListView extends ListView {

    RepoListViewOverScrollListener overScrollListener;

    public RepoListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setOnOverScrollListener(RepoListViewOverScrollListener listener)
    {
        overScrollListener = listener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY)
    {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(overScrollListener != null && (clampedX || clampedY))
        {
            overScrollListener.onOverScrolled(scrollX, scrollY, clampedX,
                    clampedY);
        }
    }
}


