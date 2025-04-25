package in.codifi.PmsServiceSpec;

import org.jboss.resteasy.reactive.RestResponse;
import in.codifi.Response.GenericResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

/**
 * Service interface for PMS data operations.
 * 
 */
public interface PmsServiceSpec {
    /**
     * Uploads and processes an Excel file containing PMS data.
     * 
     * @author Sudharsun
     * @param file The uploaded Excel file
     * @return RestResponse containing the processing result
     */
    RestResponse<GenericResponse> uploadExcelFile(FileUpload file);
}