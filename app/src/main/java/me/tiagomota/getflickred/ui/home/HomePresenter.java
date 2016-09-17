package me.tiagomota.getflickred.ui.home;

import javax.inject.Inject;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.ui.base.injection.scope.PersistentScope;
import me.tiagomota.getflickred.ui.base.mvp.BasePresenter;
import me.tiagomota.getflickred.utils.RxUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@PersistentScope
class HomePresenter extends BasePresenter<HomeView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    HomePresenter(final DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        RxUtils.cancel(mSubscription);
        super.detachView();
    }

    /**
     * Requests the DataManager to find the user that has the given username.
     *
     * @param username String
     */
    void findUser(final String username) {
        mSubscription = mDataManager.getUser(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<User>() {
                            @Override
                            public void call(User user) {
                                if (user.isStatusSuccess()) {
                                    getView().onSuccessFindingUser(user);
                                } else {
                                    getView().onErrorFindingUser(user.getMessage());
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                getView().onErrorFindingUser(throwable.getMessage());
                            }
                        }
                );
    }
}
