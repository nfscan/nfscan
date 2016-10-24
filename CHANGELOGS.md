##nfscan 1.2
* Access AWS resources using IAM roles instead of hardcoded accessKeys and secretKeys.
* Remove references to BasicCredentials since we're gonna use AWSCredentialsChain for that.
* Parameterize backend username and password.
* Upgrade AWS sdk to 1.11.44.

##nfscan 1.1
* Add QRCode and manual donation capabilities.
* Add unit tests targeting JDK 7 >=.
* Upgrade spring security for higher security.
* Clean up unused libraries on pom.xml.
