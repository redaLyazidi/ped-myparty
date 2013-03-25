package net.ped.shared;

import java.io.File;
import java.io.IOException;

public interface FileManagerItf {
	public File get(int id) throws IOException;
}
