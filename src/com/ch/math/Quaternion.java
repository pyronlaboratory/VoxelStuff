package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * is a mathematical utility class that provides functions to perform quaternion
 * arithmetic and conversion between various formats. It includes methods for calculating
 * quaternion multiplication, conjugation, normalization, rotation, and conversion
 * from Euler angles. Additionally, it provides getters and setters for the quaternion's
 * x, y, z, and w components.
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
	 * calculates the magnitude of a 3D vector by taking the square root of the sum of
	 * its x, y, and z components.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a 3D point.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing each component by its length, resulting
	 * in a new quaternion with a length of 1.
	 * 
	 * @returns a normalized quaternion representation of the original input quaternion.
	 * 
	 * The function returns a new Quaternion instance with the given input's length
	 * normalized. The length is calculated using the `length()` method and is used to
	 * scale the quaternion components. As a result, the quaternion is now in a unit form,
	 * making it more useful for various mathematical operations.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * generates a new quaternion object by providing the original quaternion's scalar
	 * component and the negative of each vector component.
	 * 
	 * @returns a new quaternion object with the conjugate of the input quaternion's coordinates.
	 * 
	 * 	- The output is a Quaternion object, representing the conjugate of the input Quaternion.
	 * 	- The conjugate of a Quaternion is defined as the vector that is obtained by
	 * multiplying the original Quaternion by its own conjugate.
	 * 	- The conjugate of a Quaternion has the same magnitude as the original Quaternion,
	 * but its direction is reversed. In other words, if the original Quaternion points
	 * in the direction of a certain axis, then the conjugate Quaternion points in the
	 * opposite direction of that axis.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value and returns a new quaternion with the product.
	 * 
	 * @param r scalar value that is multiplied with the quaternion's components to produce
	 * the resulting quaternion.
	 * 
	 * @returns a quaternion object representing the product of the original quaternion
	 * and the scalar value `r`.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * multiplies a quaternion by another quaternion, calculating the product of the two
	 * rotations and storing it in a new quaternion object.
	 * 
	 * @param r quaternion to be multiplied with the current quaternion, and its values
	 * are used to calculate the output quaternion's components.
	 * 
	 * 	- `w`: The real part of the quaternion representation.
	 * 	- `x`, `y`, `z`: The imaginary parts of the quaternion representation.
	 * 
	 * @returns a new quaternion instance representing the product of the input quaternions.
	 * 
	 * 	- `w_`: The magnitude (or length) of the resultant quaternion, which is equal to
	 * the product of the magnitudes of the input quaternions.
	 * 	- `x_`, `y_`, and `z_`: The three components of the resultant quaternion, which
	 * are obtained by multiplying the corresponding components of the input quaternions.
	 * These components represent the rotational part of the resultant quaternion.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * multiplies a quaternion by a vector, computing the resulting quaternion's coordinates
	 * in a straightforward manner.
	 * 
	 * @param r 3D vector that is multiplied with the quaternion.
	 * 
	 * 	- `r` is a `Vector3f` instance representing a 3D vector with real values for `x`,
	 * `y`, and `z`.
	 * 
	 * @returns a quaternion representing the result of multiplying the input vector `r`
	 * by the quaternion represented by the function parameters.
	 * 
	 * The `Quaternion` object returned has four components: w_, x_, y_, and z_. These
	 * components represent the product of the input quaternion and the vector passed as
	 * an argument.
	 * 
	 * The value of w_ is equal to the dot product of the input quaternion's w component
	 * and the argument vector's x, y, and z components, minus the product of the input
	 * quaternion's x, y, and z components and the argument vector's x, y, and z components.
	 * 
	 * The value of x_ is equal to the dot product of the input quaternion's x component
	 * and the argument vector's x, y, and z components, plus the product of the input
	 * quaternion's w component and the argument vector's y component, minus the product
	 * of the input quaternion's y component and the argument vector's x component.
	 * 
	 * The value of y_ is equal to the dot product of the input quaternion's y component
	 * and the argument vector's x, y, and z components, plus the product of the input
	 * quaternion's w component and the argument vector's z component, minus the product
	 * of the input quaternion's z component and the argument vector's y component.
	 * 
	 * The value of z_ is equal to the dot product of the input quaternion's z component
	 * and the argument vector's x, y, and z components, plus the product of the input
	 * quaternion's w component and the argument vector's x component, minus the product
	 * of the input quaternion's x component and the argument vector's y component.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the difference between the two quaternions.
	 * 
	 * @param r quaternion to be subtracted from the current quaternion, resulting in a
	 * new quaternion that represents the difference between the two.
	 * 
	 * 	- `w`: The real part of the quaternion.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion, representing the angle
	 * and direction of rotation around the x, y, and z axes, respectively.
	 * 
	 * @returns a new quaternion representing the difference between the input quaternion
	 * and the reference quaternion.
	 * 
	 * 	- The `Quaternion` object represents the difference between two quaternions, which
	 * means that it holds the residual value after subtracting the right-hand side
	 * quaternion from the left-hand side quaternion.
	 * 	- The values of the four components (w, x, y, and z) represent the residual values
	 * in each dimension.
	 * 	- The order of the quaternions is preserved in the returned object, meaning that
	 * the resulting quaternion has the same orientation as the original quaternion.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the sum of the two quaternions. The returned quaternion has the same
	 * components (w, x, y, and z) as the original quaternion plus the corresponding
	 * components of the input quaternion.
	 * 
	 * @param r quaternion to be added to the current quaternion.
	 * 
	 * The `Quaternion` class represents a quaternion in 3D space, which is a mathematical
	 * object that can be used to rotate points or vectors in 3D space. The instance
	 * variable `w` represents the real part of the quaternion, while `x`, `y`, and `z`
	 * represent the imaginary parts.
	 * 
	 * @returns a new quaternion with the sum of the inputs' coefficients.
	 * 
	 * 	- The output is a `Quaternion` instance representing the sum of the input arguments.
	 * 	- The `W`, `X`, `Y`, and `Z` components of the output are calculated by adding
	 * the corresponding components of the input arguments.
	 * 	- The output has the same orientation as the input, but with the sum of their values.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * takes a quaternion representation of a rotation and returns a corresponding 4x4
	 * homogeneous matrix.
	 * 
	 * @returns a 4x4 rotation matrix.
	 * 
	 * 	- The return type is `Matrix4f`, which represents a 4x4 homogeneous transformation
	 * matrix.
	 * 	- The method `initRotation` is called to create a rotation matrix from the forward,
	 * up, and right vectors.
	 * 	- The forward vector has a magnitude of 2.0f and directional components that are
	 * twice the corresponding elements of the input matrix.
	 * 	- The up vector has a magnitude of 1.0f and directional components that are equal
	 * to the corresponding elements of the input matrix minus twice the corresponding
	 * elements of the forward vector.
	 * 	- The right vector has a magnitude of 1.0f and directional components that are
	 * equal to the corresponding elements of the input matrix minus the corresponding
	 * elements of the up vector.
	 * 
	 * Overall, the `toRotationMatrix` function returns a rotation matrix that can be
	 * used to perform rotations in 3D space based on the input vectors.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of two quaternions, returning a scalar value.
	 * 
	 * @param r 4D quaternion value to which the `x`, `y`, `z`, and `w` components of the
	 * current quaternion are dot-producted, resulting in a scalar value.
	 * 
	 * 	- `x`, `y`, `z`, and `w` are the components of the Quaternion structure.
	 * 	- `getX()`, `getY()`, `getZ()`, and `getW()` are methods that return the corresponding
	 * component values of `r`.
	 * 
	 * @returns a floating-point number representing the dot product of the input quaternion
	 * and another quaternion argument.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a linear interpolation between two quaternions based on the given factor,
	 * and returns the result normalized to have length equal to the maximum value of the
	 * input quaternions.
	 * 
	 * @param dest 4-dimensional vector that the function will be lerped to, with its
	 * components being updated based on the provided `lerpFactor` and `shortest` parameters.
	 * 
	 * 	- `dest`: A Quaternion object representing the destination point in 3D space. It
	 * has four components: `w`, `x`, `y`, and `z`.
	 * 	- `lerpFactor`: A floating-point value representing the interpolation factor
	 * between the current position and the destination position.
	 * 	- `shortest`: An optional boolean value indicating whether to shorten the
	 * interpolated quaternion to avoid overshooting. If set to `true`, the function will
	 * adjust the destination quaternion if it would result in a negative dot product
	 * with the original quaternion.
	 * 
	 * @param lerpFactor 0-1 interpolating factor used to blend between the starting and
	 * ending quaternions.
	 * 
	 * @param shortest direction of rotation to be performed, and when set to `true`, it
	 * forces the function to return the shortest quaternion possible to achieve the
	 * desired rotation, even if it results in a non-normalized quaternion.
	 * 
	 * @returns a quaternion that represents the intermediate result of interpolating
	 * between two given quaternions using the linear interpolation formula.
	 * 
	 * 	- The output is a Quaternion object, representing a linear interpolation between
	 * the input `dest` and the inverse of the input multiplied by the given `lerpFactor`.
	 * 	- If the input `shortest` is set to true, then the output will be corrected to
	 * ensure that the resulting quaternion has a dot product with the input `dest` closer
	 * to zero. This is done by multiplying the input by its conjugate and subtracting
	 * it from the input.
	 * 	- The returned Quaternion object has four components representing the x, y, z,
	 * and w axes of the quaternion rotation.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions, weighted by a
	 * factor and shortest path if necessary. It first computes the dot product of the
	 * two quaternions and uses it to determine the interpolation direction and angle.
	 * Then it applies the interpolation using the quaternion multiplication and adds the
	 * destination quaternion to obtain the final result.
	 * 
	 * @param dest 4D quaternion value that is the result of the interpolation, and it
	 * is modified based on the `shortest` parameter to ensure the interpolated quaternion
	 * is properly oriented.
	 * 
	 * 	- `dest.getW()`: represents the scalar component of the quaternion along the z-axis.
	 * 	- `dest.getX()` and `dest.getY()`: represent the scalar components of the quaternion
	 * along the x and y axes, respectively.
	 * 	- `dest.getZ()`: represents the scalar component of the quaternion along the z-axis.
	 * 	- `correctedDest`: is a new quaternion instance created by adjusting the input
	 * `dest` if the shortest route criterion is met. Its properties are unchanged from
	 * `dest`.
	 * 
	 * @param lerpFactor 0-to-1 value that determines how much the quaternion should be
	 * interpolated between the source and destination values.
	 * 
	 * @param shortest shortest path between the two quaternions and when it is set to
	 * `true`, the function calculates the shortest path by reversing the quaternion if
	 * the cosine of the dot product is negative.
	 * 
	 * @returns a new Quaternion instance representing the linear interpolation of the
	 * original Quaternion based on the given lerp factor and shortest path calculation.
	 * 
	 * 	- The `Quaternion` object is constructed by multiplying the source quaternion by
	 * a factor and adding the destination quaternion.
	 * 	- The `srcFactor` and `destFactor` variables represent the interpolation factors
	 * between the source and destination quaternions, respectively. These factors are
	 * computed using the sine of the angle between the source and destination quaternions,
	 * scaled by the magnitude of the source quaternion.
	 * 	- The returned quaternion represents a linear combination of the source and
	 * destination quaternions, with the weights determined by the `lerpFactor` parameter.
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
	 * rotates a vector by 90 degrees around the x-axis to create a forward vector,
	 * returning a new Vector3f object.
	 * 
	 * @returns a vector pointing in the forward direction of the object's rotation.
	 * 
	 * The output is a `Vector3f` object representing the direction from the origin to
	 * the current position of the rotated object. The vector's coordinates represent the
	 * x, y, and z components of the forward direction, respectively.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a
	 * vector that points backwards from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of -1.
	 * 
	 * The `Vector3f` object returned by the function is a transformed version of the
	 * original vector, where it has been rotated by 90 degrees around the x-axis. This
	 * means that the x-coordinate of the vector remains unchanged, while the y-coordinate
	 * is shifted to the negative z-axis, and the z-coordinate is shifted to the positive
	 * y-axis.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis, resulting in a vector pointing
	 * upward from the original position.
	 * 
	 * @returns a rotated version of the original vector, pointing upwards.
	 * 
	 * The `Vector3f` object returned represents the upward direction from the current
	 * position of the entity. The rotation is performed using the `rotate` method, which
	 * takes the current position of the entity as its argument and returns a new `Vector3f`
	 * object in the rotated position.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a 3D vector by 90 degrees around the z-axis, resulting in a new vector
	 * pointing downwards from the original position.
	 * 
	 * @returns a rotated vector representing the downward direction.
	 * 
	 * The output is a `Vector3f` object representing the downward direction from the
	 * current position of the rotation axis.
	 * 
	 * It has a magnitude of 0 in all dimensions, indicating that it points directly
	 * downward from the origin.
	 * 
	 * Its orientation is identical to the orientation of the rotation axis at the time
	 * the function was called, as determined by the `rotate` method's rotation angle.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees clockwise to produce a new vector pointing rightward
	 * in the same coordinate system as the original vector.
	 * 
	 * @returns a rotated vector representing the right component of the object's position.
	 * 
	 * The output is a `Vector3f` object, which represents a 3D vector with three elements
	 * representing x, y, and z components. The value of each element is determined by
	 * multiplying the corresponding component of the original vector by a scalar value
	 * of 1.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees clockwise around the x-axis, resulting in a new
	 * vector that points left from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and a direction that is perpendicular
	 * to the original vector.
	 * 
	 * The Vector3f object returned is a rotated version of the original vector, with its
	 * x-axis component shifted to the left by a factor of -1.
	 * 
	 * The y-axis and z-axis components remain unchanged.
	 * 
	 * As a result, the new vector has a negative x-axis component, while the other two
	 * components remain positive.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the `x`, `y`, `z`, and `w` fields of a `Quaternion` object to the specified
	 * values, returning the modified object.
	 * 
	 * @param x 3D coordinate of the quaternion's axis of rotation.
	 * 
	 * @param y 2D component of the quaternion, which is multiplied with the original
	 * quaternion's `x` component to produce the new quaternion value.
	 * 
	 * @param z 3rd component of the quaternion, which is updated to match the value
	 * provided by the user.
	 * 
	 * @param w 4th component of the quaternion, which is used to rotate the object along
	 * the `z` axis.
	 * 
	 * @returns a new instance of the `Quaternion` class with the updated values of `x`,
	 * `y`, `z`, and `w`.
	 * 
	 * The `Quaternion` object is updated with the new values for `x`, `y`, `z`, and `w`.
	 * 
	 * After calling the `set` function, the resulting quaternion object retains its
	 * original values for `x`, `y`, `z`, and `w`.
	 * 
	 * The `set` function returns a reference to the same `Quaternion` object, allowing
	 * for chaining of method calls.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * @param eulerAngles - @see <a href="https://en.wikipedia.org/wiki/Euler_angles#Proper_Euler_angles">Wikipedia's Article on Euler Angles</a> for a description
	 *                    of their usage/definition.
	 * @return The {@link Quaternion} associated with the Euler angles.
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
	 * sets the values of a quaternion object to those of another quaternion object.
	 * 
	 * @param r 4-component quaternion that, when passed to the function, sets the
	 * corresponding components of the output quaternion.
	 * 
	 * 	- `getX()`, `getY()`, `getZ()`, and `getW()` - These are methods that retrieve
	 * the real-valued components of a Quaternion object.
	 * 
	 * @returns a reference to the same `Quaternion` object, with its fields updated with
	 * the provided values.
	 * 
	 * 	- The function sets the `x`, `y`, `z`, and `w` components of the `Quaternion`
	 * object to those of the provided `r` argument.
	 * 	- The function returns a reference to the modified `Quaternion` object, which is
	 * the same as the original object passed as an argument.
	 * 	- The function does not modify the original `Quaternion` object.
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
	 * sets the value of the `x` field of its object reference parameter to the passed
	 * float value.
	 * 
	 * @param x floating-point value that will be assigned to the `x` field of the class
	 * instance being modified by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the input `float` value.
	 * 
	 * @param y 3D position of an object in the `setY()` method, specifying its new value
	 * for storage within the class instance.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field.
	 * 
	 * @returns a floating-point value representing the z component of an object's position.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the field `z` in a class to the argument passed as a float.
	 * 
	 * @param z 2D coordinate of the current point in the graphical user interface (GUI)
	 * and sets its corresponding field `this.z` to match the value provided.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * retrieves the value of the `w` field, which is a `float` variable representing a
	 * width value.
	 * 
	 * @returns the value of the `w` field.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the value of the field `w` to the argument passed as a float.
	 * 
	 * @param w 2D width of an object being manipulated by the function.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares two `Quaternion` objects and returns a boolean indicating whether they
	 * are equal in terms of their x, y, z, and w components.
	 * 
	 * @param r 4D quaternion to be compared with the current 4D quaternion.
	 * 
	 * 	- `x`: A double variable representing the x-coordinate of the quaternion.
	 * 	- `y`: A double variable representing the y-coordinate of the quaternion.
	 * 	- `z`: A double variable representing the z-coordinate of the quaternion.
	 * 	- `w`: A double variable representing the w-coordinate of the quaternion.
	 * 
	 * @returns a boolean value indicating whether the Quaternion object is equal to
	 * another Quaternion object.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
