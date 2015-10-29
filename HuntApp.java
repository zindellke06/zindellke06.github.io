package viewui;

import java.util.Scanner;

import model.Deer;
import model.IDeerDAO;
import model.datastore.mysql.DeerDAO;

/**
 * @author Kyle Zindell
 * @version 20151015
 * 
 */
public class HuntApp {

	IDeerDAO huntList = new DeerDAO();
	Scanner sc = new Scanner(System.in);

	public HuntApp() {
		menuLoop();
	}

	private void menuLoop() {
		int buckId;
		String points, date, time, weather;
		String choice = "1";
		while (!choice.equals("0")) {
			System.out.println("\nEmployee App");
			System.out.println("0 = Quit");
			System.out.println("1 = List All Records");
			System.out.println("2 = Create New Record");
			System.out.println("3 = Retrieve Record");
			System.out.println("4 = Update Record");
			System.out.println("5 = Delete Record");
			choice = Validator.getLine(sc, "Number of choice: ", "^[0-5]$");

			switch (choice) {
			case "1":
				System.out.println(huntList.toString());
				break;
			case "2":
				buckId = Validator.getInt(sc, "New Buck ID: ");
				points = Validator.getLine(sc, "Number of points: ");
				date = Validator.getLine(sc, "Date: ");
				time = Validator.getLine(sc, "Time: ");
				weather = Validator.getLine(sc, "Weather: ");
				huntList.createRecord(new Deer(buckId, points, date, time, weather));
				break;
			case "3":
				buckId = Validator.getInt(sc, "Employee id to retrieve: ");
				System.out.println(huntList.retrieveRecordById(buckId));
				break;
			case "4":
				buckId = Validator.getInt(sc, "Buck ID to update: ");
				points = Validator.getLine(sc, "Number of points: ");
				date = Validator.getLine(sc, "Date: ");
				time = Validator.getLine(sc, "Time: ");
				weather = Validator.getLine(sc, "Weather: ");
				huntList.updateRecord(new Deer(buckId, points, date, time, weather));
				break;
			case "5":
				buckId = Validator.getInt(sc, "Buck ID to delete: ");
				System.out.println(huntList.retrieveRecordById(buckId));
				String ok = Validator.getLine(sc, "Delete this record? (y/n) ", "^[yYnN]$");
				if (ok.equalsIgnoreCase("Y")) {
					huntList.deleteRecord(buckId);
				}
				break;
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new HuntApp();
	}
}
