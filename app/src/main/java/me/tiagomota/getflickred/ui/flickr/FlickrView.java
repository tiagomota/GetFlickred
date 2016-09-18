package me.tiagomota.getflickred.ui.flickr;

import me.tiagomota.getflickred.ui.base.mvp.UiView;

interface FlickrView extends UiView {

    /**
     * The {@link FlickrPresenter} can update the FlickrView, when it retrieves a successful answer
     * when searching for a user.
     *
     * @param userId String
     * @param username String
     * @param realName String
     */
    void onUserFound(final String userId, final String username, final String realName);

    /**
     * The {@link FlickrPresenter} can update the FlickrView, when it retrieves an error answer
     * when searching for a user.
     *
     * @param message String
     */
    void onUserNotFound(final String message);
}
