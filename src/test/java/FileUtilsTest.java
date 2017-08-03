import com.athena.utils.FileUtils;
import com.athena.utils.StringUtils;

import java.util.Arrays;

public class FileUtilsTest {
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(FileUtils.getFileChunk("test.txt")));
        //System.out.println(StringUtils.formatFileBytes(FileUtils.getFileChunk("test.txt")));

        /*for (byte[] b : StringUtils.formatFileBytes(FileUtils.getFileChunk("wordList.txt"))) {
            System.out.println(StringUtils.byteArrayToString(b));
        }*/

        //System.out.println(FileUtils.getBytes("wordList.txt"));
        //System.out.println(StringUtils.formatFileBytes(FileUtils.getFileChunk("empty.txt")));

        long start;

        start = System.currentTimeMillis();
        System.out.println(FileUtils.getLineCount("test.txt"));
        System.out.println(FileUtils.getUniques("test.txt"));
        System.out.println("Test took - " + (System.currentTimeMillis() - start));
    }
}