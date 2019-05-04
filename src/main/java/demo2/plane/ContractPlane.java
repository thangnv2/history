package demo2.plane;

import java.util.LinkedList;

import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;

import rabitmq.draw.model.Item;
import rabitmq.draw.model.RuntimeItem;

public interface ContractPlane {
	
	interface view{
		void loadImage(String urlImage);
		void setLon(double lon);
		void setLat(double lat);
		void setZoom(double speedZoom);
		void setMapWorldScreen(JMapPane mapPane, MapContent mapContent);
		void setWidthAndHight();
		void drawPlane(int idPlane);
		double getLat();
		double getLon();
		void finDataItem();
	}
	
	interface Presenter {
		void setWidthAndHight();
		void mapWorldAndScreen(JMapPane mapPane, MapContent mapContent);
		void setSpeedZoom(double speedZoom);
		LinkedList<RuntimeItem> getDataFromServer(int idItem);
		Item findItem(int idItem);
	}

}
