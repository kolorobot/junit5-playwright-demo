package pl.codeleak.demos.playwright;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TodoMvcParameterizedTests extends PlaywrightTest {

    TodoMvcPage todoMvc;

    @BeforeEach
    void navigateTo() {
        todoMvc = new TodoMvcPage(page);
        todoMvc.navigateTo();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/todos.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Creates Todo with given name")
    void createsTodo(String todo) {
        todoMvc.createTodo(todo);
        assertSingleTodoShown(todo);
    }

    @ParameterizedTest(name = "{index} - {0}, done = {1}")
    @CsvFileSource(resources = "/todos.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Creates and optionally removes Todo with given name")
    void createsAndRemovesTodo(String todo, boolean done) {

        todoMvc.createTodo(todo);
        assertSingleTodoShown(todo);

        todoMvc.showActive();
        assertSingleTodoShown(todo);

        if (done) {
            todoMvc.completeTodo(todo);
            assertNoTodoShown(todo);

            todoMvc.showCompleted();
            assertSingleTodoShown(todo);
        }

        todoMvc.removeTodo(todo);
        assertNoTodoShown(todo);
    }

    private void assertSingleTodoShown(String todo) {
        assertAll(
                () -> assertThat(todoMvc.getTodoCount()).isOne(),
                () -> assertThat(todoMvc.todoExists(todo)).isTrue()
        );
    }

    private void assertNoTodoShown(String todo) {
        assertAll(
                () -> assertThat(todoMvc.getTodoCount()).isZero(),
                () -> assertThat(todoMvc.todoExists(todo)).isFalse()
        );
    }
}
