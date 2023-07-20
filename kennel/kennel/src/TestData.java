


// Max Holm maho6364

/**
 * Denna klass är till för att möjliggöra testning av de klasser och metoder som
 * du själv får namnge. Många av JUnit-testfallen till inlämingsuppgiftens olika
 * delar behöver kunna identifiera dessa, och de kommer att leta efter namnen
 * här.
 * <p>
 * Vilka konstanter som behöver finnas varierar från uppgift till uppgift.
 * Kommentarerna nedan nämner är konstanterna först blir relevanta. Därefter kan
 * de användas av de test som behöver dem. JUnit-testfallen för de olika
 * uppgifterna kontrollerar att allt som behövs finns tillgängligt, och varnar
 * om så inte är fallet.
 */
public final class TestData {

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör metoder i klassen Dog (introduceras i U6.2)
	// Den första konstanten nedan introduceras i U8.3
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Konstanten ska innehålla namnet på den metod i klassen Dog som sätter ägaren
	 * till hunden. Denna metod introduceras i U8.3.
	 */
	public static final String SET_OWNER_OF_DOG_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på den metod i klassen Dog som tar bort
	 * ägaren från hunden. Denna metod introduceras i U8.6.
	 * <p>
	 * Beroende på hur du valt att designa klassen kan detta vara samma metod som i
	 * <code>SET_OWNER_OF_DOG_METHOD_NAME</code>, eller en helt annan.
	 */
	public static final String REMOVE_OWNER_OF_DOG_METHOD_NAME = "...";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör metoder i inläsningsklassen (introduceras i U6.3)
	// Samtliga konstanter nedan introduceras i U6.3
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Detta är namnet på din inläsningsklass från U6.3.
	 */
	public static final String SCANNER_ADAPTER_CLASS_NAME = "ScannerInput";

	/**
	 * Detta är namnet på den metod i din inläsningsklass som läser in text från
	 * användaren.
	 */
	public static final String READ_TEXT_METHOD_NAME = "readText";

	/**
	 * Detta är namnet på den metod i din inläsningsklass som läser in ett heltal
	 * från användaren.
	 */
	public static final String READ_INTEGER_METHOD_NAME = "readInteger";

	/**
	 * Detta är namnet på den metod i din inläsningsklass som läser in ett
	 * decimaltal från användaren.
	 */
	public static final String READ_DECIMAL_METHOD_NAME = "readDecimal";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör metoder i hundlistklassen (introduceras i U7.6)
	// Samtliga konstanter nedan introduceras i U7.6
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Detta är namnet på din hundlistklass från U7.6.
	 */
	public static final String DOG_LIST_CLASS_NAME = "...";

	/**
	 * Detta är namnet på metoden i hundlistklassen som lägger till en hund till
	 * listan.
	 */
	public static final String DOG_LIST_CLASS_ADD_DOG_METHOD_NAME = "...";

	/**
	 * Detta är namnet på metoden i hundlistklassen som tar bort en hund ur listan.
	 */
	public static final String DOG_LIST_CLASS_REMOVE_DOG_METHOD_NAME = "...";

	/**
	 * Detta är namnet på metoden i hundlistklassen som kontrollerar om en given
	 * hund finns i listan eller inte.
	 */
	public static final String DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME = "...";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör metoder i klassen Owner (introduceras i U8.1)
	// Den första konstanten nedan introduceras i U8.3
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Konstanten ska innehålla namnet på den metod i klassen Owner som lägger till
	 * en hund till ägaren. Denna metod introduceras i U8.3.
	 */
	public static final String ADD_DOG_TO_OWNER_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på den metod i klassen Owner som tar bort en
	 * hund från ägaren. Denna metod introduceras i U8.6.
	 */
	public static final String REMOVE_DOG_FROM_OWNER_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på den metod i klassen Owner som kontrollerar
	 * om ägaren äger en given hund. Denna metod introduceras i U8.5.
	 */
	public static final String OWNS_DOG_METHOD_NAME = "...";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör datat i de klasser som heter AssignmentXPointY.
	// Den första konstanten nedan introduceras i U7.1
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Detta är namnet på instansvariabeln som håller klassens lista av hundar. Den
	 * introduceras i U7.1.
	 */
	public static final String MAIN_DOG_LIST_NAME = "...";

	/**
	 * Detta är namnet på instansvariabeln som håller klassens lista av ägare. Den
	 * introduceras i U8.1.
	 */
	public static final String MAIN_OWNER_LIST_NAME = "...";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör hjälpmetoderna du ska skriva i de klasser som heter
	// AssignmentXPointY. Den första konstanten nedan introduceras i U7.3
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Konstanten ska innehålla namnet på metoden för att hitta en given hund. Denna
	 * metod introduceras i U7.3.
	 */
	public static final String FIND_DOG_METHOD_NAME = "findDogByName";

	/**
	 * Konstanten ska innehålla namnet på metoden för att hitta en given ägare.
	 * Denna metod introduceras i U8.2.
	 */
	public static final String FIND_OWNER_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på hjälp-metoden för att hitta hundar med en
	 * tillräckligt lång svans som kan implementeras i U7.2. Denna är inte
	 * obligatorisk, men gör det lättare att implementera den föregående metoden.
	 */
	public static final String FIND_DOGS_WITH_TAILLENGTH_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden för att byta plats på två hundar
	 * med din egen implementation av bytet. Denna metod introduceras i U7.7,
	 * specifikt U7.7.1.1.
	 */
	public static final String SWAP_DOGS_OWN_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden för att byta plats på två hundar
	 * med hjälp av Collections.swap. Denna metod introduceras i U7.7, specifikt
	 * U7.7.1.2.
	 */
	public static final String SWAP_DOGS_USING_CLASS_LIBRARY_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden för att hitta den "..." av de
	 * kvarvarande hundarna. Denna metod introduceras i U7.7, specifikt U7.7.3.
	 */
	public static final String FIND_SMALLEST_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden för att sortera hundarna. Denna
	 * metod introduceras i U7.7, specifikt U7.7.4.
	 */
	public static final String SORT_DOGS_METHOD_NAME = "...";

	//////////////////////////////////////////////////////////////////////////
	// Konstanter som rör metoderna du ska skriva i de klasser som heter
	// AssignmentXPointY för att implementera de olika kommandona som det
	// slutgitiga systemet ska ha. Den första konstanten nedan introduceras
	// i U7.1.
	//////////////////////////////////////////////////////////////////////////

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U7.1.
	 */
	public static final String REGISTER_NEW_DOG_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U7.2.
	 */
	public static final String LIST_DOGS_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U7.4.
	 */
	public static final String INCREASE_AGE_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U7.5.
	 */
	public static final String REMOVE_DOG_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U8.1.
	 */
	public static final String REGISTER_NEW_OWNER_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U8.3.
	 */
	public static final String GIVE_DOG_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U8.4.
	 */
	public static final String LIST_OWNERS_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U8.6.
	 */
	public static final String REMOVE_OWNED_DOG_METHOD_NAME = "...";

	/**
	 * Konstanten ska innehålla namnet på metoden som ska implementeras i U8.7.
	 */
	public static final String REMOVE_OWNER_METHOD_NAME = "...";

}