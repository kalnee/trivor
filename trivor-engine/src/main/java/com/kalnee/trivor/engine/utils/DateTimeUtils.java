package com.kalnee.trivor.engine.utils;

import java.util.Objects;

public final class DateTimeUtils {

	private DateTimeUtils() {
	}

	/**
	 * @param time Pattern hh:mm or hh:mm:ss
	 *
	 * @return minutes as integer
	 */
	public static Integer minutes(String time) {
		Objects.nonNull(time);

		if (!time.contains(":")) {
			throw new IllegalArgumentException("time must follow the pattern hh:mm");
		}

		String[] tokens = time.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		return (60 * hours) + minutes;
	}
}
