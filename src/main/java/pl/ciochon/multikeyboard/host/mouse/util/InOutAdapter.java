package pl.ciochon.multikeyboard.host.mouse.util;

import com.sun.jna.platform.win32.WinDef;
import pl.ciochon.multikeyboard.host.mouse.generator.MOUSEINPUT;
import pl.ciochon.multikeyboard.host.mouse.hook.LowLevelMouseProc;

import java.util.HashMap;
import java.util.Map;

import static pl.ciochon.multikeyboard.host.mouse.generator.MOUSEINPUT.DW_FLAG_ENUM;
import static pl.ciochon.multikeyboard.host.mouse.hook.LowLevelMouseProc.MOUSEPROCWPARAM.ENUM;


/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class InOutAdapter {

    private static Map<ENUM, DW_FLAG_ENUM> mouserocwparamToDw_flag_enum;

    static {
        mouserocwparamToDw_flag_enum = new HashMap<ENUM, DW_FLAG_ENUM>();
        mouserocwparamToDw_flag_enum.put(ENUM.WM_LBUTTONDOWN, DW_FLAG_ENUM.MOUSEEVENTF_LEFTDOWN);
        mouserocwparamToDw_flag_enum.put(ENUM.WM_LBUTTONUP, DW_FLAG_ENUM.MOUSEEVENTF_LEFTUP);
        mouserocwparamToDw_flag_enum.put(ENUM.WM_MOUSEMOVE, DW_FLAG_ENUM.MOUSEEVENTF_ABSOLUTE);
    }

    public static MOUSEINPUT adapt(LowLevelMouseProc.MOUSEPROCWPARAM wparam, LowLevelMouseProc.MSLLHOOKSTRUCT lparam) {
        WinDef.DWORD dwFlags = new WinDef.DWORD(mouserocwparamToDw_flag_enum.get(wparam.getValue()).getValue());
        if (dwFlags != null) {
            MOUSEINPUT input = new MOUSEINPUT();
            input.dx = lparam.pt.x;
            input.dy = lparam.pt.y;
            input.dwFlags = dwFlags;
            input.mouseData = lparam.mouseData;
            input.dwExtraInfo = lparam.dwExtraInfo;
            input.time = new WinDef.DWORD(0);
            return input;
        } else {
            return null;
        }
    }

}
