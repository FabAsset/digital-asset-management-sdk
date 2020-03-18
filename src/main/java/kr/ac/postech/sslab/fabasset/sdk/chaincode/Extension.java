package kr.ac.postech.sslab.fabasset.sdk.chaincode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.postech.sslab.fabasset.sdk.user.AddressUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.util.*;

import static kr.ac.postech.sslab.fabasset.sdk.util.Function.*;

public class Extension {

    private static final Logger logger = LogManager.getLogger(Extension.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public long balanceOf(String owner, String type) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- balanceOf SDK called ----------------");

        long balance;
        try {
            if (!AddressUtils.isValidAddress(owner)) {
                throw new IllegalArgumentException();
            }

            String[] args = { owner, type };
            String balanceStr = InvokeChaincode.queryByChainCode(BALANCE_OF_FUNCTION_NAME, args);
            balance = Long.parseLong(balanceStr);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return balance;
    }

    public List<String> tokenIdsOf(String owner, String type) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- tokenIdsOf SDK called ----------------");

        List<String> tokenIds = new ArrayList<String>();
        try {
            if (!AddressUtils.isValidAddress(owner)) {
                throw new IllegalArgumentException();
            }

            String[] args = { owner, type };
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

    public boolean mint(String tokenId, String type, Map<String, Object> xattr, Map<String, String> uri) throws ProposalException, InvalidArgumentException, JsonProcessingException, TransactionException {
        logger.info("---------------- mint SDK called ----------------");

        boolean result;
        try {
            String xattrJson = objectMapper.writeValueAsString(xattr);
            String uriJson = objectMapper.writeValueAsString(uri);
            String[] args = { tokenId, type, xattrJson, uriJson };
            result = InvokeChaincode.submitTransaction(MINT_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public boolean setURI(String tokenId, String index, String value) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- setURI SDK called ----------------");

        boolean result;
        try {
            String[] args = { tokenId, index, value };
            result = InvokeChaincode.submitTransaction(SET_URI_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public String getURI(String tokenId, String index) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- getURI SDK called ----------------");

        String value;
        try {
            String[] args = { tokenId, index };
            value = InvokeChaincode.queryByChainCode(GET_URI_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return value;
    }

    public boolean setXAttr(String tokenId, String index, Object value) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- setXAttr SDK called ----------------");

        boolean result;
        try {
            String[] args = { tokenId, index, String.valueOf(value) };
            result = InvokeChaincode.submitTransaction(SET_XATTR_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public String getXAttr(String tokenId, String index) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- getXAttr SDK called ----------------");

        String value;
        try {
            String[] args = { tokenId, index };
            value = InvokeChaincode.queryByChainCode(GET_XATTR_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return value;
    }
}
