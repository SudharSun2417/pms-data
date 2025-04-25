package in.codifi.PmsController;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import in.codifi.Entity.PmsOverview;
import in.codifi.Entity.PmsPerformance;
import in.codifi.Entity.PmsSectorAllocation;
import in.codifi.PmsService.PmsService;
import in.codifi.Response.GenericResponse;
import in.codifi.Utility.AppConstants;
import io.quarkus.logging.Log;
import org.jboss.resteasy.reactive.RestResponse;

/**
 * REST controller for PMS data operations.
 * 
 * @author Sudharsun
 */
@Path("/pms")
@Produces(MediaType.APPLICATION_JSON)
public class PmsController {

    @Inject
    PmsService pmsService;

    /**
     * Retrieves all PMS overview records.
     * 
     * @author Sudharsun
     * @return List of PmsOverview entities
     */
    @GET
    @Path("/overview")
    public List<PmsOverview> getAllPmsOverviews() {
        Log.info("Fetching all PMS overview data");
        return pmsService.getAllPmsOverviews();
    }

    /**
     * Retrieves PMS performance records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsPerformance entities
     */
    @GET
    @Path("/performance/{pmsName}")
    public List<PmsPerformance> getPerformanceByPmsName(@PathParam("pmsName") String pmsName) {
        Log.info("Fetching PMS performance for " + pmsName);
        return pmsService.getPerformanceByPmsName(pmsName);
    }

    /**
     * Retrieves PMS sector allocation records by PMS name.
     * 
     * @author Sudharsun
     * @param pmsName The PMS name
     * @return List of PmsSectorAllocation entities
     */
    @GET
    @Path("/sector-allocation/{pmsName}")
    public List<PmsSectorAllocation> getSectorAllocationsByPmsName(@PathParam("pmsName") String pmsName) {
        Log.info("Fetching PMS sector allocations for " + pmsName);
        return pmsService.getSectorAllocationsByPmsName(pmsName);
    }

    /**
     * Uploads and processes an Excel file containing PMS data.
     * 
     * @author Sudharsun
     * @param file The uploaded Excel file
     * @return RestResponse containing the processing result
     */
    @POST
    @Path("/uploadExcel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestResponse<GenericResponse> uploadExcel(@RestForm("file") FileUpload file) {
        Log.info("Processing uploaded Excel file: " + (file != null ? file.fileName() : "null"));
        return pmsService.uploadExcelFile(file);
    }

    /**
     * Test endpoint for verifying API connectivity.
     * 
     * @author Sudharsun
     * @return GenericResponse with test message
     */
    @GET
    @Path("/test")
    public GenericResponse test() {
        Log.info("Test endpoint called");
        GenericResponse response = new GenericResponse();
        response.setStatus(AppConstants.SUCCESS_STATUS);
        response.setMessage("API is operational");
        response.setResult(null);
        return response;
    }
}