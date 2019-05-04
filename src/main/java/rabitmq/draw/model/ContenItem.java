package rabitmq.draw.model;

/**
 * thể hiện liên kết giữa item, type và RuntiemItem
 * @author anhnn2
 *
 */
public class ContenItem {
	
	private int mId;
	private int mIdItem;
	private int mIdType;
	
	public ContenItem(int mId, int mIdItem, int mIdType) {
		this.mId = mId;
		this.mIdItem = mIdItem;
		this.mIdType = mIdType;
	}

	public int getmId() {
		return mId;
	}

	public int getmIdItem() {
		return mIdItem;
	}

	public int getmIdType() {
		return mIdType;
	}

}
