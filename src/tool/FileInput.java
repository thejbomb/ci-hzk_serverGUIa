package tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileInput {

    public String[] readFile(String fileName) throws IOException {
        long numberOfLines;
        try (Stream<String> s = Files.lines(Paths.get(fileName),
                Charset.defaultCharset())) {

            numberOfLines = s.count();

        }
        String[] result = new String[(int) numberOfLines];
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                result[i] = currentLine;
                i++;
                System.out.println(currentLine);
            }

        }

        return result;
    }

    public String[][] parseUsersData(String[] data) {
        String[][] result = new String[data.length][3];


        for (int i = 0; i < data.length; i++) {
            int ii = 0;
            int jj = 0;
            for (int j = 0; j < data[i].length(); j++) {
                if (data[i].charAt(j) == '#') {
                    result[i][ii] = data[i].substring(jj, j);
                    jj = j + 1;
                    ii++;
                }
            }
        }
        return result;
    }
}
