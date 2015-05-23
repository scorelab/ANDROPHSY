package lk.score.androphsy.main;// Copyright 2015 Indeewari Akarawita
//
// This file is a part of ANDROPHSY
//
// ANDROPHSY is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lk.score.androphsy.database.ConnectDb;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * @author indeewari
 *
 */
public class AdbExecCmd {
	private String device;
	private Runtime runtime;

	public AdbExecCmd() {
		runtime = Runtime.getRuntime();
		System.out.println("create runtime object");
	}

	public AdbExecCmd(String device) {
		this();
		this.setDevice(device);
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	// start adb server on forensics workstation
	public boolean startAdb() {
		String result = "";
		String line = "";
		try {
			System.out.println("adb start-server");
			Process proc = runtime.exec("adb start-server");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
			proc.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// stop adb server on forensic workstation
	public void killAdb() {
		String result = "";
		String line = "";
		try {
			Process proc = runtime.exec("adb kill-server");
			proc.waitFor();
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// get list of connected device - should have one
	public String[] getDevices() {
		try {
			String line = "", result = "";
			String devices[] = null;

			Process proc = runtime.exec("adb devices");
			DataInputStream data = new DataInputStream(proc.getInputStream());
			byte buf[] = new byte[1024];
			int len = data.read(buf);
			String list[] = new String(buf, 0, len).split("\n");

			if (list.length > 1) {
				devices = new String[list.length - 1];
				for (int i = 1; i < list.length; i++) {
					devices[i - 1] = list[i].split("\t")[0];
				}
				// always take 0th index device
				device = devices[0];

			}
			return devices;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// Read target device properties
	public String getDeviceProperty(DeviceTemplate devicetemplate) {
		ConnectDb conndb = new ConnectDb();
		Connection conn = conndb.getConnection();
		String result = "";
		String line = "";
		String query = "";
		String value = "";

		try {
			Statement stmt = conn.createStatement();

			Process proc = runtime.exec("adb shell getprop");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				String components[] = line.split(":");
				value = components[1].substring(2, components[1].length() - 1);
				if (components[0].equals("[ro.product.name]")) {
					devicetemplate.setDeviceName(value);
					result += "Device Name:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.model]")) {
					devicetemplate.setModel(value);
					result += "Device Model:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.build.version.release]")) {
					devicetemplate.setOs(value);
					result += "Operating System Version:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.manufacturer]")) {
					devicetemplate.setManufacturer(value);
					result += "Manufaturer:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.build.display.id]")) {
					devicetemplate.setBuildNumber(value);
					result += "Build Version:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.version]")) {
					devicetemplate.setSwVersion(value);
					result += "Software Number:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.processor]")) {
					devicetemplate.setProcessorSpeed(value);
					result += "Processor Speed:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.ram]")) {
					devicetemplate.setRam(value);
					result += "RAM Capacity:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.wifi]")) {
					devicetemplate.setWifi(value);
					result += "Wi-Fi:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.product.bluetooth]")) {
					devicetemplate.setBluetooth(value);
					result += "Bluetooth:\t\t" + value + "\n";

				} else if (components[0].equals("[ro.secure]")) {
					devicetemplate.setKernelSecure(value.equals("1") ? "Yes" : "No");
					result += "Kernal Secure:\t\t";
					if (value.equals("1")) {
						result += "Yes \n";
					} else {
						result += "No \n";
					}

				} else if (components[0].equals("[ro.debuggable]")) {
					devicetemplate.setUsbDebugEnabled(value.equals("0") ? "Yes" : "No");
					result += "USB Debugging Enabled:\t\t";
					if (value.equals("1")) {
						result += "Yes \n";
					} else {
						result += "No \n";
					}

				} else if (components[0].equals("[persist.service.adb.enable]")) {
					devicetemplate.setAdbEnabled(value.equals("1") ? "Yes" : "No");
					result += "adb: \t\t";
					if (value.equals("1")) {
						result += "Yes \n";
					} else {
						result += "No \n";
					}
				} else {
					result += line + "\n";
				}
			}

			String bmac = getBluetoothMac();
			result += "Bluetooth Mac Address: \t\t" + bmac;
			devicetemplate.setBtMac(bmac);

			String imei = getImei();
			result += "Iemi: \t\t" + imei;
			devicetemplate.setImei(imei);

			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}

			BasicInfo.populateData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// Interface to execute adb commands
	public String execute(String command) {
		String result = "";
		String line = "";
		try {
			Process proc = runtime.exec("adb " + command);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// Interface to execute adb shell commands
	public String executeShell(String command) {
		String result = "";
		String line = "";
		try {
			Process proc = runtime.exec("adb shell " + command);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				result += line;
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isDeviceConnected() {
		String devlist[] = getDevices();
		if (devlist == null) {
			return false;
		} else {
			return devlist.length > 0;
		}
	}

	public boolean getDeviceBasicInfo(DeviceTemplate devicetemplate) {
		if (isDeviceConnected()) { // to set device serial

			String model = executeShell("getprop ro.product.model");
			System.out.println("model: " + model);
			devicetemplate.setModel(model);

			String manuf = executeShell("getprop ro.product.manufacturer");
			System.out.println("Manufacturer: " + manuf);
			devicetemplate.setManufacturer(manuf);

			String name = executeShell("getprop ro.product.name");
			System.out.println("name: " + name);

			String serial = getDevice();
			System.out.println("serial" + serial);
			devicetemplate.setSerialNo(serial);

			return true;
		} else {
			return false;
		}
	}

	public String root() {
		String result = "";
		String line = "";
		Process proc;
		try {
			System.out.println("rooting..");
			ProcessBuilder pb = new ProcessBuilder("files/install.sh");
			proc = pb.start();

			System.out.println("rooting 1..");
			BufferedReader buff = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = buff.readLine()) != null) {
				result += line;
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void removeKernelSecure() {
		execute("adb uninstall eu.chainfire.adbd");
	}

	public String unroot() {
		String result = "";
		String line = "";
		Process proc;
		try {
			System.out.println("unrooting..");
			java.util.List<String> templist = new ArrayList<String>();
			templist.add("files/unroot_copy.sh");
			templist.add("adb uninstall eu.chainfire.supersu");
			ProcessBuilder pb = new ProcessBuilder(templist);
			proc = pb.start();
			proc = pb.start();
			System.out.println("unrooting 1..");
			BufferedReader buff = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = buff.readLine()) != null) {
				result += line;
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isRooted() {
		String result = "";
		String line = "";
		Process proc;
		try {
			System.out.println("is rooted..");
			ProcessBuilder pb = new ProcessBuilder("files/test.sh");
			proc = pb.start();
			System.out.println("is rooted 1..");
			BufferedReader buff = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = buff.readLine()) != null) {
				result = line;
				System.out.println("*** " + result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.equalsIgnoreCase("success");
	}

	public boolean isKernelSecure() {
		String result = "";
		result = executeShell("getprop ro.secure");
		String output[] = result.split("\n");
		if (output[0].equals("0")) {
			System.out.println("secure");
			return true;
		} else {
			System.out.println("insecure");
			return false;
		}
	}

	public boolean prepareDevice() {
		int msgout = -1;
		String line = "";
		Shell shell = new Shell();
		try {
			if (!isDeviceConnected() || !isRooted()) {
				MessageBox msgBox = new MessageBox(shell, SWT.ERROR);
				msgBox.setMessage("Please verify your device connection and root the device");
				msgout = msgBox.open();
				return false;
			}
			if (isKernelSecure()) {
				ProcessBuilder pb = new ProcessBuilder("files/insecure.sh");
				Process proc = pb.start();
				BufferedReader bufin =
				                       new BufferedReader(
				                                          new InputStreamReader(
				                                                                proc.getErrorStream()));
				while ((line = bufin.readLine()) != null) {
					System.out.println(line);
				}
				MessageBox msgBox = new MessageBox(shell, SWT.OK);
				msgBox.setMessage("Enable insecure adbd and click ok to continue");
				msgout = msgBox.open();
				if (msgout == SWT.OK) {
					return prepareDevice();
				}
				return false;
			} else {
				// kernel is insecure
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// file system acquisition
	public void pullRoot(String savePath, String partition) {
		String result = "";
		String line = "";
		String pull = "";

		if (partition == "DATA") {
			pull = "/data/data";
			savePath += "/data";
		} else if (partition == "SYSTEM") {
			pull = "/system/";
			savePath += "/system";
		} else if (partition == "CACHE") {
			pull = "/cache/";
			savePath += "/cache";
		} else {
			pull = "/";
			savePath += "/root";
		}

		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		System.out.println(savePath);

		try {
			Process proc = runtime.exec("adb pull " + pull + " " + savePath);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String generateFileHash(String file) {
		String line = "", result = "", shash = "";
		try {
			int last = file.lastIndexOf("/");
			String name = file.substring(last);
			System.out.println("file name " + name);
			File dir = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File hashfile = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes/" + name + ".hash");
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(hashfile)));

			Process proc = runtime.exec("md5deep -s -q " + file);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));
			shash = bufin.readLine();
			writer.write(line);
			writer.flush();
			hashfile.setReadOnly();

			System.out.println("hash file can write?" + hashfile.canWrite());
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}

			return shash;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void generateHash(String sourcedir) {
		String line = "", result = "";
		try {
			int last = sourcedir.lastIndexOf("/");
			String name = sourcedir.substring(last);
			System.out.println("file name " + name);
			File dir = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File hashfile = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes/" + name + ".hash");
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(hashfile)));

			Process proc = runtime.exec("md5deep -r -s " + sourcedir);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader errorin =
			                         new BufferedReader(
			                                            new InputStreamReader(proc.getErrorStream()));

			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
				writer.append(result);
			}
			while ((line = errorin.readLine()) != null) {
				result += line + "\n";
			}
			writer.flush();
			hashfile.setReadOnly();
			System.out.println("hash file can write?" + hashfile.canWrite());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getBluetoothMac() {
		String bmac = "";
		String filename = AndrospyMain.gb_CasePath + "Acquire/data/misc/bluetoothd/";
		File dir = new File(filename);
		String temp[] = dir.list();
		if (temp != null) {
			bmac = temp[0];
		}
		return bmac;
	}

	public void captureScreen(String path) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String name = sf.format(new Date());
		System.out.println("Capture name " + name);
		try {
			ArrayList<String> cmdlist = new ArrayList<String>();
			cmdlist.add("files/screencap.sh");
			cmdlist.add(AndrospyMain.gb_CasePath + "Analysis/ScreenCapture/" + name + ".png");
			ProcessBuilder pb = new ProcessBuilder(cmdlist);
			Process proc = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateHexdump(String casepath) {
		String pathToRaw = casepath + "Acquire/data.dd";
		String pathToHex = casepath + "Analysis/Data_Hex.txt";
		System.out.println("raw " + pathToRaw + "\nhex " + pathToHex);
		try {
			File file = new File(pathToHex);
			Process proc = Runtime.getRuntime().exec("hexdump -C " + pathToRaw);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedWriter bufout =
			                        new BufferedWriter(
			                                           new OutputStreamWriter(
			                                                                  new FileOutputStream(
			                                                                                       file)));
			int read = bufin.read();
			while (read != -1) {
				bufout.write(read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CreateStringFile(String rawImagePath) {
		String result = "";
		String globalPath = "/home/indeewari/1/";
		try {
			Process proc = Runtime.getRuntime().exec("strings " + rawImagePath);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			System.out.println(globalPath);
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(
			                                                                               globalPath +
			                                                                                       "Analysis/strings_file.txt")));
			String line = bufin.readLine();
			while (line != null) {
				result += line + "\n";
				writer.write(result);
				line = bufin.readLine();
			}

			System.out.println("compare hashes after sttrings: " + compareRawHash(rawImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean compareRawHash(String rawImagePath) {
		try {
			String refpath = "", line = "", result = "";
			if (rawImagePath.endsWith("data.dd")) {
				refpath = AndrospyMain.gb_CasePath + "Acquire/Hashes/data.dd.hash";
			} else if (rawImagePath.endsWith("system.dd")) {
				refpath = AndrospyMain.gb_CasePath + "Acquire/Hashes/system.dd.hash";
			} else if (rawImagePath.endsWith("cache.dd")) {
				refpath = AndrospyMain.gb_CasePath + "Acquire/Hashes/cache.dd.hash";
			} else {
				return false;
			}

			Process proc = Runtime.getRuntime().exec("md5deep " + rawImagePath + " x " + refpath);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			return (line = bufin.readLine()) == null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean findBinary(String binaryName) {
		boolean found = false;
		String[] places =
		                  { "/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
		                   "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/",
		                   "/data/local/" };
		for (String where : places) {
			File f = new File("/system/xbin/su");
			found = f.exists();

		}
		return found;
	}

	public void recoverDeleted(String path) {
		try {
			File file = new File(AndrospyMain.gb_CasePath + "Acquire/carving.conf");
			if (!file.exists()) {
				MessageBox msgbox = new MessageBox(AndrospyMain.shell);
				msgbox.setText("File Carving Error");
				msgbox.setMessage("Select file types for Carving\n Click on Arrow Button");
				msgbox.open();
				return;
			}

			File destination = new File(AndrospyMain.gb_CasePath + "Analysis/File_Carved");
			if (!destination.exists()) {
				destination.mkdir();
			}

			File source = new File(AndrospyMain.gb_CasePath + "Analysis/TmpCarved");
			if (!source.exists()) {
				source.mkdir();
			}

			Process proc =
			               runtime.exec("scalpel -c " + AndrospyMain.gb_CasePath +
			                            "Acquire/carving.conf" + " -o " + AndrospyMain.gb_CasePath +
			                            "Analysis/TmpCarved/" + " " + path);
			moveFiles(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void moveFiles(File source, File destination) {
		try {
			Path dest = destination.toPath();
			File srcdir[] = source.listFiles();
			for (File f : srcdir) {
				System.out.println(f.getName());
				if (f.getName() == "audit.txt") {
					f.delete();
					continue;
				}
				Path src = f.toPath();
				Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getPackageList() {
		getUnistalledPackageList();
		get3pPackageList();
		getSystemPackageList();
		getDisabledPackageList();
		getEnabledPackageList();

		AppList.populateData();
	}

	public void getUnistalledPackageList() {
		String line = "", result = "";
		File fileuninstalled =
		                       new File(AndrospyMain.gb_CasePath +
		                                "Analysis/pkg_list_uninstalled.txt");

		try {
			Process proc = Runtime.getRuntime().exec("adb shell pm list packages -u -f");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(
			                                                                               fileuninstalled)));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			writer.write(result);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void get3pPackageList() {
		String line = "", result = "";
		File file3p = new File(AndrospyMain.gb_CasePath + "Analysis/pkg_list_3p.txt");

		try {
			Process proc = Runtime.getRuntime().exec("adb shell pm list packages -3 -f");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			Writer writer =
			                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file3p)));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			writer.write(result);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getSystemPackageList() {
		String line = "", result = "";
		File filesystem = new File(AndrospyMain.gb_CasePath + "Analysis/pkg_list_system.txt");

		try {
			Process proc = Runtime.getRuntime().exec("adb shell pm list packages -s -f");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(
			                                                                               filesystem)));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			writer.write(result);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getDisabledPackageList() {
		String line = "", result = "";
		File filedisabled = new File(AndrospyMain.gb_CasePath + "Analysis/pkg_list_disabled.txt");

		try {
			Process proc = Runtime.getRuntime().exec("adb shell pm list packages -d -f");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(
			                                                                               filedisabled)));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			writer.write(result);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getEnabledPackageList() {
		String line = "", result = "";
		File fileenabled = new File(AndrospyMain.gb_CasePath + "Analysis/pkg_list_enabled.txt");

		try {
			Process proc = Runtime.getRuntime().exec("adb shell pm list packages -e -f");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			Writer writer =
			                new BufferedWriter(
			                                   new OutputStreamWriter(
			                                                          new FileOutputStream(
			                                                                               fileenabled)));
			while ((line = bufin.readLine()) != null) {
				result += line + "\n";
			}
			writer.write(result);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getMounts(String partition) {
		ArrayList<String> mounts = new ArrayList<String>();
		String tmp = "/data";
		if (partition == "DATA") {
			tmp = "/data";
		} else if (partition == "SYSTEM") {
			tmp = "/system";
		} else if (partition == "CACHE") {
			tmp = "/cache";
		} else {
			tmp = "/root";
		}
		String line = executeShell("mount | grep " + tmp);
		System.out.println(line);
		String tokens[] = line.split(" ");
		for (String item : tokens) {
			mounts.add(item);
			/*
			 * 0: device 1: mount point 2: file system type 3: flags
			 */
		}
		return mounts;
	}

	public String getImei() {
		Process proc;
		String iemi = "";
		try {
			String line = "";
			proc = Runtime.getRuntime().exec("adb shell dumpsys iphonesubinfo");
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = br.readLine()) != null) {
				System.out.println("iemi: " + line);
				if (line.trim().startsWith("Device ID")) {
					String tokens[] = line.split("=");
					iemi = tokens[1];
				}
			}
			return iemi;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iemi;
	}

	public String getPrimaryMail() {
		String primary = "";
		String path = AndrospyMain.gb_CasePath + "Acquire/data/system/users/0/accounts.db";
		String query = "SELECT name FROM accounts";

		Statement stmt = AndrospyUtility.createConnection(path);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				primary = rs.getString("name");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return primary;

	}

	public String getPasswordEncrption(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passbyte = password.getBytes();
			md.reset();
			byte[] digested = md.digest(passbyte);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getCurrenttime() {
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
		Date current = new Date();
		return sf.format(current);
	}

}
