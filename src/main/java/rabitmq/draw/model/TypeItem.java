package rabitmq.draw.model;

/**
 * class này thể hiện các loại của vật thể
 * 
 * @author anhnn2
 *
 */
public class TypeItem {

	private int mId;
	private String mName;
	private String mNote;

	public TypeItem(int id, String name, String note) {
		super();
		this.mId = id;
		this.mName = name;
		this.mNote = note;
	}

	public int getmId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
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
}
