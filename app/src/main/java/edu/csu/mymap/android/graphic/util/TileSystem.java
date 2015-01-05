package edu.csu.mymap.android.graphic.util;

public class TileSystem {

	private static final String TAG = "TileSystem";
	private static final double EarthRadius = 6378137;
	private static final double MinLatitude = -85.05112878;
	private static final double MaxLatitude = 85.05112878;
	private static final double MinLongitude = -180;
	private static final double MaxLongitude = 180;

	/**
	 * Clip(Clips a number to the specified minimum and maximum values.)
	 * (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param The
	 *            number to clip.
	 * @param Minimum
	 *            allowable value.
	 * @param Maximum
	 *            allowable value.
	 * @return The clipped value.
	 * @exception
	 * @since 1.0.0
	 */
	private static double Clip(double n, double minValue, double maxValue) {
		return Math.min(Math.max(n, minValue), maxValue);
	}

	// / <summary>
	// / Converts a point from latitude/longitude WGS-84 coordinates (in
	// degrees)
	// / into pixel XY coordinates at a specified level of detail.
	// / </summary>
	// / <param name="latitude">Latitude of the point, in degrees.</param>
	// / <param name="longitude">Longitude of the point, in degrees.</param>
	// / <param name="levelOfDetail">Level of detail, from 1 (lowest detail)
	// / to 23 (highest detail).</param>
	// / <param name="pixelX">Output parameter receiving the X coordinate in
	// pixels.</param>
	// / <param name="pixelY">Output parameter receiving the Y coordinate in
	// pixels.</param>
	public static int[] LatLongToPixelXY(double latitude, double longitude,
			int levelOfDetail) {
		int[] pixelXY = new int[2];
		latitude = Clip(latitude, MinLatitude, MaxLatitude);
		longitude = Clip(longitude, MinLongitude, MaxLongitude);

		double x = (longitude + 180) / 360;
		// double sinLatitude = Math.sin(latitude * Math.PI / 180);
		// double y = 0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude))
		// / (4 * Math.PI);
		double y = (90 - latitude) / 180;

		long[] mapSize = MapSize(levelOfDetail);
		pixelXY[0] = (int) Clip(x * mapSize[0] + 0.5, 0, mapSize[0] - 1);
		pixelXY[1] = (int) Clip(y * mapSize[1] + 0.5, 0, mapSize[1] - 1);
		return pixelXY;
	}

	// / <summary>
	// / Determines the map width and height (in pixels) at a specified level
	// / of detail.
	// / </summary>
	// / <param name="levelOfDetail">Level of detail, from 1 (lowest detail)
	// / to 23 (highest detail).</param>
	// / <returns>The map width and height in pixels.</returns>
	public static long[] MapSize(int levelOfDetail) {
		long[] mapSize = new long[2];
		mapSize[0] = (long) 256 << levelOfDetail;
		mapSize[1] = (long) 256 << (levelOfDetail - 1);
		return mapSize;
	}

	// / <summary>
	// / Converts a pixel from pixel XY coordinates at a specified level of
	// detail
	// / into latitude/longitude WGS-84 coordinates (in degrees).
	// / </summary>
	// / <param name="pixelX">X coordinate of the point, in pixels.</param>
	// / <param name="pixelY">Y coordinates of the point, in pixels.</param>
	// / <param name="levelOfDetail">Level of detail, from 1 (lowest detail)
	// / to 23 (highest detail).</param>
	// / <param name="latitude">Output parameter receiving the latitude in
	// degrees.</param>
	// / <param name="longitude">Output parameter receiving the longitude in
	// degrees.</param>
	public static double[] PixelXYToLatLong(int pixelX, int pixelY,
			int levelOfDetail) {
		double[] latLon = new double[2];
		long[] mapSize = MapSize(levelOfDetail);
		double x = (Clip(pixelX, 0, mapSize[0] - 1) / mapSize[0]) - 0.5;
		double y = 0.5 - (Clip(pixelY, 0, mapSize[1] - 1) / mapSize[1]);

		// latLon[0] = 90 - 360 * Math.atan(Math.exp(-y * 2 * Math.PI)) /
		// Math.PI;
		latLon[0] = 180 * y;
		latLon[1] = 360 * x;
		return latLon;
	}

	// / <summary>
	// / Converts pixel XY coordinates into tile XY coordinates of the tile
	// containing
	// / the specified pixel.
	// / </summary>
	// / <param name="pixelX">Pixel X coordinate.</param>
	// / <param name="pixelY">Pixel Y coordinate.</param>
	// / <param name="tileX">Output parameter receiving the tile X
	// coordinate.</param>
	// / <param name="tileY">Output parameter receiving the tile Y
	// coordinate.</param>
	public static int[] PixelXYToTileXY(int pixelX, int pixelY) {
		int[] tile = new int[2];
		tile[0] = pixelX / 256;
		tile[1] = pixelY / 256;
		return tile;
	}

	// / <summary>
	// / Converts tile XY coordinates into pixel XY coordinates of the
	// upper-left pixel
	// / of the specified tile.
	// / </summary>
	// / <param name="tileX">Tile X coordinate.</param>
	// / <param name="tileY">Tile Y coordinate.</param>
	// / <param name="pixelX">Output parameter receiving the pixel X
	// coordinate.</param>
	// / <param name="pixelY">Output parameter receiving the pixel Y
	// coordinate.</param>
	public static int[] TileXYToPixelXY(int tileX, int tileY) {
		int[] pixel = new int[2];
		pixel[0] = tileX * 256;
		pixel[1] = tileY * 256;
		return pixel;
	}

	// / <summary>
	// / Converts tile XY coordinates into a QuadKey at a specified level of
	// detail.
	// / </summary>
	// / <param name="tileX">Tile X coordinate.</param>
	// / <param name="tileY">Tile Y coordinate.</param>
	// / <param name="levelOfDetail">Level of detail, from 1 (lowest detail)
	// / to 23 (highest detail).</param>
	// / <returns>A string containing the QuadKey.</returns>
	public static String TileXYToQuadKey(int tileX, int tileY, int levelOfDetail) {
		// StringBuilder quadKey = new StringBuilder();
		// for (int i = levelOfDetail; i > 0; i--) {
		// char digit = '0';
		// int mask = 1 << (i - 1);
		// if ((tileX & mask) != 0) {
		// digit++;
		// }
		// if ((tileY & mask) != 0) {
		// digit++;
		// digit++;
		// }
		// quadKey.append(digit);
		// }
		// return quadKey.toString();
		return levelOfDetail + "_" + tileX + "_" + tileY;
	}
}
