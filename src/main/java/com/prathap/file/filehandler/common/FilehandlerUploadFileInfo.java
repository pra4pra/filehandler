package com.prathap.file.filehandler.common;

public class FilehandlerUploadFileInfo {

	private  String fileUploadTime;
	private  String fileUploadMessage;
	private  String fileName;

	public FilehandlerUploadFileInfo(Builder builder) {
		this.fileUploadTime = builder.fileUploadTime;
		this.fileUploadMessage = builder.fileUploadMessage;
		this.fileName = builder.fileName;
	}

	public static class Builder {

		private final String fileUploadTime;
		private final String fileUploadMessage;
		private String fileName;

		public Builder setFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public Builder(String fileUploadTime, String fileUploadMessage) {
			super();
			this.fileUploadTime = fileUploadTime;
			this.fileUploadMessage = fileUploadMessage;
		}

		public FilehandlerUploadFileInfo build() {
			return new FilehandlerUploadFileInfo(this);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileUploadTime() {
		return fileUploadTime;
	}

	public String getFileUploadMessage() {
		return fileUploadMessage;
	}

}
