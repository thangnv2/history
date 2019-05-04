package rabitmq.draw.model;

/**
 * Thể hiện các thông tin cơ bản của vật thể 
 * @author anhnn2
 *
 */
public class Item {
	
	private int mId;
	private int mStatus;
	private String mName;
	private String mNote;
	
	public Item(int id, String name, String note) {
		this.mId = id;
		this.mName = name;
		this.mNote = note;
	}
	
	public void setStatus(int status) {
		mStatus = status;
	}
	
	public int getStatus() {
		return mStatus;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getNote() {
		return mNote;
	}

	public void setNote(String note) {
		this.mNote = note;
	}

	public int getId() {
		return mId;
	}
}
