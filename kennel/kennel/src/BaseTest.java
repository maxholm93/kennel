/*
 * Denna klass innehåller kod som är gemensam för alla JUnit-testfall till inlämningsuppgiftens olika delar.
 * För att dessa ska fungera måste du ladda ner denna fil och lägga den i samma katalog som koden som ska testas.
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

	private static Duration MAXIMUM_METHOD_INVOCATION_TIME_MILLIS = Duration.ofMillis(100);

	public static final int VERSION = 1;

	private Map<Class<?>, Integer> versionRequirements = new HashMap<>();

	protected void requireVersion(Class<?> c, int minimumVersion) {
		versionRequirements.put(c, minimumVersion);
	}

	@BeforeEach
	protected void checkVersionRequirements() {
		if (versionRequirements.isEmpty())
			fail("Inga versionskrav angivna för testfallen. Detta betyder att du har en gammal version av %s.java.\nLadda ner en ny version från ilearn. Du bör också prenumerera på forumet där ändringar i testfallen annonseras så att du inte missar fler uppdateringar."
					.formatted(getClass().getSimpleName()));

		Set<Class<?>> leftToCheck = new HashSet<>(versionRequirements.keySet());
		for (Class<?> c = getClass(); c != null; c = c.getSuperclass()) {
			leftToCheck.remove(c);

			int requiredVersion = versionRequirements.getOrDefault(c, 0);
			if (VERSION < requiredVersion)
				fail(String.format(
						"Fel version av %s.java. Den version du har är version %d, men måste var version %d eller högre.\nLadda ner en ny version från ilearn. Du bör också prenumerera på forumet där ändringar i testfallen annonseras så att du inte missar fler uppdateringar.",
						c.getSimpleName(), VERSION, requiredVersion));
		}

		assertEquals(Collections.emptySet(), leftToCheck);
	}

	protected static boolean isSubclassOfSoftwareUnderTest(FieldUnderTest f) {
		return f.isAssignableTo(SoftwareUnderTest.class);
	}

	protected static void checkSoftwareUnderTestData(Class<?> c) {
		ClassUnderTest cut = new ClassUnderTest(c, "BaseTest.checkSoftwareUnderTestData(Class<?>)");
		cut.getConstants().filter(ApiBaseTest::isSubclassOfSoftwareUnderTest).forEach(f -> {
			try {
				SoftwareUnderTest<?> sut = (SoftwareUnderTest<?>) f.getValue(null);
				if (sut != null)
					sut.sut();
			} catch (IllegalStateException e) {
				throw new IllegalStateException("""
						Konstanten %s i %s har inte ett korrekt värde.
						%s""".formatted(f, c, e.getMessage()), e);
			}
		});

		cut.getClassVariables().filter(ApiBaseTest::isSubclassOfSoftwareUnderTest).findAny().ifPresent(f -> {
			fail("Ska %s verkligen vara en klassvariabel i %s och inte en konstant?".formatted(f, c));
		});

		cut.getInstanceFields().filter(ApiBaseTest::isSubclassOfSoftwareUnderTest).findAny().ifPresent(f -> {
			fail("Ska %s verkligen vara ett instansfält i %s och inte en konstant?".formatted(f, c));
		});
	}

	protected void setIn(String format, Object... args) {
		String input = String.format(format, args);
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}

	protected final void assertEqualsIgnoreCase(String expected, Object actual) {
		assertTrue(actual instanceof String, actual + " is not a String");
		assertEquals(expected.toLowerCase(), ((String) actual).toLowerCase(), String.format(
				"Var \"%s\", men borde varit \"%s\" (utan hänsyn till stora och små bokstäver)", actual, expected));
	}

	protected final void assertEqualsIgnoreCase(String expected, Object actual, String msg) {
		assertTrue(actual instanceof String, actual + " is not a String");
		assertEquals(expected.toLowerCase(), ((String) actual).toLowerCase(),
				String.format(msg + ",var \"%s\", men borde varit \"%s\" (utan hänsyn till stora och små bokstäver)",
						actual, expected));
	}

	protected void assertBasicStructureOfSingleMethodAssignmentMainClass(ClassUnderTest cut, MethodUnderTest... muts) {
		// Klassen
		assertTrue(cut.exists(), "Hittar inte klassen %s. Är namnet korrekt i TestData.java?".formatted(cut.name()));

		// Dessa test kan ge ett felaktigt fel om namnet på klassen är fel i
		// TestData.java
		List<FieldUnderTest> classVariables = cut.getClassVariables().toList();
		assertTrue(classVariables.isEmpty(),
				"Det borde inte behövas några statiska variabler i klassen %s för denna uppgift. De som fanns var: %s"
						.formatted(cut.name(), classVariables));
		List<MethodUnderTest> classMethods = cut.getClassMethods().toList();
		assertTrue(classMethods.isEmpty(),
				"Det borde inte behövas några statiska metoder i klassen %s för denna uppgift. De som fanns var: %s"
						.formatted(cut.name(), classMethods));

		for (Class<?> c : new Class<?>[] { String.class, char.class, byte.class, short.class, int.class, long.class,
				float.class, double.class }) {
			assertEquals(0, cut.getInstanceFields(c).count(),
					"Det borde inte finnas något behov av någon instansvariabel av typen %s i klassen %s".formatted(c,
							cut.name()));
		}

		// Metoden under test
		for (MethodUnderTest mut : muts) {
			assertTrue(mut.exists(),
					"Hittar ingen metod med namnet %s() i klassen %s. Är namnen korrekta i TestData.java?"
							.formatted(mut.name(), cut.name()));
		}
	}

	protected void assertListContains(FieldUnderTest list, Object sut, Object... expectedItems) {
		assertListContainsEveryoneBut(list, sut, null, expectedItems);
	}

	protected void assertListContainsEveryoneBut(FieldUnderTest list, Object sut, Object exception,
			Object... expectedItems) {
		Collection<?> actual = (Collection<?>) list.getValue(sut);
		Collection<?> expected = new ArrayList<>(Arrays.asList(expectedItems));
		expected.remove(exception);

		assertTrue(actual.containsAll(expected),
				"Något saknas i %s, det förväntade var %s".formatted(actual, expected));
		assertTrue(expected.containsAll(actual),
				"%s innehåller för mycket, det förväntade var %s".formatted(actual, expected));
	}

	// Går tyvärr inte att kontrollera att listan faktiskt innehåller det vi vill,
	// så vi nöjer oss med att kontrollera att det är en samling
	@SuppressWarnings("unchecked")
	protected <T> List<T> getList(FieldUnderTest field, Object sut) {
		return (List<T>) field.getValue(sut);
	}

	// TODO: ordna metoderna i klasserna nedan i en mer logisk ordning, nu ligger de
	// i den ordning de skapades

	public static abstract class SoftwareUnderTest<T> {

		private final T sut;
		private final String name;
		private final String source;

		public SoftwareUnderTest(T sut, String name, String source) {
			this.sut = sut;
			this.name = name;
			this.source = source;
		}

		public String name() {
			return name;
		}

		public boolean exists() {
			return sut != null;
		}

		public T sut() {
			if (sut == null)
				throw new IllegalStateException("""
						Kunde inte hitta en %s med namnet \"%s\".
						Källan till denna %s är %s.
						Det är inte säkert att felet finns där, men det är ett bra ställe att börja leta efter det."""
						.formatted(sutTypeName(), name, sutTypeName(), source));
			return sut;
		}

		protected abstract String sutTypeName();

		protected final static boolean isPublic(Member m) {
			return Modifier.isPublic(m.getModifiers());
		}

		protected final static boolean isPrivate(Member m) {
			return Modifier.isPrivate(m.getModifiers());
		}

		protected final static boolean isStatic(Member m) {
			return Modifier.isStatic(m.getModifiers());
		}

		protected final static boolean isFinal(Member m) {
			return Modifier.isFinal(m.getModifiers());
		}

		protected final static boolean isConstant(Field f) {
			return isStatic(f) && isFinal(f) && f.getName().equals(f.getName().toUpperCase());
		}

		protected final static boolean isConstant(Field f, Class<?>... okTypes) {
			boolean okType = false;
			for (Class<?> type : okTypes) {
				okType = okType || f.getType() == type;
			}
			return okType && isConstant(f);
		}

		@Override
		public String toString() {
			// return "%s (%s)".formatted(name, source);
			return name;
		}

	}

	public static class ClassUnderTest extends SoftwareUnderTest<Class<?>> {

		public ClassUnderTest(String name, String source) {
			this(load(name), name, source);
		}

		public ClassUnderTest(Class<?> cut, String source) {
			this(cut, cut.getName(), source);
		}

		private ClassUnderTest(Class<?> cut, String name, String source) {
			super(cut, name, source);
		}

		private static Class<?> load(String className) {
			try {
				return ClassLoader.getSystemClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
				return null;
			}

		}

		@Override
		protected String sutTypeName() {
			return "klass";
		}

		public Stream<FieldUnderTest> getFields() {
			return Arrays.stream(sut().getDeclaredFields())
					.map(f -> new FieldUnderTest(f, "ClassUnderTest.getFields()"));
		}

		public Stream<FieldUnderTest> getInstanceFields() {
			return getFields().filter(f -> f.isNonStatic());
		}

		public Stream<FieldUnderTest> getInstanceFields(Class<?> type) {
			return getFields().filter(f -> f.isNonStatic() && f.hasType(type));
		}

		// TODO: Borde finnas en för alla fält, inte bara instans
		public Stream<FieldUnderTest> getInstanceFieldsAssignableTo(Class<?> type) {
			return getFields().filter(f -> type.isAssignableFrom(f.sut().getType()));
		}

		public Stream<FieldUnderTest> getFinalAndNonFinalInstanceVariables() {
			return getFields().filter(f -> f.isNonStatic());
		}

		public Stream<FieldUnderTest> getNonFinalInstanceVariables() {
			return getFields().filter(f -> f.isNonStatic() && f.isNonFinal());
		}

		public Stream<FieldUnderTest> getClassVariables() {
			return getFields().filter(f -> f.isStatic() && f.isNonFinal());
		}

		public Stream<FieldUnderTest> getConstants() {
			return getFields().filter(f -> f.isStatic() && f.isFinal());
		}

		public Stream<FieldUnderTest> getStaticFields() {
			return getFields().filter(f -> f.isStatic());
		}

		public Stream<ConstructorUnderTest> getConstructors() {
			return Arrays.stream(sut().getDeclaredConstructors())
					.map(c -> new ConstructorUnderTest(c, "ClassUnderTest.getConstructors()"));
		}

		public Stream<ConstructorUnderTest> getPublicConstructors() {
			return getConstructors().filter(c -> c.isPublic());
		}

		public Stream<MethodUnderTest> getMethods() {
			return Arrays.stream(sut().getDeclaredMethods())
					.map(m -> new MethodUnderTest(m, "ClassUnderTest.getMethods()"))
					.filter(m -> !m.name().contains("lambda$"));
		}

		public Stream<MethodUnderTest> getInstanceMethods() {
			return getMethods().filter(m -> m.isNonStatic());
		}

		public Stream<MethodUnderTest> getClassMethods() {
			return getMethods().filter(m -> m.isStatic());
		}

		public Stream<MethodUnderTest> getPublicMethods() {
			return getMethods().filter(m -> m.isPublic());
		}

		public FieldUnderTest getField(String name, String source) {
			return getField(name, Object.class, source);
		}

		public FieldUnderTest getField(String name, Class<?> expectedTypeOrSuperclassOfType, String source) {
			try {
				var field = new FieldUnderTest(sut().getDeclaredField(name), source);
				if (!expectedTypeOrSuperclassOfType.isAssignableFrom(field.sut().getType()))
					throw new IllegalArgumentException("Fältet %s har fel typ, borde vara %s (eller en subklass)"
							.formatted(field, expectedTypeOrSuperclassOfType));
				return field;
			} catch (NoSuchFieldException | SecurityException e) {
				throw new IllegalStateException(
						"Hittar inget fält med namnet %s i %s. Källan till namnet är %s. Det är inte säkert att felet är där, men det är ett bra ställe att börja leta."
								.formatted(name, name(), source));
			}
		}

		public FieldUnderTest getOnlyFieldOfType(Class<?> type) {
			var fields = getFields().filter(f -> f.hasType(type)).toList();
			if (fields.size() == 0)
				throw new IllegalStateException("Hittar inget fält med typen %s i  %s".formatted(type, name()));
			if (fields.size() > 1)
				throw new IllegalStateException(
						"Det finns flera fält av typen %s i %s, testprogrammet förväntade sig bara ett".formatted(type,
								name()));
			return fields.get(0);
		}

		public FieldUnderTest getOnlyFieldAssignableFromType(Class<?> type) {
			var fields = getInstanceFieldsAssignableTo(type).toList();
			if (fields.size() == 0)
				throw new IllegalStateException(
						"Inget fält av typen %s (eller subklasser) i %s".formatted(type, name()));
			if (fields.size() > 1)
				throw new IllegalStateException(
						"Flera fält av typen %s (eller subklasser) i %s, testprogrammet förväntade sig bara ett"
								.formatted(type, name()));
			return fields.get(0);
		}

		public FieldUnderTest getOnlyFieldAssignableFromType(ClassUnderTest c) {
			return getOnlyFieldAssignableFromType(c.sut());
		}

		public ConstructorUnderTest getConstructor(Class<?>... parameterTypes) {
			try {
				return new ConstructorUnderTest(sut().getConstructor(parameterTypes),
						"ClassUnderTest.getConstructor(Class<?>...)");
			} catch (NoSuchMethodException | SecurityException e) {
				throw new IllegalStateException("Det finns ingen konstruktor med parametrarna %s i %s"
						.formatted(paramsAsString(parameterTypes), name()));
			}
		}

		public MethodUnderTest getMethod(String methodName, String source, Class<?>... parameterTypes) {
			String fullMethodName = fullMethodName(methodName, parameterTypes);
			try {
				return new MethodUnderTest(sut().getDeclaredMethod(methodName, parameterTypes), fullMethodName, source);
			} catch (NoSuchMethodException | SecurityException e) {
				return new MethodUnderTest(null, fullMethodName, source);
			}
		}

		private String fullMethodName(String methodName, Class<?>... parameterTypes) {
			return "%s.%s(%s)".formatted(name(), methodName, paramsAsString(parameterTypes));
		}

		private String paramsAsString(Class<?>... parameterTypes) {
			return Arrays.stream(parameterTypes).map(p -> p.getName()).collect(Collectors.joining(", "));
		}

	}

	public static abstract class MemberUnderTest<T extends Member> extends SoftwareUnderTest<T> {
		public MemberUnderTest(T sut, String name, String source) {
			super(sut, name, source);
		}

		public final boolean isPublic() {
			return isPublic(sut());
		}

		public final boolean isPrivate() {
			return isPrivate(sut());
		}

		public final boolean isStatic() {
			return isStatic(sut());
		}

		public final boolean isNonStatic() {
			return !isStatic();
		}

		public final boolean isFinal() {
			return isFinal(sut());
		}

		public final boolean isNonFinal() {
			return !isFinal();
		}

	}

	public static class FieldUnderTest extends MemberUnderTest<Field> {

		public FieldUnderTest(Field fut, String source) {
			super(fut, fut.getName(), source);
			fut.setAccessible(true);
		}

		public FieldUnderTest(String name, String source) {
			super(null, name, source);
		}

		@Override
		protected String sutTypeName() {
			return "fält";
		}

		public boolean hasType(Class<?> type) {
			return sut().getType() == type;
		}

		public boolean isAssignableTo(Class<?> type) {
			return type.isAssignableFrom(sut().getType());
		}

		public Object getValue(Object obj) {
			try {
				return sut().get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalArgumentException(
						"Unable to access the field %s on the object %s".formatted(sut(), obj), e);
			}
		}

	}

	public static class ConstructorUnderTest extends MemberUnderTest<Constructor<?>> {

		public ConstructorUnderTest(Constructor<?> cut, String source) {
			super(cut, cut.getName(), source);
		}

		public ConstructorUnderTest(String name, String source) {
			super(null, name, source);
		}

		@Override
		protected String sutTypeName() {
			return "konstruktor";
		}

		public Object newInstance(Object... args) {
			try {
				return sut().newInstance(args);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				if (e.getClass() == InvocationTargetException.class)
					throw new RuntimeException("Fel vid instansiering av klassen %s med argumenten %s: %s"
							.formatted(name(), Arrays.toString(args), e.getCause()), e.getCause());

				throw new RuntimeException("Fel vid instansiering av klassen %s med argumenten %s: %s".formatted(name(),
						Arrays.toString(args), e), e);
			}
		}

	}

	public static class MethodUnderTest extends MemberUnderTest<Method> {

		public MethodUnderTest(Method mut, String source) {
			this(mut, mut.getName(), source);
		}

		private MethodUnderTest(Method mut, String name, String source) {
			super(mut, name, source);
			if (mut != null)
				mut.setAccessible(true);
		}

		@Override
		protected String sutTypeName() {
			return "metod";
		}

		public int getParameterCount() {
			return sut().getParameterCount();
		}

		public Class<?> getReturnType() {
			return sut().getReturnType();
		}

		public Object invoke(Object obj, Object... args) {
			try {
				return assertTimeoutPreemptively(MAXIMUM_METHOD_INVOCATION_TIME_MILLIS, () -> sut().invoke(obj, args),
						"Det tog för lång tid att köra metoden %s. Maxgränsen är satt till %.1fs vilket bör vara tillräckligt"
								.formatted(name(), MAXIMUM_METHOD_INVOCATION_TIME_MILLIS.toMillis() / 1000.0));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(
						"Ett problem med argument vid metodanrop upptäcktes när %s försökte köras. Testprogrammet anropade metoden med argumenten %s.\nObservera att detta fel också kan komma från metodanrop som metoden som testas gör. Det ursprungliga undantaget finns därför nedan."
								.formatted(this, Arrays.toString(args)),
						e);
			}
		}

	}

}
