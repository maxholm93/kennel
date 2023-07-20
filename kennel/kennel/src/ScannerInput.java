// Max Holm maho6364
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class ScannerInput {
	private static ArrayList<InputStream> streamList = new ArrayList <>();
	private Scanner input;

	public ScannerInput( InputStream stream ){
		if (streamList.contains(stream)) {
			throw new IllegalStateException ("Error: input not found");
		}
		this.input = new Scanner(stream);
		streamList.add(stream);
	}

	public ScannerInput() {
		this(System.in);
	}
		
	private int readInteger(String s) {
		System.out.print( s + "?>" );
		int readInteger = input.nextInt(); 
		input.nextLine();
		return readInteger;
		
	}
    
    private double readDecimal(String s) {
        System.out.print( s + "?>" ); 
		double readDecimal = input.nextDouble(); 
		input.nextLine();
		return readDecimal;
	}
   
    private String readText(String s) {
        System.out.print( s + "?>" );
		String readText = input.nextLine().trim(); 
		return readText;
    }
	
		
	public String inputCorrection(String prompt) { 
		System.out.print(prompt); 
		String corrScan = "";
		String ownerScan = input.nextLine().toLowerCase().trim();
		if(!ownerScan.isEmpty()) {
			corrScan = ownerScan.substring(0, 1).toUpperCase() + ownerScan.substring(1).toLowerCase();
		}
		return corrScan;
	}
	public void closeDown(){
	    input.close();
	}
}
