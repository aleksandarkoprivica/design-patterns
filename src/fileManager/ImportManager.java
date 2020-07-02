package fileManager;

public class ImportManager implements Import {

	private Import importer;
	
	public ImportManager(Import importer) {
		this.importer = importer;
	}
	
	@Override
	public Object importData(String path) {
		// TODO Auto-generated method stub
		return importer.importData(path);
	}

	
}
