package com.github.emre1101.gpsc.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author emreg
 */
public class NPMUtil {

	private static NPMUtil _instance = null;

	private static final Logger logger = LoggerFactory.getLogger(NPMUtil.class);

	private static final String CONFIG_FILE = "config/npm/npm-config.properties";
	private static final String CONFIG_IS_NULL_OR_ZERO_LENGTH = " is null or is not defined! Please check config file: ";

	private static String destFolder;
	private static String cmd;
	public static String url;

	public static void main(String[] args) {
		NPMUtil.getInstance().run();
	}

	private NPMUtil() {
		loadConfig();
	}

	public static NPMUtil getInstance() {

		if (_instance == null) {
			synchronized (NPMUtil.class) {
				if (_instance == null) {
					_instance = new NPMUtil();
				}
			}
		}
		return _instance;
	}

	private void loadConfig() {

		final String configFile = CONFIG_FILE;
		final Properties configEntries = new Properties();
		InputStream inputStream;

		try {
			inputStream = new FileInputStream(configFile);
			configEntries.load(inputStream);

			destFolder = configEntries.getProperty("destFolder");
			if (destFolder == null || destFolder.length() == 0) {
				final String errorMsg = "destFolder" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
				logger.error(errorMsg);
				throw new IllegalArgumentException(errorMsg);
			}

			cmd = configEntries.getProperty("cmd");
			if (cmd == null || cmd.length() == 0) {
				final String errorMsg = "cmd" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
				logger.error(errorMsg);
				throw new IllegalArgumentException(errorMsg);
			}
			url = configEntries.getProperty("url");
			if (url== null || url.length() == 0) {
				final String errorMsg = "url" + CONFIG_IS_NULL_OR_ZERO_LENGTH + configFile;
				logger.error(errorMsg);
				throw new IllegalArgumentException(errorMsg);
			}

		} catch (IOException e) {
			final String errorMsg = "Failed to load config: " + configFile;
			throw new IllegalStateException(errorMsg, e);
		}
	}

	static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

	static void run() {
		logger.info("The npm project directory:" + destFolder);
		File jsFile = new File(destFolder);

		try {
			Process process = new ProcessBuilder(cmd, "start").directory(jsFile).start();
			process.waitFor();
			TimeUnit.SECONDS.sleep(5);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getUrl(){
		return url;
	}

}
