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


class FlickrPhotosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<PhotoEntry> mPhotoEntries;

    FlickrPhotosListAdapter(final List<PhotoEntry> photos) {
        mPhotoEntries = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_photos_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final PhotoViewHolder vh = (PhotoViewHolder) holder;
        final PhotoEntry entry = mPhotoEntries.get(position);

        // set title
        vh.mTitleView.setText(entry.getPhotoTitle());

        // set uploaded at
        final String uploadedDate = vh.itemView.getContext().getString(R.string.flickr_list_item_uploaded_at) + entry.getPhotoUploadDate();
        vh.mUploadedAtView.setText(uploadedDate);

        // set taken date
        final String takenDate = vh.itemView.getContext().getString(R.string.flickr_list_item_taken_at) + entry.getPhotoTakenDate();
        vh.mTakenAtView.setText(takenDate);

        // set photo view
        final String url = entry.getPhotoImageUrl(PhotoSize.Dimension.SQUARE);
        if (url != null) {
            Picasso.with(vh.itemView.getContext())
                    .load(url)
                    .into(vh.mPhotoView);
        } else {
            // TODO
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoEntries.size();
    }


    public void add(final PhotoEntry entry) {
        mPhotoEntries.add(entry);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(final List<PhotoEntry> entries) {
        final int startInsertion = getItemCount();
        mPhotoEntries.addAll(entries);
        notifyItemRangeInserted(startInsertion, getItemCount());
    }


    private static final class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView mPhotoView;
        TextView mTitleView;
        TextView mUploadedAtView;
        TextView mTakenAtView;

        PhotoViewHolder(final View view) {
            super(view);

            mPhotoView = (ImageView) view.findViewById(R.id.photo);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mUploadedAtView = (TextView) view.findViewById(R.id.upload_date);
            mTakenAtView = (TextView) view.findViewById(R.id.taken_date);
        }
    }
}
