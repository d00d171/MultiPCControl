package pl.ciochon.multipccontrol.mouse.hook.nativeapi;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
//https://msdn.microsoft.com/en-us/library/windows/desktop/dd162805(v=vs.85).aspx
public interface LowLevelMouseProc extends WinUser.HOOKPROC {
    User32 INSTANCE = Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

    WinDef.LRESULT callback(int nCode, MOUSEPROCWPARAM wParam, MSLLHOOKSTRUCT lParam);

    class MOUSEPROCWPARAM extends WinDef.WPARAM {

        public ENUM getValue() {
            return ENUM.fromValue(this.longValue());
        }

        public enum ENUM {
            WM_LBUTTONDOWN(0x0201), WM_LBUTTONUP(0x0202), WM_MOUSEMOVE(0x0200), WM_MOUSEWHEEL(0x020A), WM_MOUSEHWHEEL(0x020E), WM_RBUTTONDOWN(0x0204), WM_RBUTTONUP(0x0205);
            private long value;

            ENUM(long value) {
                this.value = value;
            }

            public static ENUM fromValue(long value) {
                for (ENUM en : ENUM.values()) {
                    if (en.value == value) {
                        return en;
                    }
                }
                return null;
            }

            public long getValue() {
                return value;
            }
        }

    }

    class MSLLHOOKSTRUCT extends Structure {
        public LPOINT pt;
        public WinDef.DWORD mouseData;
        public WinDef.DWORD flags;
        public WinDef.DWORD time;
        public BaseTSD.ULONG_PTR dwExtraInfo;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(new String[]{"pt", "mouseData", "flags",
                    "time", "dwExtraInfo"});
        }
    }

    class LPOINT extends Structure {
        public static class ByReference extends LPOINT implements Structure.ByReference {
        }

        public static final List<String> FIELDS = createFieldsOrder("x", "y");

        public WinDef.LONG x;
        public WinDef.LONG y;

        public LPOINT() {
            super();
        }

        public LPOINT(Pointer memory) {
            super(memory);
            read();
        }

        public LPOINT(WinDef.LONG x, WinDef.LONG y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "LPOINT{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        protected List<String> getFieldOrder() {
            return FIELDS;
        }
    }

}
