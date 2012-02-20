package de.uni.stuttgart.informatik.ToureNPlaner.Data;

import de.uni.stuttgart.informatik.ToureNPlaner.Net.JacksonManager;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.mapsforge.core.GeoPoint;

import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable {
	private GeoPoint[][] way;
	private ArrayList<ResultNode> points;
	private Integer distance;
	private Double apx;

	private int version = 0;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public GeoPoint[][] getWay() {
		return way;
	}

	public ArrayList<ResultNode> getPoints() {
		return points;
	}
	public Integer getDistance(){
		return distance;
	}
	public Double getApx(){
		return apx;
	}

	static void jacksonParse(JsonParser jp, ArrayList<ArrayList<GeoPoint>> ways, ArrayList<ResultNode> points, Misc misc) throws IOException {
		int lt = 0, ln = 0;
		int id = 0;
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			if ("constraints".equals(jp.getCurrentName())) {
				// consume
				while (jp.nextToken() != JsonToken.END_OBJECT && jp.getCurrentToken() != JsonToken.VALUE_NULL) {
					
				}
			}
			if ("misc".equals(jp.getCurrentName())) {
				// consume
				while (jp.nextToken() != JsonToken.END_OBJECT && jp.getCurrentToken() != JsonToken.VALUE_NULL) {
					if(jp.getCurrentName().equals("distance")){
						jp.nextToken();
						misc.distance = jp.getIntValue();
						
					}
					if(jp.getCurrentName().equals("apx")){
						jp.nextToken();
						misc.apx = jp.getDoubleValue();
						
					}
				}
			}
			if ("points".equals(jp.getCurrentName())) {
				if (jp.nextToken() == JsonToken.START_ARRAY) {
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							if (jp.getCurrentName().equals("lt")) {
								jp.nextToken();
								lt = jp.getIntValue();
							} else if (jp.getCurrentName().equals("ln")) {
								jp.nextToken();
								ln = jp.getIntValue();
							} else if (jp.getCurrentName().equals("id")) {
								jp.nextToken();
								id = jp.getIntValue();
							}
						}
						points.add(new ResultNode(id, lt, ln));
					}
				}
			}
			if ("way".equals(jp.getCurrentName())) {
				if (jp.nextToken() == JsonToken.START_ARRAY) {
					JsonToken curr;
					while ((curr = jp.nextToken()) != JsonToken.END_ARRAY) {
						if (curr == JsonToken.START_ARRAY) {
							ArrayList<GeoPoint> currentWay = new ArrayList<GeoPoint>();
							while (jp.nextToken() != JsonToken.END_ARRAY) {
								while (jp.nextToken() != JsonToken.END_OBJECT) {
									if (jp.getCurrentName().equals("lt")) {
										jp.nextToken();
										lt = jp.getIntValue() / 10;
									} else if (jp.getCurrentName().equals("ln")) {
										jp.nextToken();
										ln = jp.getIntValue() / 10;
									}
								}
								currentWay.add(new GeoPoint(lt, ln));
							}
                            if(!currentWay.isEmpty()) {
                                ways.add(currentWay);
                                // duplicate the last one
                                if (ways.size() > 1) {
                                    ArrayList<GeoPoint> second_last = ways.get(ways.size() - 2);
                                    ArrayList<GeoPoint> last = ways.get(ways.size() - 1);
                                    second_last.add(last.get(0));
                                }
                            }
						}
					}
				}
			}
		}
	}

	public static Result parse(JacksonManager.ContentType type, InputStream stream) throws IOException {
		Result result = new Result();
		ArrayList<ArrayList<GeoPoint>> ways = new ArrayList<ArrayList<GeoPoint>>();
		ArrayList<ResultNode> points = new ArrayList<ResultNode>();
		Misc misc = result.new Misc();
		ObjectMapper mapper = JacksonManager.getMapper(type);

		JsonParser jp = mapper.getJsonFactory().createJsonParser(stream);
		try {
			jacksonParse(jp, ways, points,misc);
		} finally {
			jp.close();
		}

		int size = ways.size();
		result.way = new GeoPoint[size][];
		for (int i = 0; i < size; i++) {
			result.way[i] = ways.get(i).toArray(new GeoPoint[ways.get(i).size()]);
		}
		result.points = points;
		result.distance = misc.distance;
		return result;
	}
	public class Misc {
		private Integer distance;
		private Double apx;
		}
}


