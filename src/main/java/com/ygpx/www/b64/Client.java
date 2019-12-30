package com.ygpx.www.b64;

import com.ygpx.www.b64.impl.B642X;
import com.ygpx.www.b64.impl.SwitchDataChannel;
import com.ygpx.www.b64.impl.X2B64;

import java.io.File;

@SuppressWarnings("unused")
public class Client {

    public static void main(String[] args) {
        String path = "C:\\Users\\hgy\\Desktop\\test";
        Client client = new Client();
//        client.encodeB4(path);
//        client.decodeB4(path);

        // 使用内存映射方式
        SwitchDataChannel dataChannel = new SwitchDataChannel();
        client.encodeB4ByChannel(path, dataChannel);
//        client.decodeB4ByChannel(path, dataChannel);
    }

    public void encodeB4(String path) {
        B64Template.dealFile(new File(path), (file) -> {
            X2B64 x2B64 = new X2B64();
            x2B64.x2B64(file);
        });
    }

    public void decodeB4(String path) {
        B64Template.dealFile(new File(path), (file) -> {
            B642X b642X = new B642X();
            b642X.b642X(file);
        });
    }

    public void encodeB4ByChannel(String path, SwitchDataChannel dataChannel) {
        B64Template.dealFile(new File(path), dataChannel::x2B64ByChannel);
    }

    public void decodeB4ByChannel(String path, SwitchDataChannel dataChannel) {
        B64Template.dealFile(new File(path), dataChannel::b642XByChannel);
    }
}
