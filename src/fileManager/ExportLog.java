package fileManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ExportLog implements Export {

	@Override
	public void exportData(Object objectsToExport, String path) {
		// TODO Auto-generated method stub
		try {
			
			String objects = (String) objectsToExport;
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(objects.getBytes(Charset.forName("UTF-8")));
			fos.close();
			
		}catch (FileNotFoundException fnfE) {
			fnfE.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
