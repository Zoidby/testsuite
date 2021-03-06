package org.jboss.hal.testsuite.fragment.shared.layout;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.fragment.PopUpFragment;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.SettingsWindow;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.Workaround;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by jcechace on 18/02/14.
 */
public class Footer extends BaseFragment {
    @ArquillianResource
    private TakesScreenshot screenshot;

    public String getDisplayedVersion() {
        String label = PropUtils.get("footer.version.label");
        ByJQuery selector = ByJQuery.selector(".footer-link[title='" + label + "']");
        WebElement version = root.findElement(selector);
        return version.getText();
    }

    /**
     *  Returns footer link based on its text label.
     *
     *
     * @param label
     * @return
     */
    public WebElement getLink(String label) {
        ByJQuery selector = ByJQuery.selector(".footer-link:contains('" + label + "')");
        WebElement link = root.findElement(selector);

        return link;
    }


    /**
     * Open tools popup menu.
     *
     * @return tools menu
     */
    public PopUpFragment openTools() {
        String label = PropUtils.get("footer.links.tools.label");
        getLink(label).click();

        Console console = Console.withBrowser(browser);
        PopUpFragment popup = console.openedPopup(PopUpFragment.class, PopUpFragment.ROOT_SELECTOR);

        return popup;
    }

    /**
     *  Opens console settings.
     *
     * @return settings window
     */
    public SettingsWindow openSettings() {
        String label = PropUtils.get("footer.links.settings.label");
        getLink(label).click();

        Workaround.withBrowser(browser)
                  .clickLinkUntilWindowIsOpened(getLink(label), WindowFragment.ROOT_SELECTOR);

        Console console = Console.withBrowser(browser);
        SettingsWindow window = console.openedWindow(SettingsWindow.class);

        return window;
    }

}
