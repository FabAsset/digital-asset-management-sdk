package kr.ac.postech.sslab.fabasset.digital.asset.management.sdk.chaincode;

import kr.ac.postech.sslab.fabasset.digital.asset.management.sdk.client.ChannelClient;
import kr.ac.postech.sslab.fabasset.digital.asset.management.sdk.client.FabricClient;
import kr.ac.postech.sslab.fabasset.digital.asset.management.sdk.config.Config;
import kr.ac.postech.sslab.fabasset.digital.asset.management.sdk.user.UserContext;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

public class SetConfig {
    static String owner;
    static String receiver;
    static Enrollment enrollment;

    static UserContext userContext;
    static FabricClient fabClient;

    public static UserContext initUserContext() {
        if(enrollment == null) {
            System.out.println("No enrollment");
            return null;
        }

        userContext = new UserContext();
        userContext.setName(owner);
        userContext.setAffiliation(Config.ORG1);
        userContext.setMspId(Config.ORG1_MSP);
        userContext.setEnrollment(enrollment);

        return userContext;
    }

    public static FabricClient getFabClient() {
        return fabClient;
    }

    public static ChannelClient initChannel() throws InvalidArgumentException, TransactionException {

        try {
            fabClient = new FabricClient(userContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
        Channel channel = channelClient.getChannel();
        Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
        EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", Config.EVENT_HUB);
        Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();

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
