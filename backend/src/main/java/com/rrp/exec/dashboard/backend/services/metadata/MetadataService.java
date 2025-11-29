package com.rrp.exec.dashboard.backend.services.metadata;

import com.rrp.exec.dashboard.backend.adapters.couchbase.MetadataPort;
import com.rrp.exec.dashboard.backend.models.dto.TenantDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {

  private final MetadataPort metadataPort;

  public MetadataService(MetadataPort metadataPort) {
    this.metadataPort = metadataPort;
  }

  public List<TenantDTO> getAllTenants() {
    return metadataPort.getAllTenants();
  }
}
