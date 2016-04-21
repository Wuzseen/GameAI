package utility;

public class Vector2 {
	private double _x;
	private double _y;

	public double get_x() {
		return _x;
	}

	public void set_x(double _x) {
		this._x = _x;
	}

	public double get_y() {
		return _y;
	}

	public void set_y(double _y) {
		this._y = _y;
	}
	
	public Vector2 add(Vector2 other) {
		Vector2 ret = new Vector2();
		ret._x = this._x + other._x;
		ret._y = this._y + other._y;
		return ret;
	}
	
	public Vector2 subtract(Vector2 other) {
		Vector2 ret = new Vector2();
		ret._x = this._x - other._x;
		ret._y = this._y - other._y;
		return ret;
	}
	
	public double magnitude() {
		return Math.sqrt(this._x * this._x + this._y * this._y);
	}
	
	public Vector2 normal() {
		double mag = this.magnitude();
		return new Vector2(this._x / mag, this._y / mag);
	}
	
	public Vector2 scalarMultiply(double scale) {
		return new Vector2(this._x * scale, this._y * scale);
	}
	
	public Vector2() {
		this._x = 0;
		this._y = 0;
	}
	
	public Vector2(double x, double y) {
		this._x = x;
		this._y = y;
	}
	
	public String toString() {
		return "(" + this._x + "," + this._y + ")";
	}
	
	public double alpha() {
		Vector2 norm = this.normal();
		return Math.atan2(norm._y, norm._x);
	}
	
	public double dotProduct(Vector2 other) {
		return this._x * other._x + other._y * this._y;
	}
	
	public double distance(Vector2 other) {
		double dx = other._x - this._x;
		double dy = other._y - this._y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	// Grabbed from the other vector2 class in RotatedRectangle.java
	// ang in rads
	public static Vector2 rotateVector(Vector2 source, double ang) {
        double t;
        double cosa = Math.cos(ang);
        double sina = Math.sin(ang);
        Vector2 ret = new Vector2(source._x, source._y);
        t = ret._x; 
        ret._x = t*cosa + ret._y*sina; 
        ret._y = -t*sina + ret._y*cosa;
        return ret;
	}
}
