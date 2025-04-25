package in.codifi.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tbl_pms_overview")
public class PmsOverview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "about_company", length = 1000)
    private String aboutCompany;

    @Column(name = "pms_name", nullable = false)
    private String pmsName;

    @Column(name = "pms_details", length = 1000)
    private String pmsDetails;

    @Column(name = "investment_strategy", length = 1000)
    private String investmentStrategy;

    @Column(name = "fund_managers", length = 1000)
    private String fundManagers;
}