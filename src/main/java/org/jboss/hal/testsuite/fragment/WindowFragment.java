package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.jboss.hal.testsuite.util.Workaround;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jcechace on 18/02/14.
 */
public class WindowFragment extends BaseFragment {

    private static final Logger log = LoggerFactory.getLogger(WindowFragment.class);

    public static final By ROOT_SELECTOR = By.className(PropUtils.get("modals.window.class"));

    protected boolean closed;

    public boolean isClosed() {
        try {
            if (closed) {
                return true;
            }
            root.isDisplayed();
            return false;
        } catch (NoSuchElementException ignored) {
            return true;
        }
    }


    /**
     * Waits until at least one window is closed.
     * @param windowCount
     * @param fail
     */
    public void waitUntilClosed(int windowCount, boolean fail) {
        Workaround.withBrowser(browser).waitUntilWindowIsClosed(windowCount, fail);
    }

    /**
     * Waits until window is closed.
     * Use this method ONLY when ensured that NO MORE than 1 window is opened  at the time.
     * @param fail
     */
    public void waitUntilClosed(boolean fail) {
        try {
            Graphene.waitGui().until().element(root).is().not().present();
        } catch (TimeoutException e) {
            log.info("After waiting period some window is still opened (this may or may not be an issue).");
            if (fail) {
                throw e;
            }
        }
    }

    public void waitUntilClosed() {
        waitUntilClosed(true);
    }


    public void close() {
        By selector = By.className(PropUtils.get("modals.window.close.class"));
        WebElement close = root.findElement(selector);

        int windowCount = Console.withBrowser(browser).getWindowCount();

        close.click();

        waitUntilClosed(windowCount, true);

        closed = true;
    }

    public void cancel() {
        String label = PropUtils.get("modals.window.cancel.label");
        clickButton(label);

        waitUntilClosed();
        closed = true;
    }

    public void clickButton(String label) {
        log.debug("Trying to click \"" + label + "\" button in current window");
        By selector = ByJQuery.selector("button:contains(" + label + "):visible");
        Graphene.waitGui().until().element(selector).is().visible();
        WebElement button = root.findElement(selector);

        button.click();
    }

    public String getHeadTitle() {
        By selector = By.className(PropUtils.get("modals.window.title.class"));
        WebElement title = root.findElement(selector);

        return title.getText();
    }

    public String getTitle() {
        By selector = By.tagName(PropUtils.get("modals.window.content.title.tag"));
        WebElement title = root.findElement(selector);

        return title.getText();
    }

}
