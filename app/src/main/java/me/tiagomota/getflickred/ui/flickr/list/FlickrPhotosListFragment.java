package me.tiagomota.getflickred.ui.flickr.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.ui.base.BaseFragment;
import me.tiagomota.getflickred.ui.flickr.FlickrActivity;

public class FlickrPhotosListFragment extends BaseFragment implements FlickrPhotosListView {

    public static final String TAG = "MyFlickrPhotosListFragment";
    private static final String KEY_USER_ID = "key_user_id";

    private String mUserID;

    @Inject
    FlickrPhotosListPresenter mPresenter;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private FlickrPhotosListAdapter mAdapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_flickr_photos_list;
    }

    @Override
    protected void mapLayoutViews(final View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.attachView(this);

        if (getArguments() != null && getArguments().containsKey(KEY_USER_ID)) {
            mUserID = getArguments().getString(KEY_USER_ID);
            mPresenter.loadUserPublicPhotos(mUserID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View root = super.onCreateView(inflater, container, savedInstanceState);

        configurePhotosRecyclerView();

        return root;
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void handleFirstPhotosPageLoaded(final List<PhotoEntry> firstEntries) {
        if (firstEntries.isEmpty()) {
            ((FlickrActivity) getActivity()).onUserWhithoutPhotos();
        } else {
            ((FlickrActivity) getActivity()).onUserFirstPhotosPageLoaded();
            mAdapter.addAll(firstEntries);
        }
    }

    @Override
    public void handleNewPhotosPageLoaded(final List<PhotoEntry> newEntries) {
        // TODO dismiss progress indicator
        mAdapter.addAll(newEntries);
    }

    @Override
    public void handleErrorLoadingNewPhotosPage(final String message) {
        // TODO dismiss progress indicator
        // TODO show error msg.
    }

    /**
     * Creates a new instance of the FlickrPhotosListFragment.
     *
     * @param userId String
     * @return FlickrPhotosListFragment
     */
    public static FlickrPhotosListFragment newInstance(final String userId) {
        final Bundle args = new Bundle();
        args.putString(KEY_USER_ID, userId);

        final FlickrPhotosListFragment fragment = new FlickrPhotosListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Configures the Photos Recycler View.
     */
    private void configurePhotosRecyclerView() {
        mAdapter = new FlickrPhotosListAdapter(mPresenter.getLoadedPhotos());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
