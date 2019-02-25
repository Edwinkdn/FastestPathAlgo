import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;

public class MapDescriptor {
    public static String mapA = "C:\\Users\\Edwin\\eclipse-workspace\\FastestPathAlgo\\src\\mapA.txt";
    public static String mapB = "C:\\Users\\Edwin\\eclipse-workspace\\FastestPathAlgo\\src\\mapB.txt";
    public static HashMap<String, String> hexTable = new HashMap<String, String>(){{
        put("0000", "0");
        put("0001", "1");
        put("0010", "2");
        put("0011", "3");
        put("0100", "4");
        put("0101", "5");
        put("0110", "6");
        put("0111", "7");
        put("1000", "8");
        put("1001", "9");
        put("1010", "A");
        put("1011", "B");
        put("1100", "C");
        put("1101", "D");
        put("1110", "E");
        put("1111", "F");
    }
    };

    public static String hexToBinary(String hex){
        return new BigInteger(hex, 16).toString(2);
    }

    public static String binaryToHex(String binary){
        StringBuilder sb = new StringBuilder();
        int length = binary.length();
        for(int i = 0; i<length; i+=4){
            sb.append(hexTable.get(binary.substring(i, i+4)));
        }
        return sb.toString();
    }

    public static String getMapBinary(String mapPath){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(mapPath)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}