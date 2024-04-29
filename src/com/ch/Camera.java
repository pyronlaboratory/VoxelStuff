package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as transforming positions and rotations. It also has an abstract
 * method for calculating the projection matrix and another for adjusting to a specified
 * viewport size.
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
	 * computes and returns a `Matrix4f` object representing the view projection
	 * transformation, which combines the camera's view matrix and projection matrix to
	 * produce the final output image.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- The `viewProjectionMat4` variable is a matrix object that represents the view
	 * projection transformation.
	 * 	- It is initialized to `null` if no transformation has been applied or if the
	 * `transform` field has changed since the last calculation.
	 * 	- The `calculateViewMatrix()` method is called to compute the view matrix when
	 * the `viewProjectionMat4` is null or has changed.
	 * 
	 * The `viewProjectionMat4` object contains information about the view and projection
	 * transformations, including the position, orientation, and scale of the camera
	 * relative to the world coordinates. This information can be used in various
	 * applications such as rendering 3D graphics, performing physics simulations, or
	 * creating machine learning models.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix for a 3D camera based on its rotation and translation.
	 * The function takes the camera's rotational transformation and translation as input
	 * and returns the resulting view matrix.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a 3D camera.
	 * 
	 * 	- The output is a 4x4 matrix, representing the view matrix.
	 * 	- The first three columns represent the rotation of the camera relative to the
	 * world axis, while the fourth column represents the translation of the camera along
	 * the z-axis.
	 * 	- The matrix is constructed by multiplying the rotation matrix (`cameraRotation`)
	 * with the translation matrix (`cameraTranslation`).
	 * 	- The resulting matrix (`viewProjectionMat4`) combines the effects of both the
	 * view and projection transformations, providing a unified representation of the
	 * camera's position and orientation in 3D space.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a translation matrix representing the camera's position relative to its
	 * initial position.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix that represents the negative of
	 * the current position of the transform.
	 * 
	 * The matrix is a 4x4 transformation matrix that represents a translation in 3D space.
	 * The elements of the matrix represent the x, y, and z coordinates of the translation
	 * vector in homogeneous form. Specifically, the first three columns represent the
	 * translation in the x, y, and z directions, respectively, while the last column
	 * represents the 1.0 value that indicates a non-scaled translation.
	 * The matrix is returned as an instance of the `Matrix4f` class, which provides
	 * methods for multiplying the matrix by other matrices or vectors, as well as accessing
	 * its individual elements.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which is a crucial part of the program's functionality.
	 * 
	 * @returns a reference to an instance of the `Transform` class.
	 * 
	 * 	- The `transform` variable returns an instance of the `Transform` class, which
	 * represents a transformation matrix.
	 * 	- The `transform` field has several attributes, including `a`, `b`, `c`, and `d`,
	 * each representing a component of the transformation matrix.
	 * 	- These components can take on any valid value within the range of -1 to 1,
	 * indicating the amount of stretching or shrinking to apply to the corresponding axis.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base for other classes in the Camera package.
	 * It provides an abstract method called `getAsMatrix4()` which returns a Matrix4f
	 * object, but does not provide any implementation details. The class also does not
	 * contain any fields or methods of its own, and is intended to be extended by other
	 * classes in the package.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
