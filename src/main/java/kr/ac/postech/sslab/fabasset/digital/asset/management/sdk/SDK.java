package kr.ac.postech.sslab.fabasset.digital.asset.management.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SDK {
    private ObjectMapper objectMapper;

    public SDK() {}

    public SDK(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
