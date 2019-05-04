package demo2.uity;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.geotools.data.DataUtilities;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class Common {
	
	public static final int LAT_MAX = 180;
	public static final int LAT_MIN = -180;
	
	public static int hightMain = 750;
	public static int width_main = 900;
	public static int yMap = 0;
	public static int hightMap = 0;
	
	public static final int WIDTH_MENU = 100;
	
	
	public static Image loadImage(String urlImage) {
		Image image = null;
		try {
			image = ImageIO.read(new File(urlImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * [VN] vẽ nối các đường thẳng theo list tọa độ điểm
	 * @param coordinates
	 * @return
	 * @throws SchemaException
	 */
	public static Layer createLine(List<Coordinate> coordinates) throws SchemaException {
		GeometryFactory geometryFactory = new GeometryFactory();
        LineString lineString = geometryFactory.createLineString((Coordinate[]) coordinates.toArray(new Coordinate[] {}));
	      Layer layer = getLayerLineByCoord(lineString.getCoordinates());
	    return layer;
	  }
	
	/**
	 * [VN] vẽ nối 2 tọa độ điểm
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 * @throws SchemaException
	 */
	static Layer createLine(double x1, double y1, double x2, double y2) throws SchemaException {
		GeometryFactory geometryFactory = new GeometryFactory();
		List<Coordinate> coordinates = new ArrayList<>();
		coordinates.add(new Coordinate(x1, y1));
		coordinates.add(new Coordinate(x2, y2));
        LineString lineString = geometryFactory.createLineString((Coordinate[]) coordinates.toArray(new Coordinate[] {}));
	      Layer layer = getLayerLineByCoord(lineString.getCoordinates());
	    return layer;
	  }
	

	
	/**
	 * [VN] tạo điểm point 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Layer drawMyPoint(double x, double y) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("MyFeatureType");
        builder.setCRS( DefaultGeographicCRS.WGS84 ); // set crs        
        builder.add("location", Point.class); // add geometry
        // build the type
        SimpleFeatureType TYPE = builder.buildFeatureType();
        // create features using the type defined
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        org.locationtech.jts.geom.Point point = geometryFactory.createPoint(new Coordinate(x, y));
        featureBuilder.add(point);
        SimpleFeature feature = featureBuilder.buildFeature("FeaturePoint");
        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection("internal", TYPE);
        featureCollection.add(feature); // Add feature 1, 2, 3, etc
        Style style = SLD.createPointStyle("Star", Color.BLUE, Color.BLUE, 0.3f, 15);
        Layer layer = new FeatureLayer(featureCollection, style);
        layer.setTitle("NewPointLayer");
        return layer;
    }

	
	  public static Layer getLayerLineByCoord(Coordinate[] coords) throws SchemaException {
	        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	        LineString line = geometryFactory.createLineString(coords);
	        SimpleFeatureType TYPE = DataUtilities.createType("test", "line", "the_geom:LineString");
	        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder((SimpleFeatureType) TYPE);
	        featureBuilder.add(line);
	        SimpleFeature feature = featureBuilder.buildFeature("LineString_Sample");

	        DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
	        lineCollection.add(feature);

	        Style style = SLD.createLineStyle(Color.BLUE, 1);
	        return new FeatureLayer(lineCollection, style);
	    }
}
