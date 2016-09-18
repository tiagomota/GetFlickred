package me.tiagomota.getflickred.ui.flickr.list;

import java.util.List;

import me.tiagomota.getflickred.ui.base.mvp.UiView;

interface FlickrPhotosListView extends UiView {


    /**
     * Allows the {@link FlickrPhotosListPresenter} to request the {@link FlickrPhotosListView}
     * to update the UI by populating the list with the first photos batch loaded.
     */
    void handleFirstPhotosPageLoaded(final List<PhotoEntry> firstEntries);

    /**
     * Allows the {@link FlickrPhotosListPresenter} to request the {@link FlickrPhotosListView}
     * to update the list of Photos by adding the ones passed as argument.
     *
     * @param newEntries List
     */
    void handleNewPhotosPageLoaded(final List<PhotoEntry> newEntries);

    /**
     * Allows the {@link FlickrPhotosListPresenter} to request the {@link FlickrPhotosListView}
     * to display the error in the more appropriate way.
     *
     * @param message String
     */
    void handleErrorLoadingNewPhotosPage(final String message);
}
