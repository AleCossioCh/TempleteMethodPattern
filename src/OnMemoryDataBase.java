import java.util.HashMap;
import java.util.Map;

public class OnMemoryDataBase {
    public static final Map<String, String> process_documents = new HashMap<>();
    public static final int[] customers = new int[]{1,2,3,4,5,6,7,8,9,10,20,30,40,50};

    public static String getFileStatus(String fileName){
        if (process_documents.containsKey(fileName)){
            return process_documents.get(fileName);
        }
        return null;
    }

    public static void setProcessFile(String fileName){
        process_documents.put(fileName, "Procesado");
    }

    public static boolean customerExist(int id){
        for(int i : customers){
            if(i == id){
                return true;
            }
        }
        return false;
    }
}
