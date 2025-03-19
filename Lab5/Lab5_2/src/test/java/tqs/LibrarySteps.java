package tqs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LibrarySteps {

    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        String isoDateString = String.format("%s-%s-%s", year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(isoDateString, formatter);
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @Given("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addAnotherBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @Given("I have the following books in the store")
    public void haveBooksInTheStoreByMap(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            library.addBook(new Book(columns.get("title"), columns.get("author"), LocalDate.now()));
        }
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(LocalDate from, LocalDate to) {
        result = library.findBooks(from, to);
    }

    @When("I search for books by author {string}")
    public void searchForBooksByAuthor(String author) {
        result = library.findBooksByAuthor(author);
    }

    @Then("I find {int} books")
    public void i_find_books(Integer booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
