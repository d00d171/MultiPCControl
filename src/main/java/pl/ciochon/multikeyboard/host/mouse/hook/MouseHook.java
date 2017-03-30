package pl.ciochon.multikeyboard.host.mouse.hook;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import pl.ciochon.multikeyboard.host.mouse.generator.MOUSEINPUT;
import pl.ciochon.multikeyboard.host.mouse.util.InOutAdapter;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Konrad Ciochoń on 2017-03-27.
 */
//https://msdn.microsoft.com/en-us/library/windows/desktop/ms644986(v=vs.85).aspx
public class MouseHook {

    private LinkedBlockingQueue<MOUSEINPUT> mouseInputQueue;
    private static volatile boolean quit;
    private static LowLevelMouseProc mouseHook;
    private static WinUser.HHOOK hhk;

    public MouseHook(LinkedBlockingQueue<MOUSEINPUT> mouseInputQueue) {
        this.mouseInputQueue = mouseInputQueue;
    }

    public void start() {
        final User32 lib = User32.INSTANCE;
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        mouseHook = new LowLevelMouseProc() {
            public WinDef.LRESULT callback(int nCode, MOUSEPROCWPARAM wParam, MSLLHOOKSTRUCT info) {

                if (nCode >= 0) {
                    MOUSEINPUT input = InOutAdapter.adapt(wParam, info);

                    if (input != null) {
                        try {
                            mouseInputQueue.put(input);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //MouseEventGenerator.generateEvent(input);

                        //return > 0 to prevent the rest of hook chain from processing event
                        if (!MOUSEINPUT.DW_FLAG_ENUM._MOUSEVENTF_ABSOLUTE_MOVE.equals(input.getValue())) {
                            return new WinDef.LRESULT(1);
                        }
                    }
                }

                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };

        hhk = lib.SetWindowsHookEx(WinUser.WH_MOUSE_LL, mouseHook, hMod, 0);

        System.out.println("Mouse hook installed");
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                System.err.println("unhook and exit");
                lib.UnhookWindowsHookEx(hhk);
                System.exit(0);
            }
        }.start();

        // This bit never returns from GetMessage
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            } else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }

        lib.UnhookWindowsHookEx(hhk);
    }

}
