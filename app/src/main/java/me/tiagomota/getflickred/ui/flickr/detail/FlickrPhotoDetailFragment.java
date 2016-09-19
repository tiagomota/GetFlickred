package me.tiagomota.getflickred.ui.flickr.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.tiagomota.getflickred.BuildConfig;
import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.ui.base.BaseFragment;
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;
import me.tiagomota.getflickred.utils.ViewUtils;

public class FlickrPhotoDetailFragment extends BaseFragment {

    public static final String TAG = "FlickrPhotoDetailFragment";
    private static final String KEY_PHOTO_ENTRY = "key_photo_entry";

    //  The photo selected by the user
    private PhotoEntry mPhotoEntry;

    // Content Screen views
    private NestedScrollView mContentContainer;
    private ImageView mPhotoView;
    private TextView mTitleView;
    private TextView mDescriptionView;
    private TextView mDescriptionLabelView;
    private TextView mNrOfTagsView;
    private TextView mNrOfCommentView;
    private TextView mPostedDateView;
    private TextView mTakenDateView;

    // Empty screen views
    private RelativeLayout mEmptyContainer;
    private ImageView mEmptyImageView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_flickr_photo_detail;
    }

    @Override
    protected void mapLayoutViews(final View root) {
        // Content screen
        mContentContainer = (NestedScrollView) root.findViewById(R.id.content_container);
        mPhotoView = (ImageView) root.findViewById(R.id.image);
        mTitleView = (TextView) root.findViewById(R.id.title);
        mDescriptionView = (TextView) root.findViewById(R.id.description);
        mDescriptionLabelView = (TextView) root.findViewById(R.id.description_label);
        mNrOfTagsView = (TextView) root.findViewById(R.id.tags);
        mNrOfCommentView = (TextView) root.findViewById(R.id.comments);
        mPostedDateView = (TextView) root.findViewById(R.id.posted_date);
        mTakenDateView = (TextView) root.findViewById(R.id.taken_date);

        // Empty screen
        mEmptyContainer = (RelativeLayout) root.findViewById(R.id.empty_container);
        mEmptyImageView = (ImageView) root.findViewById(R.id.empty_image);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFragmentComponent().inject(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_PHOTO_ENTRY)) {
            mPhotoEntry = savedInstanceState.getParcelable(KEY_PHOTO_ENTRY);
        } else if (getArguments() != null && getArguments().containsKey(KEY_PHOTO_ENTRY)) {
            mPhotoEntry = getArguments().getParcelable(KEY_PHOTO_ENTRY);
        }

        if (mPhotoEntry != null) {
            configureContentScreen();
        } else {
            configureEmptyScreen();
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        outState.putParcelable(KEY_PHOTO_ENTRY, mPhotoEntry);
        super.onSaveInstanceState(outState);
    }

    /**
     * Returns a new instance of the {@link FlickrPhotoDetailFragment} that can show a details view of the photo, and an empty screen if the photo passed is
     * null.
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
     * Configures all the views of the content screen.
     */
    private void configureContentScreen() {
        mContentContainer.setVisibility(View.VISIBLE);
        mEmptyContainer.setVisibility(View.GONE);
        configurePhotoView();
        configureTitleView();
        configureDescriptionView();
        configureNrOfTagsView();
        configureNrOfCommentsView();
        configurePostedDateView();
        configureTakenDateView();
    }

    /**
     * Configures all views of the empty screen.
     */
    private void configureEmptyScreen() {
        mContentContainer.setVisibility(View.GONE);
        mEmptyContainer.setVisibility(View.VISIBLE);
        mEmptyImageView.setImageDrawable(
                ViewUtils.getTintedDrawable(getContext(), R.drawable.ic_image_black_48dp, R.color.textColorPrimary)
        );
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
            //noinspection deprecation
            mDescriptionView.setText(Html.fromHtml(description));
        } else {
            mDescriptionLabelView.setVisibility(View.GONE);
            mDescriptionView.setVisibility(View.GONE);
        }
    }

    /**
     * Configures the NrOfTags view of this detail screen.
     */
    private void configureNrOfTagsView() {
        try {
            final String tags = mPhotoEntry.getPhotoNrOfTags() + getString(R.string.flickr_detail_tags);
            mNrOfTagsView.setText(tags);
        } catch (final NullPointerException ex) {
            mNrOfTagsView.setVisibility(View.GONE);
        }
    }

    /**
     * Configures the NrOfComments view of this detail screen.
     */
    private void configureNrOfCommentsView() {
        try {
            final String comments = mPhotoEntry.getPhotoNrOfComments() + getString(R.string.flickr_detail_comments);
            mNrOfCommentView.setText(comments);
        } catch (final NullPointerException ex) {
            mNrOfCommentView.setVisibility(View.GONE);
        }
    }

    /**
     * Configures the PostedDate view of this detail screen.
     */
    private void configurePostedDateView() {
        try {
            final String posted = getString(R.string.flickr_detail_posted) + mPhotoEntry.getPhotoUploadDate();
            mPostedDateView.setText(posted);
        } catch (final NullPointerException ex) {
            mPostedDateView.setVisibility(View.GONE);
        }
    }

    /**
     * Configures the TakenDate view of this detail screen.
     */
    private void configureTakenDateView() {
        try {
            final String taken = getString(R.string.flickr_detail_taken) + mPhotoEntry.getPhotoTakenDate();
            mTakenDateView.setText(taken);
        } catch (final NullPointerException ex) {
            mTakenDateView.setVisibility(View.GONE);
        }
    }
}
