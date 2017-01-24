import java.io.File;
import java.io.IOException;
import java.util.List;
 
/**
 * This program demonstrates a usage of the MultipartUtility class.
 * @author www.codejava.net
 *
 */
public class FileUploadTest {
 
    public static void main(String[] args) {
        String charset = "UTF-8";
        File uploadFile1 = new File("/home/haslbema/documents/test.txt"); 
        String requestURL = "http://localhost:8080/HackaTUM/Submission";
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
           // multipart.addHeaderField("User-Agent", "CodeJava");
          //  multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("aufgabe", "2"); 

            multipart.addFilePart("submission", uploadFile1); 
            multipart.addFilePart("code[]", uploadFile1); 
            multipart.addFilePart("code[]", uploadFile1); 
            
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}