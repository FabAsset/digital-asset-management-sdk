package kr.ac.postech.sslab.fabasset.sdk.chaincode;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import static kr.ac.postech.sslab.fabasset.sdk.util.Function.*;

public class ERC721 {
    private static final Logger logger = LogManager.getLogger(ERC721.class);

    public long balanceOf(String owner) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- balanceOf SDK called ----------------");

        long balance;
        try {
            String[] args = { owner };
            String balanceStr = ChaincodeCommunication.queryByChainCode(BALANCE_OF_FUNCTION_NAME, args);
            balance = Long.parseLong(balanceStr);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return balance;
    }

    public String ownerOf(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- ownerOf SDK called ----------------");

        String owner;
        try {
            String[] args = { tokenId };
            owner = ChaincodeCommunication.queryByChainCode(OWNER_OF_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return owner;
    }


    public boolean transferFrom(String from, String to, String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- transferFrom SDK called ----------------");

        boolean result;
        try {
            String[] args = { from, to, tokenId };
            result = ChaincodeCommunication.sendTransaction(TRANSFER_FROM_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public boolean approve(String approved, String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- approve SDK called ----------------");

        boolean result;
        try {
            String[] args = { approved, tokenId };
            result = ChaincodeCommunication.sendTransaction(APPROVE_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public boolean setApprovalForAll(String operator, boolean approved) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- setApprovalForAll SDK called ----------------");

        boolean result;
        try {
            String[] args = { operator, Boolean.toString(approved) };
            result = ChaincodeCommunication.sendTransaction(SET_APPROVAL_FOR_ALL_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public String getApproved(String tokenId) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- getApproved SDK called ----------------");

        String approved;
        try {
            String[] args = { tokenId };
            approved = ChaincodeCommunication.queryByChainCode(GET_APPROVED_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return approved;
    }

    public boolean isApprovedForAll(String owner, String operator) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- isApprovedForAll SDK called ----------------");

        boolean result;
        try {
            String[] args = { owner, operator };
            String response = ChaincodeCommunication.queryByChainCode(IS_APPROVED_FOR_ALL_FUNCTION_NAME, args);
            result = Boolean.parseBoolean(response);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public static void main(String[] args)  {}
}