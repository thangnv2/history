package demo2.app;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

import demo2.Send;
import demo2.uity.Common;
import rabitmq.draw.model.Item;

public class PresenterApp extends Send implements ContractApp.Prisenter {
	

	public PresenterApp() throws IOException, TimeoutException {
		super();
	}

	@Override
	public double setSpeedZoom(double latMax, double latMin) {
		double tempNew = Math.abs(latMax - latMin);
		double tempOld = Math.abs(Common.LAT_MAX - Common.LAT_MIN);
		return tempNew / tempOld;
	}

	@Override
	public LinkedList<Item> getItemFromJson(String json) {
		LinkedList<Item> listItem = new LinkedList<>();
		JSONArray jsonArray = new JSONArray(json);
		JSONObject jsonObject;
		int i = 0; 
		for (i = 0; i < jsonArray.length(); i++) {
			jsonObject = new JSONObject(jsonArray.get(i).toString());
			Item item = new Item(
					jsonObject.getInt("mId"), 
					jsonObject.getString("mName"), 
					jsonObject.getString("mNote"));
			listItem.add(item);
		}
		return listItem;
	}


}
