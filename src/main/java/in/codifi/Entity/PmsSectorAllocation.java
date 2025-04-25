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
@Entity(name = "tbl_pms_sector_allocation")
public class PmsSectorAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pms_name", nullable = false)
    private String pmsName;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "weightage")
    private Double weightage;
}