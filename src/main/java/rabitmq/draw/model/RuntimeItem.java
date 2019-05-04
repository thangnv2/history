package rabitmq.draw.model;

/**
 * thể hiện dữ liệu theo thời gian thực của vật thể.
 * @author anhnn2
 *
 */
public class RuntimeItem {
	private int mId;
	
	private int mIdItem;
	/**
	 * Tốc độ của vật thế.
	 */
	private float mSpeed;
	/**
	 * kinh độ 
	 */
	private double mLongitude;
	
	/**
	 * vĩ độ 
	 */
	private double mLatitude;

	public RuntimeItem(int id, int idItem, float speed, double longitude, double latitude) {
		this.mId = id;
		this.mIdItem = idItem;
		this.mSpeed = speed;
		this.mLongitude = longitude;
		this.mLatitude = latitude;
	}

	public float getmSpeed() {
		return mSpeed;
	}

	public void setSpeed(float speed) {
		this.mSpeed = speed;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public int getId() {
		return mId;
	}

	public int getmIdItem() {
		return mIdItem;
	}

	public void setmIdItem(int mIdItem) {
		this.mIdItem = mIdItem;
	}
	
}
