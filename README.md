# ANDROPHSY 
ANDROPHSY is an opensource forensic tool for Android smartphones that helps digital forensic investigator throughout the life cycle of digital forensic investigation. Services provided by ANDROPHSY includes

- [x] Digital forensic case and evidence management
- [x]	Raw data acquisition – physical acquisition and logical – file system level acquisition
- [x]	Meaningful evidence extraction and analysis support
- [x]	Evidence presentation

# License information
Please read license agreement carefully [here](https://github.com/scorelab/Androspy/blob/master/LICENSE).

# Disclaimer notice
Before go further from this point please read and understand disclaimer notice carefully available [here](https://github.com/scorelab/Androspy/blob/master/disclaimer.txt).

# How to run ANDROPHSY project
This section describes how to setup development environment for ANDROPHSY. Currently ANDROPHSY is compatible with Linux platform only. It was implemented using Java language as an Eclipse project and successfully tested on Ubuntu 12.04 LTS. 

- [x] Install Java on Ubuntu
- [x] Install and configure Android SDK with Eclipse IDE.
- [x] Setup MySQL database
- [x] Copy given 51-android.rules udev rule files to /etc/udev/rules.d. Modify this rules configuration file to add support for more Android smartphone vendors 
- [x] Import or copy project to Eclipse workspace and can build using Eclipse
- [x] Run dependencies.sh as root
- [x] Install bulk_extractor following guidelines [here](https://github.com/simsong/bulk_extractor/wiki/Installing-bulk_extractor).

# Credit and Acknowledgment
ANDROPHSY is an opensource tool and has employed several existing command line utilities to perform several jobs at the back end. ANDROPHSY consists of device rooting module and has used existing exploits to gain root access. ANDROPHSY authors acknowledge all of the original work contributors and respect their rights. 

# Inquiries
For inquires related to ANDROPHSY project contact: indeewariua[at]gmail[dot]com, amilads[at]gmail[dot]com
 

