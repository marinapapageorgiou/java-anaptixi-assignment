package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import engine.Engine;

public class Client {
	
	static Scanner sc = new Scanner(System.in);
	static String file;
	static String type;
	static String alias;
	static Engine engine = null;
	static List<List<String>> inputSpec;
	static List<Engine> engines = new ArrayList<Engine>();
	static List<String> listAlias = new ArrayList<String>();
	
	public static void main(String[] args) {
		System.out.print("<------------------------- Επεξεργαστής Κειμένου ------------------------->\n");
		
		String option = "0";
		
		while(true) {
			
			System.out.print("\nΕπέλεξε μία από τις παρακάτω επιλογές για να συνεχίσεις:\n");
			System.out.print("1. Δημιούργησε ένα καινούριο αρχείο προς επεξεργασία. \n");
			System.out.print("2. Φόρτωσε το αρχείο που έχεις δημιουργήσει ή/και χαρακτήρισε την παραγράφους του με βάση τους δωθέντες κανόνες.\n");
			System.out.print("3. Εισήγαγε κανόνες για ένα αρχείο (Ύστερα επέλεξε το 2 για να χαρακτηριστούν οι παράγραφοι). \n");
			System.out.print("4. Εκτύπωσε τα στατιστικά ενός αρχείου. \n");
			System.out.print("5. Εξήγαγε ένα αρχείο σε μορφή Markdown. \n");
			System.out.print("6. Εξήγαγε ένα αρχείο σε μορφή PDF. \n");
			System.out.print("7. Τερμάτισε το πρόγραμμα. \n");
			System.out.print("**Για να μπορέσεις να εκτελέσεις τις επιλογές 2-6 πρέπει να έχεις τουλάχιστον ένα δημιουργημένο αρχείο.** \n");
			
			option = sc.nextLine();
			
			if (option.equals("1")) {
				System.out.print("\nΓράψε την πλήρη διαδρομή του αρχείου που θέλεις να εισάγεις. \n");
				file = sc.nextLine();
				
				System.out.print("Γράψε τον τύπο του αρχείου που εισήγαγες (RAW ή ANNOTATED). \n");
				type = sc.nextLine();
				
				System.out.print("Γράψε το φιλικό όνομα (alias) που θες να δώσεις στο αρχείο (πρέπει να είναι μοναδικό). \n");
				alias = sc.nextLine();
				listAlias.add(alias);
				
				engine = new Engine(file, type, alias);
				engines.add(engine);
				
				List<List<String>> inputAnnSpec = new ArrayList<List<String>>();
				if (type.equals("ANNOTATED")) {
					System.out.print("Το αρχείο που επιλέξατε είναι επισημειωμένο. \n"
								   + "Εισάγετε τους κανόνες που έχετε ήδη δημιουργήσει για το αρχείο (μετά από κάθε κανόνα πατήστε ENTER). \n"
								   + "Πρέπει να είναι της μορφής π.χ. \"H1 STARTS_WITH #\". \n"
								   + "Όταν ολοκληρώσετε την εισαγωγή γράψτε \"no\". \n");
					
					String addAnnotatedRule = "";
					
					while (!addAnnotatedRule.equals("no")) {
						addAnnotatedRule = sc.nextLine();
						if (!addAnnotatedRule.equals("no")) {
							String [] rule = addAnnotatedRule.split(" ");
							List<String> listRule = new ArrayList<String>();
							listRule.add(rule[0]);
							listRule.add(rule[1]);
							listRule.add(rule[2]);
							inputAnnSpec.add(listRule);
						}
					}
					
					System.out.print("\nΕισάγετε τα prefixes πατώντας ENTER μετά από το καθένα, όταν τελειώσετε πληκτρολογήστε \"no\". \n");
					String prefix = "";
					List<String> prefixes = new ArrayList<String>();
					
					while(!prefix.equals("no")) {
						prefix = sc.nextLine();
						prefixes.add(prefix);
					}
					
					engine.registerInputRuleSetForAnnotatedFiles(inputAnnSpec, prefixes);
				}
			}
			else if (option.equals("2")) {
				chooseFile();
				
				engine.loadFileAndCharacterizeBlocks();
			}
			else if (option.equals("3")) { //Input Rules
				chooseFile();
								
				inputSpec = new ArrayList<List<String>>();
								
				String addRule = "";
				while(!addRule.equals("no")) {
					System.out.print("Θέλεις να εισάγεις κανόνα; Αν ναι γράψε τον κανόνα σε σωστή μορφή και πάτησε ENTER αλλιώς γράψε \"no\". \n");
					addRule = sc.nextLine();
					if (!addRule.equals("no")) {
						String [] rule = addRule.split(" ");
						List<String> listRule = new ArrayList<String>();
						listRule.add(rule[0]);
						listRule.add(rule[1]);
						if(rule.length == 3) //all_caps
							listRule.add(rule[2]); 
						inputSpec.add(listRule);
					}
				}

				engine.registerInputRuleSetForPlainFiles(inputSpec);
			}
			else if (option.equals("4")) { //Statistics
				chooseFile();
				
				System.out.print(engine.reportWithStats());
			}
			else if (option.equals("5")) { //Markdown
				chooseFile();
				
				System.out.print("Εισάγετε το πλήρες path του αρχείου στο οποίο θέλετε να εξάγετε το αρχείο εισόδου. \n");
				String output = sc.nextLine();
				engine.exportMarkDown(output);
			}
			else if (option.equals("6")) { //Pdf
				chooseFile();
				
				System.out.print("Εισάγετε το πλήρες path του αρχείου στο οποίο θέλετε να εξάγετε το αρχείο εισόδου. \n");
				String output = sc.nextLine();
				engine.exportPdf(output);
			}
			else if (option.equals("7")) {
				System.out.print("\nΤο πρόγραμμα τερματίστηκε.\n");
				System.exit(0);
				sc.close();
				System.out.print("\n<-------------------------------- Τέλος ------------------------------->\n");
			}
			else {
				System.out.print("Η επιλογή που επέλεξες δεν υπάρχει ή δεν έχει διατυπωθεί σωστά. Πληκτρολόγησε έναν αριθμό από 1-7. \n");
			}
		}	
	}
	
	private static void chooseFile() {
		
		System.out.print("Επέλεξε ένα από τα υπάρχον αρχεία: \n");
		for (int i=0; i<listAlias.size(); i++) {
			System.out.print(listAlias.get(i) + "\n");
		}
		
		alias = sc.nextLine();
		
		for (int i=0; i<engines.size(); i++) {
			if (alias.equals(engines.get(i).getAlias())) {
				engine = engines.get(i);
			}
		}
	}

}
