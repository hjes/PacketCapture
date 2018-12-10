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
        private long headUsage;
        private long headMax;
        private long nonHeapUsage;
        private long nonHeapMax;

        public long getHeadUsage() {
            return headUsage;
        }

        public void setHeadUsage(long headUsage) {
            this.headUsage = headUsage;
        }

        public long getHeadMax() {
            return headMax;
        }

        public void setHeadMax(long headMax) {
            this.headMax = headMax;
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
