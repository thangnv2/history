package Test;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ListIterator;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapContent;
import org.geotools.map.MapContext;
import org.geotools.swing.JMapPane;

public class Transform extends JMapPane{  
    private AffineTransform tr;  
    /** 
     * Initializes the route painter 
     * 
     * @param route the route to draw 
     */  
    public Point transform(DirectPosition2D route) {  
        tr = getWorldToScreenTransform();
        Point previous = new Point();
        tr.transform(route, previous);
        return previous;

    }

	public Transform(MapContent mapContent) {
		 super(mapContent);  
		
	}

} 
