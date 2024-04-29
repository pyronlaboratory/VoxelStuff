package com.ch.math;

/**
 * is a data structure that represents a 3D point in the x, y, and z dimensions. It
 * has fields for each component (x, y, and z) and provides methods for accessing and
 * modifying those components. The class also includes methods for comparing equality
 * with other vectors.
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
	 * calculates the Euclidean distance of a 3D point from its origin using the square
	 * root of the sum of the squares of the x, y, and z coordinates.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a point
	 * in 3D space.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * computes the length of a point in three-dimensional space by squaring its coordinates
	 * and summing them.
	 * 
	 * @returns a floating-point representation of the square of the length of the given
	 * vector.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * calculates the maximum value of three arguments: `x`, `y`, and `z`. It returns the
	 * maximum value using the `Math.max()` method.
	 * 
	 * @returns the maximum of the input values `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * computes the dot product of a `Vector3f` object and another vector, returning the
	 * result as a float value.
	 * 
	 * @param r 3D vector to be dot-producted with the current vector.
	 * 
	 * 	- `x`, `y`, and `z` are variables of type `float` that represent the coordinates
	 * of the vector.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the vector component.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes the cross product between two vectors in a 3D space, returning a new
	 * vector with magnitude and direction based on the dot product of the input vectors.
	 * 
	 * @param r 3D vector to cross with the current vector.
	 * 
	 * 	- `r` is a `Vector3f` object containing the values for the x, y, and z components.
	 * 	- `x`, `y`, and `z` are the individual component values of `r`.
	 * 
	 * @returns a new vector with the cross product of the input vectors.
	 * 
	 * 	- The output is a new Vector3f object that represents the cross product of the
	 * input vectors.
	 * 	- The components of the output vector are calculated using the dot product formula
	 * for cross products, where x, y, and z are the components of the input vectors.
	 * 	- The output vector has the same magnitude as the input vectors, but its direction
	 * is perpendicular to both input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a vector by dividing it by its magnitude, returning a new vector with
	 * the same direction but with a length equal to 1.
	 * 
	 * @returns a normalized version of the input vector, with a length of 1.
	 * 
	 * 	- The output is a `Vector3f` object representing a normalized version of the
	 * original vector.
	 * 	- The x, y, and z components of the output represent the normalized values of the
	 * corresponding components of the original vector, scaled by the length of the
	 * original vector.
	 * 	- The length of the output vector is always non-zero, since the normalization is
	 * done to ensure that the resulting vector has a non-zero magnitude.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a 3D vector by an angle around a specified axis, based on the sin and cos
	 * of the angle.
	 * 
	 * @param axis 3D rotation axis around which the object will be rotated.
	 * 
	 * 	- `float angle`: The angle of rotation in radians.
	 * 	- `Vector3f axis`: The axis of rotation, which can be any 3D vector representing
	 * a point in space.
	 * 
	 * @param angle 3D rotation angle of the vector.
	 * 
	 * @returns a new vector that represents the rotated version of the original vector.
	 * 
	 * 	- The output is a `Vector3f` object, representing the rotated position of the
	 * original vector.
	 * 	- The axis parameter represents the direction of rotation, and the angle parameter
	 * represents the angle of rotation in radians.
	 * 	- The function first computes the sine and cosine of the angle of rotation using
	 * the `Math.sin()` and `Math.cos()` methods.
	 * 	- Then, it multiplies the original vector by the sine and cosine values to create
	 * two new vectors that represent the rotated position along the axis of rotation.
	 * 	- The function then adds the result of the multiplication of the `this` object
	 * (representing the original position) and the vector created by multiplying the
	 * axis vector by the dot product of the `this` vector and a vector representing the
	 * angle of rotation in radians.
	 * 
	 * The output of the `rotate` function can be further explained as follows:
	 * 
	 * 	- The returned vector represents the rotated position of the original vector along
	 * the specified axis of rotation.
	 * 	- The magnitude of the returned vector is equal to the magnitude of the original
	 * vector, but its direction has been rotated by the specified angle.
	 * 	- The rotation is around the origin of the coordinate system, as specified by the
	 * axis parameter.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * takes a quaternion parameter and rotates a `Vector3f` object by that amount,
	 * returning the resultant vector.
	 * 
	 * @param rotation 4D quaternion of the rotation to be applied to the current vector,
	 * which is multiplied with the current vector's components to produce the new vector.
	 * 
	 * The `rotation` argument is of type `Quaternion`, which represents a 4D mathematical
	 * object used to describe rotations in 3D space. The `conjugate` property returns
	 * the conjugate of the quaternion, which is another quaternion that represents the
	 * inverse of the original quaternion.
	 * 
	 * The multiplication of `rotation` with its conjugate (`w`) results in a new quaternion
	 * `w` that represents the rotation of the input vector relative to the origin of the
	 * quaternion. The resulting quaternion `w` is then converted into a 3D vector `new
	 * Vector3f(w.getX(), w.getY(), w.getZ())`, which represents the rotated vector.
	 * 
	 * @returns a vector representing the rotated position of the original vector.
	 * 
	 * 	- The output is a `Vector3f` object representing the rotated position of the
	 * original input vector.
	 * 	- The x, y, and z components of the output represent the rotated position in the
	 * x, y, and z directions, respectively.
	 * 	- These components are derived from the multiplication of the rotation quaternion
	 * with the original input vector followed by the conjugate of the rotation quaternion.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * interpolates a vector between two given values, using a linear interpolation
	 * formula. The resulting vector is the sum of the intermediate vectors multiplied
	 * by the interpolation factor and added to the initial vector.
	 * 
	 * @param dest 3D vector that the current vector is being interpolated towards.
	 * 
	 * 	- `dest` is a `Vector3f` instance representing the destination point in 3D space.
	 * 	- It has three components: `x`, `y`, and `z`, each representing the coordinate
	 * value of the destination point.
	 * 	- The `lerpFactor` argument is a floating-point number representing the interpolation
	 * factor between the current point and the destination point.
	 * 
	 * @param lerpFactor 0-1 value used to interpolate between the current position of
	 * the object and its destination position.
	 * 
	 * @returns a vector that interpolates between two given vectors.
	 * 
	 * The `Vector3f` object that is generated represents a point in 3D space with x, y,
	 * and z components.
	 * 
	 * The `dest` parameter represents the target position towards which the current
	 * position is being interpolated.
	 * 
	 * The `lerpFactor` parameter represents the interpolation factor used to blend the
	 * current position with the target position.
	 * 
	 * When the `lerpFactor` is applied to the current position, it results in a new
	 * position that is a weighted combination of the current position and the target position.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the sum
	 * of the values of the current instance and the input `r`.
	 * 
	 * @param r 3D vector to be added to the current vector, resulting in a new 3D vector
	 * output.
	 * 
	 * The `Vector3f` class has three fields (`x`, `y`, and `z`) that represent the
	 * coordinates of a 3D vector in float format. Therefore, `r` is a `Vector3f` object
	 * with specific values assigned to its fields (`x`, `y`, and `z`).
	 * 
	 * @returns a new vector instance with the sum of the input vectors' x, y, and z components.
	 * 
	 * 	- The output is a `Vector3f` object that represents the sum of the input vectors.
	 * 	- The x-component of the output is equal to the sum of the x-components of the
	 * input vectors.
	 * 	- The y-component of the output is equal to the sum of the y-components of the
	 * input vectors.
	 * 	- The z-component of the output is equal to the sum of the z-components of the
	 * input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the component-wise sum of a vector to the corresponding components of the object.
	 * 
	 * @param r 3D vector that adds its components to the corresponding components of the
	 * current object, resulting in an updated position for the object.
	 * 
	 * 	- `x`, `y`, and `z` represent the components of the vector in the 3D space, respectively.
	 * 	- The vector is a class representing a 3D point in the mathematical representation,
	 * which is used to perform mathematical operations on it.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * adds a scalar value to the components of a `Vector3f` object, returning a new
	 * `Vector3f` instance with the modified components.
	 * 
	 * @param r 3D vector to be added to the current vector's components.
	 * 
	 * @returns a new `Vector3f` instance with the sum of the original vector's components
	 * and the given scalar value.
	 * 
	 * 	- The `Vector3f` object returned by the function has an x-component that is equal
	 * to the sum of the original vector's x-component and the input value r.
	 * 	- The y-component of the returned vector is equal to the sum of the original
	 * vector's y-component and the input value r.
	 * 	- The z-component of the returned vector is equal to the sum of the original
	 * vector's z-component and the input value r.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * adds a scaled version of a `Vector3f` object to the current vector, scaling the
	 * components of the original vector by the specified float value.
	 * 
	 * @param v 3D vector to be scaled and added to the current vector.
	 * 
	 * 	- `v`: A `Vector3f` object that contains the vector to be added with a scale factor.
	 * 	- `scale`: A float value representing the scaling factor applied to the `v` vector.
	 * 
	 * @param scale scalar value by which the provided `Vector3f` instance is multiplied
	 * before being added to the current vector instance.
	 * 
	 * @returns a new vector that is the result of adding the provided vector scaled by
	 * the given factor to the original vector.
	 * 
	 * The `addScaledVector` function takes two arguments - `v` and `scale`. It returns
	 * a new `Vector3f` object that represents the sum of the original input vector
	 * (`this`) multiplied by the given scale factor. The resulting vector has the same
	 * components as the original input vector, but with the magnitude increased by the
	 * specified scale factor.
	 * 
	 * The returned vector has the following properties:
	 * 
	 * 	- Magnitude: The magnitude of the returned vector is equal to the product of the
	 * magnitudes of the original input vector and the scale factor.
	 * 	- Direction: The direction of the returned vector is the same as that of the
	 * original input vector, unchanged by the scaling operation.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a `Vector3f` object by a scalar value and adds it to the current vector
	 * of the class, scaling the vector's components by the provided scale factor.
	 * 
	 * @param v 3D vector that is being scaled, and it is multiplied by the `scale`
	 * parameter before being added to the current vector.
	 * 
	 * 	- `v`: A Vector3f object representing a 3D vector with x, y, and z components.
	 * 	- `scale`: A scalar value indicating the scaling factor to be applied to the vector.
	 * 
	 * @param scale 3D vector multiplication factor applied to the `Vector3f` object
	 * passed as an argument to the function, which results in the scaling of the vector's
	 * components.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * computes the vector difference between two `Vector3f` objects, returning a new
	 * `Vector3f` object representing the difference.
	 * 
	 * @param r 3D vector that the method will subtract from the original vector.
	 * 
	 * The `Vector3f` class represents a 3D vector with floating-point values. The instance
	 * variable `x`, `y`, and `z` represent the coordinates of the vector in the x, y,
	 * and z directions, respectively.
	 * 
	 * @returns a new vector representing the difference between the input vector and the
	 * given reference vector.
	 * 
	 * 	- The Vector3f object represents the difference between the current vector and
	 * the reference vector in terms of its x, y, and z components.
	 * 	- Each component of the returned vector is calculated by subtracting the corresponding
	 * component of the reference vector.
	 * 	- The resulting vector has the same units as the original vectors.
	 * 	- The properties of the returned vector are independent of the reference vector's
	 * magnitude or direction.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * calculates the vector difference between the input vector and the reference vector.
	 * 
	 * @param r 3D vector offset to be subtracted from the current position of the
	 * `Vector3f` object, resulting in the new position after subtraction.
	 * 
	 * @returns a new vector with the difference of the input value `r` from the original
	 * vector's coordinates.
	 * 
	 * The `Vector3f` object returned by the function represents a point in 3D space,
	 * with x, y, and z components representing the differences between the original
	 * position and the subtraction of r from it.
	 * 
	 * The value of the x component represents the difference between the original x
	 * position and the subtraction of r from it.
	 * 
	 * The value of the y component represents the difference between the original y
	 * position and the subtraction of r from it.
	 * 
	 * The value of the z component represents the difference between the original z
	 * position and the subtraction of r from it.
	 * 
	 * In summary, the `sub` function returns a new `Vector3f` object that represents the
	 * difference between the original position and the subtraction of r from it.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` instance with the
	 * product of the component values of the input vector and the corresponding components
	 * of the current vector.
	 * 
	 * @param r 3D vector to multiply with the current vector.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector in question.
	 * 	- `getX()`, `getY()`, and `getZ()` are methods that retrieve the values of these
	 * components, respectively.
	 * 
	 * @returns a new vector with the product of the input vectors' coordinates.
	 * 
	 * 	- The output is a new Vector3f object that represents the multiplication of the
	 * input vectors x, y, and z with their corresponding components in the r vector.
	 * 	- The return type of the function is a Vector3f object, which means that it can
	 * hold 3D vector values.
	 * 	- The function modifies the original input variables by assigning the result of
	 * the multiplication to them.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies its input vector by a scalar value and returns the result as a new vector.
	 * 
	 * @param r scalar value used to multiply each component of the `Vector3f` instance
	 * being passed in.
	 * 
	 * @returns a vector with the product of the component values and the input scalar
	 * value `r`.
	 * 
	 * The `Vector3f` object that is returned has three elements, each representing the
	 * product of the corresponding component of the input vector and the scalar value `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * divides its input vector by a given reference vector, returning a new vector with
	 * the result.
	 * 
	 * @param r scalar value that divides the vector `x`, `y`, and `z`.
	 * 
	 * `x`, `y`, and `z` are the components of the vector, which represent the magnitude
	 * or length of the vector in the corresponding directions. These components are used
	 * to calculate the resultant vector after division with another vector.
	 * 
	 * @returns a vector with the same x, y, and z components as the input vector, scaled
	 * by the reciprocal of the input value.
	 * 
	 * 	- The output is a new `Vector3f` instance with elements scaled by the reciprocals
	 * of the corresponding elements in the input vector `r`.
	 * 	- The scaling is done element-wise, meaning that each element of the output is
	 * calculated as the input element multiplied by the reciprocal of its corresponding
	 * element in `r`.
	 * 	- The resultant vector has the same magnitude as the input vector, but its direction
	 * is inverted, meaning that it points in the opposite direction of the input vector.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a scalar value `r` and returns a vector with its components divided by `r`.
	 * 
	 * @param r scalar value used to perform the division operation on the instance
	 * variables of the `Vector3f` class.
	 * 
	 * @returns a vector with the same x, y, and z components scaled by the given factor
	 * `r`.
	 * 
	 * The output is a `Vector3f` object, which represents a 3D vector with x, y, and z
	 * components. The values of these components are calculated by dividing the corresponding
	 * variables of the input vector by the input parameter `r`.
	 * 
	 * The returned vector has the same direction as the input vector, but its magnitude
	 * is reduced by a factor of `r`. This means that the output vector represents a
	 * scaled-down version of the original vector.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * computes the absolute value of a vector, returning a new vector with the absolute
	 * values of its components.
	 * 
	 * @returns a new `Vector3f` instance representing the absolute value of the original
	 * vector's components.
	 * 
	 * The output is a `Vector3f` object, representing a 3D vector with magnitude equal
	 * to the absolute value of the input vector's coordinates.
	 * The coordinate values are in the range (-∞, +∞), where each component represents
	 * the magnitude of the corresponding component of the input vector.
	 * The resulting vector has the same orientation as the input vector, i.e., its
	 * direction is unchanged.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * returns a string representation of a given object, including its three component
	 * parts: `x`, `y`, and `z`.
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three numbers
	 * separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object containing the x and y coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the x and y coordinates of the point.
	 * 
	 * 	- The `Vector2f` object returned represents a 2D point in homogeneous coordinates,
	 * with x and y components representing the horizontal and vertical positions of the
	 * point, respectively.
	 * 	- The `x` and `y` components of the vector have real-valued attributes that
	 * represent the absolute values of the corresponding position coordinates.
	 * 	- As a result, the `getXY` function returns a valid 2D point with x and y components
	 * that can be used in various contexts within the code.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object representing the y and z coordinates of a point.
	 * 
	 * @returns a `Vector2f` object representing the y- and z-coordinates of a point.
	 * 
	 * The `Vector2f` object returned represents a 2D point in homogeneous coordinates
	 * with two components, `y` and `z`, which represent the Y and Z coordinates, respectively.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` object representing the component values of z and x.
	 * 
	 * @returns a vector object of type `Vector2f`, containing the values of `z` and `x`.
	 * 
	 * 	- `z`: The z-coordinate of the point represented by the Vector2f object. It is a
	 * double value that represents the vertical component of the point in the xy plane.
	 * 	- `x`: The x-coordinate of the point represented by the Vector2f object. It is
	 * also a double value that represents the horizontal component of the point in the
	 * xy plane.
	 * 
	 * These values can be used to represent any point in the 2D space, with the z-coordinate
	 * representing the height and the x and y coordinates representing the position on
	 * the xy plane.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a `Vector2f` object containing the `y` and `x` coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the `y` and `x` coordinates of a point.
	 * 
	 * The output is a `Vector2f` object, which represents a 2D point with two elements
	 * - `y` and `x`. The values of these elements correspond to the x-coordinate and
	 * y-coordinate of the point, respectively.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a `Vector2f` object representing the z-coordinate and y-coordinate of a point.
	 * 
	 * @returns a `Vector2f` object representing the component values of z and y.
	 * 
	 * 	- z: The y-coordinate value of the vector.
	 * 	- y: The x-coordinate value of the vector.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a `Vector2f` object representing the x and z coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the x and z coordinates of a point.
	 * 
	 * 	- `x`: The first component of the vector represents the x-coordinate of the point.
	 * It is a double value that ranges from -1 to 1.
	 * 	- `z`: The second component of the vector represents the z-coordinate of the
	 * point. It is a double value that ranges from -1 to 1.
	 * 
	 * The output of the function is a `Vector2f` object, which is a two-dimensional
	 * vector in the homogeneous coordinates format. This means that the x and z components
	 * are represented as separate values, rather than being combined into a single value
	 * as in other coordinate systems.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * updates the components of a `Vector3f` object with the specified values, returning
	 * the modified object for further manipulation.
	 * 
	 * @param x 3D position of the vector along the x-axis.
	 * 
	 * @param y 2D position of the vector in the Y-axis.
	 * 
	 * @param z 3rd component of the `Vector3f` object, which is being set to the provided
	 * value.
	 * 
	 * @returns a reference to the modified `Vector3f` object.
	 * 
	 * The `Vector3f` object is modified to have its `x`, `y`, and `z` components set to
	 * the input values `x`, `y`, and `z`, respectively.
	 * 
	 * After modification, the returned object is a reference to the same instance of
	 * `Vector3f`. This means that any modifications made to the returned object will
	 * affect the original object as well.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the component values of this vector to the corresponding values of the provided
	 * reference object.
	 * 
	 * @param r 3D vector to set the values of the `Vector3f` object.
	 * 
	 * The function takes in a `Vector3f` object `r`, which represents a 3D vector with
	 * three components: `x`, `y`, and `z`. Each component is a floating-point number
	 * that represents the magnitude and direction of the vector. The function sets each
	 * component of the receiver vector to the corresponding component of the input vector,
	 * updating the properties of the receiver object.
	 * 
	 * @returns a reference to the original `Vector3f` instance, unchanged.
	 * 
	 * 	- `Vector3f r`: This is the input parameter passed to the function, which represents
	 * a 3D vector in homogeneous coordinates.
	 * 	- `set(r.getX(), r.getY(), r.getZ())`: This line of code sets the components of
	 * the returned output vector to the corresponding values of the input vector `r`.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns the value of the `x` field.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the object's `x` field to the provided float argument.
	 * 
	 * @param x float value to be assigned to the instance variable `x` of the class on
	 * which the `setX()` method is defined.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a `float` variable.
	 * 
	 * @returns a floating-point value representing the y-coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the provided float value.
	 * 
	 * @param y new value of the `y` field in the object being modified by the `setY()`
	 * method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the variable `z`.
	 * 
	 * @returns a floating-point value representing the z component of an object's position.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the `z` field of an object to the provided float value.
	 * 
	 * @param z 3D coordinate of the object in the x, y, and z dimensions, and its value
	 * is assigned to the `z` field of the class instance.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares the `x`, `y`, and `z` components of two `Vector3f` objects and returns
	 * `true` if they are equal, otherwise `false`.
	 * 
	 * @param r 3D vector to which the current vector is compared for equality.
	 * 
	 * 	- `x`: The first component of the `Vector3f` object, which corresponds to the
	 * x-coordinate of the vector.
	 * 	- `y`: The second component of the `Vector3f` object, which corresponds to the
	 * y-coordinate of the vector.
	 * 	- `z`: The third component of the `Vector3f` object, which corresponds to the
	 * z-coordinate of the vector.
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
