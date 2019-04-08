package com.sapbasemodule.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.UploadFileResponse;

@Component
public class UploadUtility {

	private final static Logger logger = Logger.getLogger(UploadUtility.class);

	public UploadFileResponse uploadFile(InputStream fileInputStream, String fileName) throws ServicesException {
		 String url = "";
		 
		 /*String uploadDestination = configProperties.getProperty("upload.destination.name");*/
		 String filePathAndName = "/" + "Ripple" + "/" + fileName;
//		 String filePathAndName = FILE_SEPARATOR + fileName;
		 System.out.println("File Path And Name: " + filePathAndName);
		 
		try {
				final String ACCESS_TOKEN="zAAAzWynaiAAAAAAAAAAEDK0DJarxVFPUYOup2DNmsbvM_18JdxkFiHwLEVbYG7P";
				/*final String ACCESS_TOKEN="fkzBNoV81CAAAAAAAAAAChu0NRDmJEKeW7xW8sAwkTlSI6O7DNYtzqbfeAIyze3z";*/
		        
		        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial");
		        
		        //Set acces token and config for upload
		        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		        SharedLinkMetadata slm;
				try {
					//Upload the file
					try (InputStream in = fileInputStream) {
					    FileMetadata metadata = client.files()
					    		.uploadBuilder(filePathAndName)
					    		.withMode(com.dropbox.core.v2.files.WriteMode.OVERWRITE)
					        .uploadAndFinish(in);
					    logger.debug("Filemetadata: "+metadata.toString());
					}
					
					//Get Shared Link
					slm = client.sharing().createSharedLinkWithSettings(filePathAndName, 
							SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC)
							.build());
					  url = slm.getUrl();
					  System.out.println("Upload successfull***url***"+url);
					  
				} catch (CreateSharedLinkWithSettingsErrorException e) {
					ListSharedLinksResult result = client.sharing().listSharedLinksBuilder()
						    .withPath(filePathAndName).withDirectOnly(true).start();
					url = result.getLinks().get(0).getUrl();
				}
				//TODO: Put the replace URL's in cnfigurable file
				String contentUrl = url.replace("www.dropbox.com", "dl.dropboxusercontent.com");
				UploadFileResponse uploadFileResponse = new UploadFileResponse(url, contentUrl);
				return uploadFileResponse;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServicesException("608");
		}
	}
	
	
	public UploadFileResponse uploadFile(MultipartFile file, String fileName) throws ServicesException {
		
		try {
			InputStream inputStream = new BufferedInputStream(file.getInputStream());		
			byte[] fileBytes = new byte[(int) inputStream.available()];
			System.out.println("Logo fileName " + fileName);
			System.out.println("Logo Image Length " + fileBytes.length);				
			if(StringUtils.isNotBlank(fileName) || fileBytes.length != 0 ){					
				return uploadFile(file.getInputStream(), fileName);	
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServicesException("608");
		}
	}
}
