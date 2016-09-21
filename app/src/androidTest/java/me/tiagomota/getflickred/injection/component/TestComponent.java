package me.tiagomota.getflickred.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import me.tiagomota.getflickred.injection.module.ApplicationTestModule;
import me.tiagomota.getflickred.injection.module.DataTestModule;
import me.tiagomota.getflickred.ui.base.injection.components.ApplicationComponent;

@Singleton
@Component(modules = {
        ApplicationTestModule.class,
        DataTestModule.class
})
public interface TestComponent extends ApplicationComponent {

}
