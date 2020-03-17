package kr.ac.postech.sslab.fabasset.sdk.chaincode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.postech.sslab.fabasset.sdk.util.ChaincodeCommunication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.util.*;

import static kr.ac.postech.sslab.fabasset.sdk.util.Function.*;

public class TokenTypeManagement {
    private static final Logger logger = LogManager.getLogger(TokenTypeManagement.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<String> tokenTypesOf() throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- tokenTypesOf SDK called ----------------");

        String tokenTypsString;
        List<String> tokenTypes = new ArrayList<String>();
        try {
            String[] args = {};
            tokenTypsString = ChaincodeCommunication.queryByChainCode(TOKEN_TYPES_OF_FUNCTION_NAME, args);
            if (tokenTypsString != null) {
                tokenTypes = Arrays.asList(tokenTypsString.substring(1, tokenTypsString.length() - 1).split(", "));
            }
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return tokenTypes;
    }

    public boolean enrollTokenType(String type, Map<String, List<String>> xattr) throws ProposalException, InvalidArgumentException, JsonProcessingException, TransactionException {
        logger.info("---------------- enrollTokenType SDK called ----------------");

        boolean result;
        try {
            String json = objectMapper.writeValueAsString(xattr);
            String[] args = { type, json };
            result = ChaincodeCommunication.sendTransaction(ENROLL_TOKEN_TYPE_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public boolean dropTokenType(String type) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- dropTokenType SDK called ----------------");

        boolean result;
        try {
            String[] args = { type };
            result = ChaincodeCommunication.sendTransaction(DROP_TOKEN_TYPE_FUNCTION_NAME, args);
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return result;
    }

    public Map<String, List<String>> retrieveTokenType(String type) throws ProposalException, InvalidArgumentException, IOException, TransactionException {
        logger.info("---------------- getTokenType SDK called ----------------");

        String json;
        Map<String, List<String>> xattr = new HashMap<String, List<String>>();
        try {
            String[] args = { type };
            json = ChaincodeCommunication.queryByChainCode(RETRIEVE_TOKEN_TYPE_FUNCTION_NAME, args);
            if (json != null) {
                xattr = objectMapper.readValue(json, new TypeReference<Map<String, List<String>>>() {});
            }
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return xattr;
    }

    public List<String> retrieveAttributeOfTokenType(String type, String attribute) throws ProposalException, InvalidArgumentException, TransactionException {
        logger.info("---------------- retrieveAttributeOfTokenType SDK called ----------------");

        String string;
        List<String> pair = new ArrayList<String>();
        try {
            String[] args = { type, attribute };
            string = ChaincodeCommunication.queryByChainCode(RETRIEVE_ATTRIBUTE_OF_TOKEN_TYPE_FUNCTION_NAME, args);
            if (string != null) {
                pair = Arrays.asList(string.substring(1, string.length() - 1).split(", "));
            }
        } catch (ProposalException e) {
            logger.error(e);
            throw new ProposalException(e);
        }
        return pair;
    }
}
