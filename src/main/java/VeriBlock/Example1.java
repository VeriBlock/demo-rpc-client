// VeriBlock Node Core
// Copyright (C) 2017 VeriBlock, Inc.
// All rights reserved.

//This is demo code intentionally kept very simple.
//Do NOT use it for production-level code
//The API may change

package VeriBlock;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import nodecore.api.grpc.AdminGrpc;
import nodecore.api.grpc.VeriBlockMessages;
import nodecore.cli.utilities.Utility;

import java.util.concurrent.TimeUnit;

public class Example1 {


 /*
    ==================================================
    Example 1: Call getinfo and display output
    Got AdminServiceClient

    == Command: getinfo ==
    Number of Blocks: 27235
    Default Address: V34RwWk5CEp5uE8L4SSpJWcwN29dKu
    Default Address Amount: 32249999997

    == Command: getpeerinfo ==
    Address:45.77.8.173 Port:6500
    Address:94.130.33.180 Port:6500
    Address:78.46.106.162 Port:6500
    Address:144.202.49.232 Port:6500
  */
    public static void Run()
    {
        System.out.println("==================================================");
        System.out.println("Example 1: Call getinfo and display output");

        //These are the commands from the NC_CLI:
        //https://wiki.veriblock.org/index.php?title=NodeCore_CommandLine

        //Setup channel and create client
        ManagedChannel channel = NettyChannelBuilder.forAddress("127.0.0.1", 10500)
                .usePlaintext(true)
                .build();
        AdminGrpc.AdminBlockingStub blockingStub = AdminGrpc.newBlockingStub(channel);

        //Call Command: GetInfo
        System.out.println("== Command: getinfo ==");
        VeriBlockMessages.GetInfoReply getInfoReply = blockingStub.getInfo(VeriBlockMessages.GetInfoRequest.newBuilder().build());

        System.out.println("Number of Blocks: " + getInfoReply.getNumberOfBlocks());

        //Call utility class to show manipulating data
        System.out.println("Default Address: " +
                Utility.bytesToBase58(getInfoReply.getDefaultAddress().getAddress().toByteArray())
        );
        System.out.println("Default Address Amount: " +
                Utility.formatAtomicLongWithDecimal(getInfoReply.getDefaultAddress().getAmount()));
        System.out.println();

        //Call Command: GetPeerInfo
        System.out.println("== Command: getpeerinfo ==");
        VeriBlockMessages.GetPeerInfoReply peerInfoReply = blockingStub.getPeerInfo(VeriBlockMessages.GetPeerInfoRequest.newBuilder().build());
        for (VeriBlockMessages.NodeInfo item : peerInfoReply.getConnectedNodesList()) {
            System.out.println(String.format("Address:%1$s Port:%2$s\t", item.getAddress(), item.getPort()));
        }
        System.out.println();

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
