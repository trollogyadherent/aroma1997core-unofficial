package aroma1997.core.web;

import aroma1997.core.log.LogHelperPre;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.logging.log4j.Level;

public class Downloader {
  public static boolean downloadFile(String url, File outputFile) {
    LogHelperPre.log(Level.TRACE, "Downloading File " + url + " to " + outputFile + " .");
    try {
      InputStream remoteStream = (new URL(url)).openStream();
      outputFile.createNewFile();
      FileOutputStream stream = new FileOutputStream(outputFile);
      while (true) {
        int data = remoteStream.read();
        if (data == -1) {
          stream.close();
          break;
        } 
        stream.write(data);
      } 
    } catch (IOException e) {
      LogHelperPre.log(Level.TRACE, "Failed to download file.");
      return false;
    } 
    LogHelperPre.log(Level.TRACE, "Done downloading file.");
    return true;
  }
}
