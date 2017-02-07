import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HurricaneData {
    public static void main(String[] args) throws IOException {
        String filePath = "G:\\WORKSPACE\\CodeTest\\resources\\data.txt";
        readFile(filePath);
    }

    public static void readFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fileReader);
        List<Hurricane> hurricanes = new LinkedList<>();
        String name = "";
        int maxWind = 0;
        int lineCounter = 0;
        int windStrength;
        for (String textLine = br.readLine(); textLine != null; textLine = br.readLine()) {
            Hurricane hurricane = new Hurricane();
            if (((textLine.startsWith("EP") || textLine.startsWith("CP"))) && textLine.contains("2009")) {
                String tempName = "";
                tempName = textLine.substring(19, 28).toString().replace(" ", "");
                name = tempName;
                hurricane.setName(name);
                //System.out.println("NAME " + name);
                //System.out.println("lineCounter: " + lineCounter);
                windStrength = 0;
                lineCounter = Integer.parseInt(textLine.substring(34, 36).replace(" ", ""));
                for (int i = 0; i < lineCounter; i++) {
                    textLine = br.readLine();
                    maxWind = Integer.parseInt(textLine.substring(38, 41).replaceFirst(" ", ""));
                    /*System.out.println(textLine);
                    System.out.println(lineCounter);*/
                    if (maxWind > windStrength) {
                        windStrength = maxWind;
                        hurricane.setMaximumSustainedWind(windStrength);
                    }
                }
                hurricanes.add(hurricane);
            }
        }
        br.close();
        for (Hurricane h : hurricanes) {
            System.out.println(h.getName() + " " + h.getMaximumSustainedWind());
        }
    }

    static class Hurricane {
        private String name;
        private int maximumSustainedWind;

        public Hurricane(String name, int maximumSustainedWind) {
            this.name = name;
            this.maximumSustainedWind = maximumSustainedWind;
        }

        public Hurricane() {
        }

        public String getName() {
            return name;
        }

        public int getMaximumSustainedWind() {
            return maximumSustainedWind;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMaximumSustainedWind(int maximumSustainedWind) {
            this.maximumSustainedWind = maximumSustainedWind;
        }
    }
}
