/**
 * VM options -Xmx512m -Xms512m
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */


public class TypeMeter {
    public static final int CONTAINER_LENGTH = 1_000_000;
    public static final int LOOP_COUNT = 5;
    EnumTypes argType;
    String stringSample;
    Object obj;

    public TypeMeter(byte arg) {
        argType = EnumTypes.BYTE;
    }
    public TypeMeter(boolean arg) {
        argType = EnumTypes.BOOLEAN;
    }
    public TypeMeter(short arg) {
        argType = EnumTypes.SHORT;
    }
    public TypeMeter(char arg) {
        argType = EnumTypes.CHAR;
    }
    public TypeMeter(int arg) {
        argType = EnumTypes.INT;
    }
    public TypeMeter(float arg) {
        argType = EnumTypes.FLOAT;
    }
    public TypeMeter(long arg) {
        argType = EnumTypes.LONG;
    }
    public TypeMeter(double arg) {
        argType = EnumTypes.DOUBLE;
    }
    public TypeMeter(String arg) {
        argType = EnumTypes.STRING;
        stringSample = arg;
    }
    public TypeMeter(Object arg) {
        argType = EnumTypes.REFERENCE;
        obj = arg;
    }


    public long getSize() throws InterruptedException, IllegalAccessException, InstantiationException {
        Runtime runtime = Runtime.getRuntime();
        long freeMemBefore = 0;
        long freeMemAfter = 0;
        long[] res = new long[LOOP_COUNT];

        for (int l=0; l<LOOP_COUNT; l++) {

            switch (argType) {
                case BYTE:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    byte[] containerByte = new byte[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case BOOLEAN:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    boolean[] containerBool = new boolean[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case CHAR:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    char[] containerChar = new char[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case SHORT:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    short[] containerShort = new short[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case INT:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    int[] containerInt = new int[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case FLOAT:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    float[] containerFloat = new float[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case LONG:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    long[] containerLong = new long[CONTAINER_LENGTH];
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemAfter = runtime.freeMemory();
                    break;
                case DOUBLE:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    double[] containerDouble = new double[CONTAINER_LENGTH];
                    freeMemAfter = runtime.freeMemory();
                    break;
                case STRING:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    String[] containerString = new String[CONTAINER_LENGTH];
                    for (int j = 0; j < CONTAINER_LENGTH; j++) {
                        StringBuilder sb = new StringBuilder(stringSample);
                        containerString[j] = sb.toString();
                    }
                    freeMemAfter = runtime.freeMemory();
                    break;
                case REFERENCE:
                    System.gc();
                    Thread.currentThread().sleep(1000);
                    freeMemBefore = runtime.freeMemory();
                    Object[] containerObject = new Object[CONTAINER_LENGTH];
                    long containerMem = runtime.freeMemory();
                    for (int j=0;j<CONTAINER_LENGTH;j++) {
                        containerObject[j] = obj.getClass().newInstance();
                    }
                    freeMemAfter = runtime.freeMemory();
                    break;
            }
            res[l] = (freeMemBefore- freeMemAfter)/CONTAINER_LENGTH;
        }
        long result = 0;
        for (int l=0; l<LOOP_COUNT; l++) {
            result = result + res[l];
        }
        return result/LOOP_COUNT;

    }


}
