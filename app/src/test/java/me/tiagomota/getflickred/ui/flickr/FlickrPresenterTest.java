package me.tiagomota.getflickred.ui.flickr;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import me.tiagomota.getflickred.data.DataManager;
import me.tiagomota.getflickred.data.model.User;
import me.tiagomota.getflickred.util.RxSchedulersOverrideRule;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlickrPresenterTest {

    private FlickrView mFlickrView;
    private DataManager mDataManager;
    private FlickrPresenter mPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mFlickrView = mock(FlickrView.class);
        mDataManager = mock(DataManager.class);
        mPresenter = new FlickrPresenter(mDataManager);
        mPresenter.attachView(mFlickrView);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }


    @Test
    public void findUserNotFoundTest() {
        final User user = mock(User.class);
        when(mDataManager.getUser(anyString())).thenReturn(Observable.just(user));

        // test status message false
        when(user.isStatusSuccess()).thenReturn(false);
        mPresenter.findUser(anyString());
        verify(mFlickrView).onUserNotFound(anyString());
        verify(mFlickrView, never()).onUserFound(any(User.class));
    }

    @Test
    public void findUserFoundTest() {
        final User user = mock(User.class);
        when(mDataManager.getUser(anyString())).thenReturn(Observable.just(user));

        // test status message ok
        when(user.isStatusSuccess()).thenReturn(true);
        mPresenter.findUser(anyString());
        verify(mFlickrView).onUserFound(user);
        verify(mFlickrView, never()).onUserNotFound(anyString());
    }

    @Test
    public void findUserErrorTest() {
        when(mDataManager.getUser(anyString())).thenReturn(
                Observable.<User>error(new RuntimeException())
        );

        mPresenter.findUser(anyString());
        verify(mFlickrView, never()).onUserFound(any(User.class));
        verify(mFlickrView).onUserNotFound(anyString());
    }
}
