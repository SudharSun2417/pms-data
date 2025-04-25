package in.codifi.PmsRepository;

import in.codifi.Entity.PmsPerformance;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PmsPerformanceRepository implements PanacheRepository<PmsPerformance> {
}