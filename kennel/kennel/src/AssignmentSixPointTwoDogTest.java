/*
 * Denna fil innehåller JUnit-testfall för hundklassen i U6.2.
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

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.regex.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


@DisplayName(value = "JUnit-testfall för U6.2 - hundklassen")
public class AssignmentSixPointTwoDogTest extends ApiBaseTest {

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentSixPointTwoDogTest");

	private static final double MAX_DIFFERENCE_ALLOWED_FOR_TAIL_LENGHT = 0.001;

	private static final String DEFAULT_NAME = "karo";
	private static final String DEFAULT_NONE_DACHSHUND_BREED = "bulldog";
	private static final int DEFAULT_AGE = 3;
	private static final int DEFAULT_WEIGHT = 7;

	// Inte static för att olika test inte ska störa varandra
	private final Dog defaultDog = new Dog(DEFAULT_NAME, DEFAULT_NONE_DACHSHUND_BREED, DEFAULT_AGE, DEFAULT_WEIGHT);

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentSixPointTwoDogTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	private boolean containsMisspelledLength(String name) {
		// Detta är ett mycket vanligt fel, som kan leda till problem med såväl
		// kompilering som andra test
		return name.toLowerCase().contains("lenght");
	}

	@Test
	@DisplayName(value = "Är length rättstavat överallt?")
	public void noLengthSpelledLenght() {
		DOG_CLASS.getFields().map(FieldUnderTest::name).forEach(name -> {
			assertFalse(containsMisspelledLength(name), "Längd stavas \"lengTH\" på engelska, inte \"lengHT\"");
		});

		DOG_CLASS.getMethods().map(MethodUnderTest::name).forEach(name -> {
			assertFalse(containsMisspelledLength(name), "Längd stavas \"lengTH\" på engelska, inte \"lengHT\"");
		});
	}

	@Test
	@DisplayName(value = "Sätter konstruktorn namnet på hunden?")
	public void constructorSetsName() {
		assertEqualsIgnoreCase(DEFAULT_NAME, defaultDog.getName(), "Fel namn på hunden");
	}

	@Test
	@DisplayName(value = "Sätter konstruktorn rasen på hunden?")
	public void constructorSetsBreed() {
		assertEqualsIgnoreCase(DEFAULT_NONE_DACHSHUND_BREED, defaultDog.getBreed(), "Fel ras på hunden");
	}

	@Test
	@DisplayName(value = "Sätter konstruktorn åldern på hunden?")
	public void constructorSetsAge() {
		assertEquals(DEFAULT_AGE, defaultDog.getAge(), "Fel ålder på hunden");
	}

	@Test
	@DisplayName(value = "Sätter konstruktorn vikten på hunden?")
	public void constructorSetsWeight() {
		assertEquals(DEFAULT_WEIGHT, defaultDog.getWeight(), "Fel vikt på hunden");
	}

	@Test
	@DisplayName(value = "Räknas svanslängden ut korrekt för en hund som inte är en tax?")
	public void tailLengthCalculatedForNonDachshund() {
		assertEquals(2.1, defaultDog.getTailLength(), MAX_DIFFERENCE_ALLOWED_FOR_TAIL_LENGHT,
				"Fel svanslängd på hunden");
	}

	@Test
	@DisplayName(value = "Räknas svanslängden ut korrekt för en engelsk tax?")
	public void tailLengthCalculatedForDachshund() {
		Dog dog = new Dog(DEFAULT_NAME, "Dachshund", DEFAULT_AGE, DEFAULT_WEIGHT);
		assertEquals(3.7, dog.getTailLength(), MAX_DIFFERENCE_ALLOWED_FOR_TAIL_LENGHT,
				"Fel svanslängd för en (engelsk) tax");
	}

	@Test
	@DisplayName(value = "Räknas svanslängden ut korrekt för en svensk tax?")
	public void tailLengthCalculatedForTax() {
		Dog dog = new Dog(DEFAULT_NAME, "Tax", DEFAULT_AGE, DEFAULT_WEIGHT);
		assertEquals(3.7, dog.getTailLength(), MAX_DIFFERENCE_ALLOWED_FOR_TAIL_LENGHT,
				"Fel svanslängd för en (svensk) tax");
	}

	@ParameterizedTest(name = "{index} Returnerar {0} en {1}?")
	@CsvSource({ "getName, java.lang.String", "getBreed, java.lang.String", "getAge, int", "getWeight, int",
			"getTailLength, double" })
	@DisplayName(value = "Har get-metoderna korrekta returtyper?")
	public void correctReturnTypeForGetMethods(String methodName, String expectedType)
			throws NoSuchMethodException, SecurityException {
		MethodUnderTest m = DOG_CLASS.getMethod(methodName,
				"AssignmentSixPointTwoDogTest.correctReturnTypeForGetMethods(String, String)");
		assertEquals(expectedType, m.getReturnType().getName(),
				String.format("Metoden %s har fel returtyp", methodName));
	}

	@ParameterizedTest(name = "{index} {0}")
	@CsvSource({ "Karo", "fido", "Milou", "REX", "Fluffy destroyer of worlds" })
	@DisplayName(value = "Accepterar konstruktorn namn i olika format?")
	public void constructorAcceptsNameInDifferentFormats(String name) {
		Dog dog = new Dog(name, DEFAULT_NONE_DACHSHUND_BREED, DEFAULT_AGE, DEFAULT_WEIGHT);
		assertEqualsIgnoreCase(name, dog.getName(), "Fel namn på hunden");
	}

	@ParameterizedTest(name = "{index} {0}")
	@CsvSource({ "Schäfer", "PULI", "golden retriever" })
	@DisplayName(value = "Accepterar konstruktorn raser i olika format?")
	public void constructorAcceptBreedInDifferentFormats(String breed) {
		Dog dog = new Dog(DEFAULT_NAME, breed, DEFAULT_AGE, DEFAULT_WEIGHT);
		assertEqualsIgnoreCase(breed, dog.getBreed(), "Fel ras på hunden");
	}

	@ParameterizedTest(name = "{index} {0}")
	@CsvSource({ "Dachshund", "DACHSHUND", "dachshund", "Tax", "TAX", "tax" })
	@DisplayName(value = "Accepterar konstruktorn taxar i olika format?")
	public void constructorAcceptDacshundsInDifferentFormats(String breed) {
		Dog dog = new Dog(DEFAULT_NAME, breed, DEFAULT_AGE, DEFAULT_WEIGHT);
		assertEqualsIgnoreCase(breed, dog.getBreed(), "Fel ras på hunden");
		assertEquals(3.7, dog.getTailLength(), "Fel svanslängd för taxen");
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens namn?")
	public void toStringContainsName() {
		String result = defaultDog.toString();
		assertTrue(result.toLowerCase().contains(DEFAULT_NAME.toLowerCase()),
				String.format("toString innehåller inte namnet %s: \"%s\"", DEFAULT_NAME, result));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens ras?")
	public void toStringContainsBreed() {
		String result = defaultDog.toString();
		assertTrue(result.toLowerCase().contains(DEFAULT_NONE_DACHSHUND_BREED.toLowerCase()),
				String.format("toString innehåller inte rasen %s: \"%s\"", DEFAULT_NONE_DACHSHUND_BREED, result));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens ålder?")
	public void toStringContainsAge() {
		String result = defaultDog.toString();
		assertTrue(result.toLowerCase().contains("" + DEFAULT_AGE),
				String.format("toString innehåller inte åldern %d: \"%s\"", DEFAULT_AGE, result));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens vikt?")
	public void toStringContainsWeight() {
		String result = defaultDog.toString();
		assertTrue(result.toLowerCase().contains("" + DEFAULT_WEIGHT),
				String.format("toString innehåller inte vikten %d: \"%s\"", DEFAULT_WEIGHT, result));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar någon radbrytning?")
	public void toStringContainsNewLine() {
		String result = defaultDog.toString();
		assertFalse(result.contains("\n"),
				"toString innehåller \\n vilket kan ställa till med problem för andra test längre fram, och indikerar att metoden används för formatering vilket inte riktigt är syftet");
		assertFalse(result.contains("\r"),
				"toString innehåller \\r vilket kan ställa till med problem för andra test längre fram, och indikerar att metoden används för formatering vilket inte riktigt är syftet");
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens svanslängd?")
	public void toStringContainsTailLength() {
		String result = defaultDog.toString().replaceAll("\n", "");
		Pattern p = Pattern.compile(".*2[.,](1|09).*");
		Matcher m = p.matcher(result);

		assertTrue(m.matches(), String
				.format("toString innehåller inte svanslängden 2.1 eller 2,1 (med lite felmarginal): \"%s\"", result));
	}

	/*
	 * Detta test är hårdare än ovanstående och kräver att det inte blir ett
	 * avrundningsfel i första decimalen. Det är inte ett absolut krav att detta
	 * test fungerar, det är avslaget i ilearn, men det borde göra det. Om du får en
	 * svanslängd som är 2.0999... så gör du beräkningen i fel ordning, och
	 * troligtvis har du en parentes på fel ställe.
	 * 
	 * Kursboken på IDSV (som många av er gått) tar upp problemet med precisionen
	 * hos flyttal.
	 */
//	@Test
//	@DisplayName(value = "Innehåller det toString returnerar hundens svanslängd? (Mer exakt test)")
//	public void toStringContainsStrictTailLength() {
//		String result = defaultDog.toString().replaceAll("\n", "");
//		Pattern p = Pattern.compile(".*2[.,]1.*");
//		Matcher m = p.matcher(result);
//
//		assertTrue(m.matches(), String.format("toString innehåller inte svanslängden 2.1 eller 2,1: \"%s\"", result));
//	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar utrymme mellan de olika delarna?")
	public void toStringContainsSeparators() {
		// Detta test är väldigt svagt. Syftet är att kontrollera att de olika
		// delarna i toString kan skiljas från varandra, men det enda som kontrolleras
		// är att längden på strängen är tillräckligt lång för att detta ska vara
		// möjligt. Anledningen till detta svaga test är att formatet på strängen
		// bestäms av dig.
		int minLength = DEFAULT_NAME.length() + DEFAULT_NONE_DACHSHUND_BREED.length() + 1/* age */ + 1/* weight */
				+ 3/* tail length */ + 4/* separators */;
		assertTrue(defaultDog.toString().length() >= minLength,
				"toString ger en för kort sträng för att kunna innehålla separatorer mellan attributen");
	}

	private boolean hasNameRelatedToAge(MethodUnderTest m) {
		String name = m.name().toLowerCase();

		for (String word : new String[] { "age", "old", "year" }) {
			if (name.contains(word))
				return true;
		}

		return false;
	}

	private MethodUnderTest identifyAgeingMethod() {
		List<MethodUnderTest> candidates = DOG_CLASS.getPublicMethods().filter(m -> !m.name().contains("get"))
				.filter(m -> hasNameRelatedToAge(m) && m.getParameterCount() <= 1).toList();

		assertEquals(1, candidates.size(),
				"Hittade fel antal möjliga metoder för att ändra en hunds ålder: %s. Eftersom metoden inte är specificerad i klassdiagrammet i uppgiften försöker detta test gissa sig till vilken metod det rör sig om genom att titta på skydddsnivån, namnet och antalet parametrar."
						.formatted(candidates));

		return candidates.get(0);
	}

	public void ageDogOneYear(Dog dog) {
		ageDog(dog, 1);
	}

	public void ageDog(Dog dog, int years) {
		MethodUnderTest m = identifyAgeingMethod();

		if (m.getParameterCount() == 0) {
			for (int n = 0; n < years; n++) {
				m.invoke(dog);
			}
		} else {
			m.invoke(dog, years);
		}
	}

	@ParameterizedTest(name = "{index} Försök att öka åldern {0} år")
	@ValueSource(ints = { 1, 2, 3 })
	@DisplayName(value = "Går det att öka åldern?")
	public void ageAttributeUpdatedOnAgeing(int years) {
		ageDog(defaultDog, years);
		assertEquals(DEFAULT_AGE + years, defaultDog.getAge(),
				"En hund som var %d år från början och vars ålder ökat med %d år borde ha åldern %d"
						.formatted(DEFAULT_AGE, years, DEFAULT_AGE + years));
	}

	@Test
	@DisplayName(value = "Går det att minska åldern?")
	public void ageAttributeUnchangedWhenTryingToDecreaseAge() {
		ageDog(defaultDog, -1);
		assertEquals(DEFAULT_AGE, defaultDog.getAge(),
				"En hund som var %d år från början och man försökt minska åldern på borde fortfarande ha åldern %d. Hundar kan aldrig bli yngre än vad de är."
						.formatted(DEFAULT_AGE, DEFAULT_AGE));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens nya ålder efter att åldern ökats?")
	public void toStringContainsNewAgeAfterAgeing() {
		ageDogOneYear(defaultDog);

		String result = defaultDog.toString();
		int expectedAge = DEFAULT_AGE + 1;
		assertTrue(result.toLowerCase().contains("" + expectedAge),
				String.format("toString innehåller inte åldern %d: \"%s\"", expectedAge, result));
	}

	@ParameterizedTest(name = "{index} När hundens ålder ökar med {0}")
	@CsvSource(value = { "1, 2.8", "2, 3.5", "3, 4.2" })
	@DisplayName(value = "Ändras svanslängden då åldern ändras?")
	public void tailLengthUpdatedOnAgeing(int years, double newTailLength) {
		ageDog(defaultDog, years);
		assertEquals(newTailLength, defaultDog.getTailLength(), MAX_DIFFERENCE_ALLOWED_FOR_TAIL_LENGHT,
				"Hundens svanslängd efter att ha ökat åldern med %d år borde vara (ungefär) %.1f".formatted(years,
						newTailLength));
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar hundens nya svanslängd efter att åldern ökats?")
	public void toStringContainsNewTailLengthAfterAgeing() {
		ageDogOneYear(defaultDog);

		String result = defaultDog.toString().replaceAll("\n", "");
		Pattern p = Pattern.compile(".*2[.,]8.*");
		Matcher m = p.matcher(result);

		assertTrue(m.matches(), String.format("toString innehåller inte svanslängden 2.8 eller 2,8: \"%s\"", result));
	}

	@Test
	@DisplayName(value = "Finns det en setAge-metod?")
	public void noSetAgeMethod() throws NoSuchMethodException, SecurityException {
		// Inga parametrar
		assertThrows(NoSuchMethodException.class, () -> Dog.class.getMethod("setAge"),
				"Metoden som saknas i klassdiagrammet ska öka åldern, inte sätta den till ett visst värde som namnet setAge antyder");
		// En parameter
		assertThrows(NoSuchMethodException.class, () -> Dog.class.getMethod("setAge", Integer.TYPE),
				"Metoden som saknas i klassdiagrammet ska öka åldern, inte sätta den till ett visst värde som namnet setAge antyder");
	}

	@Test
	@DisplayName(value = "Innehåller hundklassen några statiska metoder?")
	public void noStaticMethods() {
		assertEquals(0, DOG_CLASS.getClassMethods().count(), "Hundklassen ska inte innehålla några statiska metoder.");
	}

	@Test
	@DisplayName(value = "Innehåller hundklassen några statiska variabler?")
	public void noStaticVariables() {
		assertEquals(0, DOG_CLASS.getClassVariables().count(),
				"Hundklassen ska inte innehålla några statiska variabler. En gissning är att dessa är tänkta att vara konstanter istället, alltså static och final.");
	}

	@Test
	@DisplayName(value = "Har hundklassen en konstruktor?")
	public void onlyOneConstructor() {
		assertEquals(1, DOG_CLASS.getConstructors().count(),
				"Hundklassen ska bara ha en konstruktor, den som finns i klassdiagrammet till U6.2");
	}

	@Test
	@DisplayName(value = "Har hundklassen en rimlig mängd publika metoder?")
	public void reasonableNumberOfPublicMethods() {
		long methods = DOG_CLASS.getPublicMethods().count();

		assertTrue(methods >= 7,
				"Det finns för få publika metoder i hundklassen för att den ska kunna stämma med klassdiagrammet. Det måste minst finnas minst 7. De sex som syns i diagrammet och en extra för att öka åldern enligt uppgiften.");
		assertTrue(methods <= 14,
				"Det finns (antagligen) för många publika metoder i hundklassen. Detta test kan ha fel, och i så fall får du höra av dig till handledningsforumet för att få det uppdaterat, men, inte ens om du implementerat klart hela systemet borde det behövas så här många publika metoder. Gränsen är ganska generöst satt.");
	}

	private long countInstanceVariables(Class<?> type) {
		return DOG_CLASS.getFinalAndNonFinalInstanceVariables().filter(f -> f.hasType(type)).count();
	}

	@Test
	@DisplayName(value = "Har hundklassen en rimlig mängs instansvariabler?")
	public void reasonableNumberOfInstanceVariables() {
		String msg = "Det finns (antagligen) för många instansvariabler av typen %s i klassen. "
				+ "Detta test kan ha fel, och i så fall får du höra av dig till handledningsforumet för att få det uppdaterat. "
				+ "Men, innan du gör det, kontrollera om inte någon, eller några, av instansvariablerna borde vara konstanter, "
				+ "lokala variabler, eller kan plockas bort helt.";

		long s = countInstanceVariables(String.class);
		assertTrue(s <= 2, String.format(msg, "String"));

		long i = countInstanceVariables(int.class);
		assertTrue(i <= 2, String.format(msg, "int"));

		long d = countInstanceVariables(double.class);
		assertTrue(d <= 1, String.format(msg, "double"));

		long sa = countInstanceVariables(String[].class);
		assertTrue(sa == 0, String.format(msg, "String[]"));

		long od = countInstanceVariables(Dog.class);
		assertTrue(od == 0, String.format(msg, "Dog"));
	}

	@Test
	@DisplayName(value = "Är konstanter korrekt deklarerade?")
	public void constantsAreBothStaticAndFinal() {
		var field = DOG_CLASS.getFields()
				.filter(f -> !(f.hasType(String.class) || f.hasType(int.class)) && f.isStatic() != f.isFinal())
				.findAny();

		assertTrue(field.isEmpty(), String.format(
				"Konstanter är både static och final i Java. Fältet \"%s\" verkar bryta mot detta. Detta test kan ha fel, och i så fall får du höra av dig till handledningsforumet för att få det uppdaterat.",
				field.orElse(null)));
	}

	@Test
	@DisplayName(value = "Finns det några svenska taxar i koden?")
	public void noSwedishDachshunds() {
		// Funderade en kort sekund på att kalla testet noTaxationWithoutRepresentation,
		// men övergav det snabbt. Det må vara marginellt roligare, men säger inget om
		// vad testet kontrollerar: Don't be cute! Det kommer bara att förvirra läsarna
		// av din kod.

		DOG_CLASS.getFields().map(FieldUnderTest::name).forEach(name -> {
			assertFalse(name.toLowerCase().contains("tax"), String.format(
					"Fältnamnet \"%s\" verkar innehålla ordet \"tax\". Uppgiften handlar om hundar, inte om skatter.",
					name));
		});

		DOG_CLASS.getMethods().map(MethodUnderTest::name).forEach(name -> {
			assertFalse(name.toLowerCase().contains("tax"), String.format(
					"Metodnamnet \"%s\" verkar innehålla ordet \"tax\". Uppgiften handlar om hundar, inte om skatter.",
					name));
		});
	}

	@Test
	@DisplayName(value = "Är tio namngiven?")
	public void noFieldNamedTen() {
		DOG_CLASS.getFields().map(FieldUnderTest::name).forEach(name -> {
			assertFalse(name.toLowerCase().equals("ten"), String.format(
					"Fältet \"%s\" har ett namn som inte säger något om syftet med fältet. När man tar bort magiska tal så ska man namnge konstanterna efter vad värdet representerar, inte vad värdet är för tal.",
					name));
		});
	}

	@Test
	@DisplayName(value = "Finns det en svanslängdskonstant?")
	public void noConstantTailLengthField() {
		Optional<FieldUnderTest> fut = DOG_CLASS.getFields().filter(f -> f.name().toLowerCase().equals("taillength"))
				.findAny();

		assertTrue(fut.isEmpty() || fut.get().isNonFinal(), String.format(
				"Det borde inte finnas någon användning för en konstant vid namn \"%s\" i denna uppgift. Om den har med taxars svanslängd att göra bör detta speglas i namnet.",
				fut.orElse(null)));
	}


}