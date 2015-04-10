


import java.io.File;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class RetrofitClient {
//	private static final String API_URL = "http://localhost:8080";
	private static final String API_URL = "http://localhost:8080/assignment4";

//	static JacksonConverter converter = new JacksonConverter(new ObjectMapper());
	
	
	public static void main(String[] args) {
                // Build the Retrofit REST adaptor pointing to the URL specified
		RestAdapter restAdapter = new RestAdapter.Builder()
	            .setEndpoint(API_URL)
//	            .setConverter(converter)
	            .build();
	
	        // Create an instance of our SimpleApi interface.
		SimpleApi simpleApi = restAdapter.create(SimpleApi.class);
	
		// Call our method
		Map<String, String> mapGet = simpleApi.simpleGet();
		System.out.println("simpleApi.simpleGet()=<<" + mapGet + ">>");
		
		String pdfFilePath = "C:\\Users\\Masood\\Desktop\\winter_sem_4\\PCSMA\\Assignments\\Assignment_4\\testpdf.pdf";
//		simpleApi.uploadPdf(@Body new TypedFile("application/pdf", new File(pdfFilePath));// MIME TYPE and File
		Response response = simpleApi.uploadPdf(new TypedFile("application/pdf", new File(pdfFilePath)));
		
		
	}

}