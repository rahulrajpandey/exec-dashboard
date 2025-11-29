package com.rrp.exec.dashboard.backend.adapters.couchbase;

import com.rrp.exec.dashboard.backend.models.dto.TenantDTO;
import com.rrp.exec.dashboard.backend.util.JsonLoader;
import java.util.List;

public class MockMetadataAdapter implements MetadataPort {

  @Override
  public List<TenantDTO> getAllTenants() {
    return JsonLoader.loadList("mock-data/tenants.json", TenantDTO.class);
  }
}
