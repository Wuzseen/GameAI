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
		ret._x = other._x - this._x;
		ret._y = other._y - this._y;
		return ret;
	}
	
	public double magnitude() {
		return Math.sqrt(this._x * this._x + this._y * this._y);
	}
	
	public Vector2 normal() {
		double mag = this.magnitude();
		return new Vector2(this._x / mag, this._y / mag);
	}
	
	public Vector2() {
		this._x = 0;
		this._y = 0;
	}
	
	public Vector2(double x, double y) {
		this._x = x;
		this._y = y;
	}
}
