package com.testconfig.runner;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/4/24, MarkHuang,new
 * </ul>
 * @since 2018/4/24
 */
public class DbTestRunner extends SpringJUnit4ClassRunner {
    public DbTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new DbTestRunnerListener());
        super.run(notifier);
    }
}
