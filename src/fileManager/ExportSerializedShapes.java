package fileManager;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ExportSerializedShapes implements Export {

	@Override
	public void exportData(Object objectsToExport, String path) {
		// TODO Auto-generated method stub
		if (path == null)
			return;
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			
			oos.writeObject(objectsToExport);
		}
		catch (Exception ex) {
		
		ex.printStackTrace();
		
	}
	}

}
