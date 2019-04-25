package com.americanlistening.core;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.americanlistening.util.Rounder;

/**
 * Class for handling process crashes.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class CrashReport {

	private Throwable e;
	private Thread t;
	private Instance inst;

	/**
	 * Creates a new crash report object.
	 * 
	 * @param e
	 *            The exception that caused the crash.
	 * @param t
	 *            The thread from which the exception was thrown.
	 * @param inst
	 *            The current instance.
	 */
	public CrashReport(Throwable e, Thread t, Instance inst) {
		this.e = e;
		this.t = t;
		this.inst = inst;
	}

	/**
	 * Writes the crash report to an output stream.
	 * 
	 * @param outp
	 *            The stream to write to.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public void writeReport(OutputStream outp) throws IOException {
		PrintWriter out = new PrintWriter(outp);
		out.print(buildReport(t, e));
		out.close();
	}

	private String buildReport(Thread t, Throwable e) {
		StringBuilder build = new StringBuilder();
		build.append("The application has crashed!\n");
		build.append("Details are below:\n\n");

		build.append("- - - - - - - S Y S T E M - - - - - - -\n\n");

		build.append("Operating System:\n");
		build.append(getSection("System") + "\n\n");

		build.append("Memory:\n");
		build.append(getSection("Memory") + "\n\n");

		build.append("Monitor:\n");
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		build.append("Number of Devices: " + devices.length + "\n");
		build.append("Devices: \n");
		for (int i = 0; i < devices.length; i++) {
			build.append("Device " + (i + 1) + ":\n");
			build.append(getDeviceInfo(devices[i], "\t") + "\n");
		}
		build.append("\n");

		build.append("JRE Information:\n");
		build.append("java.home: " + System.getProperty("java.home") + "\n");
		build.append("java.library.path: " + System.getProperty("java.library.path") + "\n");
		build.append("java.ext.dirs: " + System.getProperty("java.ext.dirs") + "\n");
		build.append("java.version: " + System.getProperty("java.version") + "\n");
		build.append("java.runtime.version: " + System.getProperty("java.runtime.version") + "\n");
		build.append("java.class.version: " + System.getProperty("java.class.version") + "\n");
		build.append("java.vendor: " + System.getProperty("java.vendor") + "\n");
		build.append("java.vendor.url: " + System.getProperty("java.vendor.url") + "\n\n");

		build.append("User Information:\n");
		build.append("user.name: " + System.getProperty("user.name") + "\n");
		build.append("user.timezone: " + System.getProperty("user.timezone") + "\n");
		build.append("user.home: " + System.getProperty("user.home") + "\n");
		build.append("user.dir: " + System.getProperty("user.dir") + "\n\n");

		build.append("Version Information:\n");
		build.append("Instance Version: " + Version.getVersion() + "\n");

		build.append("\n");
		
		build.append("- - - - - - - P R O C E S S - - - - - - -\n\n");

		build.append("Information:\n");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		build.append("Time: " + dateFormat.format(date) + "\n\n");

		build.append("JVM Information:\n");
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> args = runtimeMXBean.getInputArguments();
		build.append("java.vm.vendor: " + System.getProperty("java.vm.vendor") + "\n");
		build.append("java.vm.specification.version: " + System.getProperty("java.vm.specification.version") + "\n");
		build.append("java.vm.specification.name: " + System.getProperty("java.vm.specification.name") + "\n");
		build.append("java.vm.info: " + System.getProperty("java.vm.info") + "\n");
		build.append("VM args: ");
		if (args.size() > 0) {
			for (String arg : args) {
				build.append(arg + " ");
			}
		} else 
			build.append("[none] ");
		build.append("\n\n");

		build.append("Threads:\n");
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		for (int i = 0; i < threadArray.length; i++) {
			build.append(threadArray[i].getName() + " (daemon=" + threadArray[i].isDaemon() + ", priority="
					+ threadArray[i].getPriority() + ", id=" + threadArray[i].getId() + ")\n");
		}
		build.append("\n");

		build.append("Exception:\n");
		build.append("Thrown In: " + t.getName() + " (daemon=" + t.isDaemon() + ", priority=" + t.getPriority()
				+ ", id=" + t.getId() + ")\n\n");

		build.append("Type: " + e.getClass().getName() + "\n");
		build.append("Message: " + e.getMessage() + "\n");
		build.append("Cause: " + e.getCause() + "\n");
		build.append("Suppressed: " + e.getSuppressed().length + "\n\n");

		build.append("Stacktrace:\n");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));
		build.append(new String(out.toByteArray()) + "\n");

		build.append("- - - - - - - I N S T A N C E - - - - - - -\n\n");

		build.append("Instance Version: " + Version.getVersion() + "\n");
		build.append("Instane name: " + inst.getName() + "\n");

		build.append("\n");

		build.append("- - - - - - - E N D - - - - - - -");

		return build.toString();
	}

	private String getSection(String section) {
		if (section.equals("System")) {
			return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version "
					+ System.getProperty("os.version");
		} else if (section.equals("Memory")) {
			Runtime runtime = Runtime.getRuntime();
			long i = runtime.maxMemory();
			long j = runtime.totalMemory();
			long k = runtime.freeMemory();
			long l = i / 1024L / 1024L;
			long i1 = j / 1024L / 1024L;
			long j1 = k / 1024L / 1024L;
			return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
		} else {
			return "";
		}
	}

	private String getDeviceInfo(GraphicsDevice gd, String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix + "Resoultion: " + gd.getDisplayMode().getWidth() + "x" + gd.getDisplayMode().getHeight()
				+ "\n");
		builder.append(prefix + "Depth: " + gd.getDisplayMode().getBitDepth() + " bit\n");
		builder.append(prefix + "Refresh Rate: " + gd.getDisplayMode().getRefreshRate() + "Hz\n");
		boolean accel = gd.getDefaultConfiguration().getImageCapabilities().isAccelerated();
		builder.append(prefix + "Is Accelerated? " + accel + "\n");
		if (accel)
			builder.append(prefix + "Avaliable Accelerated Memory: "
					+ Rounder.round((gd.getAvailableAcceleratedMemory() / 1024.0 / 1024.0), 2) + "MB\n");
		builder.append(prefix + "Translucency Capable? " + gd.getDefaultConfiguration().isTranslucencyCapable());
		return builder.toString();
	}
}
