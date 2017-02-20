import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		String input = "/home/johncyr/Documents/efs/forms/BuyersGuide/Buyers Guide_noform.pdf";
		String output = "/home/johncyr/Documents/efs/forms/BuyersGuide/Results.pdf";
		
		PdDoc test = new PdDoc(input, output);
		test.createAcroForm();
		test.save();
		test.close();
	}
}

