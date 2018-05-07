// VeriBlock
// Copyright 2017-2018 VeriBlock, Inc.
// All rights reserved.
// https://www.veriblock.org
// Distributed under the MIT software license, see the accompanying
// file LICENSE or http://www.opensource.org/licenses/mit-license.php.

//This is demo code intentionally kept very simple.
//Do NOT use it for production-level code
//The API may change

package VeriBlock;

public class Main {

    public static void main(String[] args) {

        //This is a simple demo implementation to get started. It is intenionally minimalist to highlight the command calls
        // with basic coding and no enterprise application features
        //
        // It assumes connecting to a local NodeCore instance, and does not address networking security.
        //See: https://wiki.veriblock.org/index.php?title=NodeCore_Networking

        System.out.println("Demo to show how to VeriBlock commands");

        Example1.Run();
        Example2.Run();

        System.out.println("==================================================");
        System.out.println("Done with demos");
    }
}
