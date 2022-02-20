package aroma1997.core.util.file.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompression {
  public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
    zipFolder(srcFolder, destZipFile, -1);
  }
  
  public static void zipFolder(String srcFolder, String destZipFile, int compression) throws Exception {
    FileOutputStream fos = new FileOutputStream(destZipFile);
    ZipOutputStream zip = new ZipOutputStream(fos);
    zip.setLevel(compression);
    addFolderToZip("", srcFolder, zip);
    zip.flush();
    zip.close();
  }
  
  public static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
    File folder = new File(srcFile);
    if (folder.isDirectory()) {
      addFolderToZip(path, srcFile, zip);
    } else {
      byte[] buf = new byte[1024];
      FileInputStream in = new FileInputStream(srcFile);
      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
      int len;
      while ((len = in.read(buf)) > 0)
        zip.write(buf, 0, len); 
      in.close();
    } 
  }
  
  public static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
    File folder = new File(srcFolder);
    for (String fileName : folder.list()) {
      if (path.equals("")) {
        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
      } else {
        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
      } 
    } 
  }
}
