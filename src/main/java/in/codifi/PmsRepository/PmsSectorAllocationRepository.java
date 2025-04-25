package in.codifi.PmsRepository;

import in.codifi.Entity.PmsSectorAllocation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PmsSectorAllocationRepository implements PanacheRepository<PmsSectorAllocation> {
}