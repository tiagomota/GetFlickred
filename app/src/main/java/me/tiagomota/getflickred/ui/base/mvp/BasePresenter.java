package me.tiagomota.getflickred.ui.base.mvp;

/**
 * Base Presenter implementation that keeps a reference to the View it is associeted with,
 * in order to be able to talk back to it.
 *
 * @param <T>
 */
public class BasePresenter<T extends UiView> implements Presenter<T> {

    private T mView;

    /**
     * Returns the associated View.
     *
     * @return T
     */
    public T getView() {
        return mView;
    }


    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
