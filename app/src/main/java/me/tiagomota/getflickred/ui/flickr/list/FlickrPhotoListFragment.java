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
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;

public class FlickrPhotoListFragment extends BaseFragment
        implements FlickrPhotosListView, FlickrActivity.OnUserFoundListener {

    public static final String TAG = "MyFlickrPhotosListFragment";

    @Inject
    FlickrPhotosListPresenter mPresenter;

    // User info
    private String mUserId;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private FlickrPhotosListAdapter mAdapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_flickr_photo_list;
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
    public boolean onUserFound(final String userId) {
        if (mUserId != null && mUserId.equalsIgnoreCase(userId)) {
            return false;
        } else {
            mUserId = userId;
            mPresenter.loadUserPublicPhotos(userId, true);
            return true;
        }
    }

    @Override
    public void handleFirstPhotosPageLoaded(final List<PhotoEntry> firstEntries) {
        if (firstEntries.isEmpty()) {
            ((FlickrActivity) getActivity()).onUserWithoutPhotos();
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
     * Configures the Photos Recycler View.
     */
    private void configurePhotosRecyclerView() {
        mAdapter = new FlickrPhotosListAdapter(
                mPresenter.getLoadedPhotos(),
                new FlickrPhotosListAdapter.OnPhotoSelectedListener() {
                    @Override
                    public void onSelected(final PhotoEntry photoEntry) {
                        ((FlickrActivity) getActivity()).onPhotoSelected(photoEntry);
                    }
                });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
