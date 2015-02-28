package com.github.hatimiti.gamix.base.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public abstract class PropertyLoader {

	private final Properties conf;

	public PropertyLoader(final String fileName) {
		this.conf = new Properties();
		try {
			this.conf.load(this.getClass()
					.getClassLoader().getResourceAsStream(fileName + ".properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getString(final String key, final Object... params) {
		return MessageFormat.format(this.conf.getProperty(key), params);
	}

	public int getInt(final String key) {
		return Integer.parseInt(getString(key));
	}

	public long getLong(final String key) {
		return Long.parseLong(getString(key));
	}

	public boolean getBool(final String key) {
		return Boolean.parseBoolean(getString(key));
	}

}
