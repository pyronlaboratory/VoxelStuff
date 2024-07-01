package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that serves as a base for other classes in the Camera package.
 * It provides an abstract method called `getAsMatrix4()` that returns a Matrix4f
 * object, but does not provide any implementation details. The class also does not
 * contain any fields or methods of its own and is intended to be extended by other
 * classes in the package.
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
	 * Computes and returns a `Matrix4f` object representing the view-projection
	 * transformation, which combines the camera's view matrix and projection matrix. If
	 * the `viewProjectionMat4` variable is null or has changed since the last calculation,
	 * it recalculates the view matrix using the current transform state.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix that combines a rotation and translation of a camera, as
	 * represented by the `transform` and `getTranslationMatrix()` methods, using the
	 * `projection` matrix to produce the final view matrix.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a 4x4 matrix that represents a translation from the current transform
	 * position to a new position. The new position is computed by multiplying the transform
	 * position by -1 and then adding it to the original position.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix representing the camera's translation
	 * in 3D space.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a reference to an instance of the `Transform` class, which is used for
	 * geometric transformations such as rotation, scaling, and translation.
	 * 
	 * @returns the `transform` object itself.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other classes in the Camera package.
	 * It provides an abstract method called getAsMatrix4() which returns a Matrix4f
	 * object, but does not provide any implementation details.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
