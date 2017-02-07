import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ElectricityCalculator {

    public static void main(String[] args) throws IOException {

        //parsing html
        String html = "http://www.mercado.ren.pt/EN/Electr/MarketInfo/Interconnections/CapForecast/Pages/Daily.aspx";
        Document doc = Jsoup.connect(html).get();
        Elements tableElements = doc.getElementsByClass("gridALL");
        String temp = tableElements.get(0).toString();

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(temp.getBytes());
        // read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<Electricity> electricities = new ArrayList<>();

        List<Days> days = new ArrayList<>();

        //dates
        String[] tempDates = (tableElements.get(0).getElementsByClass("tabHEADER").text().replace("HOUR", "").split(" "));

        int sum = 0;
        String day = "";
        String month = "";

        //dates and month in list
        for (int i = 1; i < tempDates.length; i++) {
            Days days1 = new Days(sum, day, month);
            day = tempDates[i].substring(0, 2);
            month = tempDates[i].substring(3, 6);
            //setting day and month in days1 list
            days1.setDay(day);
            days1.setMonth(month);
            days.add(days1);
            //System.out.println(days1.getDay() + " " + days1.getMonth());
        }

        //variables
        int id = 0;
        int value = 0;
        String type = "";
        String line;

        //extracting data from html
        while ((line = br.readLine()) != null) {
            Electricity electricity = new Electricity(id, value, type);
            // forecast
            if (line.contains("txtPREV") || line.contains("txtrPREV")) {
                electricity.setValue(Integer.parseInt(line.replace("60px", "").replaceAll("\\D+", "")));
                electricity.setTypeOfData("forecast");
                electricity.setId(id++);
            }
            // actual
            else if (line.contains("txtrVERIF") || line.contains("txtVERIF")) {
                electricity.setValue(Integer.parseInt(line.replace("60px", "").replaceAll("\\D+", "")));
                electricity.setTypeOfData("actual");
                electricity.setId(id++);

            } else {
                continue;
            }
            electricities.add(electricity);
        }
        br.close();

        //test of list
        /*for (Electricity e : electricities) {
            System.out.println("id = " + e.getId() + ", value = " + e.getValue() + " type = " + e.getTypeOfData());
        }*/

        sum = 0;
        day = "";
        month = "";
        String type1 = "";
        for (int i = 0; i < days.size(); i++) {
            sum = 0;

            for (int j = i; j < electricities.size() - 1; j += 15) {
                sum += electricities.get(j).getValue();
                //System.out.println("j " + electricities.get(j).getValue());
                type1 = electricities.get(j).getTypeOfData();
            }
            days.get(i).setSum(sum);
            days.get(i).setType1(type1);
            //System.out.println(days.get(i).getSum());
        }

        for (Days d : days) {
            if (d.getType1().equals("actual")){
                System.out.println("Actual:2017-" + d.getMonth() + "-" + d.getDay() + ":<" + d.getSum() + ">");
            }
            else if(d.getType1().equals("forecast")){
                System.out.println("Forecast:2017-" + d.getMonth() + "-" + d.getDay() + ":<" + d.getSum() + ">");

            }
        }


    }

    static class Electricity {
        private int value;
        private int id;
        private String typeOfData;

        public Electricity() {
        }

        public Electricity(int id, int value, String typeOfData) {
            this.value = value;
            this.id = id;
            this.typeOfData = typeOfData;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getTypeOfData() {
            return typeOfData;
        }

        public void setTypeOfData(String typeOfData) {
            this.typeOfData = typeOfData;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getValue() {
            return value;
        }

        public int getId() {
            return id;
        }
    }


    static class Days {
        private int sum;
        private String day;
        private String month;
        private String type1;


        public Days() {
        }

        public Days(int sum, String day, String month) {
            this.sum = sum;
            this.day = day;
            this.month = month;
            this.type1 = "";
        }

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public int getSum() {
            return sum;

        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}



