

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedFile;


public interface SimpleApi {
	
	@GET("/simpleget")
//	public String simpleGet();
	public Map<String, String> simpleGet();
	
	@GET("/my_api/shop_list")
	void getMyThing(@Query("mid") String param1, Callback<List<Item>> callback);
	
	@POST("/api/uploadcontroller")
	Response uploadPdf(@Body TypedFile file);

	public Response uploadPdf(TypedFile file);
	
	
}