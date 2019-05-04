package demo2.plane;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;
import org.json.JSONObject;

import demo2.Send;
import rabitmq.draw.model.Item;
import rabitmq.draw.model.RuntimeItem;

public class PresenterPlane extends Send implements ContractPlane.Presenter{

	private final int SIZE_MAX = 30;
	private final int SIZE_MIN = 1;
	private int index = 0;
	private double mSpeedZoom;
	private Plane mPlane;
	
	
	public PresenterPlane(Plane plane) throws IOException, TimeoutException {
		super();
		this.mPlane = plane;
	}

	@Override
	public void setWidthAndHight() {
		if (mPlane != null) {
			float tempW = mPlane.getWidth();
			tempW = (int) (tempW / mSpeedZoom);
			if (tempW > SIZE_MAX) {
				tempW = SIZE_MAX;
			} else if (tempW < SIZE_MIN) {
				tempW = SIZE_MIN;
			}

			float tempH = mPlane.getHeight();
			tempH = (int) (tempH / mSpeedZoom);
			if (tempH > SIZE_MAX) {
				tempH = SIZE_MAX;
			} else if (tempH < SIZE_MIN) {
				tempH = SIZE_MIN;
			}
			mPlane.setSize((int) tempW, (int) tempH);
		}
		
	}

	@Override
	public void mapWorldAndScreen(JMapPane mapPane, MapContent mapContent) {
		if (mPlane != null) {
			double temp1 = mPlane.getLon() - mapContent.getViewport().getBounds().getLowerCorner().getCoordinate()[0];
			double temp2 = mapContent.getViewport().getBounds().getUpperCorner().getCoordinate()[0] - mPlane.getLon();
			int x = (int) ((temp1 * mapPane.getWidth()) / (temp1 + temp2));
			temp1 = mapContent.getViewport().getBounds().getUpperCorner().getCoordinate()[1] - mPlane.getLat();
			temp2 = mPlane.getLat() - mapContent.getViewport().getBounds().getLowerCorner().getCoordinate()[1];
			int y = (int) ((temp1 * mapPane.getHeight()) / (temp1 + temp2)) + mapPane.getY();
			y *= 1.004;
			mPlane.setLocation(x, y);
		}
	}

	@Override
	public void setSpeedZoom(double speedZoom) {
		mSpeedZoom = speedZoom;
		setWidthAndHight();
	}

	@Override
	public LinkedList<RuntimeItem> getDataFromServer(int idItem) {
		String jsonSend ="{\"action\": \"get_data_item_by_id\","
							+ "\"id\": "+idItem+","
						 + "\"index\": "+index+"}";
		LinkedList<RuntimeItem> list = new LinkedList<>();
		try {
			jsonSend = call(jsonSend);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject(jsonSend);
		index = jsonObject.getInt("index");
		jsonObject = new JSONObject(jsonObject.getJSONObject("data").toString());
		mPlane.setmSleep(jsonObject.getFloat("mSpeed"));
		mPlane.setLon(jsonObject.getDouble("mLongitude"));
		mPlane.setLat(jsonObject.getDouble("mLatitude"));
		return list;
	}

	@Override
	public Item findItem(int idItem) {
		String json = "{\"action\":\"get_item\","
					+ "\"id\": "+idItem+"}";
		Item item = null;
		try {
			json = call(json);
			item = findItem(json);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private Item findItem(String json) {
		JSONObject jsonObject = new JSONObject(json);
		return new Item(jsonObject.getInt("mId"),
				jsonObject.getString("mName"), 
				jsonObject.getString("mNote"));
	}
	
}
