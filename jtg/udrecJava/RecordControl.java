package udrecJava;


/**
 * @author Geist Alexander
 *
 */
public class RecordControl extends Thread
{
	Record record;

	public RecordControl(Record record) {
		this.record = record;
	}

	public boolean startRecord() {
		if (!record.start()) return false;
		return true;
	}
	
	public void run() {
		if (startRecord()) {
		    PESSplitWriterList writerList = new PESSplitWriterList();
		    PESWriter pesWriter = new PESWriter(record, writerList);
		}
	}
}
