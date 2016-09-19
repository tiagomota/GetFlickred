package me.tiagomota.getflickred.ui.flickr;

import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.mvp.UiView;

interface FlickrView extends UiView {

    /**
     * The {@link FlickrPresenter} can update the FlickrView, when it retrieves a successful answer
     * when searching for a user.
     *
     * @param user User
     */
    void onUserFound(final User user);

    /**
     * The {@link FlickrPresenter} can update the FlickrView, when it retrieves an error answer
     * when searching for a user.
     *
     * @param message String
     */
    void onUserNotFound(final String message);
}
