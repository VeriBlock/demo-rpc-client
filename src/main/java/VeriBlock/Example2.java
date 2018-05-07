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

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import nodecore.api.grpc.AdminGrpc;
import nodecore.api.grpc.VeriBlockMessages;
import nodecore.cli.services.*;
import nodecore.cli.utilities.Base58;
import nodecore.cli.utilities.Utility;

import java.util.concurrent.TimeUnit;

public class Example2 {

   /*
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
    */

    public static void Run() {
        System.out.println("==================================================");
        System.out.println("Example 2: View PoP endorsements");

        //Setup channel and create client
        ManagedChannel channel = NettyChannelBuilder.forAddress("127.0.0.1", 10500)
                .usePlaintext(true)
                .build();
        AdminGrpc.AdminBlockingStub blockingStub = AdminGrpc.newBlockingStub(channel);

        //Will pass in 2 parameters: address and searchLength
        int searchLength = 10000;  //Look for many past blocks

        //First get my default address
        String myAddress = Utility.bytesToBase58(
                blockingStub.getInfo(VeriBlockMessages.GetInfoRequest.newBuilder().build())
                        .getDefaultAddress().getAddress().toByteArray());

        System.out.println(String.format("Checking address %1$s for search length %2$s blocks \r\n" +
                "(this may take a few seconds)", myAddress, searchLength));

        //Get endorsements for my address
        VeriBlockMessages.GetPoPEndorsementsInfoRequest.Builder requestBuilder = VeriBlockMessages.GetPoPEndorsementsInfoRequest.newBuilder();
        //Set input params:
        requestBuilder.addAddresses(VeriBlockMessages.StandardAddress.newBuilder().setStandardAddress(ByteString.copyFrom(Base58.decode(myAddress))));
        requestBuilder.setSearchLength(searchLength);
        VeriBlockMessages.GetPoPEndorsementsInfoReply reply = blockingStub.getPoPEndorsementsInfo(requestBuilder.build());

        //Loop through and calculate soemthing
        Long total = 0L;
        System.out.println(String.format("Found %1$s endorsement(s)", reply.getPopEndorsementsList().size()));

        System.out.println("Bitcoin TxId\tContained in VBK Hash\tReward (VBK)");
        //NOTE - we have the Btc TxId, we can use that to call BTC APIs and collect more information
        for (VeriBlockMessages.PoPEndorsementInfo item : reply.getPopEndorsementsList()) {
            System.out.println(String.format("%1$s\t%2$s\t%3$s",
                    Utility.bytesToHex(item.getBitcoinTransactionId().toByteArray()),
                    Utility.bytesToHex(item.getContainedInVeriblockBlockHash().toByteArray()),
                    Utility.formatAtomicLongWithDecimal(item.getReward()))
            );
            total += item.getReward();
        }

        System.out.println();
        System.out.println(String.format("Total reward: %1$s", Utility.formatAtomicLongWithDecimal(total)));

        //shutdown channel
        try
        {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (Exception ex2)
        {
            System.out.println("Error shuttding down channel: " + ex2.getMessage());
        }
    }

}
