package hr.fer.oprpp1.hw08.jnotepadpp;

public interface MultipleDocumentListener {
	void currentDocumentChanged(  SingleDocumentModel currentModel);

	void documentAdded(SingleDocumentModel model);

	void documentRemoved(SingleDocumentModel model);
	
	void updateStatus(int len,int ln, int col,int sel);
	
	void currentStatusChanged( SingleDocumentModel prev, SingleDocumentModel current);

}
