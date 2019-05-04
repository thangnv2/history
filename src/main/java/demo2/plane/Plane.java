package demo2.plane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;

import demo2.menu.MenuData;
import rabitmq.draw.model.Item;

public class Plane extends JMapPane implements ContractPlane.view {

	private static final long serialVersionUID = 1L;
	
	private int mId;
	private int mStatus;
	private float mSleep;
	private double mLon = 0d;
	private double mLat = 0d;
	private String mName;
	private String mNote;
	private Image mImage;
	private MenuData mSreenData;
	
	private PresenterPlane mPresenterPlane;;
	
	/**
	 * 
	 * @param x: tọa độ x.
	 * @param y: tọa độ y.
	 * @param urlImage: đường dới tới ảnh lưu trên máy.
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public Plane(int x, int y, double lon, double lat, String urlImage) throws IOException, TimeoutException {
		setBounds(x, y, 1, 1);
		mPresenterPlane = new PresenterPlane(this);
		mLat = lat;
		mLon = lon;
		loadImage(urlImage);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				finDataItem();
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		if (mImage != null) {
			g.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	@Override
	public void loadImage(String urlImage) {
		mImage = demo2.uity.Common.loadImage(urlImage);
	}

	@Override
	public void setLon(double lon) {
		mLon = lon;
	}

	@Override
	public void setLat(double lat) {
		mLat = lat;
	}

	@Override
	public double getLat() {
		return mLat;
	}

	@Override
	public double getLon() {
		return mLon;
	}

	@Override
	public void setZoom(double speedZoom) {
		mPresenterPlane.setSpeedZoom(speedZoom);
	}

	@Override
	public void setMapWorldScreen(JMapPane mapPane, MapContent mapContent) {
		mPresenterPlane.mapWorldAndScreen(mapPane, mapContent);
	}

	@Override
	public void setWidthAndHight() {
		mPresenterPlane.setWidthAndHight();		
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getmStatus() {
		return mStatus;
	}

	public void setmStatus(int mStatus) {
		this.mStatus = mStatus;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmNote() {
		return mNote;
	}

	public void setmNote(String mNote) {
		this.mNote = mNote;
	}

	@Override
	public void drawPlane(int idPlane) {
		mPresenterPlane.getDataFromServer(idPlane);
	}

	public float getmSleep() {
		return mSleep;
	}

	public void setmSleep(float mSleep) {
		this.mSleep = mSleep;
	}

	@Override
	public void finDataItem() {
		Item item = mPresenterPlane.findItem(getmId());
		mSreenData = new MenuData(item);
	}
	

}
