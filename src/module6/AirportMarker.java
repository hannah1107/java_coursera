package module6;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;

import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public List<SimpleLinesMarker> routes ;
	
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		
	    routes = new ArrayList <SimpleLinesMarker>();
	    
	}
	
	public void addRoute(SimpleLinesMarker s) 
	{
		routes.add(s);
	}
	
	public List<SimpleLinesMarker> getRoutes() {
		return routes;
	}
	
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(255,255,0);
		pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		pg.fill(11);
		pg.text(getName()+ " ",x,y);
		
		// show routes
		
	 
	}
    
    
	public String getName()
	{
		return (String) getProperty("name");
	}
	
	
}
