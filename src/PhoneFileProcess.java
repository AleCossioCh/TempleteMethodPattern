import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PhoneFileProcess extends AbstractFileTemplete{

    private String log = "";
    public PhoneFileProcess(File file, String logPath, String movePath){
        super(file, logPath, movePath);
    }
    @Override
    protected void validateClient() throws Exception {
        String fileName = file.getName();
        if(!fileName.endsWith(".telefono")){
            throw new Exception("Nombre del archivo no valido, debe terminar con .telefono");
        }

        if (fileName.length() != 7){
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
                String[] parts = line.split(",");
                String id = parts[0];
                String customer = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];
                boolean exist = OnMemoryDataBase.customerExist(Integer.parseInt(customer));
                if (!exist) {
                    log += id + "E" + customer + "\t\t" + date + "El cliente no existe\n";
                } else if (amount > 2000) {
                    log += id + "E" + customer + "\t\t" + date + "El monto excede el maximo\n";
                } else {
                    log += id + "E" + customer + "\t\t" + date + "Aplicado exitosamente\n";
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
            if(!outFile.exists()) {
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
