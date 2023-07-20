/*
 * Denna klass innehåller kod som är gemensam för många JUnit-testfall till inlämningsuppgiftens olika delar.
 * För att dessa ska fungera måste du ladda ner denna fil och lägga den i samma katalog som koden som ska testas.
 */

/*
 * Klassen i denna fil innehåller kontroller som är gemensamma
 * för alla test där det inte borde förekomma kommunikation med
 * användaren. För att dessa test ska fungera måste denna fil 
 * finnas i samma katalog.
 * 
 * Den huvudsakliga kontrollen är att inget skrivs till System.out. 
 * Om du har ett behov av att skriva ut något, till exempel för 
 * debugging skriv ut till System.err istället. Se dock till att
 * ta bort detta innan du lämnar in i ilearn.
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

public abstract class ApiBaseTest extends BaseTest {

	public static final int VERSION = 1;

	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@BeforeEach
	public void redirectOutput() {
		System.setOut(new PrintStream(out));
	}

	@AfterEach
	public void checkNothingWrittenToSystemOut() {
		String written = out.toString();
		assertTrue(written.isEmpty(),
				"Din kod har skrivit till System.out vilket den inte ska göra i denna uppgift. Det som skrevs var: "
						+ written);
	}

}
