package com.prathap.file.filehandler.common;

public class FilehandlerHealthInfo {
	
	private final String currentTime;
	private final String message;
	
	public FilehandlerHealthInfo(String currentTime, String message) {
		super();
		this.currentTime = currentTime;
		this.message = message;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "FilehandlerHealthInfo [currentTime=" + currentTime + ", message=" + message + "]";
	}
	
}
