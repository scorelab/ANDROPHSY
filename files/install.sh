#!/bin/sh
# This can't be run from Terminal Emulator, but it can be used on a Linux or
# Mac system to run the root.
# OSX and Linux binaries for 'adb' are in the files directory.
#
echo "---           Samsung i545 VRUEMJ7 lk.score.androphsy.main.Root               ---"
echo "--- Based on the CVE-2013-6282 exploit by cubeundcube ---"
echo ""

BB=Y

if [ ! -d /home/indeewari/RootMethod/saferoot/files ]; then
	echo "[*] Fixing the folder structure"
	mkdir /home/indeewari/RootMethod/saferoot/files
	if [ -f busybox ]; then
		mv busybox /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f getroot ]; then
		mv getroot /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f unroot ]; then
		mv unroot /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f install-recovery.sh ]; then
		mv install-recovery.sh /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f su ]; then
		mv su /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f Superuser.apk ]; then
		mv Superuser.apk /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f adb.osx ]; then
		mv adb.osx /home/indeewari/RootMethod/saferoot/files
	fi
	if [ -f adb.linux ]; then
		mv adb.linux /home/indeewari/RootMethod/saferoot/files
	fi
fi

chmod 755 /home/indeewari/RootMethod/saferoot/files/adb.osx /home/indeewari/RootMethod/saferoot/files/adb.linux

systype=$(uname -s)

case x"$systype" in
	xLinux*)
		adb="/home/indeewari/RootMethod/saferoot/files/adb.linux";;
	xDarwin*)
		adb="/home/indeewari/RootMethod/saferoot/files/adb.osx";;
	*)
		echo "Unrecognized system type $systype. adb must be on your path."
		adb="adb";;
esac

echo "[*] Testing adb usability"
echo ""
#read -p "Plug in your phone and press ENTER to continue ..."
state=$(adb get-state)
if [ "X$state" != "device" ]; then
	echo ""
	echo "Watch your phone. If you see the \"Allow USB debugging\" prompt,"
	echo "tap on the \"Always allow from this computer\" checkbox,"
	echo "then tap OK."
	echo ""
	echo "If this script appears to be stuck at \"Waiting for your phone to appear\","
	echo "then you should try unplugging and re-plugging it to get the"
	echo "permission prompt for USB debugging to appear."
	echo "If you've already done that, you should tap on the USB icon"
	echo "in the notifications area that says \"Connected as a media device\"."
	echo "On the \"USB computer connection\" page, switch between"
	echo "\"Camera\" and \"Media Device\" to see if the device appears."
fi
echo "[*] Waiting for your phone to appear..."
echo "Watch your phone. Unlock it and give permission for the install to run."
adb wait-for-device
echo "[*] Your phone is detected and ready for rooting."
echo ""
echo "[*] Sending files to your device..."
adb install /home/indeewari/RootMethod/saferoot/files/Superuser.apk >/dev/null 2>&1
adb push /home/indeewari/RootMethod/saferoot/files/getroot /data/local/tmp/ >/dev/null 2>&1
adb push /home/indeewari/RootMethod/saferoot/files/su /data/local/tmp/ >/dev/null 2>&1
adb push /home/indeewari/RootMethod/saferoot/files/busybox /data/local/tmp/ >/dev/null 2>&1
adb push /home/indeewari/RootMethod/saferoot/files/unroot /data/local/tmp/ >/dev/null 2>&1
adb push /home/indeewari/RootMethod/saferoot/files/install-recovery.sh /data/local/tmp/ >/dev/null 2>&1
adb shell chmod 0755 /data/local/tmp/getroot
adb shell chmod 0755 /data/local/tmp/busybox
echo ""
echo "[*] Starting rooting program."
adb shell /data/local/tmp/getroot
echo ""
echo "[*] Checking if rooting succeeded"
rootstat=$(adb shell ls -l /data/local/tmp/su | grep No.such.file.or.directory)
if [ "X$rootstat" = "X" ]; then
    echo "[*] Unfortunately, rooting failed. Cleaning up."
    echo "[*] Note: if you see 'Your Kernel is Patched!' then that means"
    echo "[*] that Saferoot won't work with your phone. Please try some other"
    echo "[*] way of rooting it."
    adb shell rm /data/local/tmp/getroot
    adb shell rm /data/local/tmp/su
    [ "$BB" = "Y" ] && adb shell rm /data/local/tmp/busybox
    adb shell rm /data/local/tmp/unroot
    adb shell rm /data/local/tmp/install-recovery.sh
    echo ""
    echo "--- All Finished ---"
    exit 1
fi
echo "[*] Removing temporary files..."
[ "$BB" = "Y" ] && adb shell rm /data/local/tmp/getroot
[ "$BB" = "N" ] && adb shell rm /data/local/tmp/getroot -b
echo ""
echo "[*] Rebooting...Please wait."
adb reboot
echo "[*] Waiting for device to re-appear..."
adb wait-for-device
sustat=$(adb shell ls -l /system/xbin/su | grep No.such.file.or.directory)
if [ "X$sustat" = "X" ]; then
    SU="/system/xbin/su"
else
    echo "Something has deleted su - looking for a backup copy"
    superstat=$(adb shell ls -l /system/xbin/super | grep No.such.file.or.directory)
    if [ "X$superstat" = "X" ]; then
        SU="/system/xbin/super"
    else
	echo "No su program can be found. Exiting."
	exit 1
    fi
fi

read -p "Wait until your phone reboots, then unlock it and press Enter:"
echo "On your phone, open SuperSU and let it update if it asks."
read -p "When it is done updating, press Enter to continue:"
adb wait-for-device
echo "On your phone, watch for the SuperSU permission popup and give"
echo "permission for ADB Shell to gain root permissions."
echo ""
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
adb wait-for-device
adb shell $SU -c sleep 1
echo "[*] Disabling Knox"
adb shell $SU -c pm disable com.sec.knox.seandroid
echo "[*] Setting Permissions"
adb shell $SU -c mount -o remount,rw /system
adb shell $SU -c rm -f /system/etc/install-recovery-2.sh
if [ "$SU" = "/system/xbin/super" ]; then
    adb shell $SU -c cp /system/xbin/daemonsu /system/xbin/su
    adb shell $SU -c chown 0.0 /system/xbin/su
fi
adb shell $SU -c chmod 6755 /system/xbin/su
adb shell $SU -c chmod 6755 /system/xbin/daemonsu
adb shell $SU -c rm /system/xbin/super
if [ "$BB" = "Y" ]; then
    echo "[*] Installing busybox"
    adb shell $SU -c /system/xbin/busybox --install -s /system/xbin
fi
adb wait-for-device
adb shell sleep 15
adb wait-for-device
adb shell $SU -c mount -o remount,ro /system
echo ""
echo "--- Rooting Finished ---"
