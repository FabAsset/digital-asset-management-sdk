package com.github.fabasset.sdk.config;

import com.github.fabasset.sdk.client.ChannelClient;
import com.github.fabasset.sdk.client.FabricClient;
import com.github.fabasset.sdk.user.UserContext;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.util.Collection;

public class SetConfig {
    private static String owner;
    private static String receiver;
    private static Enrollment enrollment;

    private static UserContext userContext;
    private static FabricClient fabClient;
    private static ChannelClient channelClient;

    public static UserContext initUserContext(String user, Enrollment enrollment, String org, String mspId) {
        if(enrollment == null) {
            System.out.println("No enrollment");
            return null;
        }

        userContext = new UserContext();
        userContext.setName(user);
        userContext.setAffiliation(org);
        userContext.setMspId(mspId);
        userContext.setEnrollment(enrollment);

        return userContext;
    }

    public static FabricClient getFabClient() {
        return fabClient;
    }

    public static ChannelClient initChannel(String channelName, Collection<Peer> peers, Collection<Orderer> orderers, EventHub eventHub) throws InvalidArgumentException, TransactionException {

        try {
            fabClient = new FabricClient(userContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ChannelClient channelClient = fabClient.createChannelClient(channelName);
        Channel channel = channelClient.getChannel();
        for (Peer peer: peers) {
            channel.addPeer(peer);
        }

        channel.addEventHub(eventHub);

        for (Orderer orderer: orderers) {
            channel.addOrderer(orderer);
        }

        channel.initialize();

        return channelClient;
    }

    public static ChannelClient getChannelClient() {
        return channelClient;
    }

    public static void setEnrollment(String owner, Enrollment enrollment) {
        SetConfig.owner = owner;
        SetConfig.enrollment = enrollment;
    }

    public static void setEnrollmentForReceiver(String receiver, Enrollment enrollment) {
        SetConfig.receiver = receiver;
        SetConfig.enrollment = enrollment;
    }
}
