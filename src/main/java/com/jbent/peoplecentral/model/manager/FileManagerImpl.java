/**
 *
 */
package com.jbent.peoplecentral.model.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;
import com.jbent.peoplecentral.exception.AttributeValueNotValidException;
import com.jbent.peoplecentral.exception.DataException;
import com.jbent.peoplecentral.model.dao.EntityDAO;
import com.jbent.peoplecentral.model.pojo.Attribute;
import com.jbent.peoplecentral.model.pojo.AttributeFileStorage;
import com.jbent.peoplecentral.model.pojo.AttributeValueStorage;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption;
import com.jbent.peoplecentral.model.pojo.FileReport;
import com.jbent.peoplecentral.model.pojo.FieldTypeOption.FieldTypeOptions;
import com.jbent.peoplecentral.util.FileUploadHelperUtil;
import com.jbent.peoplecentral.util.TikaHelperUtil;
import com.jbent.peoplecentral.web.SessionContext;


/**
 * @author RaviT
 *
 */
public class FileManagerImpl extends ApplicationObjectSupport implements FileManager, InitializingBean {

	@Autowired
	private EntityDAO entityDAO; 
	@Autowired
	private EntityTypeManager entityTypeManager;
	private FileUploadHelperUtil fileUploadHelpUtil;	
	private String fileTypes ="";
		
	@Override

	public long Save(List<MultipartFile> files,long entityId,long attributeId,int filesCount,MappingJackson2JsonView view) throws DataException {
		Assert.notNull(files, "files  null");
		Assert.notNull(attributeId, "attributeId null");
		Assert.notNull(filesCount, "filesCount null");
		FileReport fileReport = new FileReport();
		List<FileReport> fileResults = new ArrayList<FileReport>();
		List<AttributeFileStorage> filesToSave = new ArrayList<AttributeFileStorage>();
		AttributeFileStorage afs = null ;
		Attribute attribute = null;
		String pathToSave = "";
		boolean fileInFS = false;
		
		try{
			attribute = entityTypeManager.loadAttribute(attributeId);	
			//  get Values for No of Images/Files and Types of the Files allowed to be Upload				
			if(attribute != null){
				List<FieldTypeOption> fieldOptions = attribute.getOptions();
				if(fieldOptions.size() > 0){
					for(FieldTypeOption fieldOption:fieldOptions){			  		
						//get Value for imagesLimit to Upload
						if(fieldOption.getFieldtypeOptionId() == FieldTypeOptions.IMAGELIMIT.getValue()){
							String uploadCount = fieldOption.getOptionValue();
							if(!uploadCount.equalsIgnoreCase(""))
								new Long(uploadCount);
						}
						// 	get Value for imageTypes are allowed to Upload
						if(fieldOption.getFieldtypeOptionId() == FieldTypeOptions.IMAGETYPE.getValue()){
							fileTypes = fieldOption.getOptionValue();
						}
//					 	get Value for fileTypes are allowed to Upload
						if(fieldOption.getFieldtypeOptionId() == FieldTypeOptions.FILETYPE.getValue()){
							fileTypes = fieldOption.getOptionValue();
						}
					}
				}
			}

			// Check the uploaded filesCount and fileTypes 
			/*if(filesCount > filesLimit){
				logger.error("Sorry! You are not allowed to upload more than "+filesLimit+"files");
				model.addAttribute("errorMsg", "Sorry! You are not allowed to upload more than  "+filesLimit+" files");
				return 0;
			}*/
			// List out files which are allowed to SAVE
			if(files != null && files.size()>0){
				for(MultipartFile file:files){
					boolean hasExtension = true;
					// Checking the file Extensions 
//					if(!fileTypes.equals("")){
						String[] allowedFileType = allowedfileTypes(fileTypes);
						String currentFileExt = getFileExtension(file.getOriginalFilename());	
						if(allowedFileType.length >= 0){
//							for(String ext:allowedFileType){
//								if(ext.equalsIgnoreCase(currentFileExt)){
//									hasExtension = true;
//								}	
//							}
							// if FileType is allowed then  Prepare PGObject to save into DB
							if(hasExtension){
								AttributeFileStorage attFileStorage = new AttributeFileStorage();
								attFileStorage.setFileName(file.getOriginalFilename());
								attFileStorage.setId(attribute.getId());
								attFileStorage.setOrder(attribute.getOrder());
								attFileStorage.setName(attribute.getName());
								attFileStorage.setEntityTypeId(attribute.getEntityTypeId());
								attFileStorage.setDataTypeId(attribute.getDataTypeId());
								attFileStorage.setFieldTypeId(attribute.getFieldTypeId());
								attFileStorage.setFieldTypeName(attribute.getFieldTypeName());
								attFileStorage.setIndexed(attribute.isIndexed());
								attFileStorage.setRequired(attribute.isRequired());
								attFileStorage.setMod_user(attribute.getMod_user());
								attFileStorage.setOptions(attribute.getOptions());
								attFileStorage.setOrder(attribute.getOrder());
								attFileStorage.setEntityId(entityId);
								filesToSave.add(attFileStorage);

							}
							// If File Type NOT Allowed send message
							else if(!hasExtension){
								FileReport fileResult = new FileReport();
								fileResult.setName(file.getOriginalFilename());
								fileResult.setSaved(false);
								fileResult.setDescription("FileType <B>"+currentFileExt+"</B> is NOT Allowed to Upload!");
								fileResults.add(fileResult);
							}
						}
//					}
				}
				// SAVE FILES/IMAGES
				if(filesToSave.size()> 0 ){				
					//Save files  into the DB

					
					/*afs = entityDAO.fileSave(filesToSave);
					afs.setImageLocation(tempImageLocationToSave(afs));
					afs.setImagePath(afs.getImageLocation()+"/"+afs.getFileName());
					// 	Store file into FileSystem with EntityID
					if(afs.getEntityId() > 0){*/

						//entityId= afs.getEntityId();
						//Long attributeID = afs.getId();
						//String attId = attributeID.toString();
						//pathToSave = ClientManageUtil.loadClientSchema()+File.separator+
						//afs.getEntityId()+File.separator+attId.charAt(0)+File.separator+
					 	//attId.charAt(1)+File.separator+attId.charAt(2)+File.separator+attId;					
					for(int i = 0; i < filesToSave.size(); i++){
						afs = new AttributeFileStorage();
						
						// Save File info into Attribute File Storage
						afs = entityDAO.fileSaveSingle(filesToSave.get(i));
						
						//Save file metadata into Attribute Value Storage
						AttributeValueStorage avs = new AttributeValueStorage();
						avs.setEntityId(afs.getEntityId());
						avs.setEntityTypeId(attribute.getEntityTypeId());
						avs.setId(afs.getId());
						avs.setAttributeValueFileId(afs.getAttributeFileId());
						//avs.setValueText(files.get(i).getName());
						avs.setValueText(new TikaHelperUtil().getMetadataAndFileContent(files.get(i)));
						entityDAO.avsSave(avs);
						
						if(i < filesToSave.size()-1){
							filesToSave.get(i+1).setEntityId(afs.getEntityId());
						}
						afs.setImageLocation(imageLocationToSave(afs));
						afs.setImagePath(afs.getImageLocation()+"/"+afs.getFileName());
						if(afs.getFileName().equalsIgnoreCase(files.get(i).getOriginalFilename())){
							try {
								fileInFS = fileUploadHelpUtil.storeUploadFileImage(files.get(i),afs );
								afs.setImageLocation(tempImageLocationToSave(afs));
								afs.setImagePath(afs.getImageLocation()+"/"+afs.getFileName());
								fileInFS = fileUploadHelpUtil.storeUploadFileImage(files.get(i),afs );
								if(fileInFS){
									FileReport fileResult = new FileReport();
									fileResult.setName(afs.getFileName());
									fileResult.setSaved(true);
									fileResults.add(fileResult);
								}	
							} catch (IOException e) {
								logger.error("Unable to save file into the FileSystem"+afs.getFileName());
							}
						}						
					}
					//}					
				}else{
					logger.error("FileManagerImpl.Save: No files found to save in DB:"+filesToSave.size());
				}
				fileReport.setFileReport(fileResults);
			}else{
				logger.error("FileManagerImpl.Save: passed files list empty to save:"+files);
			}

		}catch(DataException e){			
			logger.error("FileManagerImpl.Unable to save file:"+e.getMessage());
			throw new DataException("FileManagerImpl.fileSave:- Data Exception while saveing file:-" + e.getMessage(), e);
		} catch (AttributeValueNotValidException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		view.getAttributesMap().put("fileReport",fileReport);
		return afs.getEntityId();
	}
	private boolean fileAllowedToSave(MultipartFile file,List<AttributeFileStorage> filesToSave) throws DataException{
		for(AttributeFileStorage saveFile:filesToSave){
			if(saveFile.getFileName().equalsIgnoreCase(file.getOriginalFilename()))
				return true;
		}
		return false;
	}
	private String[] allowedfileTypes(String fileTypes) throws DataException{
		String ext[] = fileTypes.split(",");
		return ext;
	}
	
	private String getFileExtension(String fileName)throws DataException{
		 int mid= fileName.lastIndexOf(".");
		 String extension=fileName.substring(mid+1);
		 return extension;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(entityDAO, "entityDAO is null");
		Assert.notNull(entityTypeManager, "entityTypeManager is null");
		
	}
	@Autowired
	public void setFileUploadHelpUtil(FileUploadHelperUtil fileUploadHelpUtil) {
		this.fileUploadHelpUtil = fileUploadHelpUtil;
	}
	@Override
	public List<AttributeFileStorage> loadImages(long entityId) throws DataException {	
		List<AttributeFileStorage> afsList=null;
		List<AttributeFileStorage> finalList= new ArrayList<AttributeFileStorage> ();
		List<String> savedLt = new ArrayList<String>();
		String previousFileName = null;
		boolean saved = false;
		try {
			afsList = entityDAO.loadImages(entityId);
			if(afsList != null && afsList.size()>0){
				for(AttributeFileStorage afs:afsList){			
					for (int i = 0; i < savedLt.size(); i++) {
						if (savedLt.get(i).equalsIgnoreCase(afs.getFileName()) && previousFileName.equalsIgnoreCase(afs.getFileName())) {
							saved = true;
						}else{
							saved = false;
						}
					}
					
					 if(saved == false){
							afs.setImagePath(tempImageLocationToSave(afs)+"/"+afs.getFileName());
							File file = new File(afs.getImagePath());
							String contentType = new MimetypesFileTypeMap().getContentType(file);
							savedLt.add(afs.getFileName());
							saved = true;
							previousFileName =  afs.getFileName();
							if(contentType.startsWith("image/")){
								finalList.add(afs);
							}
						}
				}
			}
		} catch (DataException e) {
			logger.error("loadImages.Unable to loadImages:"+e.getMessage());
			throw new DataException("loadImages.:- Unable to loadImages:-" + e.getMessage(), e);
		}
		return finalList;
	}
	
	@Override
	public AttributeFileStorage IMFilterOperations(File file,List<String> arguments,String filterType,long entityId, long attributeId,
			int filesCount, MappingJackson2JsonView view) throws DataException {
		String imageMagickHome="";
		String serverPath = fileUploadHelpUtil.getServerPath();
		boolean isWindows=File.pathSeparator.equals(";");
		 if (isWindows) {
			 imageMagickHome= fileUploadHelpUtil.getWebappPath()+"/resource/ImageMagick-6.6.9-9windows";
		 }else{
			// set the linux path here
			 imageMagickHome = fileUploadHelpUtil.getWebappPath()+"resource/ImageMagick-6.7.0-9Linux/local/bin";
		 }
		
		 // set the ImageMagick Home location
		 ProcessStarter.setGlobalSearchPath(imageMagickHome);

		 // prepare Image file Object
		 AttributeFileStorage afs = new AttributeFileStorage();
		 afs.setFileName(file.getName());
		 afs.setId(attributeId);
		 afs.setEntityId(entityId);
		 afs.setImageLocation(tempImageLocationToSave(afs));
		 afs.setImagePath(afs.getImageLocation()+"/"+afs.getFileName());
		
		 String destinationFilePath = serverPath+File.separator+afs.getImageLocation()+File.separator+afs.getFileName();
		
		 // perform the IM Operations
		 if(afs != null){
			//GraphicsMagickCmd cmd = new GraphicsMagickCmd("convert");
			ConvertCmd cmd = new ConvertCmd();
			IMOperation op = new IMOperation();
			try {
				if(file.exists()){
					 op.addImage();
					 if(filterType.equalsIgnoreCase("resize")){
						 int height = Integer.parseInt(arguments.get(0));
						 int width = Integer.parseInt(arguments.get(1));
						op.resize(height,width);
					 }else if (filterType.equalsIgnoreCase("flip")){
						 op.flip(); 
					 }else if (filterType.equalsIgnoreCase("rotate")){
						 Double angle = Double.parseDouble(arguments.get(0));
						 op.rotate(angle);
					 }else if(filterType.equalsIgnoreCase("compress")){
						 Double compressedSize = Double.parseDouble(arguments.get(0));
						op.compress("Zip").quality(compressedSize);
					 }else if(filterType.equalsIgnoreCase("crop")){
						 int width = Integer.parseInt(arguments.get(0));
						 int height = Integer.parseInt(arguments.get(1));
						 int offsetx = Integer.parseInt(arguments.get(2));
						 int offsety = Integer.parseInt(arguments.get(3));
						op.crop(width, height, offsetx, offsety);
					 }else if(filterType.equalsIgnoreCase("hsb")){
						 op.colorspace("HSB").format("0, 255,  255");
					 }
					 op.addImage();
					 cmd.run(op,file.getPath(),destinationFilePath);
				 }
		    	File savedFile = fileUploadHelpUtil.retrieveUploadedFile(afs.getFileName(), afs.getImageLocation());
				if(!savedFile.exists()){
					throw new DataException(afs.getFileName()+":Image Not Found at location : "+afs.getImageLocation());
				}
			} catch (IOException e) {
				logger.error("IMFilterOperations.Unable to Perform IMFilterOperations:"+e.getMessage());
				throw new DataException("IMFilterOperations.:- Unable to Perform IMFilterOperations:-" + e.getMessage(), e);
			} catch (InterruptedException e) {
				logger.error("IMFilterOperations.Unable to Perform IMFilterOperations:"+e.getMessage());
				throw new DataException("IMFilterOperations.:- Unable to Perform IMFilterOperations:-" + e.getMessage(), e);
			} catch (IM4JavaException e) {
				logger.error("IMFilterOperations.Unable to Perform IMFilterOperations:"+e.getMessage());
				throw new DataException("IMFilterOperations.:- Unable to Perform IMFilterOperations:-" + e.getMessage(), e);
			} 
		 }
		return afs;
	}

	@Override
	public AttributeFileStorage imageSave(File fileIn,long entityId, long attributeId,MappingJackson2JsonView view) throws DataException {
		
		List<AttributeFileStorage> filesToSave = new ArrayList<AttributeFileStorage>();
		
		String serverPath = fileUploadHelpUtil.getServerPath();
		
		 // Save File in DB
		 AttributeFileStorage attFileStorage = new AttributeFileStorage();
		 attFileStorage.setFileName(fileIn.getName());
		 attFileStorage.setId(attributeId);
		 attFileStorage.setEntityId(entityId);
		 filesToSave.add(attFileStorage);
		 AttributeFileStorage afs = entityDAO.fileSave(filesToSave);		
		 afs.setImageLocation(imageLocationToSave(afs));
		 afs.setImagePath(afs.getImageLocation()+"/"+afs.getFileName());
		 if(afs != null){
			try {
				 //save file into FileSytem
				 File f = new File(serverPath+File.separator+afs.getImageLocation());
				 f.mkdirs();
				 
				 String destination = serverPath+File.separator+afs.getImageLocation()+"/"+afs.getFileName();
				 
				 File fileOut = new File(destination);
				 FileCopyUtils.copy(fileIn,fileOut);
			
		    	 File fileSaved = fileUploadHelpUtil.retrieveUploadedFile(afs.getFileName(), afs.getImageLocation());
		    	 if(!fileSaved.exists()){
		    		throw new DataException(afs.getFileName()+":Image Not Found at location : "+afs.getImageLocation());
		    	 }
				
			} catch (IOException e) {
				logger.error("imageSave.Unable to imageSave:"+e.getMessage());
				throw new DataException("imageSave.:- Unable to imageSave:-" + e.getMessage(), e);
			} 
		 }
		return afs;
	}
	
	@Override
	public AttributeFileStorage imageDelete(AttributeFileStorage afs) throws DataException {
		
		String serverPath = fileUploadHelpUtil.getServerPath();
		
		 // delete File from the DB		
		
		entityDAO.deleteFile(afs.getAttributeFileId(), afs.getMod_user());	
		
		// delete File from the File system
		 
		File f = new File(serverPath+File.separator+afs.getImagePath());
		f.delete(); 		
		return afs;
	}

	public String getSavedImagePath(AttributeFileStorage afs) throws DataException{
       	Long attributeID = afs.getId();
        String attId = attributeID.toString();
        String imagePath = ClientManageUtil.loadClientSchema()+File.separator+
        afs.getEntityId()+File.separator+attId.charAt(0)+File.separator+
        attId.charAt(1)+File.separator+attId.charAt(2)+File.separator+attId+File.separator+afs.getFileName();
        
        return  fileUploadHelpUtil.getImagePath(imagePath);
	}
	public String imageLocationToSave(AttributeFileStorage afs) throws DataException{
        Long attributeID = afs.getId();
        String attId = attributeID.toString();
        String imagePath = ClientManageUtil.loadClientSchema()+File.separator+
        afs.getEntityId()+File.separator+attId.charAt(0)+File.separator+
        attId.charAt(1)+File.separator+attId.charAt(2)+File.separator+attId;
        
        return  fileUploadHelpUtil.getImagePath(imagePath);
	}
	
	public String tempImageLocationToSave(AttributeFileStorage afs) throws DataException{
		Long attributeID = afs.getId();
        String attId = attributeID.toString();
        String imagePath = File.separator+"clients"+File.separator+"temp"+File.separator+
        afs.getEntityId()+File.separator+attId.charAt(0)+File.separator+
        attId.charAt(1)+File.separator+attId.charAt(2)+File.separator+attId;
        return  imagePath;
	}
	@Override
	public String getFileText(AttributeFileStorage attributeFileStorage) throws IOException {
		Path path = Paths.get(fileUploadHelpUtil.getServerPath() + File.separator + attributeFileStorage.getImagePath());
		StringBuilder stringBuilder = new StringBuilder();//File f = new File(fileUploadHelpUtil.getServerPath() + File.separator + attributeFileStorage.getImagePath());f.get
		for(String line : Files.readAllLines(path,Charset.forName("UTF-8"))){//Files.
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	@Override
	public void setFileText(AttributeFileStorage attributeFileStorage, String text) throws IOException {
		Path path = Paths.get(fileUploadHelpUtil.getServerPath() + File.separator + attributeFileStorage.getImagePath());
		Files.deleteIfExists(path);
		Files.write(path, text.getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE);
	}
}