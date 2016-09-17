package me.tiagomota.getflickred.ui.home;

import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.mvp.UiView;

interface HomeView extends UiView {

    /**
     * The {@link HomePresenter} can update the HomeView, when it retrieves a successful answer
     * when searching for a user.
     *
     * @param user User
     */
    void onSuccessFindingUser(final User user);

    /**
     * The {@link HomePresenter} can update the HomeView, when it retrieves an error answer
     * when searching for a user.
     *
     * @param message String
     */
    void onErrorFindingUser(final String message);

}
