import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LightFileProcess extends AbstractFileTemplete {
    private String log = "";

    public LightFileProcess(File file, String logPath, String movePath){
        super(file, logPath, movePath);
    }

    @Override
    protected void validateClient() throws Exception {
        String fileName = file.getName();
        if(fileName.endsWith(".luz")){
            throw new Exception("Nombre de archivo no valido, debe terminar en .luz");
        }
        if(fileName.length()!= 16){
            throw new Exception("El documento no tiene el formato esperado");
        }
    }

    @Override
    protected void processFile() throws Exception {
        FileInputStream input = new FileInputStream(file);
        try {
            byte[] fileContect = new byte[input.available()];
            input.read(fileContect);
            String message = new String(fileContect);
            String[] lines = message.split("\n");
            for(String line: lines) {
                String id = line.substring(0, 3);
                String customer = line.substring(3, 5);
                double amount = Double.parseDouble(line.substring(5, 8));
                String date = line.substring(8, 16);
                boolean exist = OnMemoryDataBase.customerExist(
                        Integer.parseInt(customer));
                if (!exist) {
                    log += id + "E" + customer + "\t\t" + date
                            + "El cliente no existe\n";
                } else if (amount > 2000) {
                    log += id + " E" + customer + "\t\t" + date
                            + " El monto excede el maximo";
                } else {
                    log += id + " E" + customer + "\t\t" + date
                            + "Aplicado exitosamente\n";
                }
            }
        }finally {
            try{
                input.close();
            }catch (Exception e){
            }
        }
    }

    @Override
    protected void createLog() throws Exception {
        FileOutputStream out = null;
        try {
            File outFile = new File(logPath + "/" + file.getName());
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile, false);
            out.write(log.getBytes());
            out.flush();
        }finally {
            out.close();
        }
    }

}
