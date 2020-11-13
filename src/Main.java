import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends TimerTask {
    private static final String[] paths =
            new String[]{"/home/ale/IdeaProjects/wii/TempleteMethodPattern/src/files/luz", "/home/ale/IdeaProjects/wii/TempleteMethodPattern/src/files/telefono"};
    private static final String log_dir = "/home/ale/IdeaProjects/wii/TempleteMethodPattern/src/logs";
    private static final String process_dir = "/home/ale/IdeaProjects/wii/TempleteMethodPattern/src/files/process";

    public static void main(String[] args){
        new Main().start();
    }

    private void start() {
        try {
            Timer timer = new Timer();
            timer.schedule(this, new Date(), (long) 1000 * 10);
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("> Monitoring start");
        File f = new File(paths[0]);
        if (!f.exists()){
            throw new RuntimeException("El path " + paths[0] + " no existe");
        }
        File[] luzFiles = f.listFiles();
        for (File file : luzFiles){
            try {
                System.out.println("File found > " + file.getName());
                new LightFileProcess(file, log_dir,process_dir).execute();
                System.out.println("Archivo procesado > " + file.getName());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        f = new File(paths[1]);
        if(!f.exists()){
            throw new RuntimeException("El path " + paths[1] + " no existe");
        }
        System.out.println("> Read path " + paths[1]);
        File[] phoneFiles = f.listFiles();
        for (File file: phoneFiles){
            try {
                System.out.println("> File found > " + file.getName());
                new PhoneFileProcess(file, log_dir, process_dir).execute();
                System.out.println("Archivo procesado > " + file.getName());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


}
