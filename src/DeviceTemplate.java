// Copyright 2015 Indeewari Akarawita
//
//    This file is a part of Androspy
//
//    Androspy is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

/**
 * @author indeewari
 *
 */
class DeviceTemplate{
	private int DeviceId;
	private String DeviceName  = "";
	private String Model = "";
	private String Manufacturer = "";
	private String SerialNo = "";
	private String Os = "";
	private String BuildNumber = "";
	private String SwVersion = "";
	private String ProcessorSpeed = "";
	private String Ram = "";
	private String Wifi = "";
	private String WifiMac = "";
	private String Bluetooth = "";
	private String BtMac = "";
	private String KernelSecure = "";
	private String UsbDebugEnabled = "";
	private String AdbEnabled = "";
	private String Imei = "";
	private String Imsi = "";
	
	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}

	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String deviceName) {
		DeviceName = deviceName;
	}

	public String getOs() {
		return Os;
	}

	public void setOs(String os) {
		Os = os;
	}

	public String getBuildNumber() {
		return BuildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		BuildNumber = buildNumber;
	}

	public String getSwVersion() {
		return SwVersion;
	}

	public void setSwVersion(String swVersion) {
		SwVersion = swVersion;
	}

	public String getProcessorSpeed() {
		return ProcessorSpeed;
	}

	public void setProcessorSpeed(String processorSpeed) {
		ProcessorSpeed = processorSpeed;
	}

	public String getRam() {
		return Ram;
	}

	public void setRam(String ram) {
		Ram = ram;
	}

	public String getWifi() {
		return Wifi;
	}

	public void setWifi(String wifi) {
		Wifi = wifi;
	}

	public String getWifiMac() {
		return WifiMac;
	}

	public void setWifiMac(String wifiMac) {
		WifiMac = wifiMac;
	}

	public String getBluetooth() {
		return Bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		Bluetooth = bluetooth;
	}

	public String getBtMac() {
		return BtMac;
	}

	public void setBtMac(String btMac) {
		BtMac = btMac;
	}

	public String getKernelSecure() {
		return KernelSecure;
	}

	public void setKernelSecure(String kernelSecure) {
		KernelSecure = kernelSecure;
	}

	public String getUsbDebugEnabled() {
		return UsbDebugEnabled;
	}

	public void setUsbDebugEnabled(String usbDebugEnabled) {
		UsbDebugEnabled = usbDebugEnabled;
	}

	public String getAdbEnabled() {
		return AdbEnabled;
	}

	public void setAdbEnabled(String adbEnabled) {
		AdbEnabled = adbEnabled;
	}

	public String getImei() {
		return Imei;
	}

	public void setImei(String imei) {
		Imei = imei;
	}

	public String getImsi() {
		return Imsi;
	}

	public void setImsi(String imsi) {
		Imsi = imsi;
	}

	public int getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(int deviceId) {
		DeviceId = deviceId;
	}
	
}
