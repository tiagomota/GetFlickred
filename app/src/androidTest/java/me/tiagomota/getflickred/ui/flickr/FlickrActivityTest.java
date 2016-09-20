package me.tiagomota.getflickred.ui.flickr;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import me.tiagomota.getflickred.TestComponentRule;
import me.tiagomota.getflickred.TestDataFactory;
import me.tiagomota.getflickred.data.model.User;
import rx.Observable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FlickrActivityTest {
    public final TestComponentRule component = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<FlickrActivity> activity =
            new ActivityTestRule<>(FlickrActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(activity);


    @Test
    public void useAppContext() throws Exception {
        User user = TestDataFactory.get().makeUser();
        when(component.getMockDataManager().getUser(anyString()))
                .thenReturn(Observable.just(user));
        activity.launchActivity(null);
    }
}
