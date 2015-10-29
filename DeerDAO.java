package model.datastore.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Deer;
import model.IDeerDAO;


/**
 * @author Kyle Zindell
 * @version 20151015
 *
 */
public class DeerDAO implements IDeerDAO {
	
	protected final static boolean DEBUG = true;

	@Override
	public void createRecord(Deer deer) {
		final String QUERY = "insert into deer  (buckId, points, date, time, weather) VALUES (null, ?, ?, ?, ?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY);) {
			stmt.setString(1, deer.getPoints());
			stmt.setString(2, deer.getDate());
			stmt.setString(3, deer.getTime());
			stmt.setString(4, deer.getWeather());
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("createRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public Deer retrieveRecordById(int id) {
		final String QUERY = "select buckId, points, date, time, weather from deer";
		// final String QUERY = "select buckId, points, date, time, weather;		
		Deer emp = null;

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			// stmt.setInt(1, id);
			if(DEBUG) System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery(QUERY);

			if (rs.next()) {
				emp = new Deer(rs.getInt("buckId"), rs.getString("points"), rs.getString("date"),
						rs.getString("time"), rs.getString("weather"));
			}
		} catch (SQLException ex) {
			System.out.println("retrieveRecordById SQLException: " + ex.getMessage());
		}

		return emp;
	}

	@Override
	public List<Deer> retrieveAllRecords() {
		final List<Deer> myList = new ArrayList<>();
		final String QUERY = "select buckId, points, date, time, weather drom deer";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			if(DEBUG) System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery(QUERY);
			
			while (rs.next()) {
				myList.add(new Deer(rs.getInt("buckId"), rs.getString("points"), rs.getString("date"),
						rs.getString("time"), rs.getString("weather")));
			}
		} catch (SQLException ex) {
			System.out.println("retrieveAllRecords SQLException: " + ex.getMessage());
		}

		return myList;
	}

	@Override
	public void updateRecord(Deer updatedDeer) {
		final String QUERY = "update deer set points=?, date=?, time=?, weather=? where buckId=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			stmt.setString(1, updatedDeer.getPoints());
			stmt.setString(2, updatedDeer.getDate());
			stmt.setString(3, updatedDeer.getTime());
			stmt.setString(4, updatedDeer.getWeather());
			stmt.setInt(5, updatedDeer.getBuckId());
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("updateRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public void deleteRecord(int id) {
		final String QUERY = "delete from deer where buckId = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			stmt.setInt(1, id);
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("deleteRecord SQLException: " + ex.getMessage());
		}
	}

//	@Override
//	public void deleteRecord(Deer deer) {
//		final String QUERY = "delete from deer where buckId = ?";
//
//		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
//			stmt.setInt(1, deer.getBuckId());
//			if(DEBUG) System.out.println(stmt.toString());
//			stmt.executeUpdate();
//		} catch (SQLException ex) {
//			System.out.println("deleteRecord SQLException: " + ex.getMessage());
//		}
//	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Deer deer : retrieveAllRecords()) {
			sb.append(deer.toString() + "\n");
		}

		return sb.toString();
	}
}
