package com.ch;

import org.lwjgl.Sys;

/**
 * is a Java package that provides functions for measuring time and updating frames
 * per second (FPS). The class has several methods for calculating and storing time,
 * FPS, and delta (the difference between the current time and the previous frame).
 * The update() method updates the FPS and calculates the delta, while the getDelta(),
 * getFPS(), and getTime() methods provide access to these values.
 */
public class Timer {

	private static float fps;
	private static long lastFPS;
	private static long lastFrame;
	public static float delta;
	public static float currentFPS;
    public static float time;

	/**
	 * calculates the current time in milliseconds using the `Sys.getTime()` and
	 * `Sys.getTimerResolution()` methods, and returns the result as a long value.
	 * 
	 * @returns a long value representing the current time in milliseconds, calculated
	 * by multiplying the current system time in seconds by 1000 and dividing it by the
	 * timer resolution.
	 */
	private static long getTimeS() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * initializes a variable `lastFPS` with the current time value, captured using the
	 * `getTimeS()` method.
	 */
	public static void init() {
		lastFPS = getTimeS();
	}

	/**
	 * calculates the time elapsed between two frames, represented by `time` and `lastFrame`,
	 * respectively. It returns the elapsed time as a float value.
	 * 
	 * @returns a floating-point value representing the time difference between two frames.
	 */
	private static float calculateDelta() {
		long time = getTimeS();
		float delta = (int) (time - lastFrame);
		lastFrame = getTimeS();
		return delta;
	}

	/**
	 * updates the frames per second (FPS) metric by incrementing the current FPS value
	 * and resetting a timer every 1000 milliseconds.
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
	 * returns the `delta` value, which is a static instance variable containing the
	 * difference between two values.
	 * 
	 * @returns a floating-point value representing the delta.
	 */
	public static float getDelta() {
		return delta;
	}

	/**
	 * returns the current frame rate as a floating-point value.
	 * 
	 * @returns the current frame rate of the application in floating-point format.
	 */
	public static float getFPS() {
		return currentFPS;
	}

    /**
     * returns the value of a `time` field, which is likely used to store the current
     * time or date information.
     * 
     * @returns a floating-point representation of the current time.
     */
    public static float getTime() {
        return time;
    }

    /**
     * updates frame rate, calculates delta time, and adds it to a variable `time`.
     */
    public static void update() {
        updateFPS();
        delta = ((calculateDelta() / 1000));
        delta = delta < 0 || delta > 1 ? 0 : delta;
        time += delta;
    }

}
