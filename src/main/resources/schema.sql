CREATE TABLE IF NOT EXISTS tbl_pms_overview (
         id BIGINT PRIMARY KEY AUTO_INCREMENT,
         company_name VARCHAR(255) NOT NULL,
         about_company VARCHAR(1000),
         pms_name VARCHAR(255) NOT NULL,
         pms_details VARCHAR(1000),
         investment_strategy VARCHAR(1000),
         fund_managers VARCHAR(1000),
         created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
         last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         status VARCHAR(50) DEFAULT 'ACTIVE',
         UNIQUE (pms_name) -- Added to support foreign key
     );

     CREATE TABLE IF NOT EXISTS tbl_pms_performance (
         id BIGINT PRIMARY KEY AUTO_INCREMENT,
         pms_name VARCHAR(255) NOT NULL,
         six_months DOUBLE,
         one_year DOUBLE,
         three_years DOUBLE,
         since_inception DOUBLE,
         benchmark_six_months DOUBLE,
         benchmark_one_year DOUBLE,
         benchmark_three_years DOUBLE,
         benchmark_since_inception DOUBLE,
         created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
         last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         status VARCHAR(50) DEFAULT 'ACTIVE',
         FOREIGN KEY (pms_name) REFERENCES tbl_pms_overview(pms_name) ON DELETE CASCADE
     );

     CREATE TABLE IF NOT EXISTS tbl_pms_sector_allocation (
         id BIGINT PRIMARY KEY AUTO_INCREMENT,
         pms_name VARCHAR(255) NOT NULL,
         sector VARCHAR(255) NOT NULL,
         weightage DOUBLE,
         created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
         last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         status VARCHAR(50) DEFAULT 'ACTIVE',
         FOREIGN KEY (pms_name) REFERENCES tbl_pms_overview(pms_name) ON DELETE CASCADE
     );