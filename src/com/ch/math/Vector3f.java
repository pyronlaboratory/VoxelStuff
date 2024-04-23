package com.ch.math;

/**
 * is a mathematical representation of a 3D vector in Java. It has three fields (x,
 * y, and z) that represent the component values of the vector, and various methods
 * for performing mathematical operations on the vector such as addition, subtraction,
 * multiplication, division, and more. Additionally, it provides methods for normalizing
 * the vector and rotating it around a given axis.
 */
public class Vector3f {

	private float x;
	private float y;
	private float z;

	public Vector3f() {
		this(0, 0, 0);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * calculates the Euclidean distance of a point from its origin using the Pythagorean
	 * theorem.
	 * 
	 * @returns the square root of the sum of the squares of the three Cartesian coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * computes the length of a point in 3D space by squaring its coordinates and summing
	 * them.
	 * 
	 * @returns a value representing the length of the square of the input coordinates.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * computes and returns the maximum value of three input values `x`, `y`, and `z`.
	 * 
	 * @returns the maximum value of `x`, `y`, or `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * computes the dot product of a vector `r` and the input vector `x`, `y`, or `z`.
	 * 
	 * @param r 3D vector that the dot product is being computed for.
	 * 
	 * `r`: A `Vector3f` object representing a 3D vector with properties `x`, `y`, and `z`.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the vector represented by the function parameters.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes the vector that is perpendicular to two given vectors in a three-dimensional
	 * space.
	 * 
	 * @param r 3D vector that is being crossed with the current vector, resulting in a
	 * new 3D vector output.
	 * 
	 * 	- `r` is a `Vector3f` object representing a 3D vector in homogeneous coordinates.
	 * 	- `x`, `y`, and `z` are the components of the vector in the x, y, and z directions,
	 * respectively.
	 * 
	 * @returns a new vector with the cross product of the input vectors.
	 * 
	 * 	- The output is a `Vector3f` object representing the cross product of the input
	 * vectors.
	 * 	- The components of the output vector are given by the equations x_, y_, and z_,
	 * which are calculated using the dot product of the input vectors.
	 * 	- The output vector has a magnitude equal to the product of the magnitudes of the
	 * input vectors, and its direction is perpendicular to both input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a `Vector3f` instance by dividing its components by the magnitude of
	 * the vector, resulting in a unitized representation of the original vector.
	 * 
	 * @returns a vector with the magnitude of the original vector and the direction unchanged.
	 * 
	 * The output is a `Vector3f` object that represents the normalized version of the
	 * input vector. The normalization is done by dividing each component of the input
	 * vector by its corresponding length.
	 * 
	 * The resulting vector has a length of 1, which means that it is a unit vector in
	 * the coordinate system. This property makes it useful for various mathematical
	 * operations and applications in computer graphics, physics, and other fields.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a 3D vector by an angle around a specified axis, based on the dot product
	 * of the vector and the axis.
	 * 
	 * @param axis 3D rotation axis around which the object will be rotated.
	 * 
	 * 	- `axis`: A 3D vector representing the rotation axis. It has three components:
	 * x, y, and z.
	 * 
	 * @param angle 3D rotation angle of the vector around the specified axis.
	 * 
	 * @returns a new vector that represents the rotation of the original vector along
	 * the specified axis by the given angle.
	 * 
	 * 	- The output is a vector representation of the rotated object.
	 * 	- The vector components represent the position of the rotated object in 3D space
	 * after applying the rotation.
	 * 	- The axis of rotation is specified by the `axis` parameter, which determines the
	 * direction of the rotation.
	 * 	- The angle of rotation is specified by the `angle` parameter, which represents
	 * the amount of rotation applied to the object.
	 * 	- The function returns a new vector object that represents the rotated position,
	 * based on the axis and angle parameters provided.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * rotates a `Vector3f` instance by the angle represented by the given `Quaternion`
	 * rotation.
	 * 
	 * @param rotation 3D rotation matrix that is applied to the `Vector3f` instance being
	 * rotated.
	 * 
	 * 	- Quaternion is an object that represents a 3D rotation, consisting of a scalar
	 * component and a vector component.
	 * 	- conjugate() returns the complex conjugate of the quaternion, which is used in
	 * the multiplication operation.
	 * 
	 * @returns a vector representing the rotated position of the original vector.
	 * 
	 * 	- The output is a `Vector3f` object representing the rotated position of the
	 * original input vector.
	 * 	- The x, y, and z components of the output represent the rotated position in the
	 * x, y, and z directions, respectively.
	 * 	- The output is obtained by multiplying the rotation quaternion with the original
	 * input vector using the `mul` method, then concatenating the result with the conjugate
	 * of the rotation quaternion using the ` mul` method again.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * calculates the linear interpolation between two vectors, resulting in a new vector
	 * that is the weighted sum of the original vectors.
	 * 
	 * @param dest 3D vector to which the current vector will be interpolated, and its
	 * value is used as the starting point for the interpolation.
	 * 
	 * 	- The `Vector3f` class is used to represent a 3D vector with floating-point values.
	 * 	- The `sub` method calculates the difference between two vectors by subtracting
	 * one vector from another.
	 * 	- The `mul` method multiplies a vector by a scalar value, which in this case is
	 * `lerpFactor`.
	 * 	- The `add` method adds a vector to another vector or a scalar value.
	 * 
	 * @param lerpFactor factor by which the current vector is to be interpolated towards
	 * the destination vector.
	 * 
	 * @returns a vector that interpolates between the input `dest` and the current
	 * position of the object.
	 * 
	 * The `Vector3f` object returned by the function is a result of interpolating between
	 * the input `Vector3f` objects using the provided `lerpFactor`.
	 * 
	 * The resulting vector has the same direction and magnitude as the input vectors,
	 * but its coordinates are adjusted according to the interpolation factor. Specifically,
	 * the x, y, and z components of the output vector are calculated as follows:
	 * 
	 * x = (dest.x * lerpFactor) + (this.x * (1 - lerpFactor));
	 * y = (dest.y * lerpFactor) + (this.y * (1 - lerpFactor));
	 * z = (dest.z * lerpFactor) + (this.z * (1 - lerpFactor));
	 * 
	 * Therefore, the output vector has a smoothed value between the input vectors, with
	 * the interpolation factor determining the degree of smoothing.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * adds two `Vector3f` objects and returns a new vector with the sum of their components.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 	- `y`: The y-coordinate of the input vector.
	 * 	- `z`: The z-coordinate of the input vector.
	 * 
	 * @returns a new `Vector3f` object representing the sum of the input vectors.
	 * 
	 * The `Vector3f` object returned by the function represents a sum of two other
	 * `Vector3f` objects. The x, y, and z components of the returned vector are calculated
	 * by adding the corresponding components of the two input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the components of a provided `Vector3f` object to the corresponding components
	 * of the current object, resulting in an updated representation of the current object.
	 * 
	 * @param r 3D vector to be added to the current position of the object, and its
	 * values are used to increment the object's x, y, and z components.
	 * 
	 * 	- `x`, `y`, and `z` are the coordinates of the vector, each representing a single
	 * component of the vector in 3D space.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * takes a single float parameter and adds it to the vector's x, y, or z component,
	 * returning a new Vector3f instance with the updated values.
	 * 
	 * @param r 3D vector that is added to the current vector value of the object, resulting
	 * in a new vector with the sum of the two values.
	 * 
	 * @returns a new `Vector3f` object with the sum of the input vector's x, y, and z
	 * components and the given scalar value.
	 * 
	 * The return type of the `add` function is `Vector3f`, which represents a 3D vector
	 * with floating-point values for x, y, and z components.
	 * 
	 * The expression `x + r` generates an integer value representing the addition of the
	 * float argument `r` to the component `x`. Similarly, `y + r` generates an integer
	 * value representing the addition of `r` to the component `y`, and `z + r` generates
	 * an integer value representing the addition of `r` to the component `z`.
	 * 
	 * The resulting vector values are then assigned to a new `Vector3f` object using the
	 * `new Vector3f()` constructor.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * takes a `Vector3f` and a scalar value as input, returns the result of adding the
	 * scaled vector to the current vector.
	 * 
	 * @param v 3D vector to be scaled and added to the current vector.
	 * 
	 * 	- `v` is a Vector3f class instance representing a 3D vector with x, y, and z components.
	 * 	- `scale` is an input float value representing the scaling factor applied to `v`.
	 * 
	 * @param scale scalar value that is multiplied with the input `Vector3f` before
	 * adding it to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the given `v` vector scaled by
	 * `scale`.
	 * 
	 * The output is a new `Vector3f` instance that represents the sum of the original
	 * vector and the scaled version of the input vector.
	 * The scale factor is applied to both components of the input vector before adding
	 * them to the original vector.
	 * The resulting vector has the same components as the original vector, but with the
	 * scaled components.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a `Vector3f` object by a scalar value and adds it to the current vector
	 * representation of the object.
	 * 
	 * @param v 3D vector to be scaled.
	 * 
	 * 	- `v`: A `Vector3f` object representing a 3D vector with x, y, and z components.
	 * 	- `scale`: A floating-point value representing the scalar factor to be applied
	 * to the `v` vector.
	 * 
	 * @param scale scalar value that is multiplied with the `Vector3f` output of the
	 * `addSelf()` method, thereby scaling the result of the method call.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * calculates the vector difference between two `Vector3f` objects, returning a new
	 * vector with the differences.
	 * 
	 * @param r 3D vector to be subtracted from the input `Vector3f`.
	 * 
	 * 	- `x`: an integer value representing the x-coordination of `r`.
	 * 	- `y`: an integer value representing the y-coordination of `r`.
	 * 	- `z`: an integer value representing the z-coordination of `r`.
	 * 
	 * @returns a new `Vector3f` object representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * 	- The returned value is a new Vector3f instance containing the difference between
	 * the input vectors' x, y, and z components.
	 * 	- The values of x, y, and z are calculated by subtracting the corresponding values
	 * of the input vector r from those of the current vector.
	 * 	- The resulting vector has the same orientation as the original vector, but it
	 * moves in the opposite direction.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a single floating-point value `r` and subtracts it from the corresponding
	 * components of a `Vector3f` object, returning a new `Vector3f` object with the
	 * resultant values.
	 * 
	 * @param r 3D position from which to subtract the vector.
	 * 
	 * @returns a new `Vector3f` instance representing the difference between the original
	 * vector and the provided value.
	 * 
	 * The `Vector3f` object returned by the function has three components: `x`, `y`, and
	 * `z`. Each component represents the difference between the original value and the
	 * input parameter `r`. For example, if `x` is 2.0 and `r` is 1.0, then the `x`
	 * component of the output will be 1.0.
	 * 
	 * The components of the output are in a specific order, with the `x`, `y`, and `z`
	 * components corresponding to the x, y, and z axes of a 3D coordinate system.
	 * 
	 * The output is a new instance of the `Vector3f` class, which means it has its own
	 * memory location and can be used independently of the original input parameters.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * multiplies the components of a `Vector3f` object by the corresponding components
	 * of another `Vector3f` object, and returns a new `Vector3f` object with the resulting
	 * values.
	 * 
	 * @param r 3D vector to which the current vector is multiplied, resulting in a new
	 * 3D vector output.
	 * 
	 * 	- `x`, `y`, and `z` are the components of `r`, which represent vectors in 3D space.
	 * 	- `getX()`, `getY()`, and `getZ()` are methods that provide access to the individual
	 * components of `r`.
	 * 
	 * @returns a new vector with the product of the input vectors' coordinates.
	 * 
	 * 	- The output is a new Vector3f object with the product of the input parameters.
	 * 	- The x, y, and z components of the output are calculated by multiplying the
	 * corresponding components of the input vectors.
	 * 	- The resulting vector has the same semantic meaning as the multiplication of the
	 * two input vectors in 3D space.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * takes a single float argument and multiplies it to the corresponding components
	 * of a `Vector3f` object, returning a new `Vector3f` object with the result.
	 * 
	 * @param r scalar value used to multiply each component of the `Vector3f` object.
	 * 
	 * @returns a new vector with the product of the input vector's components and the
	 * scalar value `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * takes a reference to another `Vector3f` object and returns a new `Vector3f` object
	 * with the componentwise result of dividing the input by the reference argument.
	 * 
	 * @param r vector to be divided by, and it is used to calculate the output vector's
	 * components.
	 * 
	 * The `Vector3f` class represents a three-dimensional vector in homogeneous coordinates.
	 * The `x`, `y`, and `z` attributes represent the component values of the vector.
	 * 
	 * Therefore, when dividing a vector by another vector using this function, the output
	 * vector will have component values computed as the ratio of the corresponding
	 * components of the input vectors.
	 * 
	 * @returns a new vector with the same components as the input vector, but scaled by
	 * the reciprocal of the input value.
	 * 
	 * 	- The output is a new `Vector3f` instance with scaled values based on the division
	 * operation performed on the input vectors.
	 * 	- The x, y, and z components of the output represent the scaled values of the
	 * corresponding components of the input vectors.
	 * 	- The scale factor in each component is calculated by dividing the corresponding
	 * value of the input vector by the corresponding value of the input vector passed
	 * as a parameter to the function.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector3f` instance with x,
	 * y, and z components scaled by the reciprocal of `r`.
	 * 
	 * @param r scalar value that is used to divide each component of the `Vector3f`
	 * object returned by the function.
	 * 
	 * @returns a vector with x, y, and z components scaled by the input factor `r`.
	 * 
	 * 	- The output is a new instance of the `Vector3f` class with the values of the
	 * original input divided by the given scalar value.
	 * 	- The output has the same dimensions as the input, with each component representing
	 * the corresponding coordinate of the vector divided by the scalar value.
	 * 	- The output is normalized to have a length of 1, ensuring that the resulting
	 * vector is properly scaled.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * computes and returns a new vector with the absolute values of its component.
	 * 
	 * @returns a new `Vector3f` instance with the absolute values of its components.
	 * 
	 * The output is a new Vector3f object containing the absolute value of the input
	 * vector's x, y, and z components.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * returns a string representation of a object by concatenating three values: `x`,
	 * `y`, and `z`.
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three values
	 * separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object containing the x and y coordinates of an entity.
	 * 
	 * @returns a vector containing the x and y coordinates of a point.
	 * 
	 * 	- `x`: The first component of the vector, representing the x-coordinate of the point.
	 * 	- `y`: The second component of the vector, representing the y-coordinate of the
	 * point.
	 * 
	 * Both `x` and `y` are doubles, representing real numbers that represent the coordinates
	 * of the point in a 2D space.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object representing the y and z components of a point.
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `z`.
	 * 
	 * 	- `y`: This represents the y-component of the vector, which is a floating-point
	 * value.
	 * 	- `z`: This represents the z-component of the vector, which is also a floating-point
	 * value.
	 * 
	 * The vector itself is an instance of the `Vector2f` class, which is a part of the
	 * Java Foundation Library (JFL). The `Vector2f` class provides methods for manipulating
	 * 2D vectors and represents mathematical vectors in two dimensions using homogeneous
	 * coordinates.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` object containing the `z` and `x` components of an unknown entity.
	 * 
	 * @returns a `Vector2f` object representing the coordinate pair (z, x).
	 * 
	 * 	- z is a double value representing the z-component of the vector.
	 * 	- x is a double value representing the x-component of the vector.
	 * 	- The vector is represented as a 2D object with two components (x and z).
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a `Vector2f` object representing the position (x and y coordinates) of an
	 * object.
	 * 
	 * @returns a `Vector2f` object containing the values of `x` and `y`.
	 * 
	 * 	- The returned object is of type `Vector2f`, which represents a 2D point in
	 * homogeneous coordinates.
	 * 	- The `y` field of the object contains the Y-coordinate of the point, while the
	 * `x` field contains the X-coordinate.
	 * 	- The resulting object is a new instance of `Vector2f`, rather than modifying the
	 * original input parameters.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * generates a vector with the values of z and y.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `y` coordinates of the point.
	 * 
	 * 	- The returned Vector2f object represents a point in 2D space with coordinates z
	 * and y.
	 * 	- The z coordinate is a float value representing the vertical position of the
	 * point, ranging from -1 to 1.
	 * 	- The y coordinate is also a float value representing the horizontal position of
	 * the point, ranging from -1 to 1.
	 * 
	 * Overall, the `getZY` function returns a valid 2D coordinate point that can be used
	 * in various applications.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a `Vector2f` object containing the `x` and `z` coordinates of an entity.
	 * 
	 * @returns a `Vector2f` object containing the `x` and `z` coordinates of a point.
	 * 
	 * 	- The `Vector2f` object represents a 2D point with x-axis value `x` and z-axis
	 * value `z`.
	 * 	- The `Vector2f` class is a part of the Java Standard Library and provides
	 * operations on 2D points.
	 * 	- The returned object can be used in various contexts such as graphics, game
	 * development, and mathematical calculations.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * modifies the components of a `Vector3f` object, assigning new values to `x`, `y`,
	 * and `z`.
	 * 
	 * @param x 3D position of the vector in the x-axis direction.
	 * 
	 * @param y 2D position of the vector along the Y-axis.
	 * 
	 * @param z 3rd component of the vector and sets its value to the provided float value.
	 * 
	 * @returns a reference to the same `Vector3f` object, which now contains the new x,
	 * y, and z values.
	 * 
	 * The `Vector3f` object is modified to have new values for `x`, `y`, and `z`.
	 * The returned object is itself, with the modifications applied.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the components of the `Vector3f` object to the corresponding values of the
	 * provided `r` argument.
	 * 
	 * @param r 3D vector to be set as the value of the `Vector3f` object.
	 * 
	 * 	- `getX()`: Returns the x-coordinate of `r`.
	 * 	- `getY()`: Returns the y-coordinate of `r`.
	 * 	- `getZ()`: Returns the z-coordinate of `r`.
	 * 
	 * @returns a reference to the modified vector instance.
	 * 
	 * The function returns a reference to the original object, indicating that the state
	 * of the object remains unchanged after modification. This is useful for chaining
	 * methods together or avoiding unnecessary copies of objects.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * retrieves the value of the `x` field, which is a `float` variable.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the field `x` of its object to the provided float value.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field.
	 * 
	 * @returns a floating-point value representing the y coordinate of the point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field in the current object to the provided float value.
	 * 
	 * @param y float value that will be assigned to the `y` field of the class instance
	 * being manipulated by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field in the provided object.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the field `z` to the provided float argument.
	 * 
	 * @param z 3D coordinates of an object in the `x`, `y`, and `z` dimensions, which
	 * is stored in the instance variable `this.z` after being assigned the value provided
	 * by the caller.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares a `Vector3f` object with another vector by checking if its `x`, `y`, and
	 * `z` components are equal.
	 * 
	 * @param r 3D vector that is being compared to the current vector for equality.
	 * 
	 * 	- `x`: The first coordinate of `r`, which represents the x-axis value of the vector.
	 * 	- `y`: The second coordinate of `r`, which represents the y-axis value of the vector.
	 * 	- `z`: The third coordinate of `r`, which represents the z-axis value of the vector.
	 * 
	 * @returns a boolean value indicating whether the vector's coordinates are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
