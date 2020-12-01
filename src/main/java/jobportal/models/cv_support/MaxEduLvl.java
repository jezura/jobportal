package jobportal.models.cv_support;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxEduLvl {

    private String maxEduLvlName;
    private int startPosIndex;
    private int endPosIndex;

    public MaxEduLvl() {
    }

    public MaxEduLvl(String maxEduLvlName, int startPosIndex, int endPosIndex) {
        this.maxEduLvlName = maxEduLvlName;
        this.startPosIndex = startPosIndex;
        this.endPosIndex = endPosIndex;
    }

    public String getMaxEduLvlName() {
        return maxEduLvlName;
    }

    public void setMaxEduLvlName(String maxEduLvlName) {
        this.maxEduLvlName = maxEduLvlName;
    }

    public int getStartPosIndex() {
        return startPosIndex;
    }

    public void setStartPosIndex(int startPosIndex) {
        this.startPosIndex = startPosIndex;
    }

    public int getEndPosIndex() {
        return endPosIndex;
    }

    public void setEndPosIndex(int endPosIndex) {
        this.endPosIndex = endPosIndex;
    }

    public void findAreaIndexesForVSLevel(String extractedText, int aroundArea) {
        String regex;
        int minStartIndex;
        int maxEndIndex;
        List<Integer> startIndexes = new ArrayList<>();
        List<Integer> endIndexes = new ArrayList<>();

        switch (getMaxEduLvlName()) {
            case "Vysokoskolske_doktorske":
                regex = "([Dd]oktorské\\s|[Dd]oktorát\\s)";
                break;
            case "Vysokoskolske_magisterske":
                regex = "([Mm]agisterské|[Mm]agisterský|[Mm]agistr(a)?\\s|\\sMGR\\.?\\s|\\sMgr\\.?\\s" +
                        "|[Ii]nženýr|[Ii]nženýrské|\\sING\\.?\\s|\\sIng\\.?\\s|[Nn]avazující)";
                break;
            case "Vysokoskolske_bakalarske":
                regex = "([Bb]akalář|\\s[Bb][Cc]\\s|[Bb]atchelor|[Uu]niver[zs]it|[Vv]ysok[áé]\\s[Šš]kol" +
                        "|[Vv]ysokoškol|[Ff]akult[aě]|\\sUHK\\s|\\sUPCE\\s|\\sAMU\\s|\\sAVU\\s|\\sČZU\\s" +
                        "|ČVUT\\s|JAMU\\s|\\sJU\\s|\\sMU\\s|MUNI\\s|MENDELU\\s|\\sOU\\s|\\sSU\\s|\\sTUL\\s" +
                        "|UJEP\\s|\\sUK\\s|\\sUP\\s|UPa\\s|\\sUTB\\s|\\sVFU\\s|\\sVŠB\\s|\\sVŠE\\s|VŠCHT\\s" +
                        "|\\sVŠPJ\\s|\\sVŠTE\\s|UMPRUM\\s|\\sVUT\\s|\\sZČU\\s)";
                break;
            default:
                regex = "([Bb]akalář|\\s[Bb][Cc]\\s|[Bb]atchelor|[Uu]niver[zs]it|[Vv]ysok[áé]\\s[Šš]kol" +
                        "|[Vv]ysokoškol|[Ff]akult[aě]|\\sUHK\\s|\\sUPCE\\s|\\sAMU\\s|\\sAVU\\s|\\sČZU\\s" +
                        "|ČVUT\\s|JAMU\\s|\\sJU\\s|\\sMU\\s|MUNI\\s|MENDELU\\s|\\sOU\\s|\\sSU\\s|\\sTUL\\s" +
                        "|UJEP\\s|\\sUK\\s|\\sUP\\s|UPa\\s|\\sUTB\\s|\\sVFU\\s|\\sVŠB\\s|\\sVŠE\\s|VŠCHT\\s" +
                        "|\\sVŠPJ\\s|\\sVŠTE\\s|UMPRUM\\s|\\sVUT\\s|\\sZČU\\s)";
                break;

        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        while (matcher.find()) {
            System.out.println("MaxEduLvl - findAreaIndexesForVSLevel: " + matcher.group());
            startIndexes.add(matcher.start());
            endIndexes.add(matcher.end());
        }

        if(startIndexes.isEmpty()) {
            setStartPosIndex(0);
            setEndPosIndex(0);
        }else{
            minStartIndex = startIndexes.get(0);
            for (int startIndex: startIndexes) {
                if(startIndex < minStartIndex) {
                    minStartIndex = startIndex;
                }
            }

            maxEndIndex = endIndexes.get(0);
            for (int endIndex: endIndexes) {
                if(endIndex > maxEndIndex) {
                    maxEndIndex = endIndex;
                }
            }

            minStartIndex = minStartIndex - aroundArea;
            if(minStartIndex <0) {
                minStartIndex = 0;
            }
            maxEndIndex = maxEndIndex + aroundArea;

            setStartPosIndex(minStartIndex);
            setEndPosIndex(maxEndIndex);
        }
        System.out.println("MaxEduLvl-findAreaIndexesForVS: Nastavil jsem minStartIndex na hodnotu: " + getStartPosIndex());
        System.out.println("MaxEduLvl-findAreaIndexesForVS: Nastavil jsem maxEndIndex na hodnotu: " + getEndPosIndex());
    }


    public boolean findMaxEduLvl(String extractedText, int eduSectionStartIndex, int aroundArea) {
        String textAreaSubstring;
        if(eduSectionStartIndex > 0) {
            textAreaSubstring = extractedText.substring(eduSectionStartIndex);
            System.out.println("MaxEduLvl: Hledam nejprve v oblasti vzdelani - od eduSectionStartIndex");
        }else{
            textAreaSubstring = extractedText;
            System.out.println("MaxEduLvl: Hledam opakovane jiz v celem extracted textu");
        }

        String regex;
        Pattern pattern;
        Matcher matcher;

        String[] maxEduLvls = {
                "Vysokoskolske_doktorske",
                "Vysokoskolske_magisterske",
                "Vysokoskolske_bakalarske",
                "Vyssi_odborne",
                "Stredoskolske_s_maturitou",
                "Vyuceni_nebo_Stredoskolske_bez_maturity",
                "Zakladni"
        };

        regex = "([Dd]oktorské\\s|[Dd]oktorát\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[0]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }


        regex = "([Mm]agisterské|[Mm]agisterský|[Mm]agistr(a)?" +
                "|[Ii]nženýr|\\sING\\s|[Nn]avazující\\s[Ss]tudi)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[1]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }


        regex = "([Bb]akalář|\\s[Bb][Cc]\\s|[Bb]atchelor|[Uu]niver[zs]it|[Vv]ysok[áé]\\s[Šš]kol" +
                "|[Vv]ysokoškol|[Ff]akult[aě]|UHK\\s|UPCE\\s|AMU\\s|AVU\\s|ČZU\\s|ČVUT\\s" +
                "|JAMU\\s|\\sJU\\s|\\sMU\\s|MUNI\\s|MENDELU\\s|\\sOU\\s|\\sSU\\s|\\sTUL\\s|UJEP\\s|\\sUK\\s" +
                "|\\sUP\\s|\\sUPa\\s|UTB\\s|VFU\\s|VŠB\\s|VŠE\\s|VŠCHT\\s|VŠPJ\\s|VŠTE\\s|UMPRUM\\s" +
                "|VUT\\s|ZČU\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[2]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);;

            return true;
        }


        regex = "([Vv]yšší\\s[Oo]dborn|[Vv]oš\\s|VOŠ\\s|\\sVOV\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[3]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }


        regex = "([Mm]aturit|[Gg]ymnázium|[Oo]bchodní\\s[Aa]kademie|[Úú]plné\\s[Ss]třední" +
                "|[UÚ]SO\\s|[ÚúUu][Ss][Oo]\\s|[UÚ]SV\\s|[ÚúUu][Ss][Vv]\\s|(4-leté\\s[sSVv]|4leté\\s[sSVv]|čtyřleté\\s[sSVv]))";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[4]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }


        regex = "([Ss]třední|[Ss]tředškolsk|SPŠ|SOU|SŠ|SOŠ|SOUe|SLŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[5]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }


        regex = "([Zz]ákladní|ZŠ\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setMaxEduLvlName(maxEduLvls[6]);

            if((matcher.start()+eduSectionStartIndex-aroundArea) >= 0) {
                setStartPosIndex(matcher.start()+eduSectionStartIndex-aroundArea);
            }else{
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end()+eduSectionStartIndex+aroundArea);

            return true;
        }

        // pokud se jiz jedna o pruchod celeho extracted textu
        if(eduSectionStartIndex == 0) {
            setMaxEduLvlName(maxEduLvls[6]);
            setStartPosIndex(0);
            setEndPosIndex(0);

            return true;
        }

        return false;
    }
}