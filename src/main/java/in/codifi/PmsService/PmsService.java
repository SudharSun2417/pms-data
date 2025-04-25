package in.codifi.PmsService;

import in.codifi.Entity.PmsOverview;
import in.codifi.Entity.PmsPerformance;
import in.codifi.Entity.PmsSectorAllocation;
import in.codifi.PmsRepository.PmsOverviewRepository;
import in.codifi.PmsRepository.PmsPerformanceRepository;
import in.codifi.PmsRepository.PmsSectorAllocationRepository;
import in.codifi.Response.GenericResponse;
import in.codifi.Utility.AppConstants;
import in.codifi.Utility.PrepareResponse;
import in.codifi.PmsServiceSpec.PmsServiceSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to handle PMS data operations, including Excel file processing.
 * 
 * @author Sudharsun
 */
@ApplicationScoped
public class PmsService implements PmsServiceSpec {
    private static final Logger logger = LogManager.getLogger(PmsService.class);
    private static final String SECTOR_WEIGHTAGE_SHEET_NAME = "Sector Allocation";
    private static final int MAX_SECTOR_FIELD_LENGTH = 255; // Adjust as needed

    @Inject
    PmsOverviewRepository overviewRepository;

    @Inject
    PmsPerformanceRepository performanceRepository;

    @Inject
    PmsSectorAllocationRepository sectorAllocationRepository;

    @Inject
    PrepareResponse prepareResponse;

    /**
     * Uploads and processes an Excel file containing PMS data, inserting it into the database.
     * 
     * @author Sudharsun
     * @param file The uploaded Excel file
     * @return RestResponse containing the processing result
     */
    @Override
    @Transactional
    public RestResponse<GenericResponse> uploadExcelFile(FileUpload file) {
        logger.info("Processing /pms/uploadExcel request with file: {}", file == null ? "null" : file.fileName());
        if (file == null || file.uploadedFile() == null) {
            logger.error("No file uploaded");
            return prepareResponse.prepareFailedResponse("No file uploaded");
        }

        try (InputStream inputStream = file.uploadedFile().toFile().toURI().toURL().openStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);

            // Log sheet names for debugging
            logger.info("Excel file sheets:");
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                logger.info("Sheet {}: {}", i, workbook.getSheetName(i));
            }

            // Validate sheet count
            if (workbook.getNumberOfSheets() < 3) {
                logger.error("Invalid Excel file: Expected at least 3 sheets, found {}", workbook.getNumberOfSheets());
                return prepareResponse.prepareFailedResponse("Invalid Excel file: Expected at least 3 sheets");
            }

            // Process Sheet 1: PMS Overview (index 0)
            Sheet overviewSheet = workbook.getSheetAt(0);
            List<PmsOverview> overviews = processOverviewSheet(overviewSheet);
            if (overviews.isEmpty()) {
                logger.warn("No valid data found in PMS Overview sheet: {}", overviewSheet.getSheetName());
            } else {
                logger.info("Persisting {} PmsOverview records", overviews.size());
                try {
                    overviewRepository.persist(overviews);
                } catch (Exception e) {
                    logger.error("Failed to persist PmsOverview records: {}", e.getMessage(), e);
                    throw e;
                }
            }

            // Process Sheet 2: PMS Performance (index 1)
            Sheet performanceSheet = workbook.getSheetAt(1);
            List<PmsPerformance> performances = processPerformanceSheet(performanceSheet);
            if (performances.isEmpty()) {
                logger.warn("No valid data found in PMS Performance sheet: {}", performanceSheet.getSheetName());
            } else {
                logger.info("Persisting {} PmsPerformance records", performances.size());
                try {
                    performanceRepository.persist(performances);
                } catch (Exception e) {
                    logger.error("Failed to persist PmsPerformance records: {}", e.getMessage(), e);
                    throw e;
                }
            }

            // Process Sheet 3: PMS Sector Allocation (index 2)
            Sheet sectorSheet = workbook.getSheetAt(2);
            List<PmsSectorAllocation> sectorAllocations = processSectorAllocationSheet(sectorSheet);
            if (sectorAllocations.isEmpty()) {
                logger.warn("No valid data found in PMS Sector Allocation sheet: {}", sectorSheet.getSheetName());
            } else {
                logger.info("Persisting {} PmsSectorAllocation records", sectorAllocations.size());
                try {
                    sectorAllocationRepository.persist(sectorAllocations);
                } catch (Exception e) {
                    logger.error("Failed to persist PmsSectorAllocation records: {}", e.getMessage(), e);
                    throw e;
                }
            }

            logger.info("Excel file processed successfully: {}", file.fileName());
            return prepareResponse.prepareSuccessResponse("Excel file processed and data inserted successfully");
        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage(), e);
            return prepareResponse.prepareFailedResponse("Invalid or corrupted Excel file: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing Excel file: {}", e.getMessage(), e);
            return prepareResponse.prepareFailedResponse(AppConstants.INTERNAL_ERROR + ": " + e.getMessage());
        }
    }

    /**
     * Processes the PMS overview sheet and extracts data into PmsOverview entities.
     * 
     * @author Sudharsun
     * @param sheet The Excel sheet containing PMS overview data
     * @return List of PmsOverview entities
     */
    private List<PmsOverview> processOverviewSheet(Sheet sheet) {
        List<PmsOverview> overviews = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        logger.debug("Processing Overview sheet: {}, no rows or columns skipped", sheet.getSheetName());
        for (int i = 2; i <= sheet.getLastRowNum(); i++) { // Process all rows
            Row row = sheet.getRow(i);
            if (row == null) {
                logger.warn("Row {} is null, skipping", i);
                continue;
            }

            String pmsName = getCellStringValue(row.getCell(2), formatter);
            if (pmsName.isEmpty()) {
                logger.warn("Skipping row {}: pmsName is empty", i + 1);
                continue;
            }

            logger.debug("Processing row {}: companyName={}, aboutCompany={}, pmsName={}, pmsDetails={}, investmentStrategy={}, fundManagers={}", 
                i + 1,
                getCellStringValue(row.getCell(0), formatter),
                getCellStringValue(row.getCell(1), formatter),
                pmsName,
                getCellStringValue(row.getCell(3), formatter),
                getCellStringValue(row.getCell(4), formatter),
                getCellStringValue(row.getCell(5), formatter));

            PmsOverview overview = new PmsOverview();
            overview.setCompanyName(getCellStringValue(row.getCell(0), formatter));
            overview.setAboutCompany(truncate(getCellStringValue(row.getCell(1), formatter), 1000));
            overview.setPmsName(pmsName);
            overview.setPmsDetails(truncate(getCellStringValue(row.getCell(3), formatter), 1000));
            overview.setInvestmentStrategy(truncate(getCellStringValue(row.getCell(4), formatter), 1000));
            overview.setFundManagers(truncate(getCellStringValue(row.getCell(5), formatter), 1000));
            overviews.add(overview);
        }
        logger.info("Processed {} PmsOverview records", overviews.size());
        return overviews;
    }

    /**
     * Processes the PMS performance sheet and extracts data into PmsPerformance entities.
     * 
     * @author Sudharsun
     * @param sheet The Excel sheet containing PMS performance data
     * @return List of PmsPerformance entities
     */
    private List<PmsPerformance> processPerformanceSheet(Sheet sheet) {
        List<PmsPerformance> performances = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        logger.debug("Processing Performance sheet: {}, no rows or columns skipped", sheet.getSheetName());
        for (int i = 0; i <= sheet.getLastRowNum(); i++) { // Process all rows
            Row row = sheet.getRow(i);
            if (row == null) {
                logger.warn("Row {} is null, skipping", i);
                continue;
            }

            String pmsNameOrLabel = getCellStringValue(row.getCell(0), formatter).trim();
            logger.debug("Processing row {}: column 0 value = {}", i + 1, pmsNameOrLabel);

            // Check if this row contains a pmsName (start of a new group)
            if (!pmsNameOrLabel.isEmpty() && !pmsNameOrLabel.startsWith("Performance") && !pmsNameOrLabel.startsWith("Index")) {
                // Ensure there are enough rows for the group (performance and benchmark)
                if (i + 2 <= sheet.getLastRowNum()) {
                    Row performanceRow = sheet.getRow(i + 1);
                    Row benchmarkRow = sheet.getRow(i + 2);
                    if (performanceRow != null && benchmarkRow != null) {
                        String performanceLabel = getCellStringValue(performanceRow.getCell(0), formatter).trim();
                        String benchmarkLabel = getCellStringValue(benchmarkRow.getCell(0), formatter).trim();

                        if (performanceLabel.startsWith("Performance") && benchmarkLabel.startsWith("Index")) {
                            PmsPerformance performance = new PmsPerformance();
                            performance.setPmsName(pmsNameOrLabel);

                            // Performance M2M (%) values (row i + 1, columns 1 to 4)
                            performance.setSixMonths(getCellNumericValue(performanceRow.getCell(1)));
                            performance.setOneYear(getCellNumericValue(performanceRow.getCell(2)));
                            performance.setThreeYears(getCellNumericValue(performanceRow.getCell(3)));
                            performance.setSinceInception(getCellNumericValue(performanceRow.getCell(4)));

                            // Index Benchmark (%) values (row i + 2, columns 1 to 4)
                            performance.setBenchmarkSixMonths(getCellNumericValue(benchmarkRow.getCell(1)));
                            performance.setBenchmarkOneYear(getCellNumericValue(benchmarkRow.getCell(2)));
                            performance.setBenchmarkThreeYears(getCellNumericValue(benchmarkRow.getCell(3)));
                            performance.setBenchmarkSinceInception(getCellNumericValue(benchmarkRow.getCell(4)));

                            logger.debug("Processed PmsPerformance: pmsName={}, sixMonths={}, oneYear={}, threeYears={}, sinceInception={}, benchmarkSixMonths={}, benchmarkOneYear={}, benchmarkThreeYears={}, benchmarkSinceInception={}",
                                pmsNameOrLabel,
                                performance.getSixMonths(),
                                performance.getOneYear(),
                                performance.getThreeYears(),
                                performance.getSinceInception(),
                                performance.getBenchmarkSixMonths(),
                                performance.getBenchmarkOneYear(),
                                performance.getBenchmarkThreeYears(),
                                performance.getBenchmarkSinceInception());

                            performances.add(performance);
                            i += 2; // Skip to the next group
                        } else {
                            logger.warn("Row {}: Unexpected performance or benchmark label format, skipping group", i + 1);
                        }
                    } else {
                        logger.warn("Row {}: Insufficient rows for performance and benchmark data, skipping", i + 1);
                    }
                }
            }
        }
        logger.info("Processed {} PmsPerformance records", performances.size());
        return performances;
    }

    /**
     * Processes the PMS sector allocation sheet and extracts data into PmsSectorAllocation entities.
     * 
     * @author Sudharsun
     * @param sheet The Excel sheet containing PMS sector allocation data
     * @return List of PmsSectorAllocation entities
     */
    private List<PmsSectorAllocation> processSectorAllocationSheet(Sheet sheet) {
        List<PmsSectorAllocation> sectorAllocations = new ArrayList<>();
        Map<String, Integer> pmsSectorCount = new HashMap<>();
        String lastPmsName = null;

        logger.info("Found Sector Weightage sheet at index {}", sheet.getWorkbook().getSheetIndex(sheet));
        logger.debug("Processing Sector Allocation sheet: {}, no rows or columns skipped", sheet.getSheetName());

        // Log raw data for debugging
        logger.debug("Sector Allocation sheet raw data:");
        for (int i = 0; i <= sheet.getLastRowNum(); i++) { // Process all rows
            Row row = sheet.getRow(i);
            if (row == null) {
                logger.warn("Row {} is null, skipping", i);
                continue;
            }

            String pmsName = getCellValueOrDefault(row, 0, "").trim();
            if (!pmsName.isEmpty()) {
                lastPmsName = pmsName;
            } else {
                pmsName = lastPmsName != null ? lastPmsName : "";
            }

            String sector = getCellValueOrDefault(row, 1, "").trim();
            String weightageStr = getCellValueOrDefault(row, 2, "").trim();

            logger.debug("Sector Weightage Row {}: pmsName={}, sector={}, weightage={}", i + 1, pmsName, sector, weightageStr);

            // Skip rows where sector or weightage is empty
            if (sector.isEmpty() || weightageStr.isEmpty()) {
                logger.warn("Row {}: Missing sector or weightage (pmsName={}, sector={}, weightage={}), skipping", 
                    i + 1, pmsName, sector, weightageStr);
                continue;
            }

            // Convert weightage to Double
            Double weightage = null;
            try {
                weightage = Double.parseDouble(weightageStr);
                if (weightage < 0) {
                    logger.warn("Row {}: Negative weightage (pmsName={}, sector={}, weightage={}), skipping", 
                        i + 1, pmsName, sector, weightage);
                    continue;
                }
            } catch (NumberFormatException e) {
                logger.warn("Row {}: Invalid weightage format (pmsName={}, sector={}, weightage={}), skipping", 
                    i + 1, pmsName, sector, weightageStr);
                continue;
            }

            PmsSectorAllocation entity = new PmsSectorAllocation();
            entity.setPmsName(truncate(pmsName, MAX_SECTOR_FIELD_LENGTH));
            entity.setSector(truncate(sector, 500));
            entity.setWeightage(weightage);
            sectorAllocations.add(entity);

            // Track sector count per pmsName
            pmsSectorCount.merge(pmsName, 1, Integer::sum);
            logger.debug("Processed PmsSectorAllocation: {} - {} (weightage: {})", pmsName, sector, weightage);
        }

        // Validate sector counts
        for (Map.Entry<String, Integer> entry : pmsSectorCount.entrySet()) {
            String pmsName = entry.getKey();
            int count = entry.getValue();
            if (count != 5) {
                logger.warn("PMS {} has {} sectors, expected 5", pmsName, count);
            }
        }
        logger.info("Processed {} PmsSectorAllocation records for {} PMS names", 
            sectorAllocations.size(), pmsSectorCount.size());

        return sectorAllocations;
    }

    /**
     * Extracts cell value or returns a default if the cell is null.
     * 
     * @param row The Excel row
     * @param columnIndex The column index
     * @param defaultValue The default value if cell is null
     * @return String value of the cell or default value
     */
    private String getCellValueOrDefault(Row row, int columnIndex, String defaultValue) {
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return defaultValue;
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    /**
     * Truncates a string to a specified length to prevent database errors.
     * 
     * @param input The input string
     * @param maxLength The maximum length
     * @return Truncated string
     */
    private String truncate(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        }
        logger.debug("Truncating string to {} characters: {}", maxLength, input);
        return input.substring(0, maxLength);
    }

    /**
     * Extracts string value from an Excel cell using DataFormatter (for consistency with other methods).
     * 
     * @param cell The Excel cell
     * @param formatter The DataFormatter instance
     * @return String value of the cell, or empty string if null/blank
     */
    private String getCellStringValue(Cell cell, DataFormatter formatter) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        return formatter.formatCellValue(cell).trim();
    }

    /**
     * Extracts numeric value from an Excel cell (for other sheets).
     * 
     * @param cell The Excel cell
     * @return Double value of the cell, or null if invalid
     */
    private Double getCellNumericValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();
                return value.isEmpty() ? null : Double.parseDouble(value);
            }
            return null;
        } catch (Exception e) {
            logger.warn("Invalid numeric value in cell: {}", cell, e);
            return null;
        }
    }

    /**
     * Retrieves all PMS overview records from the database.
     * 
     * @author Sudharsun
     * @return List of PmsOverview entities
     */
    public List<PmsOverview> getAllPmsOverviews() {
        logger.debug("Fetching all PmsOverview records");
        try {
            return overviewRepository.findAll().list();
        } catch (Exception e) {
            logger.error("Error retrieving PMS overviews: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves PMS performance records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsPerformance entities
     */
    public List<PmsPerformance> getPerformanceByPmsName(String pmsName) {
        logger.debug("Fetching PmsPerformance for pmsName: {}", pmsName);
        try {
            if (pmsName == null || pmsName.isEmpty()) {
                logger.warn("Invalid pmsName for performance query");
                return new ArrayList<>();
            }
            return performanceRepository.find("pmsName", pmsName).list();
        } catch (Exception e) {
            logger.error("Error retrieving PMS performance for {}: {}", pmsName, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves PMS sector allocation records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsSectorAllocation entities
     */
    public List<PmsSectorAllocation> getSectorAllocationsByPmsName(String pmsName) {
        logger.debug("Fetching PmsSectorAllocation for pmsName: {}", pmsName);
        try {
            if (pmsName == null || pmsName.isEmpty()) {
                logger.warn("Invalid pmsName for sector allocation query");
                return new ArrayList<>();
            }
            return sectorAllocationRepository.find("pmsName", pmsName).list();
        } catch (Exception e) {
            logger.error("Error retrieving PMS sector allocations for {}: {}", pmsName, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}