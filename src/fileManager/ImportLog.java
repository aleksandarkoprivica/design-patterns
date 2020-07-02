package fileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ImportLog implements Import {

	@Override
	public FileReader importData(String path) {
		try {
			FileReader fileReader = new FileReader(path);
			
			return fileReader;
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
