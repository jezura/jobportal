package jobportal.models.internal_models.cv_support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;

public class RelevanceScore {
    private float[] relevanceScores;
    private static String idPrefix = "OborCinnostiProVm/";
    private static String[] fieldNames = {
            "Administrativa",
            "Doprava",
            "Finance",
            "Informační technologie",
            "Kultura a sport",
            "Management",
            "Obchod a cestovní ruch",
            "Právo, obrana a ochrana",
            "Stavebnictví",
            "Věda a výzkum",
            "Výchova a vzdělávání",
            "Výroba a provoz",
            "Služby",
            "Zdravotnictví a veterina",
            "Zemědelství a lesnictví"
    };

    public RelevanceScore() {
    }

    public RelevanceScore(float[] relevanceScores) {
        this.relevanceScores = relevanceScores;
    }

    public float[] getRelevanceScores() {
        return relevanceScores;
    }

    public float getRelevanceScore(int i) {
        return relevanceScores[i];
    }

    public String getPercentRelevanceScore(int i) {
        float percentValue = (relevanceScores[i])*100;
        double val = Double.valueOf(String.valueOf(percentValue));
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(val) + " %";
    }

    public void setRelevanceScores(float[] relevanceScores) {
        this.relevanceScores = relevanceScores;
    }

    public String getFieldName(int i) {
        return fieldNames[i];
    }

    public boolean getPredictions(CVProfile cvProfile) throws IOException {
        float [] predicitons = new float[15];
        int gender = 0;
        if(cvProfile.getGender().equals("zena")) {
            gender = 1;
        }

        // Constructing of the query URL to ask neural network to predict relevance scores
       URL url = new URL("https://fieldpredictor.herokuapp.com/get-jobfields-relevance-scores?" +
               "gender=" + gender + "&age=" + cvProfile.getAge() + "&edu_lvl="
               + cvProfile.getMaxEducation().getMaxEduLvl().getEduLevel().getAnnCode() +
               "&edu_field=" + cvProfile.getMaxEducation().getEduGeneralField().getAnnCode());

       // Get the input stream through URL Connection
       URLConnection connection = url.openConnection();
       InputStream is = connection.getInputStream();
       BufferedReader br = new BufferedReader(new InputStreamReader(is));

       String line = br.readLine();
       String[] strPredictions = line.split("<br>");

       for(int i = 0; i < 15; i++) {
           predicitons[i] = Float.valueOf(strPredictions[i]);
       }

       if (predicitons.length == 15) {
           setRelevanceScores(predicitons);
           return true;
       }else{
           return  false;
       }
    }

    public String[] getFiveHighestRelevanceFieldsIds() {
        String[] fiveHighestRelevanceFieldsIds = new String[5];
        float [] relevances = relevanceScores;
        for(int i = 0; i <= relevances.length-1; i++) {
            System.out.println(relevances[i] + ",|, ");
        }

        // get top 5 highest relevances from all 15 relevance scores list
        int [] fiveHighest = getBestKIndices(relevances, 5);
        System.out.println("Five highest: " + fiveHighest[0] + ", "+ fiveHighest[1] + ", "+ fiveHighest[2] +
                ", "+ fiveHighest[3] + ", "+ fiveHighest[4]);

        // recalculate to MPSV coding system with ignoring the "Prava" field
        for(int i = 0; i <= fiveHighest.length-1; i++) {
            if(fiveHighest[i] <= 3) {
                fiveHighest[i] += 1;
            }else {
                fiveHighest[i] += 2;
            }
        }
        System.out.println("Five highest: " + fiveHighest[0] + ", "+ fiveHighest[1] + ", "+ fiveHighest[2] +
                ", "+ fiveHighest[3] + ", "+ fiveHighest[4]);

        // retype to Strings and add Field_id prefixes before
        for(int i = 0; i <= fiveHighest.length-1; i++){
            fiveHighestRelevanceFieldsIds[i] = idPrefix + String.valueOf(fiveHighest[i]);
        }

        return fiveHighestRelevanceFieldsIds;
    }

    private int[] getBestKIndices(float[] array, int num) {
        //create sort able array with index and value pair
        IndexValuePair[] pairs = new IndexValuePair[array.length];
        for (int i = 0; i < array.length; i++) {
            pairs[i] = new IndexValuePair(i, array[i]);
        }

        //sort
        Arrays.sort(pairs, new Comparator<IndexValuePair>() {
            public int compare(IndexValuePair o1, IndexValuePair o2) {
                return Float.compare(o2.value, o1.value);
            }
        });

        //extract the indices
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = pairs[i].index;
        }
        return result;
    }

    private class IndexValuePair {
        private int index;
        private float value;

        public IndexValuePair(int index, float value) {
            this.index = index;
            this.value = value;
        }
    }

    public void wakeUp() {
        // Constructing of the query URL to ask neural network to predict relevance scores
        URL url = null;
        try {
            url = new URL("https://fieldpredictor.herokuapp.com/get-jobfields-relevance-scores?" +
                    "gender=0&age=25&edu_lvl=5&edu_field=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            URLConnection connection = url.openConnection();
            connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}