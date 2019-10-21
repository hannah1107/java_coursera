package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private Marker lastClicked;
	private Marker lastSelected;
	
	private HashMap<Integer, AirportMarker> airportRoutes;
	public void setup() {
		// setting up PAppler
		size(1200,1000, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 1150, 850);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		airportRoutes = new HashMap <Integer, AirportMarker>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	        
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		    airportRoutes.put(Integer.parseInt(feature.getId()),m);
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		
		
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			sl.setHidden(true);
			
			// add route to airportMarker
			if (airportRoutes.containsKey(source))
			{
				airportRoutes.get(source).addRoute(sl);
			}
			
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
			
		}
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
		//loop();
	}
	
	public void selectMarkerIfHover(List<Marker> airports)
	{
		for (Marker m: airports) {
			
			CommonMarker airMarker = (CommonMarker) m;
			if (airMarker.isInside(map, mouseX, mouseY)) {
				airMarker.setSelected(true);
				lastSelected = airMarker;
				return;
			}
		}
	}
	
	public void mouseClicked()
	{
		if (lastClicked != null) {
			lastClicked = null;
			showAll();
		}
		else {
			checkRoutesforClicked();
		}
		
	}
	
	public void checkRoutesforClicked()
	
	{
		
		
		if (lastClicked !=null) return;
		
		for (Marker a: airportList)
		{
			if (!a.isHidden() && a.isInside(map, mouseX, mouseY))
			{
				
				lastClicked = a;
				
				
				for (Marker m: airportList) {
					if (m!= lastClicked) {
						m.setHidden(true);
					}						
				}
				for (SimpleLinesMarker sl: ((AirportMarker)a).getRoutes()) {
				
					sl.setHidden(false);
					Integer dest = Integer.parseInt((String)sl.getProperty("destination"));
					
					airportRoutes.get(dest).setHidden(false);
					
				}
				
				
				return;
			}
		}
		
	}
	
    public void showAll() {
    	for (Marker m: airportList) {
    		m.setHidden(false);
    	}
    	for (Marker r: routeList) {
    		r.setHidden(true);
    	}
    }
    
}
