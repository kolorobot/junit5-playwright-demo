package pl.codeleak.demos.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

abstract class PlaywrightTest {

    Playwright playwright;
    Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void createBrowserContext() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        page = context.newPage();
    }

    @AfterEach
    void closeBrowserContext(TestInfo testInfo) {
        var traceName = testInfo.getTestClass().get().getSimpleName() +
                "-" + testInfo.getTestMethod().get().getName() + "-trace.zip";
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("build/reports/traces" + traceName)));


        page.close();
        context.close();
    }

}
