package in.codifi.Utility;

import in.codifi.Response.GenericResponse;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PrepareResponse {

    /**
     * Prepares a success response with a custom message.
     * 
     * @param message The success message to include in the response
     * @return RestResponse containing a GenericResponse with success status
     */
    public org.jboss.resteasy.reactive.RestResponse<GenericResponse> prepareSuccessResponse(String message) {
        GenericResponse response = new GenericResponse();
        response.setStatus(AppConstants.SUCCESS_STATUS);
        response.setMessage(message);
        response.setResult(null);
        return org.jboss.resteasy.reactive.RestResponse.status(org.jboss.resteasy.reactive.RestResponse.Status.OK, response);
    }

    /**
     * Prepares a failed response with a custom error message.
     * 
     * @param message The error message to include in the response
     * @return RestResponse containing a GenericResponse with failed status
     */
    public org.jboss.resteasy.reactive.RestResponse<GenericResponse> prepareFailedResponse(String message) {
        GenericResponse response = new GenericResponse();
        response.setStatus(AppConstants.FAILED_STATUS);
        response.setMessage(message);
        response.setResult(null);
        return org.jboss.resteasy.reactive.RestResponse.status(org.jboss.resteasy.reactive.RestResponse.Status.INTERNAL_SERVER_ERROR, response);
    }
}