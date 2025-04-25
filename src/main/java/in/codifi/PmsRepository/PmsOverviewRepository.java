package in.codifi.PmsRepository;

import in.codifi.Entity.PmsOverview;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PmsOverviewRepository implements PanacheRepository<PmsOverview> {
}