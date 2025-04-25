package in.codifi.PmsControllerSpec;

import in.codifi.Entity.PmsOverview;
import in.codifi.Entity.PmsPerformance;
import in.codifi.Entity.PmsSectorAllocation;
import in.codifi.Response.GenericResponse;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

/**
 * Interface defining the contract for PMS controller operations.
 *
 * @author Sudharsun
 */
public interface PmsControllerSpec {

    /**
     * Retrieves all PMS overview records.
     * 
     * @author Sudharsun
     * @return List of PmsOverview entities
     */
    List<PmsOverview> getAllPmsOverviews();

    /**
     * Retrieves PMS performance records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsPerformance entities
     */
    List<PmsPerformance> getPerformanceByPmsName(String pmsName);

    /**
     * Retrieves PMS sector allocation records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsSectorAllocation entities
     */
    List<PmsSectorAllocation> getSectorAllocationsByPmsName(String pmsName);

    /**
     * Uploads and processes an Excel file containing PMS data.
     * 
     * @author Sudharsun
     * @param file The uploaded Excel file
     * @return RestResponse containing the processing result
     */
    RestResponse<GenericResponse> uploadExcel(FileUpload file);

    /**
     * Test endpoint for verifying API connectivity.
     * 
     * @author Sudharsun
     * @return GenericResponse with test message
     */
    GenericResponse test();
}