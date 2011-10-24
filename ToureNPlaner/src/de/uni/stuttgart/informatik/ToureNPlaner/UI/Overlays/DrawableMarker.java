package de.uni.stuttgart.informatik.ToureNPlaner.UI.Overlays;

import org.mapsforge.android.maps.GeoPoint;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class DrawableMarker extends Drawable{
private MapView mapView;
private GeoPoint gp;
private int color = Color.BLACK;
private int index = 0;

	public DrawableMarker(MapView mapview, GeoPoint gp){
		this.mapView = mapview;
		this.gp = gp;
	}
	@Override
	public void draw(Canvas canvas) {
		  // Transfrom geoposition to Point on canvas
        Projection projection = mapView.getProjection();
	    Point point = new Point();
	    projection.toPixels(gp, point);
	     // the circle to mark the spot
	    Paint circle = new Paint();
	    Paint TextPaint = new Paint();
	    TextPaint.setColor(Color.WHITE);
	    TextPaint.setTextSize(16);
	    circle.setColor(color);
	    // draw line with antialiasing
        DrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
        canvas.setDrawFilter(drawFilter);
	   
        // add a factor to customize the standard radius
	    canvas.drawCircle(point.x, point.y, (float) ( mapView.getZoomLevel()), circle);
	    // draw Text on the circle
	    canvas.drawText(String.valueOf(index), point.x - 3, point.y + 6, TextPaint);
	}

	public void SetIndex(Integer index){
	this.index = index;
	}
	public void setColor(int color){
		this.color = color;
		
	}
	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}
		
	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
	}
