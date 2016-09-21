package me.tiagomota.getflickred.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * This rule registers SchedulerHooks for RxJava and RxAndroid to ensure that subscriptions
 * always subscribeOn and observeOn Schedulers.immediate().
 * Warning, this rule will reset RxAndroidPlugins and RxJavaPlugins before and after each test so
 * if the application code uses RxJava plugins this may affect the behaviour of the testing method.
 */
public class RxSchedulersOverrideRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.getInstance().reset();
                RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                    @Override
                    public Scheduler getMainThreadScheduler() {
                        return Schedulers.immediate();
                    }
                });

                RxJavaHooks.reset();
                RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
                    @Override
                    public Scheduler call(final Scheduler scheduler) {
                        return Schedulers.immediate();
                    }
                });
                RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
                    @Override
                    public Scheduler call(final Scheduler scheduler) {
                        return Schedulers.immediate();
                    }
                });

                base.evaluate();

                RxAndroidPlugins.getInstance().reset();
                RxJavaHooks.reset();
            }
        };
    }
}
