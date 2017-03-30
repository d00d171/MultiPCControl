package pl.ciochon.multipccontrol.mouse.generator;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import pl.ciochon.multipccontrol.mouse.generator.nativeapi.MOUSEINPUT;

import java.io.*;

/**
 * Created by Konrad Ciochoń on 2017-03-30.
 */
public class MOUSEINPUTSerializationTest {

    public static void main(String[] args) {
        String fileName = "test";

        MOUSEINPUT input = new MOUSEINPUT();
        input.time = new WinDef.DWORD(1L);
        input.dwExtraInfo = new BaseTSD.ULONG_PTR(2L);
        input.dx = new WinDef.LONG(3L);
        input.dy = new WinDef.LONG(4L);
        input.mouseData = new WinDef.DWORD(5L);
        input.dwFlags = new WinDef.DWORD(6L);

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            MOUSEINPUT p = (MOUSEINPUT) ois.readObject();
            ois.close();
            System.out.println("Person Object Read=" + p);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
