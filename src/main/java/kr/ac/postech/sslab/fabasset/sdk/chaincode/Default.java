package kr.ac.postech.sslab.fabasset.sdk.chaincode;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kr.ac.postech.sslab.fabasset.sdk.util.Function.*;

public class Default {
    private static final Logger logger = LogManager.getLogger(Default.class);

    public boolean mint(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- mint SDK called ----------------");

        boolean result;
        try {
            String[] args = { tokenId };
            result = InvokeChaincode.sendTransaction(MINT_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public boolean burn(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- burn SDK called ----------------");

        boolean result;
        try {
            String[] args = { tokenId };
            result = InvokeChaincode.sendTransaction(BURN_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public String getType(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- getType SDK called ----------------");

        String type;
        try {
            String[] args = { tokenId };
            type = InvokeChaincode.queryByChainCode(GET_TYPE_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return type;
    }

    public List<String> tokenIdsOf(String owner) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- tokenIdsOf SDK called ----------------");

        List<String> tokenIds = new ArrayList<String>();
        try {
            String[] args = { owner };
            String tokenIdsStr = InvokeChaincode.queryByChainCode(TOKEN_IDS_OF_FUNCTION_NAME, args);

            if(tokenIdsStr != null) {
                tokenIds = Arrays.asList(tokenIdsStr.substring(1, tokenIdsStr.length() - 1).split(", "));
            }
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return tokenIds;
    }

    public String query(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- query SDK called ----------------");

        String result;
        try {
            String[] args = { tokenId };
            result = InvokeChaincode.queryByChainCode(QUERY_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public List<String> history(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- queryHistory SDK called ----------------");

        List<String> histories = new ArrayList<String>();
        String result;
        try {

            String[] args = { tokenId };
            result = InvokeChaincode.queryByChainCode(HISTORY_FUNCTION_NAME, args);

            if(result != null) {
                histories = Arrays.asList(result.substring(1, result.length() - 1).split(", "));
            }

        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return histories;
    }
}
