package pl.ciochon.multipccontrol.mouse.generator.nativeapi;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class MOUSEINPUT extends WinUser.MOUSEINPUT implements Externalizable {

    public DW_FLAG_ENUM getValue() {
        return DW_FLAG_ENUM.fromValue(this.dwFlags.longValue());
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(time.longValue());
        out.writeLong(dwExtraInfo.longValue());
        out.writeLong(dx.longValue());
        out.writeLong(dy.longValue());
        out.writeLong(mouseData.longValue());
        out.writeLong(dwFlags.longValue());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        time = new WinDef.DWORD(in.readLong());
        dwExtraInfo = new BaseTSD.ULONG_PTR(in.readLong());
        dx = new WinDef.LONG(in.readLong());
        dy = new WinDef.LONG(in.readLong());
        mouseData = new WinDef.DWORD(in.readLong());
        dwFlags = new WinDef.DWORD(in.readLong());
    }

    public enum DW_FLAG_ENUM {
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
