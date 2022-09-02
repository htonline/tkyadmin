package me.zhengjie.modules.system.verify;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64Encoder extends FilterOutputStream {
    private static final char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final Charset DEFAULT_CHARSET;
    private static final byte[] STANDARD_ENCODE_TABLE;
    private static final byte[] URL_SAFE_ENCODE_TABLE;
    private int charCount;
    private int carryOver;

    public Base64Encoder(OutputStream out) {
        super(out);
    }

    public void write(int b) throws IOException {
        if (b < 0) {
            b += 256;
        }

        int lookup;
        if (this.charCount % 3 == 0) {
            lookup = b >> 2;
            this.carryOver = b & 3;
            this.out.write(chars[lookup]);
        } else if (this.charCount % 3 == 1) {
            lookup = (this.carryOver << 4) + (b >> 4) & 63;
            this.carryOver = b & 15;
            this.out.write(chars[lookup]);
        } else if (this.charCount % 3 == 2) {
            lookup = (this.carryOver << 2) + (b >> 6) & 63;
            this.out.write(chars[lookup]);
            lookup = b & 63;
            this.out.write(chars[lookup]);
            this.carryOver = 0;
        }

        ++this.charCount;
        if (this.charCount % 57 == 0) {
            this.out.write(10);
        }

    }

    public void write(byte[] buf, int off, int len) throws IOException {
        for(int i = 0; i < len; ++i) {
            this.write(buf[off + i]);
        }

    }

    public void close() throws IOException {
        int lookup;
        if (this.charCount % 3 == 1) {
            lookup = this.carryOver << 4 & 63;
            this.out.write(chars[lookup]);
            this.out.write(61);
            this.out.write(61);
        } else if (this.charCount % 3 == 2) {
            lookup = this.carryOver << 2 & 63;
            this.out.write(chars[lookup]);
            this.out.write(61);
        }

        super.close();
    }

    public static String encode(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream((int)((double)bytes.length * 1.37D));
        Base64Encoder encodedOut = new Base64Encoder(out);

        try {
            encodedOut.write(bytes);
            encodedOut.close();
            return out.toString("UTF-8");
        } catch (IOException var4) {
            return null;
        }
    }

    public static String encodeFirst(byte[] arr) {
        boolean isMultiLine = false;
        boolean isUrlSafe = false;
        if (null == arr) {
            return null;
        } else {
            int len = arr.length;
            if (len == 0) {
                return "";
            } else {
                int evenlen = len / 3 * 3;
                int cnt = (len - 1) / 3 + 1 << 2;
                int destlen = cnt + (isMultiLine ? (cnt - 1) / 76 << 1 : 0);
                byte[] dest = new byte[destlen];
                byte[] encodeTable = isUrlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
                int left = 0;
                int i = 0;
                int urlSafeLen = 0;

                while(left < evenlen) {
                    int id = (arr[left++] & 255) << 16 | (arr[left++] & 255) << 8 | arr[left++] & 255;
                    dest[id++] = encodeTable[id >>> 18 & 63];
                    dest[id++] = encodeTable[id >>> 12 & 63];
                    dest[id++] = encodeTable[id >>> 6 & 63];
                    dest[id++] = encodeTable[id & 63];
                    if (isMultiLine) {
                        ++urlSafeLen;
                        if (urlSafeLen == 19 && id < destlen - 2) {
                            dest[id++] = 13;
                            dest[id++] = 10;
                            urlSafeLen = 0;
                        }
                    }
                }

                left = len - evenlen;
                if (left > 0) {
                    i = (arr[evenlen] & 255) << 10 | (left == 2 ? (arr[len - 1] & 255) << 2 : 0);
                    dest[destlen - 4] = encodeTable[i >> 12];
                    dest[destlen - 3] = encodeTable[i >>> 6 & 63];
                    if (isUrlSafe) {
                        urlSafeLen = destlen - 2;
                        if (2 == left) {
                            dest[destlen - 2] = encodeTable[i & 63];
                            ++urlSafeLen;
                        }

                        byte[] urlSafeDest = new byte[urlSafeLen];
                        System.arraycopy(dest, 0, urlSafeDest, 0, urlSafeLen);
                        return urlSafeDest.toString();
                    }

                    dest[destlen - 2] = left == 2 ? encodeTable[i & 63] : 61;
                    dest[destlen - 1] = 61;
                }

                return dest.toString();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");
        } else {
            Base64Encoder encoder = null;
            BufferedInputStream in = null;

            try {
                encoder = new Base64Encoder(System.out);
                in = new BufferedInputStream(new FileInputStream(args[0]));
                byte[] buf = new byte[4096];

                int bytesRead;
                while((bytesRead = in.read(buf)) != -1) {
                    encoder.write(buf, 0, bytesRead);
                }
            } finally {
                if (in != null) {
                    in.close();
                }

                if (encoder != null) {
                    encoder.close();
                }

            }

        }
    }

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    }
}
