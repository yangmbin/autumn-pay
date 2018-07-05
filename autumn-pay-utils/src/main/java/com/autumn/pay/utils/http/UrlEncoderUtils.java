package com.autumn.pay.utils.http;

import com.autumn.pay.utils.DotNetToJavaStringHelper;
import com.autumn.pay.utils.exceptions.ArgumentOutOfRangeException;
import com.autumn.util.ArgumentNullException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Url 编码帮助，基ms自带修改，实现标准的相关编解码，处理掉 + / : ? 等相关问题,有 Bug
 *
 * @author yangmbin
 * @date 2018/7/4 15:59
 * @since 1.0.0
 */
public final class UrlEncoderUtils {

    private static int hexToInt(char h) {
        if (h >= '0' && h <= '9') {
            return h - '0';
        } else if (h >= 'a' && h <= 'f') {
            return h - 'a' + 10;
        } else if (h >= 'A' && h <= 'F') {
            return h - 'A' + 10;
        }
        return -1;
    }

    private static char intToHex(int n) {
        if (n <= 9) {
            return (char) (n + 0x30);
        }
        return (char) ((n - 10) + 0x61);
    }

    /**
     * 是否是安全字符
     *
     * @param ch
     * @return
     */
    private static boolean isUrlSafeChar(char ch) {
        if ((((ch < 'a') || (ch > 'z')) && ((ch < 'A') || (ch > 'Z'))) && ((ch < '0') || (ch > '9'))) {
            switch (ch) {
                case '(':
                case ')':
                case '*':
                case '-':
                case '.':
                case '!':
                case ':':
                case '/':
                case '?':
                case '&':
                case '=':
                case '+':
                case ',':
                case '_':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * Url解码
     *
     * @param value
     * @param encoding
     * @return
     */
    public static String urlDecode(String value, String encoding) {
        if (value == null) {
            return null;
        }
        if (encoding == null) {
            encoding = "UTF-8";
        }
        int length = value.length();
        UrlDecoder decoder = new UrlDecoder(length, encoding);
        for (int i = 0; i < length; i++) {
            char ch = value.charAt(i);
            if (isUrlSafeChar(ch)) {
                decoder.addByte((byte) ch);
            } else if ((ch == '%') && (i < (length - 2))) {
                if ((value.charAt(i + 1) == 'u') && (i < (length - 5))) {
                    int num3 = hexToInt(value.charAt(i + 2));
                    int num4 = hexToInt(value.charAt(i + 3));
                    int num5 = hexToInt(value.charAt(i + 4));
                    int num6 = hexToInt(value.charAt(i + 5));
                    if (((num3 < 0) || (num4 < 0)) || ((num5 < 0) || (num6 < 0))) {
                        if ((ch & 0xff80) == 0) {
                            decoder.addByte((byte) ch);
                        } else {
                            decoder.addChar(ch);
                        }
                        continue;
                    }
                    ch = (char) ((((num3 << 12) | (num4 << 8)) | (num5 << 4)) | num6);
                    i += 5;
                    decoder.addChar(ch);
                    continue;
                }
                int num7 = hexToInt(value.charAt(i + 1));
                int num8 = hexToInt(value.charAt(i + 2));
                if ((num7 >= 0) && (num8 >= 0)) {
                    byte b = (byte) ((num7 << 4) | num8);
                    i += 2;
                    decoder.addByte(b);
                    continue;
                }
            }
        }
        return validateString(decoder.getString());
    }

    /**
     * Url编码
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public static byte[] urlEncode(byte[] bytes, int offset, int count) {
        if (!validateUrlEncodingParameters(bytes, offset, count)) {
            return null;
        }
        int num = 0;
        int num2 = 0;
        for (int i = 0; i < count; i++) {
            char ch = (char) bytes[offset + i];
            if (!isUrlSafeChar(ch)) {
                num2++;
            }
        }
        if ((num == 0) && (num2 == 0)) {
            if ((offset == 0) && (bytes.length == count)) {
                return bytes;
            }
            byte[] dst = new byte[count];
            System.arraycopy(bytes, offset, dst, 0, count);

            return dst;
        }
        byte[] buffer = new byte[count + (num2 * 2)];
        int num3 = 0;
        for (int j = 0; j < count; j++) {
            byte num6 = bytes[offset + j];
            char ch2 = (char) num6;
            if (isUrlSafeChar(ch2)) {
                buffer[num3++] = num6;
            } else {
                buffer[num3++] = 0x25;
                buffer[num3++] = (byte) intToHex((num6 >> 4) & 15);
                buffer[num3++] = (byte) intToHex(num6 & 15);
            }
        }
        return buffer;
    }

    /**
     * Url编码
     *
     * @param str
     * @param encoding
     * @return
     */
    public static String urlEncode(String str, String encoding) {
        if (DotNetToJavaStringHelper.isNullOrEmpty(str)) {
            return str;
        }
        if (encoding == null) {
            encoding = "UTF-8";
        }

        String retStr = null;
        try {
            byte[] bytes = str.getBytes(encoding);
            retStr = new String(urlEncode(bytes, 0, bytes.length), "ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    private static String validateString(String input) {
        if (DotNetToJavaStringHelper.isNullOrEmpty(input)) {
            return input;
        }
        int num = -1;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isSurrogate(input.charAt(i))) {
                num = i;
                break;
            }
        }
        if (num < 0) {
            return input;
        }
        char[] chArray = input.toCharArray();
        for (int j = num; j < chArray.length; j++) {
            char c = chArray[j];
            if (Character.isLowSurrogate(c)) {
                chArray[j] = (char) 0xfffd;
            } else if (Character.isHighSurrogate(c)) {
                if (((j + 1) < chArray.length) && Character.isLowSurrogate(chArray[j + 1])) {
                    j++;
                } else {
                    chArray[j] = (char) 0xfffd;
                }
            }
        }
        return new String(chArray);
    }

    private static boolean validateUrlEncodingParameters(byte[] bytes, int offset, int count) {
        if (bytes == null && count == 0) {
            return false;
        }
        if (bytes == null) {
            throw new ArgumentNullException("bytes");
        }
        if (offset < 0 || offset > bytes.length) {
            throw new ArgumentOutOfRangeException("offset");
        }
        if (count < 0 || offset + count > bytes.length) {
            throw new ArgumentOutOfRangeException("count");
        }
        return true;
    }

    private static class UrlDecoder {
        // Fields
        private int _bufferSize;
        private byte[] _byteBuffer;
        private char[] _charBuffer;
        private String _encoding;
        private int _numBytes;
        private int _numChars;

        // Methods
        public UrlDecoder(int bufferSize, String encoding) {
            this._bufferSize = bufferSize;
            this._encoding = encoding;
            this._charBuffer = new char[bufferSize];
        }

        public final void addByte(byte b) {
            if (this._byteBuffer == null) {
                this._byteBuffer = new byte[this._bufferSize];
            }
            int index = this._numBytes;
            this._numBytes = index + 1;
            this._byteBuffer[index] = b;
        }

        public final void addChar(char ch) {
            if (this._numBytes > 0) {
                this.flushBytes();
            }
            int index = this._numChars;
            this._numChars = index + 1;
            this._charBuffer[index] = ch;
        }

        private void flushBytes() {
            if (this._numBytes > 0) {
                this._numChars += getChars(this._encoding, this._byteBuffer, 0, this._numBytes, this._charBuffer, this._numChars);
                this._numBytes = 0;
            }
        }

        public final String getString() {
            if (this._numBytes > 0) {
                this.flushBytes();
            }
            if (this._numChars > 0) {
                return new String(this._charBuffer, 0, this._numChars);
            }
            return "";
        }
    }


    /**
     * 进行byte[]解码并拷贝
     * @param encoding
     * @param bytes
     * @param byteIndex
     * @param byteCount
     * @param chars
     * @param charIndex
     * @return
     */
    private static int getChars(String encoding, byte[] bytes, int byteIndex, int byteCount, char[] chars, int charIndex) {
        // 获取真实byte[]
        List<Byte> realBytesList = new ArrayList<>();
        for (int i = byteIndex; i < bytes.length; i++) {
            if (realBytesList.size() >= byteCount)
                break;
            realBytesList.add(bytes[i]);
        }
        Byte[] tmpRealBytes = new Byte[realBytesList.size()];
        realBytesList.toArray(tmpRealBytes);
        byte[] realBytes = new byte[tmpRealBytes.length];
        for (int i = 0; i < tmpRealBytes.length; i++) {
            realBytes[i] = tmpRealBytes[i];
        }
        // byte[]转char[]
        Charset cs = Charset.forName(encoding);
        ByteBuffer bb = ByteBuffer.allocate(realBytes.length);
        bb.put(realBytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        char[] realChars = cb.array();
        // 拷贝
        int copyNum = 0;
        for (int i = charIndex; i < chars.length; i++) {
            if (i - charIndex >= realChars.length)
                break;
            chars[i] = realChars[i - charIndex];
            copyNum++;
        }
        return copyNum;
    }
}