This is a very minimalist Java code sample to show how one could call commands exposed in the NodeCore_CommandLine. These include commands like getinfo, getbalance, getpopendorsementsinfo, etc... It also shows some of the utility functions like Utility.bytesToHex or Utility.bytesToBase58.

About the project structure:
* It requires Java 1.8
* It compiles via Gradle (https://gradle.org/install/)
* It copies the Jar files from nodecore-cli-0.1.3 into a local lib folder, and has the gradle build referece those
* It was written with IntelliJ Community edition 2017.3 (https://www.jetbrains.com/idea/), but everything can be run without that

This requires a local instance of NodeCore to be running and fully load the blockchain (may take a few minutes to load the whole blockchain). Get the latest NodeCore from github: https://github.com/VeriBlock/nodecore-releases

Useful wiki references: https://wiki.veriblock.org
* https://wiki.veriblock.org/index.php?title=NodeCore_CommandLine

How to compile and run:
1. Navigate to the project directory and run gradle:

```
cd "<rootDirHere>\demo-rpc-client"
gradlew run
```


The console app produces output like so:

```
Demo to show how to VeriBlock commands
==================================================
Example 1: Call getinfo and display output
Got AdminServiceClient

== Command: getinfo ==
Number of Blocks: 27266
Default Address: V34RwWk5CEp5uE8L4SSpJWcwN29dKu
Default Address Amount: 322.49999997

== Command: getpeerinfo ==
Address:45.77.8.173 Port:6500	
Address:94.130.33.180 Port:6500	
Address:78.46.106.162 Port:6500	
Address:144.202.49.232 Port:6500	
Address:173.199.119.135 Port:6500	
Address:85.10.204.200 Port:6500	
Address:52.179.12.162 Port:6500	
Address:45.32.207.123 Port:6500	
Address:34.245.54.63 Port:6500	
Address:207.148.122.103 Port:6500	
Address:207.148.85.146 Port:6500	

==================================================
Example 2: View PoP endorsements
Got AdminServiceClient

Found 6 endorsement(s)
Bitcoin TxId	Contained in VBK Hash	Reward (VBK)
1CA7C540AF39C6D11F40569AA549AA716B12AAF052A57D521ED41049C12A97B7	00000003028BD9FD9A3FD188BF2D70A4B99FC8BB43EC9786	28.00000000
F48C43D8256EDDA1745A7D9C5DDD19844B562E9DB7EC9AE6C50691FFAAEFEA6F	000000030DA46EE1BE948032FD2536E61D17CB5D67F5324B	28.00000000
9FB8D3F61826031749E89B6E0B940CF3A265A247691C4399EE5951D3945F15D4	00000002978161A5A15B593F25D8B772B4B8CC600BC0B584	28.00000000
0E819A08791CFAAEE2C6AB2433B133D5EDBF45B838E72D37309C7BBFEB10FECF	000000021F20A518C2A23B6C6B2F9AA64721B8299BA306D4	28.00000000
D4E0325862C0DC1922F250F0E1C89480B4542D27245B563E5F9D35BCAF6D1C6C	00000000EC31B9299E47CC2A129A24C75852640C873ED4BE	28.00000000
37FAF5C0ACDB71078B3E9A4040906875603542224660F09EB9C0582A5A533C2F	00000001D0CB56D59ABD5F14BCC9F5C54A6AFD5E5519EE4A	28.00000000

Total reward: 168.00000000
==================================================
Done with demos
```
