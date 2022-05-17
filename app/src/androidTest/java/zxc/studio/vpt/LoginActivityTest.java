package zxc.studio.vpt;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {

    @Rule
    ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            // use 'activity'.
        });
    }


}
