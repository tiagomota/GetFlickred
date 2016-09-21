package me.tiagomota.getflickred.ui.base.mvp;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface Presenter<T extends UiView> {

    /**
     * Attach the view by storing the reference to the view.
     *
     * @param view T
     */
    void attachView(final T view);

    /**
     * Detach by removing the reference to the stored view.
     */
    void detachView();
}
