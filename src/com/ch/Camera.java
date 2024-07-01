package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that serves as a base for other classes in the Camera package.
 * It provides an abstract method called `getAsMatrix4()` which returns a Matrix4f
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
	 * Retrieves a Matrix4f object representing the view projection matrix, which combines
	 * the view and projection transformations. If the matrix has not been previously
	 * calculated or updated, it will be recalculated on demand.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates the view matrix by multiplying a rotation matrix, a translation matrix,
	 * and a projection matrix.
	 * 
	 * @returns a matrix representing the view transformation of a 3D scene from a camera's
	 * perspective, taking into account both rotation and translation of the camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a 4x4 matrix representing a translation from the origin, calculated by
	 * multiplying the position of the camera by -1 and then translating it using the
	 * `initTranslation` method of the `Matrix4f` class.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera relative to its
	 * initial position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a `Transform` object representing the current transformation state of an
	 * application.
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
	 * It provides an abstract method called `getAsMatrix4()` which returns a Matrix4f
	 * object, but does not provide any implementation details. The class does not contain
	 * any fields or methods of its own and is intended to be extended by other classes
	 * in the package.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
