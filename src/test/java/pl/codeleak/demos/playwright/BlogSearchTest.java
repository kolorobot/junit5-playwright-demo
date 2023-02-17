package pl.codeleak.demos.playwright;

import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

class BlogSearchTest extends PlaywrightTest {

    @Test
    void searchesForTermAndGetsResults() {
        page.navigate("https://blog.codeleak.pl");
        page.locator("button[aria-label='Search']").click();
        page.getByPlaceholder("Search this blog").fill("junit 5");
        page.getByPlaceholder("Search this blog").press("Enter");

        assertThat(page).hasURL("https://blog.codeleak.pl/search?q=junit+5");
        assertThat(page.locator("article .post")).hasCount(20);
        assertThat(page.getByText("Showing posts matching the search for junit 5"))
                .isVisible();
    }
}
