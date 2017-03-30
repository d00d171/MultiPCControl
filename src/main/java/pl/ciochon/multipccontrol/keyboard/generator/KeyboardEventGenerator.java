package pl.ciochon.multipccontrol.keyboard.generator;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * Created by Konrad Ciochoń on 2017-03-27.
 */
public class KeyboardEventGenerator {

    //https://msdn.microsoft.com/en-us/library/windows/desktop/ms646271(v=vs.85).aspx

    public void start() {
        final User32 lib = User32.INSTANCE;

        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);

        input.input.setType("ki");
        input.input.ki.wVk = new WinDef.WORD(0x52);
        input.input.ki.time = new WinDef.DWORD(0);

        lib.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

}
