public class RotatingDonut {
    public static void main(String[] args) throws Exception {
        double A = 0, B = 0;
        int width = 80;
        int height = 22;
        double[] z = new double[width * height];
        char[] b = new char[width * height];

        System.out.print("\u001b[2J");

        while (true) {
            for (int i = 0; i < width * height; i++) {
                b[i] = ' ';
                z[i] = 0;
            }

            for (double theta = 0; theta < 2 * Math.PI; theta += 0.07) {
                for (double phi = 0; phi < 2 * Math.PI; phi += 0.02) {
                    double sinTheta = Math.sin(theta);
                    double cosTheta = Math.cos(theta);
                    double sinPhi = Math.sin(phi);
                    double cosPhi = Math.cos(phi);

                    double sinA = Math.sin(A);
                    double cosA = Math.cos(A);
                    double sinB = Math.sin(B);
                    double cosB = Math.cos(B);

                    double circleX = cosTheta + 2;
                    double circleY = sinTheta;

                    double x = circleX * (cosB * cosPhi + sinA * sinB * sinPhi) - circleY * cosA * sinB;
                    double y = circleX * (sinB * cosPhi - sinA * cosB * sinPhi) + circleY * cosA * cosB;
                    double zCoord = 5 + cosA * circleX * sinPhi + circleY * sinA;
                    double ooz = 1 / zCoord;

                    int xp = (int) (width / 2 + 30 * ooz * x);
                    int yp = (int) (height / 2 - 15 * ooz * y);

                    int idx = xp + yp * width;
                    double L = cosPhi * cosTheta * sinB - cosA * cosTheta * sinPhi - sinA * sinTheta
                            + cosB * (cosA * sinTheta - cosTheta * sinA * sinPhi);

                    if (idx >= 0 && idx < width * height && ooz > z[idx]) {
                        z[idx] = ooz;
                        int luminanceIndex = (int) (L * 8);
                        if (luminanceIndex < 0) luminanceIndex = 0;
                        if (luminanceIndex > 11) luminanceIndex = 11;
                        char[] chars = ".,-~:;=!*#$@".toCharArray();
                        // char[] chars = "@$#*!=;:~-,.".toCharArray();
                        b[idx] = chars[luminanceIndex];
                    }
                }
            }

            System.out.print("\u001b[H");
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < width * height; k++) {
                sb.append(b[k]);
                if (k % width == 0 && k != 0) sb.append('\n');
            }
            System.out.print(sb.toString());

            A += 0.04;
            B += 0.02;

            Thread.sleep(30);
        }
    }
}
