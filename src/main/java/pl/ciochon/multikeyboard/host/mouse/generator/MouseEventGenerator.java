package pl.ciochon.multikeyboard.host.mouse.generator;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import static com.sun.jna.platform.win32.WinUser.SM_CXSCREEN;
import static com.sun.jna.platform.win32.WinUser.SM_CYSCREEN;

/**
 * Created by Konrad Ciochoń on 2017-03-27.
 */
public class MouseEventGenerator {

    //https://msdn.microsoft.com/en-us/library/windows/desktop/ms646273(v=vs.85).aspx
    public static void generateEvent(MOUSEINPUT mouseinput) {
        final User32 lib = User32.INSTANCE;

        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);

        input.input.setType("mi");
        input.input.mi = mouseinput;

        long screenWidth = lib.GetSystemMetrics(SM_CXSCREEN) - 1;
        long screenHeight = lib.GetSystemMetrics(SM_CYSCREEN) - 1;

        //normalizacja
        input.input.mi.dx = new WinDef.LONG(input.input.mi.dx.longValue() * (65535 / screenWidth));
        input.input.mi.dy = new WinDef.LONG(input.input.mi.dy.longValue() * (65535 / screenHeight));

        lib.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    public static void main(String[] args) {
        final User32 lib = User32.INSTANCE;

        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);

        input.input.setType("mi");
        input.input.mi.dx = new WinDef.LONG(600);
        input.input.mi.dy = new WinDef.LONG(1000);
        input.input.mi.mouseData = new WinDef.DWORD(0);
        input.input.mi.dwFlags = new WinDef.DWORD(MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_ABSOLUTE.getValue() + MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_MOVE.getValue());


        long screenWidth = lib.GetSystemMetrics(SM_CXSCREEN) - 1;
        long screenHeight = lib.GetSystemMetrics(SM_CYSCREEN) - 1;
        input.input.mi.dx = new WinDef.LONG(input.input.mi.dx.longValue() * (65535 / screenWidth));
        input.input.mi.dy = new WinDef.LONG(input.input.mi.dy.longValue() * (65535 / screenHeight));

        lib.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }
}
