/*
 * Denna klass innehåller kod som är gemensam för många JUnit-testfall till inlämningsuppgiftens olika delar.
 * För att dessa ska fungera måste du ladda ner denna fil och lägga den i samma katalog som koden som ska testas.
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

public abstract class IOBaseTest extends BaseTest {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@BeforeEach
	public void setSystemOut() {
		System.setOut(new PrintStream(out));
	}

	protected SystemOut out() {
		return new SystemOut();
	}

	private class TrimmedSystemOut extends SystemOut {
		@Override
		public String get() {
			return super.get().trim();
		}
	}

	public class SystemOut {
		public String get() {
			return out.toString();
		}

		public SystemOut trim() {
			return new TrimmedSystemOut();
		}

		@Override
		public String toString() {
			return out.toString();
		}

		public void reset() {
			out.reset();
		}

		private void assertEqualsIgnoreCase(String expected, Object actual, String msg) {
			assertTrue(actual instanceof String, actual + " is not a String");
			assertEquals(expected.toLowerCase(), ((String) actual).toLowerCase(),
					String.format(
							msg + ",var \"%s\", men borde varit \"%s\" (utan hänsyn till stora och små bokstäver)",
							actual, expected));
		}

		public void assertIs(String expected) {
			Assertions.assertEquals(expected, get(), "Fel text på System.out");
		}

		public void assertIsIgnoreCase(String expected) {
			assertEqualsIgnoreCase(expected, get(), "Fel text på System.out");
		}

		public void assertStartsWith(String expected) {
			assertTrue(get().startsWith(expected), //
					String.format("Fel text på System.out, \"%s\" börjar inte med \"%s\"", out(), expected));
		}

		public void assertStartsWith(String expected, String example) {
			assertTrue(get().startsWith(expected), //
					String.format(
							"Fel text på System.out, \"%s\" börjar inte med \"%s\". Utskriften borde var något i stil med \"%s\"",
							out(), expected, example));
		}

		public void assertEndsWith(String expected) {
			assertTrue(get().endsWith(expected), //
					String.format("Fel text på System.out, \"%s\" slutar inte med \"%s\"", out(), expected));
		}

		public void assertEndsWith(String expected, String example) {
			assertTrue(get().endsWith(expected), //
					String.format(
							"Fel text på System.out, \"%s\" slutar inte med \"%s\". Utskriften borde var något i stil med \"%s\"",
							out(), expected, example));
		}

		public void assertContains(String expected) {
			assertTrue(get().contains(expected), //
					String.format("Fel text på System.out, \"%s\" innehåller inte \"%s\"", out(), expected));
		}

		public void assertDoesNotContains(String expected) {
			assertFalse(get().contains(expected), //
					String.format("Fel text på System.out, \"%s\" innehåller \"%s\" som inte borde finnas", out(),
							expected));
		}

		public void assertContainsIgnoreCase(String expected) {
			assertTrue(get().toLowerCase().contains(expected.toLowerCase()),
					String.format("Fel text på System.out, \"%s\" innehåller inte \"%s\"", out(), expected));
		}

		public void assertDoesNotContainsIgnoreCase(String expected) {
			assertFalse(get().toLowerCase().contains(expected.toLowerCase()), String
					.format("Fel text på System.out, \"%s\" innehåller \"%s\" som inte borde finnas", out(), expected));
		}

		public void assertContainsErrorMessage() {
			String s = get().toLowerCase();
			boolean swedishError = s.contains("fel");
			boolean englishError = s.contains("error");
			assertTrue(swedishError || englishError, String.format("%s innehåller inget markerat felmeddelande", s));
		}

		public void assertDoesNotContainErrorMessage() {
			String s = get().toLowerCase();
			boolean swedishError = s.contains("fel");
			boolean englishError = s.contains("error");
			assertFalse(swedishError || englishError, String.format("%s innehåller ett markerat felmeddelande", s));
		}

	}
}
