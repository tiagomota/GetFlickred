package me.tiagomota.getflickred.ui.flickr.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.ui.base.BaseFragment;
import me.tiagomota.getflickred.ui.flickr.FlickrActivity;
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;

public class FlickrPhotoDetailFragment extends BaseFragment {

    public static final String TAG = "FlickrPhotoDetailFragment";
    private static final String KEY_PHOTO_ENTRY = "key_photo_entry";

    //  The photo selected by the user
    private PhotoEntry mPhotoEntry;

    // UI views
    private RelativeLayout mContentContainer;
    private LinearLayout mEmptyContainer;

    private ImageView mPhotoView;
    private TextView mTitleView;
    private TextView mDescriptionView;
    private TextView mDescriptionLabelView;
    private TextView mNrOfTagsView;
    private TextView mNrOfCommentViews;
    private TextView mPostedDateView;
    private TextView mTakenDateView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_flickr_photo_detail;
    }

    @Override
    protected void mapLayoutViews(final View root) {
        mContentContainer = (RelativeLayout) root.findViewById(R.id.content_container);
        mEmptyContainer = (LinearLayout) root.findViewById(R.id.empty_container);
        mPhotoView = (ImageView) root.findViewById(R.id.image);
        mTitleView = (TextView) root.findViewById(R.id.title);
        mDescriptionView = (TextView) root.findViewById(R.id.description);
        mDescriptionLabelView = (TextView) root.findViewById(R.id.description_label);
        mNrOfTagsView = (TextView) root.findViewById(R.id.tags);
        mNrOfCommentViews = (TextView) root.findViewById(R.id.comments);
        mPostedDateView = (TextView) root.findViewById(R.id.posted_date);
        mTakenDateView = (TextView) root.findViewById(R.id.taken_date);
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_PHOTO_ENTRY)) {
            mPhotoEntry = savedInstanceState.getParcelable(KEY_PHOTO_ENTRY);
        } else if (getArguments() != null && getArguments().containsKey(KEY_PHOTO_ENTRY)) {
            mPhotoEntry = getArguments().getParcelable(KEY_PHOTO_ENTRY);
        }

        if (mPhotoEntry != null) {
            mContentContainer.setVisibility(View.VISIBLE);
            mEmptyContainer.setVisibility(View.GONE);
            configurePhotoView();
            configureTitleView();
            configureDescriptionView();
            configureNrOfTagsView();
            configureNrOfCommentsView();
            configurePostedDateView();
            configureTakenDateView();
        } else {
            mContentContainer.setVisibility(View.GONE);
            mEmptyContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        outState.putParcelable(KEY_PHOTO_ENTRY, mPhotoEntry);
        super.onSaveInstanceState(outState);
    }

    /**
     * Returns a new instance of the {@link FlickrPhotoDetailFragment} that can show a details view of the photo,
     * and an empty screen if the photo passed is null.
     *
     * @param photo PhotoEntry
     * @return FlickrPhotoDetailFragment
     */
    public static FlickrPhotoDetailFragment newInstance(@Nullable final PhotoEntry photo) {
        final FlickrPhotoDetailFragment fragment = new FlickrPhotoDetailFragment();

        if (photo != null) {
            final Bundle args = new Bundle();
            args.putParcelable(KEY_PHOTO_ENTRY, photo);
            fragment.setArguments(args);
        }

        return fragment;
    }

    /**
     * Configures the PhotoView of this detail screen.
     */
    private void configurePhotoView() {
        final String url = mPhotoEntry.getPhotoImageUrl(PhotoSize.Dimension.MEDIUM);
        Picasso.with(getContext())
                .load(url)
                .fit()
                .into(mPhotoView);
    }

    /**
     * Configures the TitleView of this detail screen.
     */
    private void configureTitleView() {
        mTitleView.setText(mPhotoEntry.getPhotoTitle());
    }

    /**
     * Configures the DescriptionView of this detail screen.
     */
    private void configureDescriptionView() {
        final String description = mPhotoEntry.getPhotoDescription();
        if (description != null && !description.isEmpty()) {
            mDescriptionLabelView.setVisibility(View.VISIBLE);
            mDescriptionView.setVisibility(View.VISIBLE);
            mDescriptionView.setText(description);
        } else {
            mDescriptionLabelView.setVisibility(View.GONE);
            mDescriptionView.setVisibility(View.GONE);
        }
    }

    /**
     * Configures the NrOfTags view of this detail screen.
     */
    private void configureNrOfTagsView() {
        final String tags = mPhotoEntry.getPhotoNrOfTags() + getString(R.string.flickr_detail_tags);
        mNrOfTagsView.setText(tags);
    }

    /**
     * Configures the NrOfComments view of this detail screen.
     */
    private void configureNrOfCommentsView() {
        final String comments = mPhotoEntry.getPhotoNrOfComments() + getString(R.string.flickr_detail_comments);
        mNrOfCommentViews.setText(comments);
    }

    /**
     * Configures the PostedDate view of this detail screen.
     */
    private void configurePostedDateView() {
        final String posted = getString(R.string.flickr_detail_posted) + mPhotoEntry.getPhotoUploadDate();
        mPostedDateView.setText(posted);
    }

    /**
     * Configures the TakenDate view of this detail screen.
     */
    private void configureTakenDateView() {
        final String taken = getString(R.string.flickr_detail_taken) + mPhotoEntry.getPhotoTakenDate();
        mTakenDateView.setText(taken);
    }
}
