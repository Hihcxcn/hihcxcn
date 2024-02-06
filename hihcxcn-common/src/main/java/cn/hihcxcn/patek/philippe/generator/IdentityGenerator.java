package cn.hihcxcn.patek.philippe.generator;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * ID生成器
 */
public class IdentityGenerator {

    private static final char[] BASE64_DIGITS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

    private static final char[] BASE32_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V'
    };

    private static final int BASE64_BIT = 6;

    private static final int BASE32_BIT = 5;

    private static final SnowFlake SNOW_FLAKE = new SnowFlake();

    /**
     * 生成下一个uuid
     * @return uuid
     */
    public static String nextUUID() {
        UUID uuid = UUID.randomUUID();
        long leastSigBits = uuid.getLeastSignificantBits();
        long mostSigBits = uuid.getMostSignificantBits();
        return (digits(mostSigBits >> 32, 8) +
            digits(mostSigBits >> 16, 4) +
            digits(mostSigBits, 4) +
            digits(leastSigBits >> 48, 4) +
            digits(leastSigBits, 12));
    }

    /**
     * 使用snow_flake算法生成下一个id
     * @return SFId
     */
    public static String nextSFId() {
        return SNOW_FLAKE.nextId();
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * snowflake变种算法，使用32位机器ip作为机器标识，返回字符串
     */
    public static class SnowFlake {

        /**
         * 起始的时间戳
         */
        private final static long START_STMP = 1544544000000L;

        /**
         * 本地序号占用的位数，支持单机每毫秒生成2000+唯一id
         */
        private final static long SEQUENCE_BIT = 11;

        /**
         * 机器占用位数
         */
        private final static long MACHINE_BIT = 32;

        /**
         * 时间戳占用位数，够用69年
         */
        private final static long TIMESTAMP_BIT = 41;

        /**
         * 每一部分的最大值
         */
        private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

        /**
         * 机器标识
         */
        private long machineId = 0;

        /**
         * 内部序号
         */
        private long sequence = 0L;

        /**
         * 上一个时间戳
         */
        private long lastStamp = -1L;

        public SnowFlake() {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                            byte[] addressBytes = inetAddress.getAddress();
                            for (byte b : addressBytes) {
                                machineId = machineId << 8;
                                machineId |= b & 0xff;
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                throw new IllegalStateException(e);
            }
        }

        /**
         * 产生下一个ID
         */
        public String nextId() {
            long currentStamp = getNewStamp();
            long currentSequence;
            synchronized (this) {
                if (currentStamp < lastStamp) {
                    throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
                }

                if (currentStamp == lastStamp) {
                    //相同毫秒内，序列号自增
                    sequence = (sequence + 1) & MAX_SEQUENCE;
                    //同一毫秒的序列数已经达到最大
                    if (sequence == 0L) {
                        currentStamp = getNextMill();
                    }
                } else {
                    //不同毫秒内，序列号置为0
                    sequence = 0L;
                }
                currentSequence = sequence;
                lastStamp = currentStamp;
            }

            return removePrefixZeros(toBase32StringReorder(currentStamp - START_STMP, currentSequence));
        }

        String removePrefixZeros(String str) {
            int nonZeroPos = 0;
            for (; nonZeroPos < str.length(); ++nonZeroPos) {
                if (str.charAt(nonZeroPos) != '0') {
                    break;
                }
            }
            if (nonZeroPos > 0) {
                return new StringBuilder(str).delete(0, nonZeroPos).toString();
            }
            return str;
        }

        /**
         * 转成64进制字符串
         */
        String toBase64String(long currentStamp, long currentSequence) {
            return digits64(currentStamp >> 5, 6)
                + digits64(currentStamp << 31 | machineId >> 1, 6)
                + digits64(machineId << 11 | currentSequence, 2);
        }

        String toBase32String(long currentStamp, long currentSequence) {
            return digits32(currentStamp >> 1, 8)
                + digits32(currentStamp << 29 | machineId >> 3, 6)
                + digits32(machineId << 12 | currentSequence << 1, 3);
        }

        /**
         * 32进制重排序字符串
         *
         * 当前序列 + 时间戳 + 机器标识
         * @param currentStamp
         * @param currentSequence
         * @return
         */
        String toBase32StringReorder(long currentStamp, long currentSequence) {
            return digits32Reverse(currentSequence >> 1, 2)
                + digits32Reverse(currentSequence << 39 | currentStamp >> 2, 8)
                + digits32Reverse(currentStamp << 33 | machineId << 1, 7);
        }

        /**
         * 数字转64进制
         * @param val 待转换值
         * @param digits 64进制转换位数
         * @return 转换后的64进制字符串
         */
        String digits64(long val, int digits) {
            StringBuilder sb = new StringBuilder();
            long leftVal = val;
            for (int i = 0; i < digits; ++i) {
                int idx = (int)(leftVal & 63);
                sb.append(BASE64_DIGITS[idx]);
                leftVal >>= BASE64_BIT;
            }
            return sb.reverse().toString();
        }

        /**
         * 数字转32进制
         * @param val 待转换值
         * @param digits 32进制转换位数
         * @return 转换后的32进制字符串
         */
        String digits32(long val, int digits) {
            StringBuilder sb = new StringBuilder();
            long leftVal = val;
            for (int i = 0; i < digits; ++i) {
                int idx = (int)(leftVal & 31);
                sb.append(BASE32_DIGITS[idx]);
                leftVal >>= BASE32_BIT;
            }
            return sb.reverse().toString();
        }

        /**
         * 数字转32进制翻转高低位
         * @param val 待转换值
         * @param digits 32进制转换位数
         * @return 转换后的32进制字符串
         */
        String digits32Reverse(long val, int digits) {
            StringBuilder sb = new StringBuilder();
            long leftVal = val;
            for (int i = 0; i < digits; ++i) {
                int idx = (int)(leftVal & 31);
                sb.append(BASE32_DIGITS[idx]);
                leftVal >>= BASE32_BIT;
            }
            return sb.toString();
        }

        private long getNextMill() {
            long mill = getNewStamp();
            // 等待下一毫秒
            while (mill <= lastStamp) {
                mill = getNewStamp();
            }
            return mill;
        }

        private long getNewStamp() {
            return System.currentTimeMillis();
        }

        public static void main(String[] args) {
            SnowFlake snowFlake = new SnowFlake();
            long start = System.currentTimeMillis();
            for (int i = 0; i < (1 << 11); i++) {
                System.out.println(nextSFId());
                //nextUUID();
            }
            System.out.println(System.currentTimeMillis() - start);

        }
    }
}
