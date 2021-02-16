package jobportal.models.internal_models.cv_support;

import jobportal.dao.EduLevelRepository;
import jobportal.models.internal_models.codebooks.EduLevel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MaxEduLvl {
    private EduLevelRepository eduLevelRepository;

    private EduLevel eduLevel;
    private int startPosIndex;
    private int endPosIndex;
    private static double startIndexDistanceMultiplicator = 2;

    public MaxEduLvl(){}

    public MaxEduLvl(EduLevelRepository eduLevelRepository) {
        this.eduLevelRepository = eduLevelRepository;
    }

    public MaxEduLvl(EduLevel eduLevel, int startPosIndex, int endPosIndex) {
        this.eduLevel = eduLevel;
        this.startPosIndex = startPosIndex;
        this.endPosIndex = endPosIndex;
    }

    public EduLevel getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String maxEduLvlName) {
        this.eduLevel = eduLevelRepository.findEduLevelByName(maxEduLvlName);
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

    public void findAreaIndexesForVSLevel(String extractedText, int aroundArea, EduLog eduLog) {
        String regex;
        int minStartIndex;
        int maxEndIndex;
        List<Integer> startIndexes = new ArrayList<>();
        List<Integer> endIndexes = new ArrayList<>();

        switch (eduLevel.getName()) {
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
                regex = ".*";
                break;

        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        eduLog.addLogText("Pro urceni indexu oblasti s popisem vysokoskolskeho vzdelani jsem nasel nasledujici" +
                " klicova slova: ");
        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", ""));
            startIndexes.add(matcher.start());
            endIndexes.add(matcher.end());
        }

        if (startIndexes.isEmpty()) {
            setStartPosIndex(0);
            setEndPosIndex(0);
        } else {
            minStartIndex = startIndexes.get(0);
            for (int startIndex : startIndexes) {
                if (startIndex < minStartIndex) {
                    minStartIndex = startIndex;
                }
            }

            maxEndIndex = endIndexes.get(0);
            for (int endIndex : endIndexes) {
                if (endIndex > maxEndIndex) {
                    maxEndIndex = endIndex;
                }
            }

            minStartIndex = minStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator);
            if (minStartIndex < 0) {
                minStartIndex = 0;
            }
            maxEndIndex = maxEndIndex + aroundArea;

            setStartPosIndex(minStartIndex);
            setEndPosIndex(maxEndIndex);
        }
        eduLog.addLogText("MaxEduLvl-findAreaIndexesForVSLevel:: Nastavil jsem minStartIndex na hodnotu: " + getStartPosIndex());
        eduLog.addLogText("MaxEduLvl-findAreaIndexesForVSLevel:: Nastavil jsem maxEndIndex na hodnotu: " + getEndPosIndex());
    }


    public boolean findMaxEduLvl(String extractedText, int eduSectionStartIndex, int aroundArea, EduLog eduLog) {
        String textAreaSubstring;
        if (eduSectionStartIndex > 0) {
            textAreaSubstring = extractedText.substring(eduSectionStartIndex);
            eduLog.addLogText("MaxEduLvl-findMaxEduLvl:: Hledam nejprve v oblasti vzdelani - od eduSectionStartIndex");
        } else {
            textAreaSubstring = extractedText;
            eduLog.addLogText("MaxEduLvl-findMaxEduLvl:: Hledam opakovane jiz v celem extracted textu");
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
            setEduLevel(maxEduLvls[0]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

            return true;
        }


        regex = "([Mm]agisterské|[Mm]agisterský|[Mm]agistr(a)?" +
                "|[Ii]nženýr|\\sING\\s|[Nn]avazující\\s[Ss]tudi)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setEduLevel(maxEduLvls[1]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

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
            setEduLevel(maxEduLvls[2]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);
            ;

            return true;
        }


        regex = "([Vv]yšší\\s[Oo]dborn|[Vv]oš\\s|VOŠ\\s|\\sVOV\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setEduLevel(maxEduLvls[3]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

            return true;
        }


        regex = "([Mm]aturit|[Gg]ymnázium|[Oo]bchodní\\s[Aa]kademie|[Úú]plné\\s[Ss]třední" +
                "|[UÚ]SO\\s|[ÚúUu][Ss][Oo]\\s|[UÚ]SV\\s|[ÚúUu][Ss][Vv]\\s|(4-leté\\s[sSVv]|4leté\\s[sSVv]|čtyřleté\\s[sSVv]))";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setEduLevel(maxEduLvls[4]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

            return true;
        }


        regex = "([Ss]třední|[Ss]tředškolsk|SPŠ|SOU|SŠ|SOŠ|SOUe|SLŠ|[Vv]yučen([ía])?)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setEduLevel(maxEduLvls[5]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + getEduLevel().getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

            return true;
        }


        regex = "([Zz]ákladní|ZŠ\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubstring);

        if (matcher.find()) {
            setEduLevel(maxEduLvls[6]);
            eduLog.addLogText("Nasel jsem keyword >> " + matcher.group().replaceAll("\\s", "") +
                    " << a nastavuji maxEduLvlName na: " + eduLevel.getPrettyName());
            if ((matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator)) >= 0) {
                setStartPosIndex(matcher.start() + eduSectionStartIndex - (int) (aroundArea * startIndexDistanceMultiplicator));
            } else {
                setStartPosIndex(0);
            }
            setEndPosIndex(matcher.end() + eduSectionStartIndex + aroundArea);

            return true;
        }

        // pokud se jiz jedna o pruchod celeho extracted textu
        if (eduSectionStartIndex == 0) {
            eduLog.addLogText("Nenasel jsem keyword, nastavuji maxEduLvlName na: " + maxEduLvls[6]);
            setStartPosIndex(0);
            setEndPosIndex(0);

            return true;
        }
        return false;
    }
}