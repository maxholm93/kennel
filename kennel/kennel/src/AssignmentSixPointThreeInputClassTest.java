/*
 * Denna fil innehåller JUnit-testfall för inläsningsklassen i U6.3.
 *
 * Det är starkt rekommenderat att du använder dig av dessa testfall, och att du
 * kör dem i din egen utvecklingsmiljö, och inte i VPL. Du får bättre
 * felmeddelanden i din egen utvecklingsmiljö, och det är svårt att hålla reda
 * på versioner om du hoppar fram och tillbaka.
 *
 * För att köra testerna behöver du lägga till JUnit till ditt projekt, och
 * lägga denna fil, tillsammans med de gemensamma standardfiler som behövs i
 * samma katalog som det som ska testas. Information om hur du gör detta finns i
 * ilearn.
 *
 * Testfallen är ordnade i en "naturlig" ordning. Detta, tillsammans med att
 * hela eller delar av testkoden är bortkommenterad från början är tänkt att
 * hjälpa dig att koncentrera dig på en sak i taget. Försök inte lösa allt på en
 * gång, utan ta ett testfall i taget, uppifrån och ner.
 *
 * Slutligen: dessa testfall, och eventuella extra som körs i ilearn, är tänkta
 * att hjälpa dig på rätt väg, inte att vara perfekta. Det är alltid du själv
 * som ansvarar för att koden du lämnar in uppfyller kraven. Du måste därför
 * testa koden själv också. Men, går koden igenom dessa testfall så kommer du
 * att ha en bra grund att stå på.
 *
 * Testfallen kan också komma att uppdateras under kursens gång om vi märker att
 * de missar något viktigt. Sådana uppdateringar aviseras via ilearn.
 */


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Locale;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


@DisplayName(value = "JUnit-testfall för U6.3 - inläsningsklassen")
public class AssignmentSixPointThreeInputClassTest extends IOBaseTest {

	private static final int MAX_PUBLIC_METHODS = 4;
	private static final Duration TIMEOUT = Duration.ofMillis(100);
	private static final String TIMEOUT_MSG_TEMPLATE = "Det tog för lång tid att %s. "
			+ "Detta kan bero på flera saker, men de vanligaste är att man läser från fel ström, "
			+ "eller på att en extra inläsning görs någonstans. "
			+ "Oavsett vilket så får detta effekten att anropet verkar \"hänga\" medan inläsningen väntar på indata som aldrig kommer";

	public static final ClassUnderTest SCANNER_ADAPTER_CLASS = new ClassUnderTest(TestData.SCANNER_ADAPTER_CLASS_NAME,
			"TestData.SCANNER_ADAPTER_CLASS_NAME");
	public static final MethodUnderTest READ_TEXT_METHOD = SCANNER_ADAPTER_CLASS
			.getMethod(TestData.READ_TEXT_METHOD_NAME, "TestData.READ_TEXT_METHOD_NAME", String.class);
	public static final MethodUnderTest READ_INTEGER_METHOD = SCANNER_ADAPTER_CLASS
			.getMethod(TestData.READ_INTEGER_METHOD_NAME, "TestData.READ_INTEGER_METHOD_NAME", String.class);
	public static final MethodUnderTest READ_DECIMAL_METHOD = SCANNER_ADAPTER_CLASS
			.getMethod(TestData.READ_DECIMAL_METHOD_NAME, "TestData.READ_DECIMAL_METHOD_NAME", String.class);


	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentSixPointThreeInputClassTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(IOBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Har inläsningsklassen rätt antal publika metoder?")
	public void checkMethodCount() {
		long count = SCANNER_ADAPTER_CLASS.getPublicMethods().count();

		assertTrue(count <= MAX_PUBLIC_METHODS, String.format(
				"det fanns fler publika metoder i klassen %s än förväntat (%d/%d). De enda publika metoderna ska vara de för inläsning, plus möjligen en för att stänga scannern.",
				SCANNER_ADAPTER_CLASS.name(), count, MAX_PUBLIC_METHODS));
	}

	@Test
	@DisplayName(value = "Har namnet på inläsningsklassen något uppenbart problem?")
	public void checkNameOfClassForObviousProblems() {
		assertFalse(SCANNER_ADAPTER_CLASS.name().equals("Scanner"),
				"Scanner är inte ett bra namn på klassen, det går inte att skilja från java.util.Scanner");
		assertFalse(SCANNER_ADAPTER_CLASS.name().equals("Input"),
				"Input är inte ett bra namn på klassen, det är för allmänt i sig självt");
		assertFalse(SCANNER_ADAPTER_CLASS.name().contains("Class"),
				"Klassnamn ska inte innehålla ordet Class, det är onödigt och klottrar ner namnet");
		assertFalse(SCANNER_ADAPTER_CLASS.name().contains("Dog"),
				"Klassnamnet ska inte innehålla ordet Dog, det är inte en klass som har något med hundar att göra");
		assertFalse(SCANNER_ADAPTER_CLASS.name().contains("My"),
				"Klassnamnet ska inte innehålla ordet My, att du skrivit det är inte relevant för den som ska använda klassen");
		assertFalse(SCANNER_ADAPTER_CLASS.name().contains("Assignment"),
				"Klassnamnet ska inte innehålla ordet Assignment, att den är framtagen som en del av en inlämningsuppgift är inte relevant för den som ska använda den");
		// Detta test kan misslyckas om namnet i singular slutar på s.
		// Har du ett sådant namn hör av dig till handledningsforumet
		// så uppdaterar vi testet.
		assertFalse(SCANNER_ADAPTER_CLASS.name().endsWith("s"),
				"Klassnamn är normalt i singular, om klassen inte representerar en samling av objekt vilket inte är fallet här. Även i fallet med samlingar är det vanlig att namnet på klassen är i singular, och istället visar att det är en samling genom att heta något som innehåller Collection, List, edyl.");
	}

	private void checkNameOfMethodForObviousProblems(MethodUnderTest m) {
		assertFalse(m.name().contains("Method"),
				"Metodnamn ska inte innehålla ordet Method, det är helt onödigt och klottrar ner namnet");
	}

	@Test
	@DisplayName(value = "Har metodnamnen i inläsningsklassen några uppenbara problem?")
	public void checkNameOfMethodsForObviousProblems() {
		checkNameOfMethodForObviousProblems(READ_TEXT_METHOD);
		checkNameOfMethodForObviousProblems(READ_INTEGER_METHOD);
		checkNameOfMethodForObviousProblems(READ_DECIMAL_METHOD);
	}

	@Test
	@DisplayName(value = "Har inläsningsklassen en konstruktor utan parametrar?")
	public void classHasConstructorWithNoParameters() {
		assertDoesNotThrow(() -> SCANNER_ADAPTER_CLASS.getConstructor());
	}

	@Test
	@DisplayName(value = "Fungerar metoden för att läsa ett heltal en gång från System.in?")
	public void testMethodToReadIntFromSystemIn() {
		// Måste ske innan instansen skapas, annars används den ursprungliga strömmen
		setIn("1234\n");
		var sut = SCANNER_ADAPTER_CLASS.getConstructor().newInstance();
		int result = (int) READ_INTEGER_METHOD.invoke(sut, "UNCHECKED PROMPT");
		assertEquals(1234, result);
	}

	@ParameterizedTest(name = "{index} språk: {0} och land: {1} betyder att {2} används som decimalavskiljare")
	@CsvSource({ "en,GB,punkt", "sv,SE,komma" }) // Testar både engelska och svenska landsinställningar
	@DisplayName(value = "Fungerar metoden för att läsa ett decimaltal en gång från System.in?")
	public void testMethodReadDoubleFromSystemIn(String language, String country, String decimalSeparator) {

		Locale defaultLocale = Locale.getDefault();

		try {
			// Måste ske innan setIn för att rätt decimalavskiljare ska användas
			Locale newLocale = new Locale.Builder().setLanguage(language).setRegion(country).build();
			Locale.setDefault(newLocale);

			// Måste ske innan instansen skapas, annars används den ursprungliga strömmen
			setIn(String.format("%f%n", 1.234));

			var sut = SCANNER_ADAPTER_CLASS.getConstructor().newInstance();
			double result = (double) READ_DECIMAL_METHOD.invoke(sut, "UNCHECKED PROMPT");
			assertEquals(1.234, result);
		} finally {
			Locale.setDefault(defaultLocale);
		}
	}

	@Test
	@DisplayName(value = "Fungerar metoden för att läsa text en gång från System.in?")
	public void testMethodToReadTextFromSystemIn() {
		// Måste ske innan instansen skapas, annars används den ursprungliga strömmen
		setIn("input text\n");
		var sut = SCANNER_ADAPTER_CLASS.getConstructor().newInstance();
		String result = (String) READ_TEXT_METHOD.invoke(sut, "UNCHECKED PROMPT");
		assertEqualsIgnoreCase("input text", result);

	}

	@Test
	@DisplayName(value = "Har inläsningsklassen två konstruktorer?")
	public void classHasTwoPublicConstructors() {
		assertEquals(2, SCANNER_ADAPTER_CLASS.getPublicConstructors().count());
	}

	@Test
	@DisplayName(value = "Har inläsningsklassen en konstruktor som tar en InputStream som parameter?")
	public void classHasConstructorWithOneParameters() {
		assertDoesNotThrow(() -> SCANNER_ADAPTER_CLASS.getConstructor(InputStream.class));
	}

	private Object getNewAdapter(InputStream in) {
		var adapter = assertTimeoutPreemptively(TIMEOUT, () -> {
			return SCANNER_ADAPTER_CLASS.getConstructor(InputStream.class).newInstance(in);
		}, String.format(TIMEOUT_MSG_TEMPLATE, "skapa en instans av inläsningsklassen"));
		return adapter;
	}

	private Object getNewAdapter(String input) {
		return getNewAdapter(new ByteArrayInputStream((input + "\n").getBytes()));
	}

	private void assertPromptWas(String expected) {
		assertFalse(out().get().contains("\n"), "Utskriften av ledtexten ska inte innehålla någon radbrytning");
		assertFalse(out().get().contains("\r"), "Utskriften av ledtexten ska inte innehålla någon radbrytning");
		out().assertStartsWith(expected, "%s ?>".formatted(expected));
		out().trim().assertEndsWith("?>", "%s ?>".formatted(expected));
	}

	private Object callInputMethod(MethodUnderTest m, Object adapter, String prompt) {
		var input = assertTimeoutPreemptively(TIMEOUT, () -> {
			return m.invoke(adapter, prompt);
		}, String.format(TIMEOUT_MSG_TEMPLATE, "anropa " + m));
		return input;
	}

	private Object callInputMethod(MethodUnderTest m, String input, String prompt) {
		return callInputMethod(m, getNewAdapter(input), prompt);
	}

	@Test
	@DisplayName(value = "Fungerar metoden för att läsa ett heltal en gång?")
	public void testMethodToReadInt() {
		var result = callInputMethod(READ_INTEGER_METHOD, "123", "prompt integer");
		assertPromptWas("prompt integer");
		assertEquals(123, result);
	}

	@ParameterizedTest(name = "{index} språk: {0} och land: {1} betyder att {2} används som decimalavskiljare")
	@CsvSource({ "en,GB,punkt", "sv,SE,komma" }) // Testar både engelska och svenska landsinställningar
	@DisplayName(value = "Fungerar metoden för att läsa ett decimaltal en gång?")
	public void testMethodReadDouble(String language, String country, String decimalSeparator) {
		Locale defaultLocale = Locale.getDefault();

		try {
			Locale newLocale = new Locale.Builder().setLanguage(language).setRegion(country).build();
			Locale.setDefault(newLocale);
			// String.format för att landsinställningarna ska användas
			var result = callInputMethod(READ_DECIMAL_METHOD, String.format("%f", 1.23), "prompt decimal");
			assertPromptWas("prompt decimal");
			assertEquals(1.23, result);
		} finally {
			Locale.setDefault(defaultLocale);
		}
	}

	@Test
	@DisplayName(value = "Fungerar metoden för att läsa text en gång?")
	public void testMethodToReadText() {
		var result = callInputMethod(READ_TEXT_METHOD, "input text", "prompt text");
		assertPromptWas("prompt text");
		assertEqualsIgnoreCase("input text", result);
	}

	@Test
	@DisplayName(value = "Töms inmatningsbufferten efter att ett heltal lästs in?")
	public void readingIntegerClearsBuffer() {
		Object adapter = getNewAdapter("1\ntext");
		Object result = callInputMethod(READ_INTEGER_METHOD, adapter, "prompt");
		assertEquals(1, result);
		result = callInputMethod(READ_TEXT_METHOD, adapter, "prompt");
		assertEqualsIgnoreCase("text", result);
	}

	@Test
	@DisplayName(value = "Töms inmatningsbufferten efter att ett decimaltal lästs in?")
	public void readingDecimalClearsBuffer() {
		Object adapter = getNewAdapter(String.format("%.1f%ntext", 1.2));
		Object result = callInputMethod(READ_DECIMAL_METHOD, adapter, "prompt");
		assertEquals(1.2, result);
		result = callInputMethod(READ_TEXT_METHOD, adapter, "prompt");
		assertEqualsIgnoreCase("text", result);
	}

	@Test
	@DisplayName(value = "Fungerar blandad inläsning?")
	public void readingDifferentThingsDoesNotCauseProblemsWithBuffering() {
		Object adapter = getNewAdapter(String.format("%s%n%d%n%f%n%d%n%s%n%f%n", "first", 2, 3.0, 4, "fifth", 6.0));
		Object result;
		result = callInputMethod(READ_TEXT_METHOD, adapter, "prompt");
		assertEqualsIgnoreCase("first", result);
		result = callInputMethod(READ_INTEGER_METHOD, adapter, "prompt");
		assertEquals(2, result);
		result = callInputMethod(READ_DECIMAL_METHOD, adapter, "prompt");
		assertEquals(3.0, result);
		result = callInputMethod(READ_INTEGER_METHOD, adapter, "prompt");
		assertEquals(4, result);
		result = callInputMethod(READ_TEXT_METHOD, adapter, "prompt");
		assertEqualsIgnoreCase("fifth", result);
		result = callInputMethod(READ_DECIMAL_METHOD, adapter, "prompt");
		assertEquals(6.0, result);
	}

	@Test
	@DisplayName(value = "Kastas ett undantag när man försöker skapa flera instanser av inläsningsklassen som läser från olika källor?")
	public void creatingMultipleAdaptersDoesNotThrowExceptionWhenReadingFromDifferentStreams() {
		assertDoesNotThrow(() -> {
			getNewAdapter(new ByteArrayInputStream(new byte[] {}));
		}, "Det första försöket att skapa ett objekt av klassen borde lyckats");
		assertDoesNotThrow(() -> {
			getNewAdapter(new ByteArrayInputStream(new byte[] {}));
		}, "Det andra försöket att skapa ett objekt av klassen borde också lyckats eftersom det läser från en annan källa");
	}

	@Test
	@DisplayName(value = "Kastas ett undantag när man försöker skapa flera instanser av inläsningsklassen som läser från samma källa?")
	public void creatingMultipleAdaptersThrowsException() {
		InputStream in = new ByteArrayInputStream(new byte[] {});
		assertDoesNotThrow(() -> {
			getNewAdapter(in);
		}, "Det första försöket att skapa ett objekt av klassen borde lyckats");
		Throwable e = assertThrows(RuntimeException.class, () -> {
			getNewAdapter(in);
		}, "Det andra försöket att skapa ett objekt av klassen borde misslyckats");
		assertEquals(IllegalStateException.class, e.getCause().getClass(), "Fel typ av undantag kastas");
	}

	@ParameterizedTest(name = "InputStream nr {0} upprepas")
	@ValueSource(ints = { 1, 2, 5, 10 })
	@DisplayName(value = "Kastas ett undantag när man försöker skapa flera instanser av inläsningsklassen som läser från samma källa, även om de inte skapas direkt efter varandra?")
	public void creatingMultipleAdaptersThrowsException(int i) {
		final InputStream[] repeated = { null };
		for (int n = 1; n <= i * 3; n++) {
			InputStream in = new ByteArrayInputStream("".getBytes());
			getNewAdapter(in);
			if (n == i)
				repeated[0] = in;
		}

		Throwable e = assertThrows(RuntimeException.class, () -> {
			getNewAdapter(repeated[0]);
		}, "Det andra försöket att skapa ett objekt av klassen borde misslyckats");
		assertEquals(IllegalStateException.class, e.getCause().getClass(), "Fel typ av undantag kastas");
	}

	@Test
	@DisplayName(value = "Fungerar det att läsa från flera instanser parallellt?")
	public void readingFromMultipleStreamsInParallellGivesCorrectInput() {
		Object first = getNewAdapter(String.format("1\n2\n3\n"));
		Object second = getNewAdapter(String.format("A\nB\nC\n"));

		final String MSG = "Fel värde inläst";

		assertEquals(1, callInputMethod(READ_INTEGER_METHOD, first, "prompt"), MSG);
		assertEqualsIgnoreCase("A", callInputMethod(READ_TEXT_METHOD, second, "prompt"), MSG);
		assertEquals(2, callInputMethod(READ_INTEGER_METHOD, first, "prompt"), MSG);
		assertEqualsIgnoreCase("B", callInputMethod(READ_TEXT_METHOD, second, "prompt"), MSG);
		assertEquals(3, callInputMethod(READ_INTEGER_METHOD, first, "prompt"), MSG);
		assertEqualsIgnoreCase("C", callInputMethod(READ_TEXT_METHOD, second, "prompt"), MSG);

	}

	@Test
	@DisplayName(value = "Har inläsningsklassen några onödiga statiska fält?")
	public void noUnnecessaryStaticFields() {
		assertTrue(SCANNER_ADAPTER_CLASS.getClassVariables().count() <= 1,
				"Det borde inte finnas något behov av mer än max en statisk variabel i inläsningsklassen. (Konstanter är en annan sak, där kan det vara bra med någon till.)");
	}

	@Test
	@DisplayName(value = "Har inläsningsklassen några statiska metoder?")
	public void noStaticMethods() {
		assertEquals(0, SCANNER_ADAPTER_CLASS.getClassMethods().count(),
				"Det borde inte finnas något behov av några statiska metoder i inläsningsklassen.");
	}


}