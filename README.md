# Microdon — the model of a small social network, in Java

L2 double degree in mathematics and computer science (Université Sorbonne Paris Nord, Institut Galilée),
course *Programmation orientée objet — pédagogie inversée*, 2023–2024. Two graded assignments ("devoirs").

Microdon is a micro-blogging network: users publish posts, follow each other, like posts (but not their own)
and read a **news feed** that interleaves their own posts with those of everyone they follow, newest first.

## Devoir 1 — `sociald1`

`User`, `Post` and `NewsFeed`, written against the specification and the JUnit tests given with the
subject (`src/test`). A Maven project:

```sh
cd devoir1 && mvn test
```

## Devoir 2 — `social.model`

The model reworked around an interface `Post` with two implementations, `SimplePost` and `RePost`
(sharing someone else's post), and a generic **`FusionSortedIterator`**: the news feed is the fusion of the timelines of the people you
follow. Mine gathers the elements of every timeline into one list, sorts it with the comparator, then walks
it back and forth (`ListIterator`: `next`, `previous`, indices); at each step it also advances the timeline the
post came from and remembers it (`lastIterator`). The structural operations (`remove`, `set`, `add`) are
unsupported.

| mine | given with the subject |
|------|------------------------|
| `User.java`, `SimplePost.java`, `RePost.java`, `FusionSortedIterator.java` | `Post.java`, `NewsFeed.java`, `ExtendedListIterator.java`, the tests in `test/` and the Swing interface in `ui/` |

On my portfolio, the merge of sorted feeds is animated in the browser: <https://youyou354.github.io/>.
