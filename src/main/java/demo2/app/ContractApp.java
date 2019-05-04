package demo2.app;

import java.util.LinkedList;

import demo2.plane.Plane;
import rabitmq.draw.model.Item;

public interface ContractApp {
	
	interface View{
		void loadMap();
		void initPlane(Plane plane, Item item);
		String getAllItem();
		void addPlaneFromJson();
		void mapWorldToScreen(LinkedList<Plane> listItem);
		void update();
	}
	
	interface Prisenter{
		double setSpeedZoom(double latMax, double latMin);
		LinkedList<Item> getItemFromJson(String json);
	}

}
