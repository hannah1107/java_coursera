import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * Hello World!
 * 
 * This is the basic stub to start creating interactive maps.
 */
public class HelloUCSDWorld extends PApplet {

	UnfoldingMap map;
	UnfoldingMap map2;

	public void setup() {
		size(800, 600, OPENGL);

		map = new UnfoldingMap(this, new Google.GoogleTerrainProvider());
		map.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD
        
		map2 =  new UnfoldingMap(this, new Google.GoogleTerrainProvider());
		map2.zoomAndPanTo(14,new Location(41.838, -71.408320215));// Home
		
		MapUtils.createDefaultEventDispatcher(this, map);
		MapUtils.createDefaultEventDispatcher(this, map2);
	}

	public void draw() {
		background(0);
		map.draw();
		//map2.draw();
	}

}
