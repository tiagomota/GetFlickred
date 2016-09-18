package me.tiagomota.getflickred.ui.flickr;

import javax.inject.Inject;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.data.model.UserInfo;
import me.tiagomota.getflickred.ui.base.injection.scope.PersistentScope;
import me.tiagomota.getflickred.ui.base.mvp.BasePresenter;
import me.tiagomota.getflickred.utils.RxUtils;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@PersistentScope
class FlickrPresenter extends BasePresenter<FlickrView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    FlickrPresenter(final DataManager dataManager) {
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
                .flatMap(new Func1<User, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(final User user) {
                        // TODO handle this error
                        return mDataManager.getUserInfo(user.getId());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<UserInfo>() {
                            @Override
                            public void call(final UserInfo userInfo) {
                                if (userInfo.isStatusSuccess()) {
                                    getView().onUserFound(
                                            userInfo.getId(),
                                            userInfo.getUsername(),
                                            userInfo.getRealName()
                                    );
                                } else {
                                    getView().onUserNotFound(userInfo.getMessage());
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                getView().onUserNotFound(throwable.getMessage());
                            }
                        }
                );
    }

}
