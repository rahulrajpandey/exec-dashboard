package com.rrp.exec.dashboard.backend.controllers;

import com.rrp.exec.dashboard.backend.models.dto.TenantDTO;
import com.rrp.exec.dashboard.backend.services.metadata.MetadataService;
import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MetadataGraphQLController {

  private final MetadataService metadataService;

  public MetadataGraphQLController(MetadataService metadataService) {
    this.metadataService = metadataService;
  }

  @QueryMapping
  public List<TenantDTO> tenants() {
    return metadataService.getAllTenants();
  }
}
