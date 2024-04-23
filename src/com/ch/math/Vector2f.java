package com.ch.math;

/**
 * is used for working with 2D vectors in Java. It has several methods for manipulating
 * and operating on 2D vectors, including length, max, dot product, normalization,
 * cross product, and more. The class also provides methods for adding and subtracting
 * other vectors or floats, multiplying by a scalar or another vector, dividing by a
 * scalar or another vector, and taking the absolute value of the vector. Additionally,
 * it has methods for setting the x and y components of the vector and checking
 * equality with other vectors.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * calculates the Euclidean distance between a point and the origin, based on the
  * point's x and y coordinates.
  * 
  * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * computes the maximum value of two input floating-point numbers `x` and `y`.
  * 
  * @returns the larger of the input values `x` and `y`.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * computes the dot product of a `Vector2f` instance `r` and the instance of the class
  * `Vector2f` that the function is defined in, returning the result as a `float`.
  * 
  * @param r 2D vector to which the `x` and `y` components of the returned vector are
  * to be dot-producted.
  * 
  * `x` and `y` denote the component values of the deserialized input `r`, which is a
  * `Vector2f` type.
  * 
  * @returns a floating-point number representing the dot product of the vector and
  * the provided reference vector.
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * normalizes a vector by dividing its components by the vector's length, resulting
  * in a unitized vector representation.
  * 
  * @returns a normalized version of the input vector, with a length of 1.
  * 
  * 	- The output is a `Vector2f` object containing the normalized values of the
  * original vector's `x` and `y` components.
  * 	- The normalization is calculated using the length of the original vector, which
  * is obtained through the `length()` method.
  * 	- The resulting vector has a length of 1, indicating that it is a unit vector in
  * the coordinate system defined by the original vector.
  * 
  * The output can be used to represent the normalized version of the original vector
  * in various applications, such as mathematical computations or visualizations.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * computes the dot product of two vectors and returns their cross product as a scalar
  * value.
  * 
  * @param r 2D vector that the function will operate on, and its value is used to
  * compute the output result.
  * 
  * 	- `r` is a `Vector2f` object, representing a 2D point or vector in the float format.
  * 	- It has two attributes: `x` and `y`, which represent the coordinates of the
  * point/vector along the x-axis and y-axis, respectively.
  * 
  * @returns a floating-point value representing the cross product of two vectors.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * interpolates between two vectors, `dest` and `this`, based on a scalar value
  * `lerpFactor`. It returns the resulting vector by subtracting the difference between
  * `this` and `dest`, scaling it with `lerpFactor`, and then adding the original vector.
  * 
  * @param dest 2D destination point to which the current position is interpolated
  * with the given `lerpFactor`.
  * 
  * 	- `Vector2f dest`: This represents a 2D vector object with two components, x and
  * y. The values of these components are used in the calculation of the lerped vector.
  * 
  * @param lerpFactor 0 to 1 factor used for interpolating between the start and end
  * vectors of the function, with higher values resulting in more interpolation and
  * closer final values to the destination vector.
  * 
  * @returns a new `Vector2f` object that represents the linear interpolation of the
  * input `dest` vector and the current position of the object being animated.
  * 
  * The `Vector2f` object returned by the function is the linear interpolation of the
  * input `dest` and the current position `this`, based on the specified `lerpFactor`.
  * The resulting vector represents the position of an entity moving along a smooth trajectory.
  * 
  * The `sub()` method is used to subtract the current position from the destination
  * position, creating a direction vector.
  * 
  * The `mul()` method is used to multiply the interpolation factor by both vectors,
  * scaling their sum to obtain the final position.
  * 
  * Finally, the `add()` method adds the current position to the scaled sum, resulting
  * in the output vector representing the entity's updated position.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * takes a single angle parameter and returns a new vector with its x-component rotated
  * by the specified angle, while keeping its y-component fixed.
  * 
  * @param angle angle of rotation in radians, which is used to calculate the cosine
  * and sine values that are applied to the `x` and `y` components of the returned
  * `Vector2f` object.
  * 
  * @returns a rotated vector with x and y components scaled by the cosine and sine
  * of the angle of rotation.
  * 
  * 	- The Vector2f object represents a 2D point in homogeneous coordinates.
  * 	- The x and y components of the object represent the horizontal and vertical
  * positions of the rotated point, respectively.
  * 	- The values of x and y are modified by multiplying them with complex numbers sin
  * and cos, respectively, which are derived from the angle of rotation (in radians)
  * using the Math.cos() and Math.sin() methods.
  * 	- The resulting values for x and y represent the rotated position of the point
  * in the new coordinate system.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * adds two `Vector2f` objects, returning a new object with the sum of their x and y
  * components.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * `r`: A `Vector2f` object with attributes `x` and `y`, representing the component
  * values in the x- and y-axes, respectively.
  * 
  * @returns a new `Vector2f` object representing the sum of the input vectors.
  * 
  * The `Vector2f` object returned by the function represents the sum of the original
  * vector and the input vector `r`. Therefore, the X-coordinate of the returned vector
  * is equal to the X-coordinate of the original vector plus the X-coordinate of `r`,
  * while the Y-coordinate is equal to the Y-coordinate of the original vector plus
  * the Y-coordinate of `r`.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * takes a float argument `r` and returns a new `Vector2f` instance with the sum of
  * the current vector's `x` and `y` coordinates plus the `r` value.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * @returns a new `Vector2f` instance with the sum of the original vector's components
  * and the provided scalar value.
  * 
  * 	- The value of x and y in the input vector are incremented by an amount represented
  * by the argument r.
  * 	- The returned output is a new Vector2f object containing the updated values of
  * x and y.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * takes two floating-point arguments `x` and `y`, returns a new `Vector2f` instance
     * with the sum of the current instance's `x` and `y` components and the input `x`
     * and `y` components.
     * 
     * @param x 2D coordinate to be added to the current vector's 2D position.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` object representing the sum of the input `x` and `y` values.
     * 
     * The return type is `Vector2f`, indicating that it is a two-dimensional vector with
     * floating-point values for x and y components.
     * The expression `this.x + x` adds the `x` parameter to the current value of the `x`
     * component of the `Vector2f`. Similarly, `this.y + y` adds the `y` parameter to the
     * current value of the `y` component of the `Vector2f`.
     * The returned vector has the same x and y components as the original vector, but
     * with the additional values added to their existing values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * takes a `Vector2f` object `r` as input and returns a new `Vector2f` object
  * representing the difference between the current vector and `r`.
  * 
  * @param r 2D vector to be subtracted from the original vector.
  * 
  * 	- `x`: The first component of the vector, which represents the x-coordinate of
  * the point.
  * 	- `y`: The second component of the vector, which represents the y-coordinate of
  * the point.
  * 
  * @returns a new `Vector2f` object representing the difference between the input
  * vector and the reference vector.
  * 
  * The output is a new Vector2f instance with x and y components computed as the
  * difference between the input vector's x and y components and the input vector itself.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * takes a single float argument `r` and returns a new `Vector2f` instance with the
  * component-wise difference between the current vector and the given value.
  * 
  * @param r 2D vector to subtract from the current vector.
  * 
  * @returns a new `Vector2f` object representing the difference between the original
  * vector and the given value.
  * 
  * 	- The `Vector2f` object represents a 2D point in homogeneous coordinates.
  * 	- The `x` and `y` fields represent the x- and y-coordinates of the point, respectively.
  * 	- The `r` parameter represents the distance from the original point to be subtracted.
  * 
  * The returned output is a new `Vector2f` object that represents the difference
  * between the original point and the distance specified by the `r` parameter.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the product
  * of the current object's `x` and `y` components multiplied by the corresponding
  * components of the `r` argument.
  * 
  * @param r 2D vector that multiplies with the current vector.
  * 
  * 	- `x` and `y` are double values representing the coordinates of the vector.
  * 
  * @returns a new vector with the product of the input vector's x and y components
  * multiplied by the corresponding values of the argument vector.
  * 
  * 	- The output is of type `Vector2f`, indicating that it represents a 2D vector
  * with x and y components.
  * 	- The x and y components of the output are calculated by multiplying the x and y
  * components of the input vector `r` by the corresponding components of the input
  * vector `this`.
  * 	- The resulting output is a new vector object, which is returned as the function
  * value.
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * multiplies the vector's components by a given scalar value, resulting in a new
  * vector with the same magnitude and direction as the original, but with the x and
  * y coordinates scaled by the specified factor.
  * 
  * @param r scalar value used to multiply the vector's x and y components.
  * 
  * @returns a new `Vector2f` instance with the product of the input `r` multiplied
  * by the corresponding component of the original vector.
  * 
  * 	- The output is a `Vector2f` object that represents the result of multiplying the
  * `x` and `y` components of the input parameter `r` by the same value.
  * 	- The resulting vector has the same dimensions as the input vector, with the
  * magnitude of each component being the product of the corresponding component of
  * the input vector and the input parameter `r`.
  * 	- The direction of the output vector is the same as that of the input vector,
  * since the multiplication is commutative for vectors.
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance with x-axis
  * divided by r.x and y-axis divided by r.y.
  * 
  * @param r 2D vector to which the current vector is divided.
  * 
  * 	- `x`: The real part of the input vector.
  * 	- `y`: The imaginary part of the input vector.
  * 
  * @returns a new vector with the same x-coordinates as the original vector, scaled
  * by the ratio of the input vector's x-coordinate to its own x-coordinate.
  * 
  * The output is a new Vector2f object with x and y components computed as the dividend
  * divided by the divisor. Specifically, the x component is equal to the dividend x
  * / divisor, while the y component is equal to the dividend y / divisor.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * takes a single float argument and returns a new `Vector2f` instance with scaled x
  * and y components proportional to the input value.
  * 
  * @param r scale factor used to divide the vector's components by.
  * 
  * @returns a vector with a magnitude equal to the inverse of the input `r`, and a
  * direction that is the same as the original vector.
  * 
  * 	- The `Vector2f` class represents a 2D point in homogeneous coordinates, with x
  * and y components.
  * 	- The function returns a new instance of `Vector2f`, where both the x and y
  * components are divided by the input parameter `r`.
  * 	- The output has the same dimensions as the original input, i.e., it is a 2D point
  * in homogeneous coordinates.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * calculates the absolute value of a `Vector2f` object's x and y components, returning
  * a new `Vector2f` object with those values.
  * 
  * @returns a new `Vector2f` instance containing the absolute values of its input parameters.
  * 
  * 	- The `x` and `y` values represent the absolute value of the input vector's components.
  * 	- The output is a new Vector2f object that contains the absolute values of the
  * input vector's components.
  * 	- The returned object has the same type as the input object, which in this case
  * is Vector2f.
  * 	- The function does not modify the original input vector.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * returns a string representation of a point (x,y) by combining the values of x and
  * y into a single string.
  * 
  * @returns a string representation of a point in coordinates, consisting of the
  * values of `x` and `y` separated by a space.
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * sets the `x` and `y` components of a `Vector2f` object to the input values, returning
  * the modified object for chaining.
  * 
  * @param x 2D position of the vector in the x-axis.
  * 
  * @param y 2nd component of the `Vector2f` object being modified, and its value is
  * assigned to the `y` field of the object in the function.
  * 
  * @returns a reference to the same instance of the `Vector2f` class with updated `x`
  * and `y` values.
  * 
  * The returned output is a reference to the same `Vector2f` instance. This means
  * that the original object remains unchanged and the modified values of `x` and `y`
  * are reflected in the returned output.
  * 
  * The returned output has the same values for `x` and `y` as the input parameters,
  * which are assignable to the member variables `x` and `y` respectively.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * sets the x and y components of the vector to the corresponding values of the given
  * vector argument, and returns a reference to the modified vector object.
  * 
  * @param r 2D vector to be set as the value of the `Vector2f` object returned by the
  * function.
  * 
  * 	- `getX()` and `getY()` represent the x-axis and y-axis coordinates of the vector,
  * respectively.
  * 
  * @returns a reference to the original vector instance with its components updated
  * to match the values of the provided vector.
  * 
  * 	- The output is a reference to the same Vector2f instance as the original input
  * parameter.
  * 	- The x and y coordinates of the output are set to the values passed in the r parameter.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * returns a new `Vector3f` object containing the values of the current vector's `x`,
     * `y`, and `z` components, where all components are set to zero except for the
     * specified `x`, `y` components.
     * 
     * @returns a vector with coordinates (x, y, 0).
     * 
     * The returned object is a `Vector3f` instance, representing a 3D vector with three
     * components: x, y, and z.
     * Each component represents a floating-point value, indicating the magnitude and
     * direction of the vector in the corresponding dimension.
     * The vector is initialized to have an x-component of 0, y-component of 0, and
     * z-component of 0, which means it points in the default "up" direction in 3D space.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

 /**
  * returns the value of the `x` field.
  * 
  * @returns the value of `x`.
  */
	public float getX() {
		return x;
	}

 /**
  * sets the value of the `x` field within the object to which it belongs.
  * 
  * @param x float value that sets the field 'x' of the class to which the function belongs.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * retuns the value of the `y` field.
  * 
  * @returns the value of the `y` field.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the member field `y` to the argument passed as a `float`.
  * 
  * @param y 2D point's `y` coordinate that will be assigned to the `y` field of the
  * 2D point object upon execution of the function.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * compares two `Vector2f` objects based on their `x` and `y` components, returning
  * `true` if they are equal, and `false` otherwise.
  * 
  * @param r 2D vector to be compared with the current vector.
  * 
  * 	- `x`: The `x` property of `r` represents the real-valued coordinate along the x-axis.
  * 	- `y`: The `y` property of `r` represents the real-valued coordinate along the y-axis.
  * 
  * @returns a boolean value indicating whether the object is equal to the provided vector.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
