import org.example.DocumentManager;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentManagerTest {

    @Test
    public void testSave() {
        DocumentManager documentManager = new DocumentManager();
        DocumentManager.Author author = DocumentManager.Author.builder()
                .name("Some Name")
                .build();
        DocumentManager.Document document = DocumentManager.Document.builder()
                .id("")
                .title("Title")
                .content("Some content")
                .author(author)
                .created(Instant.now())
                .build();

        DocumentManager.Document result = DocumentManager.Document.builder()
                .id("1")
                .title("Title")
                .content("Some content")
                .author(author)
                .created(Instant.now())
                .build();

        assertEquals(documentManager.save(document), result);

        document.setId("2");
        result = documentManager.save(document);
        assertEquals(document, result);
    }

    @Test
    public void findingByIDTest() {
        DocumentManager documentManager = new DocumentManager();
        DocumentManager.Author author = DocumentManager.Author.builder()
                .name("Some Name")
                .build();
        DocumentManager.Document document = DocumentManager.Document.builder()
                .id("1")
                .title("Title")
                .content("Some content")
                .author(author)
                .created(Instant.now())
                .build();

        documentManager.save(document);

        document.setId("2");
        documentManager.save(document);

        document.setId("3");
        documentManager.save(document);

        document.setId("4");
        documentManager.save(document);

        assertEquals(documentManager.findById("4"), Optional.of(document));

        document.setId("2");
        assertEquals(documentManager.findById("2"), Optional.of(document));
    }
}
