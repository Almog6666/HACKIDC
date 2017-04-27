package dataTypes;

import java.io.Serializable;

public class Job implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int job_id;
	private String location;
	private int user_id;
	private String date;
	public Job(int job_id, String location, int user_id, String date, String time_from, String time_to, int model,
			String ecode, String edisc, String efix) {
		super();
		this.job_id = job_id;
		this.location = location;
		this.user_id = user_id;
		this.date = date;
		this.time_from = time_from;
		this.time_to = time_to;
		this.model = model;
		this.ecode = ecode;
		this.edisc = edisc;
		this.efix = efix;
	}
	@Override
	public String toString() {
		return "Job [job_id=" + job_id + ", location=" + location + ", user_id=" + user_id + ", date=" + date
				+ ", time_from=" + time_from + ", time_to=" + time_to + ", model=" + model + ", ecode=" + ecode
				+ ", edisc=" + edisc + ", efix=" + efix + "]";
	}
	private String time_from;
	private String time_to;
	private int model;
	private String ecode;
	private String edisc;//discripion
	private String efix;
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime_from() {
		return time_from;
	}
	public void setTime_from(String time_from) {
		this.time_from = time_from;
	}
	public String getTime_to() {
		return time_to;
	}
	public void setTime_to(String time_to) {
		this.time_to = time_to;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getEdisc() {
		return edisc;
	}
	public void setEdisc(String edisc) {
		this.edisc = edisc;
	}
	public String getEfix() {
		return efix;
	}
	public void setEfix(String efix) {
		this.efix = efix;
	}
}
