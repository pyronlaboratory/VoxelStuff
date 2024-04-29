package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * is a Java class that represents a quaternion as a mathematical object. It has
 * several methods for calculating and manipulating quaternions, including setting
 * the values of its components, retrieving the values of its components, and comparing
 * two quaternions for equality. The class also provides a few instance variables for
 * storing the values of its components.
 */
public class Quaternion {

	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion() {
		this(0, 0, 0, 0);
	}

	
	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Quaternion(Vector3f axis, float angle) {
		float sinHalfAngle = (float) Math.sin(angle / 2);
		float cosHalfAngle = (float) Math.cos(angle / 2);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}

	/**
	 * calculates the magnitude or length of a 3D vector by squaring its components and
	 * taking the square root.
	 * 
	 * @returns the square root of the sum of the squares of the component values of a vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing each component by its length, resulting
	 * in a new quaternion with a length of 1.
	 * 
	 * @returns a normalized quaternion representation of the original quaternion.
	 * 
	 * The output is a Quaternion object, which represents a rotational transformation
	 * in 3D space.
	 * The first component of the Quaternion (w / length) represents the direction of the
	 * rotation.
	 * The second and third components (x / length and y / length) represent the magnitude
	 * of the rotation.
	 * The fourth component (z / length) represents the axis of rotation.
	 * All the components are normalized, meaning they have a length of 1 in the range
	 * of -1 to 1.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * calculates the conjugate of a quaternion, which is a quaternion with its coordinates
	 * reversed.
	 * 
	 * @returns a new quaternion object with the conjugate of its input values.
	 * 
	 * 	- The returned object is of type `Quaternion`, indicating that it represents a
	 * quaternion value.
	 * 	- The `w` field of the returned object contains the magnitude (or length) of the
	 * quaternion.
	 * 	- The `-x`, `-y`, and `-z` fields contain the coordinates of the quaternion, which
	 * are negated in the returned object relative to their original values in the input
	 * quaternion.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value and returns the result as another quaternion.
	 * 
	 * @param r scalar value that is multiplied with the quaternion's components to produce
	 * the result quaternion.
	 * 
	 * @returns a new quaternion instance with the product of the input `r` multiplied
	 * by each component of the original quaternion.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * multiplies two quaternions element-wise, computing the product of their real and
	 * imaginary parts.
	 * 
	 * @param r 4D quaternion to be multiplied with the current quaternion, resulting in
	 * a new quaternion output.
	 * 
	 * 	- `w`: The magnitude (or length) of the quaternion.
	 * 	- `x`, `y`, and `z`: The coordinates of the quaternion.
	 * 
	 * @returns a new quaternion instance with the product of two given quaternions.
	 * 
	 * 	- The output is a new instance of the `Quaternion` class.
	 * 	- The `w_`, `x_,`, `y_`, and `z_` fields of the output represent the product of
	 * the `w`, `x`, `y`, and `z` fields of the input `r` Quaternion.
	 * 	- The order of the multiplication is (w, x, y, z) * (r.w, r.x, r.y, r.z).
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * multiplies a quaternion by a vector and returns the result as a new quaternion.
	 * 
	 * @param r 3D vector to be multiplied with the quaternion.
	 * 
	 * 	- `r` is an instance of `Vector3f`, which represents a 3D vector with three
	 * components (x, y, and z).
	 * 	- `getX()` and `getY()` are methods that retrieve the x and y coordinates of the
	 * vector, respectively.
	 * 	- `getZ()` is a method that retrieves the z coordinate of the vector.
	 * 
	 * The function then computes the product of the quaternion with `r` using the formula:
	 * `w_ = -x * r.getX() - y * r.getY() - z * r.getZ();`. The other three components
	 * (x_, y_, and z_) are computed as follows: `x_ = w * r.getX() + y * r.getZ() - z *
	 * r.getY();`, `y_ = w * r.getY() + x * r.getX() - z * r.getZ();`, and `z_ = w *
	 * r.getZ() + x * r.getY() - y * r.getX()`.
	 * 
	 * Finally, the function returns a new instance of `Quaternion` with the computed
	 * values of w_, x_, y_, and z_.
	 * 
	 * @returns a Quaternion object representing the result of multiplying the input
	 * vector `r` by the quaternion.
	 * 
	 * The output is a `Quaternion` instance, which represents a rotation matrix in 4D space.
	 * The `w_` attribute of the output contains the magnitude (or length) of the quaternion.
	 * The `x_, y_, and z_` attributes represent the three axes of the rotation, which
	 * can be used to compute the rotation matrix using the following equations:
	 * 
	 * 	- x_ represents the axis of rotation around the x-axis
	 * 	- y_ represents the axis of rotation around the y-axis
	 * 	- z_ represents the axis of rotation around the z-axis
	 * 
	 * These axes can be used to create a 3D rotation matrix by multiplying them with
	 * their corresponding dot products.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a `Quaternion` argument `r` and returns a new `Quaternion` instance with the
	 * difference between the original quaternion's values and the given `r`.
	 * 
	 * @param r 4D vector to be subtracted from the current 4D vector, resulting in a new
	 * 4D vector that represents the difference between the two vectors.
	 * 
	 * 	- `w`: The real part of the quaternion.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion.
	 * 
	 * @returns a new Quaternion object representing the difference between the current
	 * Quaternion and the provided Quaternion.
	 * 
	 * 	- The output is a new `Quaternion` object with the difference between the input
	 * `w`, `x`, `y`, and `z` components.
	 * 	- The resulting quaternion represents the difference between the original and
	 * input quaternions.
	 * 	- The output has the same dimension as the input, meaning it is also a 4-dimensional
	 * vector.
	 * 	- The order of the output is the opposite of the input, meaning that if the input
	 * quaternion is represented as (w, x, y, z), then the output of the `sub` function
	 * will be represented as (-w, -x, -y, -z).
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * with the sum of its components.
	 * 
	 * @param r quaternion to be added to the current quaternion, and its value is used
	 * to compute the resultant quaternion.
	 * 
	 * 	- `r`: A Quaternion object representing the second quaternion to be added. It has
	 * four attributes - `W`, `X`, `Y`, and `Z` - which correspond to the real and imaginary
	 * parts of the quaternion, respectively.
	 * 
	 * @returns a new Quaternion object representing the sum of the input quaternions.
	 * 
	 * 	- The new quaternion is constructed by adding the scalar values of the input
	 * quaternions (w, x, y, and z) element-wise.
	 * 	- The resulting quaternion represents the sum of the two input quaternions.
	 * 	- The quaternion components (w, x, y, and z) are non-negative and sum to 1,
	 * ensuring that the quaternion is properly normalized.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * generates a rotation matrix based on three vector inputs representing the x, y,
	 * and z axes of a 3D space. The resulting matrix is used to represent a rotation in
	 * a 3D space.
	 * 
	 * @returns a 4x4 rotation matrix.
	 * 
	 * 	- The `Matrix4f` object is initialized with the forward, up, and right vectors
	 * as input.
	 * 	- These vectors represent the rotation axis and direction in 3D space.
	 * 	- The rotation matrix is returned in a 4x4 format, which represents the orientation
	 * of the rotation in homogeneous coordinates.
	 * 	- Each element of the matrix represents the dot product of the corresponding
	 * vector in the input with the rotation axis.
	 * 	- The resulting matrix can be used to perform rotations in 3D space, such as
	 * rotating an object around a specific axis or aligning multiple objects relative
	 * to each other.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of two Quaternions, returning a floating-point value
	 * between -1 and 1.
	 * 
	 * @param r 4D quaternion to be multiplied with the `x`, `y`, `z`, and `w` components
	 * of the current quaternion, resulting in the dot product of the two quaternions.
	 * 
	 * 	- `x`, `y`, `z`, and `w` are float values representing the components of the quaternion.
	 * 	- `getX()`, `getY()`, `getZ()`, and `getW()` are methods that return these component
	 * values for further processing in the function body.
	 * 
	 * @returns a float value representing the dot product of two quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions using the linear
	 * interpolation formula. The function takes into account the shortest path and returns
	 * the interpolated quaternion normalized to have a length of 1.
	 * 
	 * @param dest 4-element quaternion value that will be lerped towards the provided
	 * target value.
	 * 
	 * 	- `Quaternion dest`: The destination quaternion to which the interpolation will
	 * be applied.
	 * 	- `lerpFactor`: A float value representing the interpolation factor between the
	 * start and end quaternions.
	 * 	- `shortest`: An boolean value indicating whether the shortest path should be
	 * taken (true) or not (false). If true, the function will correct the destination
	 * quaternion if its dot product with the input quaternion is negative.
	 * 
	 * @param lerpFactor 0-1 value that determines how much the destination quaternion
	 * is linearly interpolated from the source quaternion during the nearest-neighbor search.
	 * 
	 * @param shortest 4-vector of the shortest quaternion that will be returned if it
	 * is set to `true`, otherwise, the normalized quaternion will be returned regardless
	 * of its length.
	 * 
	 * @returns a normalized quaternion that represents the linear interpolation of the
	 * given quaternion based on the provided factor.
	 * 
	 * 	- The output is a Quaternion object, representing a linear interpolation between
	 * the input `dest` and the current value of the `this` parameter.
	 * 	- If the `shortest` parameter is set to `true`, and the dot product of the `this`
	 * parameter and the `dest` parameter is less than zero, the output is inverted to
	 * ensure that the interpolation points towards the origin.
	 * 	- The output is normalized to have a length of one.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a linear interpolation between two Quaternion values based on the input
	 * lerp factor and a boolean flag for shortest path.
	 * 
	 * @param dest 4-component quaternion that the returned quaternion will be blended
	 * with, based on the specified `lerpFactor`.
	 * 
	 * 	- `dest.getW()` represents the scalar component of the quaternion.
	 * 	- `dest.getX()`, `dest.getY()`, and `dest.getZ()` represent the vector components
	 * of the quaternion.
	 * 
	 * The `correctedDest` variable is created to handle the case where the dot product
	 * between the current quaternion and the destination quaternion is negative, which
	 * indicates that the quaternion needs to be inverted before interpolating.
	 * 
	 * @param lerpFactor 0-1 value that determines how much the quaternion should be
	 * linearly interpolated towards the destination quaternion, with values closer to 0
	 * resulting in slower interpolation and values closer to 1 resulting in faster interpolation.
	 * 
	 * @param shortest shortest quaternion path between the source and destination, which
	 * is computed by negating the destination quaternion when the cosine of the dot
	 * product between the two quaternions is less than or equal to 1, and then using the
	 * `NLerp()` function to interpolate the quaternion.
	 * 
	 * @returns a Quaternion representation of the linear interpolation between two given
	 * Quaternions, based on the specified lerp factor and shortest path setting.
	 * 
	 * 	- The output is a Quaternion object, representing a rotational transformation.
	 * 	- The quaternion is constructed by multiplying the input source and destination
	 * quaternions with scaling factors, and then adding the resulting vectors.
	 * 	- The scaling factors are calculated using the sine of the angle between the
	 * source and destination quaternions, and the length of the source quaternion.
	 * 	- If `shortest` is set to true, the quaternion is corrected to ensure that the
	 * result is always a rotation towards the destination quaternion, regardless of the
	 * orientation of the source quaternion. This is done by negating the source quaternion
	 * if the dot product of the source and destination quaternions is negative.
	 * 	- The output quaternion represents a linear interpolation between the source and
	 * destination quaternions, based on the `lerpFactor` parameter.
	 */
	public Quaternion SLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		final float EPSILON = 1e3f;

		float cos = this.dot(dest);
		Quaternion correctedDest = dest;

		if (shortest && cos < 0) {
			cos = -cos;
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
			return NLerp(correctedDest, lerpFactor, false);

		float sin = (float) Math.sqrt(1.0f - cos * cos);
		float angle = (float) Math.atan2(sin, cos);
		float invSin = 1.0f / sin;

		float srcFactor = (float) Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float) Math.sin((lerpFactor) * angle) * invSin;

		return this.mul(srcFactor).add(correctedDest.mul(destFactor));
	}

	// From Ken Shoemake's "Quaternion Calculus and Fast Animation" article
	public Quaternion(Matrix4f rot) {
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0) {
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else {
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25f * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25f * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25f * s;
			}
		}

		float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis to produce a forward-facing vector.
	 * 
	 * @returns a vector representing the forward direction of the rotated object.
	 * 
	 * The return value is a `Vector3f` object, indicating that it has three components
	 * - x, y, and z. The values of these components are determined by multiplying the
	 * corresponding components of the original vector by a scalar value of 0, 0, and 1,
	 * respectively. This results in a vector that points in the direction of the original
	 * vector but with its magnitude reduced to 1.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a
	 * vector pointing towards the negative z-axis.
	 * 
	 * @returns a rotated vector with a magnitude of -1 in the opposite direction of the
	 * original vector.
	 * 
	 * 	- The output is a vector with three elements, representing the position of an
	 * object in 3D space.
	 * 	- The first element represents the x-coordinate of the position, the second element
	 * represents the y-coordinate, and the third element represents the z-coordinate.
	 * 	- The vector is created by rotating the original vector (0, 0, -1) around the
	 * center of the object using the `rotate` method. This results in a vector that
	 * points in the opposite direction of the object's facing.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis, resulting in a new vector pointing
	 * upwards from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of 1 and an orientation of upwards.
	 * 
	 * 	- The output is a `Vector3f` object representing the up direction in 3D space.
	 * 	- The vector has an x-component of 0, a y-component of 1, and a z-component of
	 * 0, indicating that it points directly upwards in the 3D space.
	 * 	- The rotation is applied to the input vector using the `rotate` method, which
	 * creates a new vector by rotating the original vector around the specified axis.
	 * In this case, the axis is the same as the direction of the output vector.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a
	 * vector pointing downwards from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of 0 and a direction that points downward
	 * from the original vector's position.
	 * 
	 * The output is a `Vector3f` object representing the downward direction from the
	 * current position.
	 * It is created by applying a rotation to the original position using the `rotate`
	 * method.
	 * The rotation is performed based on the position of the current object, which means
	 * that the resulting vector points in the opposite direction of the current object's
	 * movement.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a new
	 * vector that points towards the right direction.
	 * 
	 * @returns a rotation of the original vector by 90 degrees around the x-axis.
	 * 
	 * 	- The output is a new `Vector3f` object that represents the rightward orientation
	 * of the current vector.
	 * 	- The rotation is performed by taking the original vector and applying a rotate
	 * transformation using the `rotate` method, which takes no arguments.
	 * 	- The resulting vector has a magnitude of 1 and a direction that is 90 degrees
	 * counter-clockwise from the original vector's direction.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees counterclockwise around the x-axis, resulting in a
	 * new vector that points left from the original one.
	 * 
	 * @returns a rotated version of the original vector, with its x-component changed
	 * to -1.
	 * 
	 * 	- The return type is `Vector3f`, indicating that it is a 3D vector with floating-point
	 * values.
	 * 	- The expression `-1, 0, 0` represents the coordinates of the leftmost point in
	 * the original vector.
	 * 	- The `rotate` method is applied to the original vector to produce the rotated vector.
	 * 
	 * The properties of the output depend on the type of rotation performed by the
	 * `rotate` method. In this case, it is a clockwise rotation around the origin,
	 * resulting in a vector that points in the negative z-direction.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the values of a Quaternion object's `x`, `y`, `z`, and `w` fields to the
	 * specified arguments.
	 * 
	 * @param x 3D position of the quaternion along the x-axis.
	 * 
	 * @param y 2D component of the quaternion.
	 * 
	 * @param z 3rd component of the quaternion, which is updated to match the provided
	 * value.
	 * 
	 * @param w 4th component of the quaternion, which is used to rotate the object along
	 * the `z` axis.
	 * 
	 * @returns a new instance of the `Quaternion` class with the updated values of `x`,
	 * `y`, `z`, and `w`.
	 * 
	 * The `Quaternion` object is updated with the provided values for `x`, `y`, `z`, and
	 * `w`. These values are assigned to the corresponding fields of the object.
	 * 
	 * As a result, the returned `Quaternion` object has the updated values of its fields,
	 * which can be used to represent the rotation more accurately. The returned object
	 * is the same as the original one, indicating that the method is idempotent.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * takes a `Vector3f` object representing Euler angles and returns a Quaternion object
	 * representing the corresponding rotation transformation.
	 * 
	 * @param eulerAngles 3D Euler angles of a rotation, which are used to calculate the
	 * quaternion representation of the rotation.
	 * 
	 * 	- `phi`: The Euler angle representing the rotation around the x-axis.
	 * 	- `theta`: The Euler angle representing the rotation around the y-axis.
	 * 	- `yota`: The Euler angle representing the rotation around the z-axis.
	 * 
	 * The function then stores the necessary cosines and sins of these angles locally
	 * to avoid recalculating them, before constructing the quaternion output.
	 * 
	 * @returns a quaternion representation of the input Euler angles.
	 * 
	 * 	- `q0`, `q1`, `q2`, and `q3` are the four components of the quaternion representation
	 * of the rotation angle. These values range from -1 to 1 and can be interpreted as
	 * the x, y, z, and w components of a quaternion, respectively.
	 * 	- The quaternion is represented in the form `q = q0 + q1*i + q2*j + q3*k`, where
	 * `i`, `j`, and `k` are the imaginary units that satisfy the quaternion algebra rules.
	 * 	- The quaternion is normalized to have a length of 1, ensuring that it represents
	 * a valid rotation angle in 3D space.
	 * 	- The function returns a new Quaternion object containing the resulting quaternion
	 * representation of the input Euler angles.
	 */
	public static Quaternion fromEuler(Vector3f eulerAngles) {
		//eulerAngles = [phi, theta, yota]
		float phi = eulerAngles.getX();
		float theta = eulerAngles.getY();
		float yota = eulerAngles.getZ();


		//locally store all cos/sin so we don't have to calculate them twice each
		float cos_half_phi = (float) Math.cos(phi / 2.0f);
		float sin_half_phi = (float) Math.sin(phi / 2.0f);
		float cos_half_theta = (float) Math.cos(theta / 2.0f);
		float sin_half_theta = (float) Math.sin(theta / 2.0f);
		float cos_half_yota = (float) Math.cos(yota / 2.0f);
		float sin_half_yota = (float) Math.sin(yota / 2.0f);

		float q0 = cos_half_phi * cos_half_theta * cos_half_yota + sin_half_phi * sin_half_theta * sin_half_yota;
		float q1 = sin_half_phi * cos_half_theta * cos_half_yota - cos_half_phi * sin_half_theta * sin_half_yota;
		float q2 = cos_half_phi * sin_half_theta * cos_half_yota + sin_half_phi * cos_half_theta * sin_half_yota;
		float q3 = cos_half_phi * cos_half_theta * sin_half_yota - sin_half_phi * sin_half_theta * cos_half_yota;

		return new Quaternion(q0, q1, q2, q3);

	}

	/**
	 * sets the quaternion components of the provided `Quaternion` object to the corresponding
	 * values of the given quaternion.
	 * 
	 * @param r 4-element vector that contains the new values to be set for the quaternion's
	 * components.
	 * 
	 * Quaternion `r`: This is the input quaternion that represents a rotation. It has
	 * four components: `x`, `y`, `z`, and `w`. Each component corresponds to a real
	 * number that represents a value between 0 and 1, inclusive.
	 * 
	 * @returns a reference to the same `Quaternion` object, unchanged.
	 * 
	 * 	- The `set` function takes four arguments, each representing a component of the
	 * input quaternion.
	 * 	- The function updates the corresponding components of this quaternion using the
	 * given values.
	 * 	- After updating the components, the function returns this quaternion as its output.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
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
	 * sets the value of the `x` field of the object on which it is called, to the argument
	 * passed as a float.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class object.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the variable `y`.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field in the object to which it belongs.
	 * 
	 * @param y 2D coordinate's Y value that is being assigned to the `y` field of the
	 * object instance upon invocation of the `setY()` method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * returns the value of the `z` field.
	 * 
	 * @returns a floating-point value representing the z component of an object's position.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the member variable `z` to the input float parameter.
	 * 
	 * @param z 3D position of an object or entity, and by assigning a new value to it
	 * within the function, the object's position is updated.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * returns the `w` field's value.
	 * 
	 * @returns a float value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the value of the field `w` of the current object to the argument passed as a
	 * float value.
	 * 
	 * @param w float value that sets the `w` field of the current object.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares two Quaternion objects by checking for exact matching of all component values.
	 * 
	 * @param r 4D vector that is being compared to the current 4D vector for equality.
	 * 
	 * `x`, `y`, `z`, and `w` represent the respective components of the quaternion.
	 * 
	 * @returns a boolean value indicating whether two quaternions are equal.
	 * 
	 * 1/ The method returns a boolean value indicating whether the specified `Quaternion`
	 * instance is equal to the current instance.
	 * 2/ The comparison is based on the values of the x, y, z, and w components of both
	 * instances.
	 * 3/ If any of these components differ, the method returns false.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
