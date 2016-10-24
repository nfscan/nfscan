# nfscan 
[![Build Status](https://travis-ci.org/nfscan/nfscan.svg?branch=master)](https://travis-ci.org/nfscan/nfscan) [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/nfscan/nfscan/master/LICENSE)

NFScan is a free, open-source software, available to non-profit organizations to receive donations effectively.

## FAQ

Frequent asked questions in [Portuguese](http://nfscan.cc/faq.html)

## What's new in this version

* Access AWS resources using IAM roles instead of hardcoded accessKeys and secretKeys.
* Remove references to BasicCredentials since we're gonna use AWSCredentialsChain for that.
* Parameterize backend username and password.
* Upgrade AWS sdk to 1.11.44.

Looking for older versions changes? Please take a look at [CHANGELOGS.md](CHANGELOGS.md)

## Quickly deploy nfscan to AWS

The easiest way to deploy nfscan to AWS is through the use of a [Cloudformation](https://aws.amazon.com/cloudformation/) template. It allows the required infrastructure and dependent services to be provisioned with little to none knowledge of AWS. 

The table below shows different regions where you want to deploy nfscan. There are numerous factors that you could take into consideration when deciding which one you're going to launch it. However, if your primary concern is about how much it's gonna cost then either N. Virgina and Ohio will be a good call. If low latency is a requirement, then São Paulo will be a better option. 

Region Name | Region | Action
------------- | ------------- | -------------
US East (N. Virginia) | us-east-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-east-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
US East (Ohio) | us-east-2 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-east-2#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
US West (N. California) | us-west-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-west-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
US West (Oregon) | us-west-2 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-west-2#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
Asia Pacific (Mumbai) | ap-south-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=ap-south-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
Asia Pacific (Seoul) | ap-northeast-2 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=ap-northeast-2#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
Asia Pacific (Singapore) | ap-northeast-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=ap-northeast-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
Asia Pacific (Sydney) | ap-southeast-2 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=ap-southeast-2#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
Asia Pacific (Tokyo) | ap-southeast-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=ap-southeast-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
EU (Frankfurt) | eu-central-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=eu-central-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
EU (Ireland) | eu-west-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=eu-west-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)
South America (São Paulo) | sa-east-1 | [![](https://s3.amazonaws.com/cloudformation-examples/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=sa-east-1#/stacks/new?stackName=nfscan&templateURL=https://s3.amazonaws.com/nfscan-cloudformation-templates/cloudformation.template)


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
