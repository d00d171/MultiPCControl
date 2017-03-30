package pl.ciochon.multikeyboard.host.mouse.generator;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;

import java.io.Serializable;

/**
 * Created by Konrad Ciochoń on 2017-03-28.
 */
public class MouseInputSerializable implements Serializable {

    private long time;

    private long dwExtraInfo;

    private long dx;

    private long dy;

    private long mouseData;

    private long dwFlags;

    public MouseInputSerializable() {
    }

    public MouseInputSerializable(MOUSEINPUT mouseinput) {
        this.time = mouseinput.time.longValue();
        this.dwExtraInfo = mouseinput.dwExtraInfo.longValue();
        this.dx = mouseinput.dx.longValue();
        this.dy = mouseinput.dy.longValue();
        this.mouseData = mouseinput.mouseData.longValue();
        this.dwFlags = mouseinput.dwFlags.longValue();
    }

    public MOUSEINPUT toMOUSEINPUT() {
        MOUSEINPUT mouseinput = new MOUSEINPUT();
        mouseinput.time = new WinDef.DWORD(time);
        mouseinput.dwExtraInfo = new BaseTSD.ULONG_PTR(dwExtraInfo);
        mouseinput.dx = new WinDef.LONG(dx);
        mouseinput.dy = new WinDef.LONG(dy);
        mouseinput.mouseData = new WinDef.DWORD(mouseData);
        mouseinput.dwFlags = new WinDef.DWORD(dwFlags);
        return mouseinput;

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDwExtraInfo() {
        return dwExtraInfo;
    }

    public void setDwExtraInfo(long dwExtraInfo) {
        this.dwExtraInfo = dwExtraInfo;
    }

    public long getDx() {
        return dx;
    }

    public void setDx(long dx) {
        this.dx = dx;
    }

    public long getDy() {
        return dy;
    }

    public void setDy(long dy) {
        this.dy = dy;
    }

    public long getMouseData() {
        return mouseData;
    }

    public void setMouseData(long mouseData) {
        this.mouseData = mouseData;
    }

    public long getDwFlags() {
        return dwFlags;
    }

    public void setDwFlags(long dwFlags) {
        this.dwFlags = dwFlags;
    }
}
