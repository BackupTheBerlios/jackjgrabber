package model;

import java.io.*;
import java.net.*;

public class BOFileWrapper {
	private File file;

	public BOFileWrapper(File file) {
		this.file = file;
	}

	public boolean canRead() {
		return file.canRead();
	}
	public boolean canWrite() {
		return file.canWrite();
	}
	public int compareTo(File pathname) {
		return file.compareTo(pathname);
	}
	public int compareTo(Object arg0) {
		return file.compareTo(arg0);
	}
	public boolean delete() {
		return file.delete();
	}
	public boolean exists() {
		return file.exists();
	}
	public File getAbsoluteFile() {
		return file.getAbsoluteFile();
	}
	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}
	public String getName() {
		return file.getName();
	}
	public String getParent() {
		return file.getParent();
	}
	public File getParentFile() {
		return file.getParentFile();
	}
	public String getPath() {
		return file.getPath();
	}
	public boolean isAbsolute() {
		return file.isAbsolute();
	}
	public boolean isDirectory() {
		return file.isDirectory();
	}
	public boolean isFile() {
		return file.isFile();
	}
	public boolean isHidden() {
		return file.isHidden();
	}
	public long lastModified() {
		return file.lastModified();
	}
	public long length() {
		return file.length();
	}
	public String[] list() {
		return file.list();
	}
	public String[] list(FilenameFilter filter) {
		return file.list(filter);
	}
	public File[] listFiles() {
		return file.listFiles();
	}
	public File[] listFiles(FileFilter filter) {
		return file.listFiles(filter);
	}
	public File[] listFiles(FilenameFilter filter) {
		return file.listFiles(filter);
	}
	public boolean mkdir() {
		return file.mkdir();
	}
	public boolean mkdirs() {
		return file.mkdirs();
	}
	public boolean renameTo(File dest) {
		return file.renameTo(dest);
	}
	public boolean setLastModified(long time) {
		return file.setLastModified(time);
	}
	public boolean setReadOnly() {
		return file.setReadOnly();
	}
	public String toString() {
		return file.getName();
	}
	public URI toURI() {
		return file.toURI();
	}
	public URL toURL() throws MalformedURLException {
		return file.toURL();
	}
}