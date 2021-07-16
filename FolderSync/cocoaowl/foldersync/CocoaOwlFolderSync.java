
package cocoaowl.foldersync;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue.ValueType;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CocoaOwlFolderSync {

	static CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    static HttpClientContext httpClientContext = HttpClientContext.create();
    static Date lastSyncedDate = null;
    static final SimpleDateFormat simpleDateFormatWithUnderscore = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
    static final String HOST_NAME = "localhost";
    static final int SERVER_PORT_NUMBER = 8080;
    static final String CLIENT_NAME = "testclient";
    static final String USERNAME = "jbent";
    static final String PASSWORD = "koala";
    static final long SERVER_ROOT_FOLDER_ID = 24;
    static final String NON_DOCUMENT_FILENAME_EXT = "_NON_DOCUMENT_ENTITYTYPE_";
    static List<Long> syncedServerEntityIdList = new ArrayList<Long>();

    public final static void main(String[] args) throws Exception {

    	System.out.println("CocoaOwl Folder Synchronization initiated for user : "+USERNAME);
        lastSyncedDate = getLastSyncedOn();
        if(lastSyncedDate == null){
        	doAuthenticate(closeableHttpClient, httpClientContext);
        	lastSyncedDate = getLastSyncedOn();
        }
        
        JsonArray rootFolderChildren = getSubFoldersFromServer(closeableHttpClient, httpClientContext, SERVER_ROOT_FOLDER_ID);
    	if(rootFolderChildren == null)
    		throw new Exception("Unable to connect or authenticate . . . . ");
    	
    	File cocoaOwlClientDirectory = new File(System.getProperty("user.home") + File.separatorChar + "CocoaOwl");
    	if(!cocoaOwlClientDirectory.exists())
    		cocoaOwlClientDirectory.mkdirs();
    	
    	syncSubFoldersFromClientToServer(SERVER_ROOT_FOLDER_ID,cocoaOwlClientDirectory);
    	syncSubFoldersFromServerToClient(SERVER_ROOT_FOLDER_ID,cocoaOwlClientDirectory);
    	updateLastSyncedOnServer();
    	System.out.println("CocoaOwl Folder Synchronization completed at : " + new Date());
    }
    
	private static void syncSubFoldersFromClientToServer(Long serverParentFolderId, File clientParentDirectory) throws IOException, ParseException, URISyntaxException {
    	
    	JsonArray serverFolders = getSubFoldersFromServer(closeableHttpClient, httpClientContext, serverParentFolderId);
    	JsonArray serverFiles = getFilesOfFolderFromServer(closeableHttpClient, httpClientContext, serverParentFolderId);
    	
		for(File clientFileOrFolder : clientParentDirectory.listFiles()){
			
			boolean isClientFileOrFolderExistsOnServer = false;
			
			if(clientFileOrFolder.isDirectory()){//if clientFileOrFolder is a folder
				
				Long serverFolderId = (long) 0;
				
				for(int i = 0; i < serverFolders.size(); i++){
					if(clientFileOrFolder.getName().equals(serverFolders.getJsonObject(i).getString("name"))){
						isClientFileOrFolderExistsOnServer = true;
						serverFolderId = Long.parseLong(serverFolders.getJsonObject(i).getString("id"));
						break;
					}
				}
				
				if(!isClientFileOrFolderExistsOnServer){
					
					Path clientFileOrFolderPath = FileSystems.getDefault().getPath(clientFileOrFolder.getAbsolutePath());
					BasicFileAttributes clientFileOrFolderBasicAttributes = Files.readAttributes(clientFileOrFolderPath, BasicFileAttributes.class);
					FileTime clientFileCreationTime = clientFileOrFolderBasicAttributes.creationTime();
					Date clientFileOrFolderCreationDate = new Date(clientFileCreationTime.toMillis());
					
					if(clientFileOrFolderCreationDate.after(lastSyncedDate)){
						
						serverFolderId = createNewFolderOnServer(clientFileOrFolder.getName(),serverParentFolderId);
						syncSubFoldersFromClientToServer(serverFolderId, clientFileOrFolder);
						
					}else{
						deleteRecursive(clientFileOrFolder);
					}
					
				}else{
					syncSubFoldersFromClientToServer(serverFolderId, clientFileOrFolder);
				}
				//syncedServerEntityIdList.add(serverFolderId);
			}else{ // if clientFileOrFolder object is a file

				JsonObject fileOnServer = null;
				for(int i = 0; i < serverFiles.size(); i++){
					
					if(clientFileOrFolder.getName().contains(NON_DOCUMENT_FILENAME_EXT) && clientFileOrFolder.getName().endsWith(".csv")){
						
						String clientFileName = clientFileOrFolder.getName();
						long entityId = Long.parseLong(clientFileName.substring(clientFileName.lastIndexOf('_')+1, clientFileName.lastIndexOf('.')));
						if((""+entityId).equals(serverFiles.getJsonObject(i).getString("id"))){
							isClientFileOrFolderExistsOnServer = true;
							fileOnServer = (serverFiles.getJsonObject(i));
							break;
						}
						
					}else if(clientFileOrFolder.getName().equals(serverFiles.getJsonObject(i).getString("name"))){
						isClientFileOrFolderExistsOnServer = true;
						fileOnServer = (serverFiles.getJsonObject(i));
						break;
					}
				}
				
				Path clientFileOrFolderPath = FileSystems.getDefault().getPath(clientFileOrFolder.getAbsolutePath());
				BasicFileAttributes clientFileOrFolderBasicAttributes = Files.readAttributes(clientFileOrFolderPath, BasicFileAttributes.class);
				
				if(!isClientFileOrFolderExistsOnServer){
					
					FileTime clientFileOrFolderCreationTime = clientFileOrFolderBasicAttributes.creationTime();
					Date clientFileOrFolderCreationDate = new Date(clientFileOrFolderCreationTime.toMillis());
					
					if(clientFileOrFolderCreationDate.after(lastSyncedDate)){
						if(clientFileOrFolder.getName().contains(NON_DOCUMENT_FILENAME_EXT) && clientFileOrFolder.getName().endsWith(".csv"))
							clientFileOrFolder.delete();
						else
							createNewFileOnServer(clientFileOrFolder,serverParentFolderId);
					}else{
						clientFileOrFolder.delete();
					}
				}else{
					
					FileTime clientFileOrFolderModTime = clientFileOrFolderBasicAttributes.lastModifiedTime();
					Date clientFileOrFolderModDate = new Date(clientFileOrFolderModTime.toMillis());
					
					if(clientFileOrFolderModDate.after(simpleDateFormatWithUnderscore.parse(fileOnServer.getString("modDate")))){
						
						if(clientFileOrFolder.getName().contains(NON_DOCUMENT_FILENAME_EXT) && clientFileOrFolder.getName().endsWith(".csv")){
							updateNonDocumentEntityOnServer(Long.parseLong(fileOnServer.getString("id")), clientFileOrFolder);
						}else{
							// TODO in phase 2 update the file based on the entityId to retain the same entityId instead of delete and re-create.
							deleteFileOrFolderOnServer(Long.parseLong(fileOnServer.getString("id")));
							createNewFileOnServer(clientFileOrFolder, serverParentFolderId);
						}
						
					}else{
						syncServerFileToClient(Long.parseLong(fileOnServer.getString("id")), clientFileOrFolder.getParentFile(), fileOnServer.getString("entityTypeName"));
					}
				}
			}
		}
	}    

	private static void syncSubFoldersFromServerToClient(Long parentId, File clientParentDirectory) throws IOException, ParseException, URISyntaxException {
    	
		JsonArray serverFolders = getSubFoldersFromServer(closeableHttpClient, httpClientContext, parentId);
    	JsonArray serverFiles = getFilesOfFolderFromServer(closeableHttpClient, httpClientContext, parentId);
    	
    	for(int i = 0; i < serverFolders.size(); i++){
    		
    		Long serverFolderId = Long.parseLong(serverFolders.getJsonObject(i).getString("id"));
//    		if(syncedServerEntityIdList.contains(serverFolderId))
//    			continue;
    		File clientDirectory = null;
			boolean isServerFolderExistsOnClient = false;
				for(File clientDir : clientParentDirectory.listFiles()){
					if(clientDir.getName().equals(serverFolders.getJsonObject(i).getString("name"))){
						isServerFolderExistsOnClient = true;
						clientDirectory = clientDir;
						break;
					}
				}
				if(!isServerFolderExistsOnClient){
					
					Date serverFileOrFolderModDate = simpleDateFormatWithUnderscore.parse(serverFolders.getJsonObject(i).getString("modDate"));
					if(serverFileOrFolderModDate.after(lastSyncedDate)){
						
						File newClientDirectory = new File(clientParentDirectory.getAbsolutePath() + File.separatorChar + serverFolders.getJsonObject(i).getString("name"));
				    	if(!newClientDirectory.exists())
				    		newClientDirectory.mkdirs();
				    	
				    	syncSubFoldersFromServerToClient(serverFolderId, newClientDirectory);
				    	
					}else{
						deleteFileOrFolderOnServer(serverFolderId);
					}
					
				}else{
					syncSubFoldersFromServerToClient(serverFolderId, clientDirectory);
				}
		}
    	
    	for(int i = 0; i < serverFiles.size(); i++){
    		
    		Long serverFileId = Long.parseLong(serverFiles.getJsonObject(i).getString("id"));
    		if(syncedServerEntityIdList.contains(serverFileId))
    			continue;
    		File clientFile = null;
			boolean isServerFileExistsOnClient = false;
			
			for(File clientFil : clientParentDirectory.listFiles()){
				if(clientFil.isFile() && clientFil.getName().equals(serverFiles.getJsonObject(i).getString("name"))){
					isServerFileExistsOnClient = true;
					clientFile = clientFil;
					break;
				}
			}
			
			if(!isServerFileExistsOnClient){
				
				Date serverFileModDate = simpleDateFormatWithUnderscore.parse(serverFiles.getJsonObject(i).getString("modDate"));
				if(serverFileModDate.after(lastSyncedDate)){
			    	syncServerFileToClient(Long.parseLong(serverFiles.getJsonObject(i).getString("id")),clientParentDirectory, serverFiles.getJsonObject(i).getString("entityTypeName"));
				}else{
					deleteFileOrFolderOnServer(serverFileId);
				}
				
			}else{
				

				Path clientFileOrFolderPath = FileSystems.getDefault().getPath(clientFile.getAbsolutePath());
				BasicFileAttributes clientFileBasicAttributes = Files.readAttributes(clientFileOrFolderPath, BasicFileAttributes.class);
				FileTime clientFileModTime = clientFileBasicAttributes.lastModifiedTime();
				Date clientFileModDate = new Date(clientFileModTime.toMillis());
				
				if(clientFileModDate.after(simpleDateFormatWithUnderscore.parse(serverFiles.getJsonObject(i).getString("modDate")))){
					
					deleteFileOrFolderOnServer(Long.parseLong(serverFiles.getJsonObject(i).getString("id")));
					createNewFileOnServer(clientFile, Long.parseLong(serverFiles.getJsonObject(i).getString("parent")));
					
				}else{
					syncServerFileToClient(Long.parseLong(serverFiles.getJsonObject(i).getString("id")), clientFile.getParentFile(),serverFiles.getJsonObject(i).getString("entityTypeName"));
				}
			}
		}
    	
	}
    
    private static void syncServerFileToClient(Long entityId, File newClientFileParent, String entityTypeName) throws URISyntaxException, UnsupportedOperationException, IOException {
		// /files/images/loadUserFile    usersFilePath
    	
    	//String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
    	
    	URI uri = new URIBuilder()
				.setScheme("http")
				.setHost(HOST_NAME)
				.setPort(SERVER_PORT_NUMBER)
		        .setPath("/app/" + CLIENT_NAME + "/entity/get/document/csv/file/")
				.addParameter("entityId", entityId+"")
				.build();
    	
    	HttpGet get = new HttpGet();
    	get.setURI(uri);
    	CloseableHttpResponse response = closeableHttpClient.execute(get, httpClientContext);
    	String fileName = "file.dat";
    	Header contentDispositionHeader = response.getFirstHeader("Content-disposition");
    	if(contentDispositionHeader != null && contentDispositionHeader.getElements().length > 0){
    		HeaderElement headerElement = contentDispositionHeader.getElements()[0];
    		if(headerElement != null)
    			fileName = headerElement.getParameterByName("fileName").getValue();
    	}
    	if(!entityTypeName.equals("Document")){
    		for(File f : newClientFileParent.listFiles()){
    			if(f.getName().contains(NON_DOCUMENT_FILENAME_EXT) && f.getName().contains(""+entityId) && f.getName().endsWith(".csv")){
    				f.delete();
				}
    		}
    		fileName = fileName.substring(0, fileName.lastIndexOf('.')) + NON_DOCUMENT_FILENAME_EXT + entityId + ".csv";
    	}

        InputStream is = response.getEntity().getContent();
        File outFile = new File(newClientFileParent.getAbsolutePath() + File.separatorChar + fileName);
        FileOutputStream fOut = new FileOutputStream(outFile);
		IOUtils.copy(is, fOut);
		fOut.close();
		is.close();
        response.close();
	}

	private static void deleteFileOrFolderOnServer(Long serverFolderId) {
		//  /entity/remove/{entityId}
    	HttpPost post = new HttpPost();
    	CloseableHttpResponse intialResponse = null;
    	try{
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost(HOST_NAME)
					.setPort(SERVER_PORT_NUMBER)
			        .setPath("/app/" + CLIENT_NAME + "/entity/remove/"+serverFolderId).build();
			
			post.setURI(uri);
			intialResponse = closeableHttpClient.execute(post, httpClientContext);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
    			intialResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}

	private static void createNewFileOnServer(File clientFile, Long parentId) throws IOException, URISyntaxException {
	
        try {
        	String newEntityId = "";
            HttpGet httpGet = new HttpGet();
        	try{
        		CloseableHttpResponse quickSaveResponse = null;
    			URI uri = new URIBuilder()
    					.setScheme("http")
    					.setHost(HOST_NAME)
    					.setPort(SERVER_PORT_NUMBER)
    			        .setPath("/app/" + CLIENT_NAME + "/entity/quick/save/")
    					.addParameter("attributeId", 170+"")
    					.addParameter("value", clientFile.getName()+"")
    					.build();
    			
    			httpGet.setURI(uri);
    			quickSaveResponse = closeableHttpClient.execute(httpGet, httpClientContext);
    			try {
                    HttpEntity resEntity = quickSaveResponse.getEntity();
                    if (resEntity != null) {
                    	InputStream is = quickSaveResponse.getEntity().getContent();
            	        JsonReader jReader = Json.createReader(is);
            			newEntityId = jReader.readObject().getInt("newEntityId")+"";
            			is.close();
            			quickSaveResponse.close();
                    }
                    EntityUtils.consume(resEntity);
                } finally {
                    quickSaveResponse.close();
                }
        	}catch(Exception e){
        		e.printStackTrace();
        	}      	
        	
        	
        	URI fileUploadURI = new URIBuilder()
					.setScheme("http")
					.setHost(HOST_NAME)
					.setPort(SERVER_PORT_NUMBER)
			        .setPath("/app/" + CLIENT_NAME + "/files/images/upload")
					.addParameter("entityId", newEntityId+"")
					.addParameter("attributeId", 171+"")
					.addParameter("fileCount", 1+"")
					.build();
            HttpPost fileUploadHttpPost = new HttpPost();
            fileUploadHttpPost.setURI(fileUploadURI);

            FileBody bin = new FileBody(clientFile);
            StringBody comment = new StringBody("A binary file of some kind", ContentType.MULTIPART_FORM_DATA);

            HttpEntity fileUploadReqestEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .addPart("comment", comment)
                    .build();
            fileUploadHttpPost.setEntity(fileUploadReqestEntity);

            
            CloseableHttpResponse fileUploadResponse = closeableHttpClient.execute(fileUploadHttpPost, httpClientContext);
            try {
                HttpEntity fileUploadResponseEntity = fileUploadResponse.getEntity();
                if (fileUploadResponseEntity != null) {
                	InputStream is = fileUploadResponse.getEntity().getContent();
        	        JsonReader jReader = Json.createReader(is);
        			newEntityId = jReader.readObject().getInt("entId")+"";
        			is.close();
                }
                EntityUtils.consume(fileUploadResponseEntity);
            } finally {
                fileUploadResponse.close();
            }
            
            httpGet = new HttpGet();
        	CloseableHttpResponse intialResponse = null;
        	try{
    			fileUploadURI = new URIBuilder()
    					.addParameter("baseEntityId", parentId+"")
    					.addParameter("entityToRelate", newEntityId+"")
    					.addParameter("relationName", "folderChild")
    					.setScheme("http")
    					.setHost(HOST_NAME)
    					.setPort(SERVER_PORT_NUMBER)
    			        .setPath("/app/" + CLIENT_NAME + "/entity/save/relation")
    			        .build();
    			
    			httpGet.setURI(fileUploadURI);
    			intialResponse = closeableHttpClient.execute(httpGet, httpClientContext);
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
        		try {
        			intialResponse.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
            fileUploadResponse.close();
            if(newEntityId != "")
            	syncedServerEntityIdList.add(Long.parseLong(newEntityId));
        } finally {
            
        }
    
		
	}

	private static JsonArray getFilesOfFolderFromServer(CloseableHttpClient cocoaOwlFolderSyncClient,
			HttpClientContext clientContext, Long folderId) {
    	JsonArray result = null;
    	CloseableHttpResponse intialResponse = null;
    	try{
	    	HttpGet intialGet = new HttpGet();
	    	URI uri = new URIBuilder()
	    			.setScheme("http")
	    			.setHost(HOST_NAME)
	    			.setPort(SERVER_PORT_NUMBER)
	    			.setPath("/app/" + CLIENT_NAME + "/folder/treeable/children/" + folderId)
	    			.build();
	    	//System.out.pr
	    	intialGet.setURI(uri);
	        intialResponse = cocoaOwlFolderSyncClient.execute(intialGet, clientContext);
	        InputStream is = intialResponse.getEntity().getContent();
	        JsonReader jReader = Json.createReader(is);
			result = jReader.readObject().getJsonArray("children");
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				intialResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return result;
    }

	private static void deleteRecursive(File clientDir) {
		for(File f : clientDir.listFiles()){
			if(f.isDirectory())
				deleteRecursive(f);
			else
				f.delete();
		}
		clientDir.delete();
	}

	private static long createNewFolderOnServer(String name, Long parentId) {
		// /entity/new/folder  newFolderName   parentFolderId
    	HttpGet get = new HttpGet();
    	CloseableHttpResponse response = null;
    	try{
			URI uri = new URIBuilder()
					.addParameter("newFolderName", name)
					.addParameter("parentFolderId", parentId+"")
					.setScheme("http")
					.setHost(HOST_NAME)
					.setPort(SERVER_PORT_NUMBER)
			        .setPath("/app/" + CLIENT_NAME + "/entity/new/folder/").build();
			
			get.setURI(uri);
			response = closeableHttpClient.execute(get, httpClientContext);
			InputStream is = response.getEntity().getContent();
	        JsonReader jReader = Json.createReader(is);
	        JsonObject j = jReader.readObject();
	        long newEntityId = 0;
	        if(ValueType.NUMBER.toString().equals(j.get("entityId").getValueType().toString()))
	        	newEntityId = Long.parseLong(j.getJsonNumber("entityId").toString());
			is.close();
			return newEntityId;
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
    			response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return 0;
	}

	private static JsonArray getSubFoldersFromServer(CloseableHttpClient cocoaOwlFolderSyncClient, HttpClientContext clientContext, long folderId){
    	JsonArray result = null;
    	CloseableHttpResponse intialResponse = null;
    	try{
    		
	    	HttpGet intialGet = new HttpGet();
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost(HOST_NAME)
					.setPort(SERVER_PORT_NUMBER)
			        .setPath("/app/" + CLIENT_NAME + "/folder/subFolders/" + folderId).build();
			intialGet.setURI(uri);
			
	        intialResponse = cocoaOwlFolderSyncClient.execute(intialGet, clientContext);
	        InputStream is = intialResponse.getEntity().getContent();
	        JsonReader jReader = Json.createReader(is);
			result = jReader.readObject().getJsonArray("children");
			is.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				intialResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return result;
    }
    
    private static void doAuthenticate(CloseableHttpClient cocoaOwlFolderSyncClient, HttpClientContext clientContext){
    	HttpPost post = new HttpPost();
    	CloseableHttpResponse response = null;
    	try{
		URI uri = new URIBuilder().addParameter("username", USERNAME)
				.addParameter("password", PASSWORD)
				.setScheme("http")
		        .setHost(HOST_NAME)
		        .setPort(SERVER_PORT_NUMBER)
		        .setPath("/login").build();
		
		post.setURI(uri);
		response = cocoaOwlFolderSyncClient.execute(post, clientContext);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private static Date getLastSyncedOn() throws ParseException{
    	HttpGet get = new HttpGet();
    	CloseableHttpResponse intialResponse = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
    	Date result = null;
    	try{
		URI uri = new URIBuilder()
				.setScheme("http")
				.setHost(HOST_NAME)
				.setPort(SERVER_PORT_NUMBER)
				.addParameter("username", USERNAME)
		        .setPath("/app/"+CLIENT_NAME+"/entity/folderbrowser/lastsyncedon/")
		        .build();
		
		get.setURI(uri);
		intialResponse = closeableHttpClient.execute(get, httpClientContext);
        InputStream is = intialResponse.getEntity().getContent();
        JsonReader jReader = Json.createReader(is);
        String lastSyncedOnStr = jReader.readObject().getString("lastSyncedOn");
		is.close();
		result = sdf.parse(lastSyncedOnStr);
    	}catch(Exception e){
    		//e.printStackTrace();
    	}finally{
    		try {
    			intialResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return result;
    }
    

	private static void updateNonDocumentEntityOnServer(long entityId, File clientFileOrFolder) throws URISyntaxException, ClientProtocolException, IOException {
		
    	URI fileUploadURI = new URIBuilder()
				.setScheme("http")
				.setHost(HOST_NAME)
				.setPort(SERVER_PORT_NUMBER)
		        .setPath("/app/" + CLIENT_NAME + "/entity/update/using/csv/file/")
				.addParameter("entityId", entityId+"")
				.build();
    	
        HttpPost fileUploadHttpPost = new HttpPost();
        fileUploadHttpPost.setURI(fileUploadURI);

        FileBody bin = new FileBody(clientFileOrFolder);
        StringBody comment = new StringBody("A binary file of some kind", ContentType.MULTIPART_FORM_DATA);

        HttpEntity fileUploadReqestEntity = MultipartEntityBuilder.create()
                .addPart("file", bin)
                .addPart("comment", comment)
                .build();
        fileUploadHttpPost.setEntity(fileUploadReqestEntity);

        
        CloseableHttpResponse fileUploadResponse = closeableHttpClient.execute(fileUploadHttpPost, httpClientContext);
		fileUploadResponse.close();
	}
	
    private static void updateLastSyncedOnServer() {
		// /entity/foldersync/update/time
    	HttpGet httpGet = new HttpGet();
    	CloseableHttpResponse intialResponse = null;
    	try{
			URI folderSyncUpdateURI = new URIBuilder()
					.setScheme("http")
					.setHost(HOST_NAME)
					.setPort(SERVER_PORT_NUMBER)
			        .setPath("/app/"+ CLIENT_NAME +"/entity/foldersync/update/time")
			        .addParameter("username", USERNAME)
			        .build();
			
			httpGet.setURI(folderSyncUpdateURI);
			intialResponse = closeableHttpClient.execute(httpGet, httpClientContext);
			EntityUtils.consume(intialResponse.getEntity());
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
    			intialResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}	
}

