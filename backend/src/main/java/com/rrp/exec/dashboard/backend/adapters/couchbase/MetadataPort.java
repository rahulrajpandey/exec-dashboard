package com.rrp.exec.dashboard.backend.adapters.couchbase;

import com.rrp.exec.dashboard.backend.models.dto.TenantDTO;

import java.util.List;

public interface MetadataPort {
    List<TenantDTO> getAllTenants();
}
