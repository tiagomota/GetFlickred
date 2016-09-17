package me.tiagomota.getflickred.ui.myFlickr.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.ui.base.BaseFragment;

public class MyFlickrPhotosListFragment extends BaseFragment implements MyFlickrPhotosListView {

    public static final String TAG = "MyFlickrPhotosListFragment";

    private static final String KEY_USER_ID = "key_user_id";

    @Inject
    MyFlickrPhotosListPresenter mPresenter;

    public static MyFlickrPhotosListFragment newInstance(final String userId) {
        final Bundle args = new Bundle();
        args.putString(KEY_USER_ID, userId);

        final MyFlickrPhotosListFragment fragment = new MyFlickrPhotosListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_flickr_photos_list;
    }

    @Override
    protected void mapLayoutViews(View root) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }


}
