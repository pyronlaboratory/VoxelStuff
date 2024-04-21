package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * is a representation of a transformable object in a 3D space, with properties for
 * position (pos), rotation (rot), and scale (scale). It also has methods for updating
 * its state based on changes to the parent transform, rotating around a given axis,
 * and looking at a specified point. Additionally, it provides a transformation matrix
 * that can be used to render the object in 3D space.
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
	 * updates an object's position, rotation, and scale based on the current values and
	 * stores the previous values for future use.
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
	 * rotates a vector representing a 3D object by an angle around a specified axis,
	 * resulting in a new rotated vector.
	 * 
	 * @param axis 3D rotational axis around which the rotation will occur.
	 * 
	 * 	- `axis` is a `Vector3f` representing a 3D vector.
	 * 	- It has three components: x, y, and z, which correspond to the axis of rotation.
	 * 	- The value of each component can range from -1 to 1, indicating the magnitude
	 * of the rotation around that axis.
	 * 	- The orientation of the axis is unchanged during the rotation process.
	 * 
	 * @param angle 3D rotation angle around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes and returns a rotation matrix that aligns a provided `point` vector with
	 * a `up` vector, relative to a reference frame.
	 * 
	 * @param point 3D position that the entity should look at.
	 * 
	 * 	- `point`: A `Vector3f` object representing a 3D point in space. It has three
	 * attributes: `x`, `y`, and `z`, which represent the coordinates of the point in the
	 * x, y, and z axes, respectively.
	 * 
	 * @param up 3D direction towards which the camera should look when rotating its
	 * orientation to face the specified `point`.
	 * 
	 * 	- `up` is a `Vector3f` object representing an upward direction.
	 * 	- It has three components: `x`, `y`, and `z`, which represent the coordinates of
	 * the upward direction in the 3D space.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a quaternion representing the rotation needed to look at a given point
	 * from a specified up direction.
	 * 
	 * @param point 3D position that the look-at rotation is based on.
	 * 
	 * 	- `point`: A 3D vector representing a point in space, with x, y, and z components.
	 * 	- `up`: A 3D vector representing a direction perpendicular to the plane of the
	 * point, with x, y, and z components.
	 * 
	 * @param up 3D direction of the look-at axis, which is used to compute the rotation
	 * quaternion that looks at the specified point from the current position.
	 * 
	 * 	- `point`: A Vector3f object representing the point in 3D space where the camera
	 * is looking.
	 * 	- `up`: A Vector3f object representing the up direction in 3D space, which is
	 * used to determine the rotation of the camera.
	 * 
	 * @returns a quaternion representing the rotation required to look at a given point
	 * from a specified up direction.
	 * 
	 * 	- The return value is a `Quaternion` object that represents the rotation from the
	 * camera's current position to look at a point in 3D space.
	 * 	- The quaternion is generated using the rotation matrix computed by multiplying
	 * the `Matrix4f` class's `initRotation` method with the input vectors representing
	 * the point and up direction.
	 * 	- The resulting quaternion represents the rotation around the camera's center,
	 * with the look-at point as the origin of the rotation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * checks if any of the object's properties have changed. It compares the current
	 * values of `parent`, `pos`, `rot`, and `scale` to their previous values, returning
	 * `true` if any have changed and `false` otherwise.
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
	 * computes a transformation matrix by combining a translation, rotation, and scaling
	 * matrix, and then multiplies it with the parent matrix.
	 * 
	 * @returns a transformation matrix that combines a translation, rotation, and scaling
	 * operation.
	 * 
	 * The output is a `Matrix4f` object, representing a 4x4 homogeneous transformation
	 * matrix.
	 * The matrix is created by multiplying the parent matrix (representing the overall
	 * transformation), with the translation, rotation, and scale matrices in that order.
	 * The translation matrix represents the offset of the object in 3D space, while the
	 * rotation matrix represents the orientation of the object around its center. The
	 * scale matrix represents the size and shape of the object in 3D space.
	 * By multiplying these matrices together, the `getTransformation` function generates
	 * a transformation matrix that can be used to transform 3D points, vectors, or other
	 * objects in the scene.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * returns the transformation matrix of its parent node in a hierarchical tree
	 * structure, based on the `parent` field and the `hasChanged()` method.
	 * 
	 * @returns a Matrix4f object representing the parent transformation matrix.
	 * 
	 * 	- `parentMatrix`: A Matrix4f object representing the parent transformation matrix.
	 * This matrix contains the transformation from the parent's local coordinate system
	 * to the world coordinate system.
	 * 	- `hasChanged()`: A boolean method that checks if the parent's transformation has
	 * changed since the last call to this function. If `hasChanged()` returns true, then
	 * the `parentMatrix` is updated with the latest transformation.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the parent transform of an object, allowing for hierarchical manipulation of
	 * transformations.
	 * 
	 * @param parent transformation to which the current transformation will be added as
	 * a child transformation.
	 * 
	 * 	- `parent`: It is a transform object that represents the parent object for this
	 * object.
	 * 
	 * Note: The response is limited to 4 sentences and does not include any personal
	 * statements or first-person language, as requested.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * transforms a `Vector3f` object using the matrix provided by the `getParentMatrix`
	 * function, returning the transformed position.
	 * 
	 * @returns a transformed position vector.
	 * 
	 * The Vector3f object returned by this method represents the transformed position
	 * of an object in a 3D space, taking into account the transformation matrix provided
	 * by the parent matrix.
	 * 
	 * The vector's components represent the x, y, and z positions of the transformed
	 * position in the local coordinate system of the parent matrix.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot`, returning the transformed rotation.
	 * 
	 * @returns a transformed quaternion representing the composition of the rotation
	 * represented by `rot` and the rotation of the parent object.
	 * 
	 * 1/ The `Quaternion` object represents the transformed rotation from the parent
	 * rotation to the current rotation.
	 * 2/ The first component of the Quaternion represents the angle of rotation around
	 * the x-axis, while the second and third components represent the angle of rotation
	 * around the y- and z-axes, respectively.
	 * 3/ The fourth component is set to 0, indicating that the rotation is around the
	 * origin (0, 0, 0).
	 * 4/ The Quaternion object is created by multiplying the parent rotation with the
	 * current rotation, as denoted by the `mul` method. This operation combines the
	 * rotations element-wise, resulting in a new rotation matrix.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns a reference to a `Vector3f` object representing the position of an entity.
	 * 
	 * @returns a reference to a `Vector3f` object containing the position of the entity.
	 * 
	 * 	- `pos`: A `Vector3f` object that represents the position of the entity in 3D
	 * space. It has three components: x, y, and z, which correspond to the position of
	 * the entity along the x, y, and z axes, respectively.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a specified value, where the position is represented
	 * as a Vector3f object.
	 * 
	 * @param pos 3D position of an object or entity that the function is called on, and
	 * it assigns that position to the `pos` field of the function's caller.
	 * 
	 * 	- `this.pos`: The current position of the object is assigned to the member variable
	 * `pos`.
	 * 	- `Vector3f`: The data type of the `pos` field.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position of an object, updating its new position based on the
	 * addition.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `Vector3f`: Represents a 3D vector in homogeneous coordinates.
	 * 	- `setPos`: A setter method that modifies the position component of the current
	 * object instance.
	 * 	- `getPos`: An getter method that returns the current position component of the
	 * object instance.
	 * 	- `add`: The `add` method takes another vector as input and adds its components
	 * to the existing components of the current vector, resulting in a new vector with
	 * the updated components.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a `Quaternion` object representing the rotation of the game object.
	 * 
	 * 	- The `rot` field is a Quaternion object that represents the rotation of the game
	 * object.
	 * 	- It contains the rotation values in a specific format that can be used to apply
	 * the rotation to an object in a 3D space.
	 * 	- The Quaternion class has several properties, such as `x`, `y`, `z`, and `w`,
	 * which represent the real and imaginary parts of the quaternion.
	 * 	- These properties can be accessed and modified through the use of method calls,
	 * allowing for precise control over the rotation values.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotation of an object represented by the `rotation` parameter to the
	 * object itself.
	 * 
	 * @param rotation 4D quaternion value that updates the rotation of the object.
	 * 
	 * 	- `Quaternion rotation`: This is an object of type `Quaternion`, which represents
	 * a 4D vector that can be used to represent rotations in 3D space. It has several
	 * attributes, including `x`, `y`, `z`, and `w`, which correspond to the four components
	 * of the quaternion.
	 * 	- `this.rot`: This refers to the current value of the `rot` field within the
	 * context of the function. The field is a `Quaternion` object that represents the
	 * rotation of the object being manipulated by the function.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns the current value of the `scale` field, which represents a vector of three
	 * floating-point numbers that represent the object's size in the x, y, and z directions.
	 * 
	 * @returns a `Vector3f` object containing the scale value.
	 * 
	 * 	- `scale`: A `Vector3f` object representing the scale of the game object.
	 * 	+ It has three components: x, y, and z, which represent the scale along the x,
	 * y, and z axes, respectively.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scaling factor for an object, updating its `scale` field with the provided
	 * value.
	 * 
	 * @param scale 3D scaling factor for the object, which is applied to its position,
	 * size, and orientation.
	 * 
	 * 	- `this.scale = scale;` sets the scale factor for this object.
	 * 	- `scale` is a `Vector3f` instance that holds the scale values for each dimension
	 * (x, y, and z).
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string.
	 * 
	 * @returns an empty string.
	 * 
	 * 	- The function returns a string value empty of any content.
	 * 	- The return type is specified as String, indicating that the function will always
	 * return a string value.
	 * 	- The function name 'toString' suggests that it is designed to provide a concise
	 * representation of its input, typically for debugging or serialization purposes.
	 */
	@Override
	public String toString() { return "";
	}

}
