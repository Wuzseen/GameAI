package utility;

public class MathUtil {
	public static double InverseLerp(double value, double min, double max) {
		double ret = (value - min) / (max - min);
		return MathUtil.clamp01(ret);
	}
	
	public static double clamp01(double val) {
		return MathUtil.clamp(val, 0, 1);
	}
	
	public static double clamp(double val, double min, double max) {
		if(val > max) {
			return max;
		}
		if(val < min) {
			return min;
		}
		return val;
	}
	
	public static double deg2Rad(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	public static double rad2Deg(double radians) {
		return 180 * radians / Math.PI;
	}
}
