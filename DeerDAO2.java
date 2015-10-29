package model.datastore.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Deer;
import model.IDeerDAO;

/**
 * @author Kyle Zindell
 * @version 20151009
 * 
 */
public class DeerDAO implements IDeerDAO {

	protected String fileName = null;
	protected final List<Deer> myList;

	public DeerDAO() {
		Properties props = new Properties();
		FileInputStream fis = null;

		// read the properties file
		try {
			fis = new FileInputStream("res/file/db.properties");
			props.load(fis);
			this.fileName = props.getProperty("DB_FILENAME");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.myList = new ArrayList<>();
		try {
			Files.createFile(Paths.get(fileName));
		} catch (FileAlreadyExistsException fae) {
			;
		} catch (IOException ioe) {
			System.out.println("Create file error with " + ioe.getMessage());
		}
		readList();
	}

	@Override
	public void createRecord(Deer deer) {
		myList.add(deer);
		writeList();
	}

	@Override
	public Deer retrieveRecordById(int Buckid) {
		for (Deer deer : myList) {
			if (deer.getBuckId() == Buckid) {
				return deer;
			}
		}
		return null;
	}

	@Override
	public List<Deer> retrieveAllRecords() {
		return myList;
	}

	@Override
	public void updateRecord(Deer updateDeer) {
		for (Deer deer : myList) {
			if (deer.getBuckId() == updateDeer.getBuckId()) {
				deer.setPoints(updateDeer.getPoints());
				deer.setDate(updateDeer.getDate());
				deer.setTime(updateDeer.getTime());
				deer.setWeather(updateDeer.getWeather());
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(int id) {
		for (Deer deer : myList) {
			if (deer.getBuckId() == id) {
				myList.remove(deer);
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(Deer deer) {
		myList.remove(deer);
		writeList();
	}

	protected void readList() {
		Path path = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int buckId = Integer.parseInt(data[0]);
				String points = data[1];
				String date = data[2];
				String time = data[3];
				String weather = data[4];
				Deer deer = new Deer(buckId, points, date, time, weather);
				myList.add(deer);
			}
		} catch (IOException ioe) {
			System.out.println("Read file error with " + ioe.getMessage());
		}
	}

	protected void writeList() {
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (Deer deer : myList) {
				writer.write(String.format("%d,%s,%s,%s,%s\n", deer.getBuckId(), deer.getPoints(),
						deer.getDate(), deer.getTime(), deer.getWeather()));
			}
		} catch (IOException ioe) {
			System.out.println("Write file error with " + ioe.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Deer deer : myList) {
			sb.append(String.format("%5d : %s, %s, %s, %s\n", deer.getBuckId(), deer.getPoints(),
					deer.getDate(), deer.getTime(), deer.getWeather()));
		}

		return sb.toString();
	}
}
