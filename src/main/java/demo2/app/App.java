package demo2.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.locationtech.jts.geom.Coordinate;

import Test.Transform;
import demo2.History;
import demo2.Line;
import demo2.plane.Plane;
import demo2.uity.Common;
import rabitmq.draw.model.Item;;

public class App extends JMapFrame implements Runnable, ContractApp.View {

	private static final long serialVersionUID = 1L;

	private double mSpeedZoom;
	private PresenterApp mPresenterApp;
	private LinkedList<Plane> listPlane;
	MapContent map;
	Plane plane;
	Line line;
	int toolbarHeight = 0;
	int loopHistory = 1;
	Transform transform;
	List<History> listHistotys;
	AffineTransform tr;

	public App() throws IOException, TimeoutException {
		loadMap();
		line = new Line(0, 0, 50, 50, "h.png");
		add(line);
		mPresenterApp = new PresenterApp();
		listPlane = new LinkedList<>();
		setSize(Common.width_main, Common.hightMain);
		enableStatusBar(true);
		enableToolBar(true);
		JButton btnHistory = new JButton("History");
		btnHistory.setSize(300, 300);
		JToolBar toolBar = getToolBar();
		toolBar.add(btnHistory);
		btnHistory.addMouseListener(mouseListener());
		// end
		mSpeedZoom = mPresenterApp.setSpeedZoom(getMapContent().getMaxBounds().getMaxX(),
				getMapContent().getMaxBounds().getMinX());
		// addPlaneFromJson();
		MapViewport vp = getMapContent().getViewport();
		vp.setMatchingAspectRatio(true);
		getMapContent().setViewport(vp);
		setVisible(true);
		while (true) {
			tr = this.map.getViewport().getWorldToScreen();
			if(tr != null)
				break;
			
		}
		//[VN] lấy ra độ cao của toolbar trên map để mapping màn tỉ lệ khung hình map với screen
		
		toolbarHeight = toolBar.getHeight();
		dataMockRealTime();

	}

	@Override
	public void run() {
		while (true) {
			mSpeedZoom = mPresenterApp.setSpeedZoom(
					getMapContent().getViewport().getBounds().getLowerCorner().getCoordinate()[0],
					getMapContent().getViewport().getBounds().getUpperCorner().getCoordinate()[0]);
			mapWorldToScreen(listPlane);
			update();
			repaint();
			
			try {
				Thread.sleep(170);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void loadMap() {
		// display a data store file chooser dialog for shapefiles
		FileDataStore store;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("ne_50m_admin_1_states_provinces_scale_rank.shp").getFile());
			store = FileDataStoreFinder.getDataStore(file);
			SimpleFeatureSource featureSource = store.getFeatureSource();
			// Create a map content and add our shapefile to it
			map = new MapContent();
			// thangnv2: create new transform map to screen

			// end
			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			Layer layer = new FeatureLayer(featureSource, style);
			map.addLayer(layer);

			// Now display the map
			setMapContent(map);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getAllItem() {
		String json = "{\"action\": \"get_all_item\"}";
		try {
			return mPresenterApp.call(json);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void addPlaneFromJson() {
		String json = getAllItem();
		LinkedList<Item> listItem = mPresenterApp.getItemFromJson(json);
		for (Item item : listItem) {
			Plane plane = null;
			initPlane(plane, item);

		}
	}

	@Override
	public void initPlane(Plane plane, Item item) {
		try {
			plane = new Plane(35, 100, 105, 22, "res/h.png");
			plane.setZoom(mSpeedZoom);
			plane.setmId(item.getId());
			plane.setmName(item.getName());
			plane.setmNote(item.getNote());
			add(plane);
			listPlane.add(plane);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mapWorldToScreen(LinkedList<Plane> listItem) {
		for (Plane plane : listItem) {
			if (plane != null && getMapPane() != null && getMapContent() != null) {
				plane.setMapWorldScreen(getMapPane(), getMapContent());
				plane.setZoom(mSpeedZoom);
				plane.setWidthAndHight();
			}
		}
	}

	@Override
	public void update() {
		int i = 0;
		Plane plane = null;
		for (i = 0; i < listPlane.size(); i++) {
			plane = listPlane.get(i);
			if (plane != null) {
				plane.drawPlane(plane.getmId());
			}
		}
	}

	/*
	 * BEGIN
	 * --------------------thangnv2-------------------------------------------------
	 * -------
	 */
	private MouseListener mouseListener() {
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				try {
					map.addLayer(Common.createLine(mockDataHistory()));
					setZoomHistory();
				} catch (SchemaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};
		return mouseListener;
	}

	
	private List<Coordinate> mockDataHistory() {
		DirectPosition2D directPosition2D = new DirectPosition2D(-86.9949, 101.4884);
		List<Coordinate> coordinates = new ArrayList<>();
		// Coordinate coordinate = new Coordinate(-86.9949,101.4884);
		coordinates.add(new Coordinate(-86.9949, 101.4884));
		coordinates.add(new Coordinate(-84.0842, 74.8077));
		coordinates.add(new Coordinate(-46.7312, 68.9864));
		coordinates.add(new Coordinate(-38.4844, 35.0291));
		coordinates.add(new Coordinate(-3.5569, 31.1483));
		coordinates.add(new Coordinate(4.6899, -1.3537));
		coordinates.add(new Coordinate(30.4004, -4.7494));
		return coordinates;
	}

	/**
	 * draw history
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawHistory(g);
	}

	public void addHistory(History history) {
		if (listHistotys == null)
			listHistotys = new ArrayList<>();
		listHistotys.add(history);
	}

	// draw history
	public void drawHistory(Graphics g) {
		g.setColor(Color.blue);
		if (listHistotys == null)
			listHistotys = new ArrayList<>();
		// draw line
		for (History history : listHistotys) {
			//add(history.getLine());
		}
		if (listHistotys.size() < 2) {
			//listHistotys.get(0).getLine().setLocation(listHistotys.get(0).getX(), listHistotys.get(0).getY());
		}
		for (int i = 0; i < listHistotys.size() - 1; i++) {
			//listHistotys.get(i+1).getLine().setLocation(listHistotys.get(i+1).getX(), listHistotys.get(i+1).getY());
			
			g.drawLine(listHistotys.get(i).getX(), listHistotys.get(i).getY(), listHistotys.get(i + 1).getX(),
					listHistotys.get(i + 1).getY());
			
		}
		line.setLocation(listHistotys.get(listHistotys.size() - 1).getX(), listHistotys.get(listHistotys.size() - 1).getY());
	}
	
	/**
	 * convert coordinate from server to coordinate screen 
	 */
	public Point mapToScreen(double lon, double lat) {
		Point point = new Point();
		if(tr == null)
			return null;
		tr.transform(new DirectPosition2D(lon, lat), point);
		return point;
		
	}
	
	public void dataMockRealTime() {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				
				List<Point> coordinateScreens = new ArrayList<>();
				coordinateScreens.add(mapToScreen(-86.9949, 101.4884));
				coordinateScreens.add(mapToScreen(-84.0842, 74.8077));
				coordinateScreens.add(mapToScreen(-46.7312, 68.9864));
				coordinateScreens.add(mapToScreen(-38.4844, 35.0291));
				coordinateScreens.add(mapToScreen(-3.5569, 31.1483));
				coordinateScreens.add(mapToScreen(4.6899, -1.3537));
				coordinateScreens.add(mapToScreen(30.4004, -4.7494));
				for (Point point : coordinateScreens) {
					History history = new History(new Line(0, 0, 50, 50, "h.png"), point.x , point.y+toolbarHeight+33);
					history.setPoint(point);
					addHistory(history);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
		
		
	}
	
	public void setZoomHistory() {
		for (History history : listHistotys) {
			//TODO
			//history.setX(mapToScreen(history.getPoint().getX(),history.getPoint().getY()).x);
			//
			//history.setY(mapToScreen(history.getPoint().getX(),history.getPoint().getY()).y);
		}
		
	}
}
