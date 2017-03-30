package pl.ciochon.multikeyboard.host.mouse.generator;

import com.sun.jna.platform.win32.WinUser;

import java.io.Serializable;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class MOUSEINPUT extends WinUser.MOUSEINPUT implements Serializable {

    public DW_FLAG_ENUM getValue() {
        return DW_FLAG_ENUM.fromValue(this.dwFlags.longValue());
    }

    public enum DW_FLAG_ENUM {
        //TODO absolute wartosc
        _MOUSEVENTF_ABSOLUTE_MOVE(0x8001),
        MOUSEEVENTF_ABSOLUTE(0x8000), MOUSEEVENTF_HWHEEL(0x01000), MOUSEEVENTF_MOVE(0x0001), MOUSEEVENTF_MOVE_NOCOALESCE(0x2000),
        MOUSEEVENTF_LEFTDOWN(0x0002), MOUSEEVENTF_LEFTUP(0x0004), MOUSEEVENTF_RIGHTDOWN(0x0008), MOUSEEVENTF_RIGHTUP(0x0010), MOUSEEVENTF_MIDDLEDOWN(0x0020),
        MOUSEEVENTF_MIDDLEUP(0x0040), MOUSEEVENTF_VIRTUALDESK(0x4000), MOUSEEVENTF_WHEEL(0x0800), MOUSEEVENTF_XDOWN(0x0080), MOUSEEVENTF_XUP(0x0100);

        private long value;

        DW_FLAG_ENUM(long value) {
            this.value = value;
        }

        public static DW_FLAG_ENUM fromValue(long value) {
            for (DW_FLAG_ENUM en : DW_FLAG_ENUM.values()) {
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
