package fileManager;

public class ExportManager implements Export {
	
	private Export export;
	
	public ExportManager(Export export) {
		this.export = export;
	}

	@Override
	public void exportData(Object objectsToExport, String path) {
		// TODO Auto-generated method stub
		export.exportData(objectsToExport, path);
	}
	

}
