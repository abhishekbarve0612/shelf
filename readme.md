# Shelf

A small Spring Boot app for keeping track of books. I'm building it to practice Java and
Spring hands-on: one small feature per commit, with a note on what I did and what I
understood along the way.

## Learning log

### 1. Getting the app to boot

I started with the smallest thing that proves the setup works: a `GET /` endpoint that
returns `{ "message": "Shelf is running!" }`, and a test that checks it.

- I set the project up with Gradle, Spring Boot and Java
- I returned a small `record Hello(String message)` instead of a plain string. That showed
  me Spring converts whatever object I return into JSON on its own.
- I wrote the test with `@SpringBootTest` and `MockMvcTester`. It loads the whole app but
  calls the endpoint without starting a real server, so I can check the actual JSON a
  client would get.

**What I understood:** a Spring endpoint is just a method with an annotation. The
framework handles the HTTP and the JSON, and I only write the part that decides what
to return.

### 2. Saving and listing books

I added `POST /books` to save a book and `GET /books` to list them, backed by a real
database.

**How I structured it**
- I put everything for the feature in a `book/` package instead of splitting by layer
  (`controllers/`, `services/`, ...). Everything about books is in one place.
- I split the work into three layers: `BookController` handles HTTP, `BookService` holds
  the logic and the transactions, and `BookRepository` talks to the database. Each class
  has one job, and the controller never touches the database directly.
- I used separate request and response classes (`CreateBookRequest`, `BookResponse`).
- `POST` returns `201 Created` instead of `200`, because a new resource was created.
- I used H2 as a file database in `./data/` so books survive a restart, and an in-memory
  one for tests so the tests never touch my real data.
- I turned off `open-in-view`, so database access stays inside the service layer and
  doesn't leak into the web layer.


**Decisions I made and then revisited**
- *Naming.* My first version called the classes `Controller`, `Service` and `Repository`.
  My thinking was that the `book` package already says they're about books. Then I learned
  that Java doesn't work like Python or JS here. The names clashed with Spring's own `@Service`, so I
  had to write the annotation's full name. Spring also names beans after the class name, so
  a second feature with its own `Service` would crash the app at startup. And Java can't
  rename an import to tell them apart. So I renamed everything with a `Book` prefix. I also
  learned that the Java way to say "only used inside this package" is package-private
  visibility, not a shorter name.
- *Records vs classes for DTOs.* The guide I followed used a record with suffix `View`, and
  it confused me, so I wrote regular classes instead. Later I understood that a record
  isn't a different pattern. It's a shorter way to write the same DTO. What confused me was
  the name "View" and how the guide used it, not records themselves. I've kept classes for
  now and might switch later.

**A bug I hit**

My test kept failing with `400 Bad Request` instead of `201`. The cause was a missing `:`
in the JSON inside my test, not anything in the API. What I took away: a `400` where my
controller never ran means Spring rejected the request before it reached my code, and the
`Resolved [HttpMessageNotReadableException ...]` line in the test output names the exact
problem. Next time I'll read that line first.

**What I understood:**

Most of a feature like this is deciding where each responsibility
lives. Spring Data even writes the query for me from the method name
(`findAllByOrderByIdAsc()`), so my job is mostly the structure.