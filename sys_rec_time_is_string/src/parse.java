public class parse {
    public static void progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 10; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());
        System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }
    public static void main(String[] arg) throws Exception {
//        String anim= "|/-\\";
//        for (int x =0 ; x < 100 ; x++) {
//            String data = "\r" + anim.charAt(x % anim.length()) + " " + x;
//            System.out.write(data.getBytes());
//            Thread.sleep(100);
        progressPercentage(500,500);
//        }
    }
}
