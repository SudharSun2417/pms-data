package in.codifi.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_pms_performance")
public class PmsPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pms_name", nullable = false)
    private String pmsName;

    @Column(name = "six_months")
    private Double sixMonths;

    @Column(name = "one_year")
    private Double oneYear;

    @Column(name = "three_years")
    private Double threeYears;

    @Column(name = "since_inception")
    private Double sinceInception;

    @Column(name = "benchmark_six_months")
    private Double benchmarkSixMonths;

    @Column(name = "benchmark_one_year")
    private Double benchmarkOneYear;

    @Column(name = "benchmark_three_years")
    private Double benchmarkThreeYears;

    @Column(name = "benchmark_since_inception")
    private Double benchmarkSinceInception;

    @Column(name = "created_date")
    private java.time.LocalDateTime createdDate;

    @Column(name = "last_updated")
    private java.time.LocalDateTime lastUpdated;

    @Column(name = "status")
    private String status;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPmsName() { return pmsName; }
    public void setPmsName(String pmsName) { this.pmsName = pmsName; }
    public Double getSixMonths() { return sixMonths; }
    public void setSixMonths(Double sixMonths) { this.sixMonths = sixMonths; }
    public Double getOneYear() { return oneYear; }
    public void setOneYear(Double oneYear) { this.oneYear = oneYear; }
    public Double getThreeYears() { return threeYears; }
    public void setThreeYears(Double threeYears) { this.threeYears = threeYears; }
    public Double getSinceInception() { return sinceInception; } // Fixed: Changed since_inception to sinceInception
    public void setSinceInception(Double sinceInception) { this.sinceInception = sinceInception; }
    public Double getBenchmarkSixMonths() { return benchmarkSixMonths; }
    public void setBenchmarkSixMonths(Double benchmarkSixMonths) { this.benchmarkSixMonths = benchmarkSixMonths; }
    public Double getBenchmarkOneYear() { return benchmarkOneYear; }
    public void setBenchmarkOneYear(Double benchmarkOneYear) { this.benchmarkOneYear = benchmarkOneYear; }
    public Double getBenchmarkThreeYears() { return benchmarkThreeYears; }
    public void setBenchmarkThreeYears(Double benchmarkThreeYears) { this.benchmarkThreeYears = benchmarkThreeYears; }
    public Double getBenchmarkSinceInception() { return benchmarkSinceInception; }
    public void setBenchmarkSinceInception(Double benchmarkSinceInception) { this.benchmarkSinceInception = benchmarkSinceInception; }
    public java.time.LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(java.time.LocalDateTime createdDate) { this.createdDate = createdDate; }
    public java.time.LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(java.time.LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}