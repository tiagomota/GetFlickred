package me.tiagomota.getflickred.base;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import me.tiagomota.getflickred.ui.flickr.FlickrActivity;

public class BaseUiTest {
    protected final TestComponentRule mComponent = new TestComponentRule(InstrumentationRegistry.getTargetContext());
    protected final ActivityTestRule<FlickrActivity> mActivity = new ActivityTestRule<>(FlickrActivity.class, false, false);

    @Rule
    public final TestRule chain = RuleChain.outerRule(mComponent).around(mActivity);

    @Before
    public void setUp() throws Exception {
        mActivity.launchActivity(null);
    }
}
