package hu.elte.inetsense.common.util;

public class InetsenseUtil {

    public static long calculateSpeed(long fileSizeInBytes, long durationMS) {
        double sec = durationMS / 1000.0;
        if(sec == 0) {
            return 0;
        }
        long bit = fileSizeInBytes * 8;
        return (long) (bit / sec);
    }
}
