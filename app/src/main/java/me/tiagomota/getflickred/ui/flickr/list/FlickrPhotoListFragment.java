package me.tiagomota.getflickred.ui.flickr.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.ui.base.BaseFragment;
import me.tiagomota.getflickred.ui.flickr.FlickrActivity;
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;
import me.tiagomota.getflickred.utils.NetworkUtils;
import me.tiagomota.getflickred.utils.SnackBarFactory;
import me.tiagomota.getflickred.utils.ViewUtils;

public class FlickrPhotoListFragment extends BaseFragment
        implements FlickrPhotosListView {

    public static final String TAG = "MyFlickrPhotosListFragment";

    @Inject
    FlickrPhotosListPresenter mPresenter;

    // Content screen
    private RecyclerView mRecyclerView;
    private FlickrPhotosListAdapter mAdapter;

    // Empty screen
    private RelativeLayout mEmptyContainer;
    private ImageView mEmptyImageView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_flickr_photo_list;
    }

    @Override
    protected void mapLayoutViews(final View root) {
        // content screen
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        // empty screen
        mEmptyContainer = (RelativeLayout) root.findViewById(R.id.empty_container);
        mEmptyImageView = (ImageView) root.findViewById(R.id.empty_image);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFragmentComponent().inject(this);
        mPresenter.attachView(this);

        configurePhotosRecyclerView();
        configureEmptyScreen();

        // checks if there is any content already loaded
        if (!mPresenter.getLoadedPhotos().isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void handleFirstPhotosPageLoaded(final List<PhotoEntry> firstEntries) {
        if (firstEntries.isEmpty()) {
            ((FlickrActivity) getActivity()).onUserWithoutPhotos();
            mEmptyContainer.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            ((FlickrActivity) getActivity()).onUserFirstPhotosPageLoaded();
            mAdapter.notifyDataSetChanged();
            mAdapter.addAll(firstEntries);
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void handleNewPhotosPageLoaded(final List<PhotoEntry> newEntries) {
        mAdapter.removeLoadingItem();
        mAdapter.addAll(newEntries);
    }

    @Override
    public void handleErrorLoadingNewPhotosPage(final String message) {
        mAdapter.removeLoadingItem();
        SnackBarFactory.build(mRecyclerView, message).show();
    }

    /**
     * Allows activity to tell {@link FlickrPhotoListFragment} that a new user
     * was found.
     *
     * @param userId String
     * @return boolean
     */
    public boolean onUserFound(final String userId) {
        return mPresenter.handleUserFound(userId);
    }

    /**
     * Configures the Photos Recycler View.
     */
    private void configurePhotosRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new FlickrPhotosListAdapter(
                mPresenter.getLoadedPhotos(),
                new FlickrPhotosListAdapter.OnPhotoSelectedListener() {
                    @Override
                    public void onSelected(final PhotoEntry photoEntry) {
                        ((FlickrActivity) getActivity()).onPhotoSelected(photoEntry);
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final boolean isScrollingDown = dy > 0;
                final int invisibleItemCount = recyclerView.getAdapter().getItemCount() - recyclerView.getChildCount();
                final int triggerThreshold = layoutManager.findFirstVisibleItemPosition() + 2;

                // based on the RecyclerView position, load more content if the
                // nr of invisible items is less or equal then the threshold defined.
                if (isScrollingDown && invisibleItemCount <= triggerThreshold) {
                    if (NetworkUtils.isNetworkConnected(getContext())) {
                        // attempt to load more content
                        if (mPresenter.loadUserPublicPhotos(false)) {
                            // if more content available, add loading item to adapter
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.addLoadingItem();
                                }
                            });
                        }
                    } else {
                        SnackBarFactory.build(
                                mRecyclerView,
                                getString(R.string.error_no_network)
                        ).show();
                    }
                }
            }
        });
    }

    /**
     * Configures the empty screen views.
     */
    private void configureEmptyScreen() {
        mEmptyImageView.setImageDrawable(
                ViewUtils.getTintedDrawable(getContext(), R.drawable.ic_collections_black_48dp, R.color.textColorPrimary)
        );
    }
}
