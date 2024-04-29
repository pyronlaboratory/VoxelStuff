package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java package that provides functions for measuring time and updating frames
 * per second (FPS). The class has several methods for calculating and storing time,
 * FPS, and delta (the difference between the current time and the previous frame).
 * These include `getTimeS()`, `init()`, `calculateDelta()`, `updateFPS()`, `getDelta()`,
 * `getFPS()`, and `update()`.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * multiplies the current system time by 1000 and divides the result by the timer
	 * resolution to return a long value representing the elapsed time in milliseconds
	 * since the function was created.
	 * 
	 * @returns a long value representing the current time in milliseconds, adjusted for
	 * timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * initializes a variable `lastFPS` with the current time.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time difference between two points, represented by `time` and
	 * `lastFrame`, respectively. It returns the time difference as a float value.
	 * 
	 * @returns a floating-point number representing the time difference between two frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * updates the frames per second (FPS) metric by incrementing the current FPS and
	 * resetting a timer when a threshold is met.
	 */
	private static void updateFPS() {
		if (getTimeS() - lastFPS > 1000) {
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	/**
	 * retrieves the value of the `delta` field, which is used to calculate the distance
	 * traveled by an object.
	 * 
	 * @returns a floating-point value representing the difference between two values.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * returns the current frame rate of a program in floating-point format.
	 * 
	 * @returns the current frame rate of the application.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a field named `time`.
     * 
     * @returns a floating-point representation of the current system time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates the FPS, calculates and limits the delta time, and increments the time variable.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
