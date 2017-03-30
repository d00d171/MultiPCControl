package pl.ciochon.multipccontrol.mouse.util;

import com.sun.jna.platform.win32.WinDef;
import pl.ciochon.multipccontrol.mouse.generator.nativeapi.MOUSEINPUT;
import pl.ciochon.multipccontrol.mouse.hook.nativeapi.LowLevelMouseProc;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class InOutAdapter {

    private static Map<LowLevelMouseProc.MOUSEPROCWPARAM.ENUM, MOUSEINPUT.DW_FLAG_ENUM> mouserocwparamToDw_flag_enum;

    static {
        mouserocwparamToDw_flag_enum = new HashMap<LowLevelMouseProc.MOUSEPROCWPARAM.ENUM, MOUSEINPUT.DW_FLAG_ENUM>();
        mouserocwparamToDw_flag_enum.put(LowLevelMouseProc.MOUSEPROCWPARAM.ENUM.WM_LBUTTONDOWN, MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_LEFTDOWN);
        mouserocwparamToDw_flag_enum.put(LowLevelMouseProc.MOUSEPROCWPARAM.ENUM.WM_LBUTTONUP, MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_LEFTUP);
        mouserocwparamToDw_flag_enum.put(LowLevelMouseProc.MOUSEPROCWPARAM.ENUM.WM_MOUSEMOVE, MOUSEINPUT.DW_FLAG_ENUM._MOUSEVENTF_ABSOLUTE_MOVE);
        mouserocwparamToDw_flag_enum.put(LowLevelMouseProc.MOUSEPROCWPARAM.ENUM.WM_RBUTTONDOWN, MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_RIGHTDOWN);
        mouserocwparamToDw_flag_enum.put(LowLevelMouseProc.MOUSEPROCWPARAM.ENUM.WM_RBUTTONUP, MOUSEINPUT.DW_FLAG_ENUM.MOUSEEVENTF_RIGHTUP);
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
