package pl.codeleak.demos.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Objects;

public class TodoMvcPage implements TodoMvc {

    private Page page;

    public TodoMvcPage(Page page) {
        Objects.requireNonNull(page, "Page is required");
        this.page = page;
    }

    @Override
    public void navigateTo() {
        page.navigate("https://todomvc.com/examples/vanillajs");
    }

    public void createTodo(String todoName) {
        page.locator(".new-todo").type(todoName);
        page.locator(".new-todo").press("Enter");
    }

    public void createTodos(String... todoNames) {
        for (String todoName : todoNames) {
            createTodo(todoName);
        }
    }

    public int getTodosLeft() {
        return Integer.parseInt(page.locator(".todo-count > strong").textContent());
    }

    public boolean todoExists(String todoName) {
        return getTodos().stream().anyMatch(todoName::equals);
    }

    public int getTodoCount() {
        return page.locator(".todo-list li").count();
    }

    public List<String> getTodos() {
        return page.locator(".todo-list li")
                .allTextContents();
    }

    public void renameTodo(String todoName, String newTodoName) {
        Locator todoToEdit = getTodoElementByName(todoName);
        todoToEdit.dblclick();
        Locator todoEditInput = todoToEdit.locator("input.edit");
        todoEditInput.clear();
        todoToEdit.type(newTodoName);
        todoToEdit.press("Enter");
    }

    public void removeTodo(String todoName) {
        Locator todoToRemove = getTodoElementByName(todoName);
        todoToRemove.hover();
        todoToRemove.locator("button.destroy").click();
    }

    public void completeTodo(String todoName) {
        Locator todoToComplete = getTodoElementByName(todoName);
        todoToComplete.locator("input.toggle").click();
    }

    public void completeAllTodos() {
        page.locator(".toggle-all").click();
    }

    public void showActive() {
        page.locator("a[href='#/active']").click();
    }

    public void showCompleted() {
        page.locator("a[href='#/completed']").click();
    }

    public void clearCompleted() {
        page.locator(".clear-completed").click();
    }

    private Locator getTodoElementByName(String todoName) {
        return page.locator(".todo-list li")
                .all()
                .stream()
                .filter(locator -> todoName.equals(locator.textContent()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo with name " + todoName + " not found!"));
    }

}
