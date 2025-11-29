package com.rrp.exec.dashboard.backend.adapters.couchbase;

import com.rrp.exec.dashboard.backend.models.dto.TenantDTO;

import java.util.List;

public class CouchbaseMetadataAdapter implements MetadataPort {

    @Override
    public List<TenantDTO> getAllTenants() {
        // TODO: query Couchbase
        throw new UnsupportedOperationException("REAL Couchbase adapter not implemented yet");
    }
}
