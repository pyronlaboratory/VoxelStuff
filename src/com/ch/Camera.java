package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides various methods for manipulating camera
 * transformations in a 3D environment. It has several attributes and methods, including:
 * 
 * 	- `projection`: a Matrix4f object representing the view projection transformation
 * 	- `getTransformation()`: returns an instance of the `Transform` class, which
 * represents a transformation matrix
 * 	- `calculateViewMatrix()`: generates a translation matrix representing the camera's
 * position relative to its initial position
 * 	- `calculateProjectionMatrix()`: returns a Matrix4f object representing the
 * projection transformation
 * 	- `adjustToViewport()`: adjusts the camera's position and orientation to fit
 * within a specified viewport size
 * 
 * The class also defines an abstract method `getAsMatrix4()` for extending classes
 * to provide their own implementation of this method.
 */
public abstract class Camera {

	protected Matrix4f projection;
	protected Matrix4f viewProjectionMat4;
	protected CameraStruct values;
	protected Transform transform;

	protected Camera(Matrix4f projection) {
		this.projection = projection;
		transform = new Transform();
	}

	/**
	 * retrieves a matrix that represents the view projection transformation, which
	 * combines the view and projection transformations. It calculates the matrix if it's
	 * null or if the transform has changed, and returns it.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- `viewProjectionMat4`: A 4x4 matrix object representing the view projection
	 * transformation. This transformation combines the view and projection matrices to
	 * produce a final matrix that represents the view of the 3D scene from a particular
	 * camera location in 3D space.
	 * 	- `transform`: An instance variable indicating whether the view projection matrix
	 * has changed since the last call to `calculateViewMatrix()`. If `transform` is true,
	 * then the view projection matrix needs to be recomputed.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates the view matrix for a given camera transformation and translation. It
	 * first computes the rotation and translation matrices, then multiplies them using
	 * the projection matrix to obtain the final view matrix.
	 * 
	 * @returns a 4x4 matrix representing the view projection transformation.
	 * 
	 * 	- `viewProjectionMat4`: This is a 4x4 matrix representing the view and projection
	 * transformations combined. The elements of this matrix can be used to compute the
	 * final transformed position of any point in the scene.
	 * 	- `cameraRotation`: This is a 3x3 rotation matrix representing the rotation of
	 * the camera about its center of rotation. It can be used to transform points in 3D
	 * space using the rotation matrix multiplication operation.
	 * 	- `cameraTranslation`: This is a 3x1 vector representing the translation of the
	 * camera relative to its center of rotation. It can be used to move the camera's
	 * position in 3D space.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a translation matrix that moves the origin of a 3D transform by a specified
	 * distance along the x, y, and z axes.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix representing the translation of
	 * the camera position by (-1, -1, -1).
	 * 
	 * 	- The Matrix4f object returned represents a 4x4 transformation matrix that includes
	 * a translation component in its fourth row.
	 * 	- The translation vector is represented by the three elements in the fourth row
	 * of the matrix, which correspond to the x, y, and z components of the translation.
	 * 	- The other elements of the matrix represent the transformation of the camera
	 * position in 3D space based on the transform property of the `transform` variable.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves a transformation object, which can be used to apply various transformations
	 * to data.
	 * 
	 * @returns a reference to an object of type `Transform`.
	 * 
	 * 	- The transform object is returned as an instance of the `Transform` class.
	 * 	- The `transform` field contains the actual transformation, which is a complex
	 * data structure that represents the mapping between the input and output of the function.
	 * 	- The transformation can be modified by assigning a new `Transform` object to the
	 * `transform` field.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base for other classes in the Camera package.
	 * It provides an abstract method called `getAsMatrix4()` which returns a Matrix4f
	 * object, but does not provide any implementation details. The class does not contain
	 * any fields or methods of its own and is intended to be extended by other classes
	 * in the package.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
