package com.ch.math;

/**
 * in Android:
 * 
 * The Vector2f class in Android represents a 2D point in homogeneous coordinates
 * with x and y components. It provides various methods for scaling, absolute value
 * calculation, division, and comparison with other vectors. The class also offers
 * chaining capabilities for modifying its components within the same instance.
 * Additionally, it provides toString() method for generating a string representation
 * of the vector's coordinates.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance between a point and the origin, using the square
	 * root of the sum of the point's x and y coordinates squared.
	 * 
	 * @returns the square root of the sum of the squares of its input coordinates,
	 * expressed as a float value.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of two floating-point arguments and returns it as a
	 * float value.
	 * 
	 * @returns the larger of the two input values, `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a vector `r` and a vector `x` or `y`, returning the
	 * result as a float value.
	 * 
	 * @param r 2D vector to be multiplied with the object's `x` and `y` components,
	 * resulting in the dot product of the two vectors.
	 * 
	 * 	- `r`: A `Vector2f` object that represents a two-dimensional point in the
	 * mathematical plane. It has two attributes: `x` and `y`, which represent the
	 * x-coordinate and y-coordinate of the point, respectively.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and a given vector `r`.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` instance by dividing its components by their magnitude,
	 * resulting in a unitized vector.
	 * 
	 * @returns a normalized vector with a length of 1.
	 * 
	 * The output is a new `Vector2f` object that represents the normalized form of the
	 * original vector.
	 * 
	 * The x-component of the output vector is calculated as the original x-component
	 * divided by the length of the original vector.
	 * 
	 * The y-component of the output vector is calculated as the original y-component
	 * divided by the length of the original vector.
	 * 
	 * The length of the output vector is always equal to 1, regardless of the length of
	 * the original vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * computes the vector product of two vectors, returning a floating-point value
	 * representing the dot product of the two vectors.
	 * 
	 * @param r 2D vector that the function will perform the cross product operation with.
	 * 
	 * 	- `r` is a `Vector2f` object representing a 2D point with x and y coordinates.
	 * 	- `x` and `y` are the x and y coordinates of the point, respectively.
	 * 
	 * @returns a floating-point number representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * takes two `Vector2f` arguments, `dest` and `lerpFactor`, and returns a new `Vector2f`
	 * object with a interpolated value between the two provided values.
	 * 
	 * @param dest 2D position to which the interpolation will be applied.
	 * 
	 * `dest`: A `Vector2f` object that represents the destination point in 2D space.
	 * `lerpFactor`: A floating-point value representing the factor by which the current
	 * position is to be interpolated between the start and end positions.
	 * 
	 * @param lerpFactor 0 to 1 value that determines how much the source vector should
	 * be blended with the destination vector to produce the resulting vector.
	 * 
	 * @returns a new `Vector2f` object representing the interpolated position between
	 * the original vector and the destination vector.
	 * 
	 * 	- The output is a `Vector2f` object that represents the interpolated value between
	 * the original `Vector2f` parameter `this` and the destination `Vector2f` parameter
	 * `dest`.
	 * 	- The interpolation is performed by subtracting the `this` parameter from the
	 * `dest` parameter, multiplying the result by the `lerpFactor`, and then adding the
	 * resulting vector back to the `this` parameter.
	 * 	- The `lerpFactor` parameter represents the ratio of the distance between the
	 * `this` and `dest` parameters to be interpolated. A value of 1.0 results in a
	 * straight line interpolation, while a value less than 1.0 results in a slowing down
	 * of the interpolation, and a value greater than 1.0 speeds it up.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a single argument `angle` representing the angle of rotation in radians and
	 * returns a new `Vector2f` instance with the rotated coordinates.
	 * 
	 * @param angle 2D angle of rotation in radians, which is multiplied by the vector's
	 * x and y components to produce the rotated vector.
	 * 
	 * @returns a new vector with x and y components rotated by the specified angle.
	 * 
	 * 	- The vector is represented as a 2D point in homogeneous coordinates, where x and
	 * y represent the coordinates of the point in the original coordinate system, and w
	 * represents the magnitude or length of the point.
	 * 	- The rotation is performed around the origin, as indicated by the angle parameter
	 * passed to the function.
	 * 	- The rotation matrix is derived from the cosine and sine of the angle, which are
	 * calculated using the `Math.toRadians` method.
	 * 	- The resulting vector is a transformation of the original vector, with its
	 * coordinates rotated by the specified angle.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * adds two `Vector2f` objects together by adding their corresponding x and y coordinates
	 * and returns a new `Vector2f` object with the result.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * 	- `x`: A double value representing the X-coordinate of the input vector.
	 * 	- `y`: A double value representing the Y-coordinate of the input vector.
	 * 
	 * @returns a new `Vector2f` instance representing the sum of the input vectors.
	 * 
	 * The returned output is a new Vector2f object that represents the sum of the input
	 * vectors' x-coordinate and y-coordinate. The x-coordinate is calculated by adding
	 * the input vector's x-coordinate to the current vector's x-coordinate, and similarly
	 * for the y-coordinate.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a floating-point value to a `Vector2f` object, returning a new `Vector2f`
	 * with the sum.
	 * 
	 * @param r 2D vector addition to be performed on the `Vector2f` instance being
	 * manipulated by the function.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the original vector and
	 * the provided float value.
	 * 
	 * The returned Vector2f object has an x-coordinate that is equal to the original
	 * x-coordinate plus the input r, and a y-coordinate that is equal to the original
	 * y-coordinate plus the input r. This means that the resulting vector's magnitude
	 * (length) is equal to the sum of the magnitudes of the original vector and the input
	 * r, and its direction is the same as the original vector's direction.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}


    /**
     * takes two floating-point arguments `x` and `y`, and returns a new `Vector2f`
     * instance with the sum of the current instance's `x` and `y` components and the
     * given `x` and `y` components.
     * 
     * @param x 2D coordinate that is added to the current position of the vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` object representing the sum of the current vector's
     * components and the input parameters.
     * 
     * The returned vector is a new instance of `Vector2f`.
     * The x component of the returned vector is equal to the sum of the x components of
     * the input vectors.
     * The y component of the returned vector is equal to the sum of the y components of
     * the input vectors.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object representing
	 * the difference between the input vector and the reference vector.
	 * 
	 * @param r 2D vector to be subtracted from the original vector.
	 * 
	 * 	- `x`: The first component of the vector (a double value).
	 * 	- `y`: The second component of the vector (also a double value).
	 * 
	 * @returns a new `Vector2f` object representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * The returned Vector2f object has two components, x and y, which represent the
	 * difference between the original vector's coordinates and the given vector's
	 * coordinates. Specifically, the value of each component is equal to the corresponding
	 * coordinate of the original vector minus the coordinate of the given vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` object
	 * representing the difference between the original vector's components and the given
	 * value.
	 * 
	 * @param r 2D vector to subtract from the current vector.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the original
	 * vector and the given value.
	 * 
	 * The `Vector2f` object returned by the function has two components, x and y, which
	 * represent the subtracted values of the original vector's x and y components,
	 * respectively. These components have the same data type as the original vector's
	 * components, i.e., they are both float values. Therefore, the returned vector has
	 * the same magnitude and direction as the original vector, but with the values of
	 * its components shifted by the given value 'r'.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies its input vector by a scalar value, resulting in a new vector with the
	 * product of the corresponding components.
	 * 
	 * @param r 2D vector that the current vector will be multiplied by, resulting in a
	 * new 2D vector output.
	 * 
	 * 	- `r.getX()` returns the x-coordinate of the input vector.
	 * 	- `r.getY()` returns the y-coordinate of the input vector.
	 * 
	 * @returns a new vector with the product of the input vectors' x and y components.
	 * 
	 * The returned value is a new Vector2f object with x and y components calculated by
	 * multiplying the current vector's x and y components with their corresponding
	 * counterparts in the passed-in argument vector r.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies its input vector by a scalar value, returning a new vector with the product.
	 * 
	 * @param r scalar value that is multiplied with the vector's X and Y components to
	 * produce the resultant vector.
	 * 
	 * @returns a vector with the product of the input `r` and the corresponding component
	 * of the original vector.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point with x and y components.
	 * 	- The `x` and `y` components of the output are calculated by multiplying the
	 * original `x` and `y` components by the input parameter `r`.
	 * 	- The output has the same orientation as the original vector, meaning that it
	 * retains the original direction but is scaled by the factor `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a reference to another `Vector2f` object as input and returns a new `Vector2f`
	 * object with the component values scaled by the reciprocal of the input parameter.
	 * 
	 * @param r 2D vector to which the current vector is being divided.
	 * 
	 * 	- `x`: The real component of the input vector.
	 * 	- `y`: The imaginary component of the input vector.
	 * 
	 * @returns a new vector with the same x-coordinate as the original vector, scaled
	 * by the reciprocal of the input vector's x-coordinate.
	 * 
	 * The output is a new Vector2f object with the x-component set to the result of
	 * dividing the input parameter `x` by the r.getX() method, and the y-component set
	 * to the result of dividing the input parameter `y` by the r.getY() method.
	 * 
	 * The returned output has the same scale as the input parameter `r`.
	 * 
	 * The returned output has a consistent direction with the input parameter `r`.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a float argument `r` and returns a new `Vector2f` instance with x-component
	 * divided by `r` and y-component divided by `r`.
	 * 
	 * @param r scalar factor by which the `Vector2f` instance is to be divided.
	 * 
	 * @returns a vector with x and y components scaled by the input parameter `r`.
	 * 
	 * The returned Vector2f object represents the division of the original Vector2f
	 * object by the input floating-point value r.
	 * 
	 * The x and y components of the returned Vector2f object have values that are equal
	 * to the original x and y components divided by the input r.
	 * 
	 * The returned Vector2f object has a length (or magnitude) that is equal to the
	 * original Vector2f object's length divided by the input r.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * returns a new `Vector2f` object containing the absolute values of its input
	 * components, `x` and `y`.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of the input
	 * vector's `x` and `y` components.
	 * 
	 * 	- The output is a new Vector2f instance, containing the absolute values of the
	 * original Vector2f's x and y components.
	 * 	- The returned Vector2f has the same scale as the original Vector2f.
	 * 	- The returned Vector2f preserves the original orientation of the Vector2f, meaning
	 * that its x-axis points in the same direction as the original Vector2f's x-axis.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of its arguments, concatenating them with parentheses
	 * and space separators.
	 * 
	 * @returns a string representation of a point in the Cartesian coordinate system,
	 * consisting of an open parenthesis, two numbers, and a closing parenthesis.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * modifies the instance fields `x` and `y` of the class `Vector2f` and returns a
	 * reference to the same modified instance.
	 * 
	 * @param x 2D coordinate of the point where the vector should be set to, and it is
	 * assigned the value passed in as an argument.
	 * 
	 * @param y 2nd component of the `Vector2f` object being modified and is assigned the
	 * value provided by the caller.
	 * 
	 * @returns a reference to the same `Vector2f` object, allowing it to be re-assigned
	 * with new values.
	 * 
	 * 	- The `Vector2f` instance is updated with the new values of `x` and `y`.
	 * 	- The returned output is the same instance as the original input, with the updates
	 * applied.
	 * 	- The output has the same type and attributes as the original input.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y components of the vector to the corresponding values of the
	 * argument vector.
	 * 
	 * @param r 2D vector to be set as the new value of the `Vector2f` instance, and its
	 * `x` and `y` components are used to update the corresponding fields of the instance.
	 * 
	 * 	- `getX()` and `getY()`: These methods are used to access the x- and y-coordinates
	 * of the vector, respectively.
	 * 
	 * @returns a reference to the original vector with its components modified.
	 * 
	 * The output is a reference to the same vector instance as the input argument.
	 * The x-component and y-component of the output vector are set to the corresponding
	 * components of the input argument.
	 * The output vector has the same values as the input argument after the assignment.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` object into a `Vector3f` object by adding an extra component
     * representing the z-coordinate.
     * 
     * @returns a new `Vector3f` instance containing the values `x`, `y`, and `0`.
     * 
     * 	- `x`: The value of x in the vector, representing the x-coordinate.
     * 	- `y`: The value of y in the vector, representing the y-coordinate.
     * 	- `z`: The value of 0 in the vector, representing the z-coordinate.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * returns the value of `x`, a `float` variable, as its output.
	 * 
	 * @returns the value of `x`, which is a floating-point number representing the
	 * x-coordinate of an object.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of a class instance variable `x` to the input `float` parameter.
	 * 
	 * @param x new value of the `x` field in the object being manipulated by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a `float` variable.
	 * 
	 * @returns a floating-point value representing the `y` coordinate of an object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the member variable `y`.
	 * 
	 * @param y value that will be assigned to the `y` field of the class instance being
	 * manipulated by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares the `x` and `y` components of the current instance with those of the
	 * provided `Vector2f` instance, returning `true` if they are equal.
	 * 
	 * @param r 2D point to be compared with the current vector for equality.
	 * 
	 * The `Vector2f` class represents a two-dimensional vector in homogeneous coordinates.
	 * The `x` and `y` fields represent the x- and y-coordinates of the vector, respectively.
	 * 
	 * @returns a boolean value indicating whether the object being compared to `r` has
	 * the same x and y coordinates as `r`.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
