package web_layer.apis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "User API", description = "The API for user management")
public interface UserApi {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found")
    })
    void login(@ApiParam(value = "Username", required = true) final String username,
               @ApiParam(value = "Password", required = true) final String password);
}
