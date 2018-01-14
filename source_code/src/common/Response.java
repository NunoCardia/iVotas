package common;

import java.io.Serializable;
import java.util.ArrayList;


public class Response implements Serializable {

	private ArrayList<String> info;
	private boolean success;

	public Response() {
	}

	public ArrayList<String> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}