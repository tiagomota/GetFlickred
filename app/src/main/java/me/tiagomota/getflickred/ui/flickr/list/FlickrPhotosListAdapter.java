package me.tiagomota.getflickred.ui.flickr.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.tiagomota.getflickred.R;
import me.tiagomota.getflickred.data.model.PhotoSize;
import me.tiagomota.getflickred.ui.flickr.PhotoEntry;


class FlickrPhotosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<PhotoEntry> mPhotoEntries;
    private final OnPhotoSelectedListener mOnPhotoSelectedListener;

    private static final int NORMAL = 0x01;
    private static final int LOADING = 0x02;

    FlickrPhotosListAdapter(final List<PhotoEntry> photos,
                            final OnPhotoSelectedListener listener) {
        mPhotoEntries = photos;
        mOnPhotoSelectedListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == NORMAL) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_photo_item, parent, false);
            return new PhotoViewHolder(view);
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == NORMAL) {
            final PhotoViewHolder vh = (PhotoViewHolder) holder;
            final PhotoEntry entry = mPhotoEntries.get(position);

            // set title
            vh.mTitleView.setText(entry.getPhotoTitle());

            // set tags
            final String nrOfTags = entry.getPhotoNrOfTags() + vh.itemView.getContext().getString(R.string.flickr_detail_tags);
            vh.mTagsView.setText(nrOfTags);

            // set comments
            final String nrOfComments = entry.getPhotoNrOfComments() + vh.itemView.getContext().getString(R.string.flickr_detail_comments);
            vh.mCommentsView.setText(nrOfComments);

            // set photo view
            final String url = entry.getPhotoImageUrl(PhotoSize.Dimension.LARGE_SQUARE);
            if (url != null) {
                Picasso.with(vh.itemView.getContext()).load(url).fit().into(vh.mPhotoView);
            } else {
                Picasso.with(vh.itemView.getContext()).load(R.drawable.ic_image_black_48dp).fit().into(vh.mPhotoView);
            }

            // set click listener
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (mOnPhotoSelectedListener != null) {
                        mOnPhotoSelectedListener.onSelected(entry);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoEntries.size();
    }

    @Override
    public int getItemViewType(final int position) {
        if (mPhotoEntries.get(position) == null) {
            return LOADING;
        } else {
            return NORMAL;
        }
    }

    /**
     * Adds a loading indicator to the list.
     */
    void addLoadingItem() {
        if (mPhotoEntries.get(getItemCount() - 1) != null) {
            mPhotoEntries.add(null); // add empty one to simulate loading item
            notifyItemInserted(getItemCount());
        }
    }

    /**
     * Removed loading indicator from the list.
     */
    void removeLoadingItem() {
        if (mPhotoEntries.get(getItemCount() - 1) == null) {
            final int pos = getItemCount() - 1;
            mPhotoEntries.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    /**
     * Adds all the entries to the local list.
     *
     * @param entries List
     */
    void addAll(final List<PhotoEntry> entries) {
        final int startInsertion = getItemCount();
        mPhotoEntries.addAll(entries);
        notifyItemRangeInserted(startInsertion, getItemCount());
    }


    interface OnPhotoSelectedListener {
        /**
         * Callback listener to when the user selects a Photo from the list.
         *
         * @param photoEntry PhotoEntry
         */
        void onSelected(final PhotoEntry photoEntry);
    }


    /**
     * ViewHolder for Photo list items.
     */
    private static final class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView mPhotoView;
        TextView mTitleView;
        TextView mTagsView;
        TextView mCommentsView;

        PhotoViewHolder(final View view) {
            super(view);

            mPhotoView = (ImageView) view.findViewById(R.id.photo);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mTagsView = (TextView) view.findViewById(R.id.tags);
            mCommentsView = (TextView) view.findViewById(R.id.comments);
        }
    }

    /**
     * ViewHolder for the Loading indicator list items.
     */
    private static final class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(final View view) {
            super(view);
        }
    }
}
