package model;

/**
 * The Employee class represents a single employee.
 * 
 * @author John Phillips
 * @version 20151015
 *
 */
public class Deer {

	private int buckId;
	private String points;
	private String date;
	private String time;
	private String weather;

	public Deer() {
		buckId = 0;
		points = "";
		date = "";
		time = "";
		weather = "";
	}

	public Deer(int buckId, String points, String date, String time, String weather) {
		this.buckId = buckId;
		this.points = points;
		this.date = date;
		this.time = time;
		this.weather = weather;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getBuckId() {
		return buckId;
	}

	public void setEmpId(int buckId) {
		this.buckId = buckId;
	}

	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return String.format("%5d : %s, %s, %s, %s", this.getBuckId(), this.getPoints(),
				this.getDate(), this.getTime(), this.getWeather());
	}
}
