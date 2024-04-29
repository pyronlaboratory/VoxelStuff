package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * is a Java class that represents a 3D transformable game object. It has several
 * fields and methods to manage the object's position, rotation, and scale. The class
 * provides getters and setters for each of these properties, as well as additional
 * methods for adding vectors to the position, rotating the object around a specified
 * axis, and scaling the object by a specified factor.
 */
public class Transform {

	private Transform parent;
	private Matrix4f parentMatrix;

	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;

	private Vector3f oldPos;
	private Quaternion oldRot;
	private Vector3f oldScale;

	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(1, 0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		
		oldPos = new Vector3f(0, 0, 0);
		oldRot = new Quaternion(1, 0, 0, 0);
		oldScale = new Vector3f(1, 1, 1);

		parentMatrix = new Matrix4f().initIdentity();
	}

	/**
	 * updates the values of the `pos`, `rot`, and `scale` fields of an object based on
	 * the current values of those fields and any changes made to them since the last update.
	 */
	public void update() {
		if (oldPos != null) {
			if (!oldPos.equals(pos))
				oldPos.set(pos);
			if (!oldRot.equals(rot))
				oldRot.set(rot);
			if (!oldScale.equals(scale))
				oldScale.set(scale);
		} else {
			oldPos = new Vector3f().set(pos);
			oldRot = new Quaternion().set(rot);
			oldScale = new Vector3f().set(scale);
		}
	}

	/**
	 * rotates a vector by an angle around a specified axis, resulting in a normalized
	 * quaternion representation of the rotation.
	 * 
	 * @param axis 3D vector that defines the rotation axis for the transformation.
	 * 
	 * 	- `axis`: A `Vector3f` object representing the rotation axis. It has three
	 * components: x, y, and z, which represent the coordinates of the rotation axis in
	 * 3D space.
	 * 	- `angle`: An `float` value representing the angle of rotation around the `axis`.
	 * 
	 * @param angle 3D rotation angle about the specified `axis` direction.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes the rotation required to face a given point while maintaining a fixed
	 * orientation with respect to the up vector.
	 * 
	 * @param point 3D position that the object should look at.
	 * 
	 * 	- `point`: A 3D vector representing the point to look at.
	 * 	- `up`: A 3D vector representing the direction of the "up" axis in the local
	 * coordinate system.
	 * 
	 * @param up 3D direction that the look-at rotation should be applied to, relative
	 * to the current orientation of the entity.
	 * 
	 * 	- `up` is a vector with three elements representing the direction of the upward
	 * vector in 3D space.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a quaternion representation of the rotation from the point of interest
	 * (`point`) to the camera's position (`pos`) and then rotates it by the upward vector
	 * (`up`).
	 * 
	 * @param point 3D position from which to compute the look-at rotation.
	 * 
	 * 	- `point`: A `Vector3f` object representing the position in 3D space.
	 * 	- `up`: A `Vector3f` object representing the up direction in 3D space.
	 * 
	 * @param up 3D vector that defines the direction of the look-at rotation, which is
	 * used to calculate the rotation quaternion.
	 * 
	 * 	- `up` is a `Vector3f` object representing an arbitrary vector in 3D space.
	 * 	- `up` has three components (x, y, z) that define its direction in 3D space.
	 * 
	 * @returns a Quaternion representation of the rotation required to face the provided
	 * point while looking along the specified up direction.
	 * 
	 * 	- The `Quaternion` object represents a rotation that is applied to a vector, which
	 * in this case is the input point minus the position vector.
	 * 	- The `Matrix4f` object used for initialization contains the rotation matrix that
	 * corresponds to the angle and axis of the look-at rotation.
	 * 	- The `up` parameter provides the direction of the up vector in the world frame,
	 * which is used as a reference for the look-at rotation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * evaluates whether an object's properties have changed by comparing them to their
	 * previous values. If any property has changed, the function returns `true`. Otherwise,
	 * it returns `false`.
	 * 
	 * @returns a boolean value indicating whether any of the object's properties have changed.
	 */
	public boolean hasChanged() {
		if (parent != null && parent.hasChanged())
			return true;

		if (!pos.equals(oldPos))
			return true;

		if (!rot.equals(oldRot))
			return true;

		if (!scale.equals(oldScale))
			return true;

		return false;
	}

	/**
	 * computes a transformation matrix based on the position, rotation, and scale of an
	 * object, and returns it as a `Matrix4f` instance.
	 * 
	 * @returns a transformed matrix representing a combination of translation, rotation,
	 * and scaling.
	 * 
	 * The return value is a `Matrix4f` object representing a transformation matrix.
	 * It is obtained by multiplying the parent matrix (representing the overall
	 * transformation), with the translation, rotation, and scaling matrices in the correct
	 * order.
	 * The translation matrix represents the offset of the object from its initial position,
	 * while the rotation matrix represents the angle and axis of rotation around which
	 * the object is rotated.
	 * The scaling matrix represents the size and orientation of the object in the x, y,
	 * and z directions.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves the transformation matrix of its parent node if it has changed, otherwise
	 * returns the current matrix unchanged.
	 * 
	 * @returns a matrix representation of the parent transformation.
	 * 
	 * 	- `parentMatrix`: A `Matrix4f` object representing the transformation matrix of
	 * the parent node in the tree. If `parent` is null, the matrix will be `null`.
	 * 	- `hasChanged()`: A boolean method that indicates whether the matrix has changed
	 * since it was last used. This information can be used to optimize the calculation
	 * of the matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the parent transformation object for the current instance, storing the reference
	 * in the `parent` field.
	 * 
	 * @param parent Transform to which the current object's position and rotation will
	 * be set.
	 * 
	 * 	- `parent`: A reference to a transformed object, which serves as the parent for
	 * this transformed object.
	 * 	- Type: `Transform`
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * transforms a `Vector3f` object `pos` using the transformation matrix of its parent
	 * component, and returns the transformed position.
	 * 
	 * @returns a transformed position vector in the parent matrix's coordinate system.
	 * 
	 * The `Vector3f` object that is returned represents the transformed position of the
	 * game object. This transformation is applied by multiplying the original position
	 * vector with the matrix represented by the `getParentMatrix` method. Therefore, the
	 * transformed position reflects the changes made to the object's position due to its
	 * parenting relationship and any additional transformations applied through the matrix.
	 * The returned vector has three components: x, y, and z, which represent the transformed
	 * position in each dimension. Each component can take on any real number value within
	 * the range of the floating-point representation used by Java.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * computes the transformed rotation of a parent object based on its own rotation and
	 * the given `rot` parameter, using the quaternion multiplication operator.
	 * 
	 * @returns a transformed rotation quaternion.
	 * 
	 * 	- `Quaternion parentRotation`: This is the rotational transformation applied to
	 * the parent object, represented as a Quaternion.
	 * 	- `rot`: The rotational transformation applied to the parent object, also represented
	 * as a Quaternion.
	 * 	- `mul`: The multiplication operation performed on the `parentRotation` and `rot`
	 * Quaternions, resulting in the transformed rotation.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns the position of an object in a three-dimensional space as a `Vector3f` object.
	 * 
	 * @returns a `Vector3f` object representing the position of the game object.
	 * 
	 * 	- `pos`: A Vector3f object that contains the position of the entity in 3D space.
	 * 	+ It has three components: x, y, and z, which represent the entity's position in
	 * the x, y, and z axes, respectively.
	 * 	+ The values of these components are stored as floating-point numbers.
	 * 	- The Vector3f object is immutable, meaning that its properties cannot be changed
	 * once it is created.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a specified value.
	 * 
	 * @param pos 3D position of an object or entity, which is assigned to the `pos` field
	 * of the current instance.
	 * 
	 * 	- `this.pos`: This is a field in the current object instance that stores the
	 * position value. It has a type of `Vector3f`, which represents a 3D coordinate system.
	 * 	- `Vector3f`: This class represents a 3D vector and contains three properties:
	 * `x`, `y`, and `z`, each representing the component of the vector in the corresponding
	 * dimension.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * updates the position of an object by adding a vector to its current position.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `Vector3f`: This is the class representing a 3D vector in Java. It has three
	 * components - x, y, and z - which represent the position of the vector in the 3D space.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object containing the rotation matrix.
	 * 
	 * The function returns a Quaternion object representing the rotation of the object.
	 * The Quaternion class in Java is used to represent 3D rotations as a combination
	 * of real and imaginary parts. The return value has four components: x, y, z, and
	 * w, which correspond to the real and imaginary parts of the quaternion.
	 * 
	 * The Quaternion object has several methods for manipulating and transforming 3D
	 * rotations, such as multiply, conjugate, and normalize. These methods are useful
	 * for creating complex rotations by combining multiple simple rotations.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the Rotation value of the class instance variable "rot" to the provided
	 * Quaternion argument.
	 * 
	 * @param rotation 4D quaternion that represents the rotation of an object in 3D
	 * space, which is assigned to the `rot` field of the `java.lang.Object` class.
	 * 
	 * 	- `Quaternion`: The type of the rotation parameter, which represents a quaternion
	 * value representing a 3D rotation.
	 * 	- `this.rot`: The field being assigned to, which stores the rotation value for
	 * this object.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns the current scale value of the `Vector3f` object.
	 * 
	 * @returns a `Vector3f` object representing the scaling factor of the game object.
	 * 
	 * The `scale` variable returns a `Vector3f` object representing the scale factor for
	 * this GameObject. This vector includes three elements that represent the scaling
	 * values for the X, Y, and Z axes, respectively. The elements of the vector are
	 * floating-point numbers that range from 0 to 1, indicating the degree to which each
	 * axis should be scaled relative to its original value.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the `scale` field of its class instance, which represents a transformation
	 * matrix used in rendering 3D graphics. The provided vector `scale` is used to update
	 * the corresponding element of the transformation matrix.
	 * 
	 * @param scale 3D vector that defines the scaling factor for the object, which is
	 * then assigned to the `scale` field of the function's caller.
	 * 
	 * 	- The `scale` field is a `Vector3f` object, which represents a 3D vector in the
	 * game engine.
	 * 	- It contains the x, y, and z components of the vector, which can be accessed
	 * through the get methods `x`, `y`, and `z()`.
	 * 	- The `Vector3f` class provides various methods for manipulating the components
	 * of the vector, such as `add()`, `sub()`, `mul()`, and `div()`.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string, indicating that the object it is called on has no meaningful
	 * representation as a string.
	 * 
	 * @returns an empty string.
	 * 
	 * 1/ The output is an empty string, indicating that the method does not return any
	 * meaningful information about the object it is called on.
	 * 2/ The use of the empty string as the output suggests that the method may be
	 * intended for internal implementation purposes only, rather than providing a useful
	 * representation of the object.
	 */
	@Override
	public String toString() { return "";
	}

}
