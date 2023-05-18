package com.example.food.firesbaseservice;

import com.example.food.exception.FailToUploadException;
import com.example.food.model.ProductRequest;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FirebaseService {
		
		static final String FIREBASE_SDK_JSON = "C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.11.1.RELEASE\\food-dashboard-backend\\src\\main\\resources\\firebase.json";
		static final String FIREBASE_BUCKET = "food-dashboard-988db.appspot.com";
		static final String FIREBASE_PROJECT_ID ="food-dashboard-988db";
		static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/food-dashboard-988db.appspot.com/o/%s?alt=media";		

			
		    public String upload(ProductRequest productRequest) throws IOException {
				
		        try {
		            String fileName = productRequest.getFileName();                        			// to get original file name
		            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  		// to generated random string values for file name. 

		            File file = this.convertToAFile(productRequest, fileName);                       // to convert to File
		            String TEMP_URL = this.uploadFile(file, fileName, productRequest.getFileType()); // to get uploaded file link
		            file.delete();                                                            			// to delete the copy of uploaded file stored in the project folder
		            
		            return TEMP_URL;
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            throw new FailToUploadException("Image Fail to Upload");
		        }

		    }
		    
		    private String uploadFile(File file, String fileName, String type) throws IOException {
		        BlobId blobId = BlobId.of(FIREBASE_BUCKET, fileName);
		        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();
		        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(FIREBASE_SDK_JSON));
		        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
					return String.format(DOWNLOAD_URL, java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8));
		        
		    }
		 
				 
		    
		    private File convertToAFile(ProductRequest productRequest, String fileName) throws IOException {
		        File tempFile = new File(fileName);
		        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
		        	byte[] decodedBytes = java.util.Base64.getDecoder().decode(productRequest.getBytes());
		            fos.write(decodedBytes);
		            fos.close();
		        }
		        return tempFile;
		    }
		    
		    private String getExtension(String fileName) {
		        return fileName.substring(fileName.lastIndexOf("."));
		    }
	
}
