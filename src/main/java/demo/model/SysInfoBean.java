package demo.model;

public class SysInfoBean {
    private int threadNum;
    private MemoryInfo memoryInfo;

    public MemoryInfo getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(MemoryInfo memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }


    public static class MemoryInfo{
        private long heapUsage;
        private long heapMax;
        private long nonHeapUsage;
        private long nonHeapMax;

        public long getHeadUsage() {
            return heapUsage;
        }

        public void setHeadUsage(long heapUsage) {
            this.heapUsage = heapUsage;
        }

        public long getHeadMax() {
            return heapMax;
        }

        public void setHeadMax(long heapMax) {
            this.heapMax = heapMax;
        }

        public long getNonHeapUsage() {
            return nonHeapUsage;
        }

        public void setNonHeapUsage(long nonHeapUsage) {
            this.nonHeapUsage = nonHeapUsage;
        }

        public long getNonHeapMax() {
            return nonHeapMax;
        }

        public void setNonHeapMax(long nonHeapMax) {
            this.nonHeapMax = nonHeapMax;
        }
    }
}
