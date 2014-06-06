package resources;

import java.io.File;
import java.io.IOException;


public class ResourceLoader{

	public static final String ResourceFolder="res/";
	public static final  String defaultLocation;
	static{
		String location=ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (location.endsWith(".jar")){
			System.out.println("in a jar: "+location);
			location =location.substring(0,location.lastIndexOf("/"));
		}
		if(ResourceFolder.startsWith("/")){
			if(location.endsWith("/")){
				location+=ResourceFolder.substring(1);
			}else{
				location+=ResourceFolder;
			}
		}else{
			if(location.endsWith("/")){
				location+=ResourceFolder;
			}else{
				location+="/"+ResourceFolder;
			}
		}
		defaultLocation=location;
	}
	
	public ResourceLoader() {
	}
		

	public ImageResource LoadImageResource(String location) throws IOException {
		try {
			return new ImageResource((defaultLocation+location).trim());
		} catch (IOException e) {
			System.err.println("cannot load "+(defaultLocation+location).trim());
			System.err.println("path relative to "+new File(".").getCanonicalPath());
			throw e;
		}
	}

}
