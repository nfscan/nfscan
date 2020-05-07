# nfscan 
[![Build Status](https://travis-ci.org/nfscan/nfscan.svg?branch=master)](https://travis-ci.org/nfscan/nfscan) [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/nfscan/nfscan/master/LICENSE)

NFScan is a free, open-source software, available to non-profit organizations to receive donations effectively.

## What's new in this version

* Create Cloudformation templates to allow easy deploy by anyone with an AWS account.
* Access AWS resources using IAM roles instead of hardcoded accessKeys and secretKeys.
* Remove references to BasicCredentials since we're gonna use AWSCredentialsChain for that.
* Parameterize backend username and password.
* Upgrade AWS sdk to 1.11.44.

Looking for older versions changes? Please take a look at [CHANGELOGS.md](CHANGELOGS.md)


## Setting up development environment

Walkthrough in [Portuguese](https://github.com/nfscan/nfscan/wiki/Develpment-environment---%5BPortuguese%5D)

## Public API

Once you've set up your development enviroment you can make calls to nfscan's public API to process or donate receipts. We've created [a wiki page](https://github.com/nfscan/nfscan/wiki/Public-API-calls-%5BPortuguese%5D) containing all requests you should use in order to integrate with whatever app/site of yours.

## Mobile

We've made an open source app for [iOS](https://github.com/nfscan/ios-receipt-scan-example) that integrates with nfscan. You may use it as a reference model or even as a white label product. That's up to you.

## Awards

<table>
  <tbody>
    <tr>
      <td><img src="https://raw.githubusercontent.com/nfscan/nfscan/master/03-Documentation/02-DocAssets/award-01.jpg" alt=""/></td>
      <td><img src="https://raw.githubusercontent.com/nfscan/nfscan/master/03-Documentation/02-DocAssets/award-02.jpg" alt=""/></td>
      <td><img src="https://raw.githubusercontent.com/nfscan/nfscan/master/03-Documentation/02-DocAssets/award-03.jpg" alt=""/></td>
    </tr>
    <tr align="center">
      <td>[Bronze] :: Cannes Lions - Cyber Category</td>
      <td>[Estrela Verde] :: CCSP - Ação beneficiente</td>
      <td>[Grand Prix] :: B9 2015 </td>
    </tr>
  </tbody>
</table>

## Contributing 

You're encouraged to contribute to nfscan. Fork the code from https://github.com/nfscan/nfscan and submit pull requests.

Make sure you're following the [contributing guidelines](https://github.com/nfscan/nfscan/blob/master/CONTRIBUTING.md) for this project.
