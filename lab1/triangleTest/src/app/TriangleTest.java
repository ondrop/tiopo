package app;

import java.io.*;

public class TriangleTest {

    private static final String TEST_FILE_PATH = "../data/test.txt";
    private static final String TEST_RESULT_FILE_PATH = "../data/test_result.txt";
    private static final String TRIANGLE_EXE_FILE_PATH = "../../triangle/src/app/Triangle.exe";

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(TEST_FILE_PATH));
            String line;
            File output = new File(TEST_RESULT_FILE_PATH);
            PrintWriter writer = new PrintWriter(output);
            while ((line = br.readLine()) != null) {
                String[] subStr = line.split(";");
                if (subStr.length < 2) {
                    throw new Exception("Error arguments count");
                }

                Runtime rt = Runtime.getRuntime();
                String appArgs = subStr[0];
                Process process = rt.exec(TRIANGLE_EXE_FILE_PATH + " " + appArgs);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String s;
                while ((s = stdInput.readLine()) != null) {
                    writer.append(subStr[0]).append(" ;");
                    String response = s.equalsIgnoreCase(subStr[1]) ? "success" : "error";
                    writer.append(response);
                }

                writer.append("\n");
            }

            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
