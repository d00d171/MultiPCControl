package pl.ciochon.multikeyboard.host.keyboard.hook;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * Created by Konrad Ciochoń on 2017-03-27.
 */
//https://msdn.microsoft.com/en-us/library/windows/desktop/ms644985(v=vs.85).aspx
public class KeyHook {
    private static volatile boolean quit;
    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;

    public static void start() {
        final User32 lib = User32.INSTANCE;
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

        keyboardHook = new WinUser.LowLevelKeyboardProc() {
            public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {
                if (nCode >= 0) {
                    switch(wParam.intValue()) {
                        case WinUser.WM_KEYDOWN:
                        case WinUser.WM_SYSKEYDOWN:
                        case WinUser.WM_KEYUP:
                        case WinUser.WM_SYSKEYUP:
                            System.err.println("in callback, key=" + info.vkCode);
                            if (info.vkCode == 81) {
                                quit = true;
                            }

                            //return > 0 to prevent the rest of hook chain from processing event
                            return new WinDef.LRESULT(1);
                    }
                }

                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };

        hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);

        System.out.println("Keyboard hook installed, type anywhere, 'q' to quit");
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try { Thread.sleep(10); } catch(Exception e) { }
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
            }
            else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }

        lib.UnhookWindowsHookEx(hhk);
    }


}
