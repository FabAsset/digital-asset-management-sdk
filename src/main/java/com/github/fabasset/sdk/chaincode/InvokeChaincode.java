package com.github.fabasset.sdk.chaincode;

import com.github.fabasset.sdk.client.ChannelClient;
import com.github.fabasset.sdk.client.FabricClient;
import com.github.fabasset.sdk.util.Manager;
import com.github.fabasset.sdk.config.SetConfig;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import java.util.Collection;

public class InvokeChaincode {
    private static String chaincodeId = Manager.getChaincodeId();

    private InvokeChaincode() {}

    public static boolean submitTransaction(String function, String[] args) throws InvalidArgumentException, TransactionException, ProposalException {
        boolean result = false;

        FabricClient fabClient = SetConfig.getFabClient();

        TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName(chaincodeId).build();
        request.setChaincodeID(ccid);
        request.setFcn(function);
        request.setArgs(args);

        ChannelClient channelClient = SetConfig.getChannelClient();
        Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
        for (ProposalResponse response : responses) {
            result = Boolean.parseBoolean(response.getMessage());
        }

        return result;
    }

    public static String queryByChainCode(String function, String[] args) throws InvalidArgumentException, TransactionException, ProposalException {
        String result = null;

        ChannelClient channelClient = SetConfig.getChannelClient();
        Collection<ProposalResponse> responses = channelClient.queryByChainCode(chaincodeId, function, args);
        for (ProposalResponse response : responses) {
            result = response.getMessage();
        }

        return result;
    }
}
