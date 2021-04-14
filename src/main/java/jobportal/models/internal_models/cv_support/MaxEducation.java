package jobportal.models.internal_models.cv_support;

import jobportal.dao.EduGeneralFieldRepository;
import jobportal.models.internal_models.codebooks.EduGeneralField;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MaxEducation class was created to extract and keep information
 * about education level and general education field from PDF/DOCX CV file.
 */
@Component
public class MaxEducation {
    private EduGeneralFieldRepository eduGeneralFieldRepository;
    private MaxEduLvl maxEduLvl;
    private EduGeneralField eduGeneralField;
    private int eduSectionStartIndex;
    private static int searchAreaLength = 300;

    public MaxEducation() {
    }

    public MaxEducation(EduGeneralFieldRepository eduGeneralFieldRepository) {
        this.eduGeneralFieldRepository = eduGeneralFieldRepository;
    }

    public MaxEduLvl getMaxEduLvl() {
        return maxEduLvl;
    }

    public void setMaxEduLvl(MaxEduLvl maxEduLvl) {
        this.maxEduLvl = maxEduLvl;
    }

    public EduGeneralField getEduGeneralField() {
        return eduGeneralField;
    }

    public void setEduGeneralField(String eduGeneralFieldName) {
        this.eduGeneralField = eduGeneralFieldRepository.findEduGeneralFieldByName(eduGeneralFieldName);
    }

    public int getEduSectionStartIndex() {
        return eduSectionStartIndex;
    }

    public void setEduSectionStartIndex(int eduSectionStartIndex) {
        this.eduSectionStartIndex = eduSectionStartIndex;
    }


    /**
     * Method find the start index of education section in CV (Vzdělání: ...). If not found, returns 0.
     *
     * @param extractedText - String with extracted text content from attached CV file
     * @param eduLog        - EduLog object to store process log information
     * @return boolean (True if section "Vzdělání" was found)
     */
    public boolean findEduSectionStartIndex(String extractedText, EduLog eduLog) {
        String regex = "(\\s[Vv]zdělání|\\sVZDĚLÁNÍ|\\s[Ss]tudium|\\sSTUDIUM)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            eduLog.addLogText("MaxEducation-findEduSectionStartIndex :: Našel jsem keyword >>" +
                    matcher.group().replaceAll("\\s+", "") + "<< na indexu: " +
                    matcher.end() + ", nastavuji jej jako eduSectionStartIndex..");
            setEduSectionStartIndex(matcher.end());
            return true;
        } else {
            eduLog.addLogText("MaxEducation-findEduSectionStartIndex :: Nepodařilo se najít oblast vzdělání..");
            return false;
        }
    }


    /**
     * Method find and set general education field for University (Vysokoškolský) education level
     *
     * @param extractedText           - String with extracted text content from attached CV file
     * @param useAreaIndexes          - boolean - if True, areaIndexes will be used during searching/extracting process
     * @param useEduSectionStartIndex - boolean - if True, eduSectionStartIndex will be used during searching/extracting process
     * @param eduLog                  - EduLog object to store process log information
     * @return boolean (True if general edu field was found and set)
     */
    public boolean findFieldForVSLevel(String extractedText, Boolean useAreaIndexes,
                                       Boolean useEduSectionStartIndex, EduLog eduLog) {
        String textAreaSubString;
        String regex;
        Pattern pattern;
        Matcher matcher;

        if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() > 0))) {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: Hledam pomoci substringu jen v okolni oblasti");
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: " +
                    "Hledam pomoci minStartIndex s hodnotou: " + maxEduLvl.getStartPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: " +
                    "Hledam pomoci maxEndIndex s hodnotou: " + maxEduLvl.getEndPosIndex());
        } else if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() == 0))) {
            // do not run search in useAreaIndexes mode => indexes not found correctly and do not delimit back area
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: Nespoustim hledani v rezimu useAreaIndexes," +
                    " indexy nebyly spravne nalezeny a neohranicuji zadnou oblast");
            return false;
        } else if ((!useAreaIndexes) && (useEduSectionStartIndex)) {
            textAreaSubString = extractedText.substring(eduSectionStartIndex, eduSectionStartIndex + searchAreaLength);
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: Hledam pomoci substringu pouze" +
                    " v uvodni oblasti s popisem vzdelani");
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: " +
                    "Hledam od indexu s hodnotou: " + eduSectionStartIndex);
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: " +
                    "Hledam po index s hodnotou: " + (eduSectionStartIndex + searchAreaLength));
        } else {
            textAreaSubString = extractedText;
            eduLog.addLogText("MaxEducation-findFieldforVSLevel:: Hledam v celem original extractedTextu");
        }


        String[] VSFields = {
                "Informatika_a_management",
                "Elektrotechnika_technika_mechanika",
                "Obchod_a_ekonomie",
                "Zdravotnictvi_a_medicina",
                "Stavebnictvi",
                "Zemedelstvi_a_lesnictvi",
                "Pedagogika_ucitelstvi_a_telovychova",
                "Filosofie_politologie_historie_psychologie_sociologie_verejna_sprava",
                "Umeni",
                "Pravo_obrana_a_ochrana",
                "Doprava",
                "Prirodni_vedy_chemie_fyzika_matematika",
                "Lingvistika"
        };

        int[] VSFieldsMatches = new int[VSFields.length];
        eduLog.addLogText("Nalezl jsem nasledujici oborova klicova slova:");

        regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Dd]ata|[Dd]atov[áý]|\\sFIM\\s|\\sFIT\\s|\\sFI\\s|\\sFIS\\s|\\sFAI\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[0] += 1;
        }


        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|\\sFEL|\\sFEKT)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[1] += 1;
        }


        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|\\sNF\\s|\\sFPH\\s|\\sECON\\s|\\sFEK\\s|" +
                "\\sFSE\\s|\\sFES\\s|\\sEKF\\s|\\sOPF\\s|\\sF[Aa]ME\\s|\\sPEF\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[2] += 1;
        }


        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Ll]ékař|[Mm]edicín|[Vv]eterin|[Ff]yzioterapie|" +
                "\\sFVZ\\s|\\sLF\\s|\\sFVL\\s|\\sVFU\\s|\\sFZS\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[3] += 1;
        }


        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|\\sFAST|\\sFSv)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[4] += 1;
        }


        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradnic|\\sFLD|\\sLDF|FROV|FAPPZ|\\sZF|\\sAF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[5] += 1;
        }


        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|FTVS|\\sFTK|\\sFPE|\\sP[Dd]F)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[6] += 1;
        }


        regex = "([Ff]ilosofie|[Ff]ilosofick|[Pp]olitologie|[Pp]olitologick|[Pp]sychologie|[Pp]sychologic|" +
                "[Ss]ociální|[Ss]ociolog|[Ss]tátní\\sspráva|[Vv]eřejnosprávní\\s|[Ss]právní|[Hh]istorie|" +
                "[Hh]istoric|[Žž]urnalist|[Mm]ezinárodní(ch)?\\svztah|[Dd]ějiny|[Pp]oliti[ck]|\\sFF|\\sFMV|\\sÚSP|([Tt]eologi(e|cká))|\\sTS|\\sTF|\\sKTF|" +
                "\\sETF|\\sHTF|CMTF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[7] += 1;
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzerva|[Tt]aneční|[Ff]ilmová|[Gg]rafi|[Ff]otog" +
                "|\\sFAMU|\\sDAMU|\\sHAMU|\\sJAMU|\\sFH|\\sFU|\\sDIFA|\\sF[Aa]VU|FUD|\\sFMK|FDULS|\\sUUD|\\sFUD)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[8] += 1;
        }


        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|\\sFVL|\\sFVT|\\sFBP|\\sFBI|\\sFBM|" +
                "[Pp]ráv[ao]|\\s[Pp]rávní|[Pp]rávnic|\\sP[Rr]F\\s|\\sFPR\\s|\\sFP\\s|\\sPF\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[9] += 1;
        }


        regex = "([Dd]opravní|[Dd]oprava|\\sDFJP|\\sFD\\s)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[10] += 1;
        }


        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|[Bb]iolog|[Jj]adern" +
                "|[Mm]atemati|[Ss]tatisti|\\sP[Řř]F|\\sFJFI|\\sFCHI|\\sFCH|\\sFZP|\\sFPF|\\sFCHT|\\sHGF|\\sFAV|" +
                "\\sFP|\\sMFF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[11] += 1;
        }


        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VSFieldsMatches[12] += 1;
        }

        eduLog.addLogText("\n");
        int maxMatches = 0;
        int j = -1;
        for (int i = 0; i < VSFields.length; i++) {
            eduLog.addLogText("Matches pro obor: " + VSFields[i] + " je: " + VSFieldsMatches[i]);
            if (VSFieldsMatches[i] > maxMatches) {
                maxMatches = VSFieldsMatches[i];
                j = i;
            }
        }

        if (j > -1) {
            setEduGeneralField(VSFields[j]);
            eduLog.addLogText("Nejvíce shod jsem našel na indexu: " + j);
            eduLog.addLogText("Jedná se o obor: " + eduGeneralField.getPrettyName());
            return true;
        } else {
            // failed to find match => no general edu field was identified
            eduLog.addLogText("Nenalezl jsem obor");
            return false;
        }
    }

    /**
     * Method find and set general education field for High School
     * (Středoškolský maturitní a VOŠ) education level
     *
     * @param extractedText           - String with extracted text content from attached CV file
     * @param useAreaIndexes          - boolean - if True, areaIndexes will be used during searching/extracting process
     * @param useEduSectionStartIndex - boolean - if True, eduSectionStartIndex will be used during searching/extracting process
     * @param eduLog                  - EduLog object to store process log information
     * @return boolean (True if general edu field was found and set)
     */
    public boolean findFieldForVOSSSMatLevel(String extractedText, Boolean useAreaIndexes,
                                             Boolean useEduSectionStartIndex, EduLog eduLog) {
        String textAreaSubString;
        String regex;
        Pattern pattern;
        Matcher matcher;

        if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() > 0))) {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: Hledam pomoci substringu jen v okolni oblasti");
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: " +
                    "Hledam pomoci minStartIndex s hodnotou: " + maxEduLvl.getStartPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: " +
                    "Hledam pomoci maxEndIndex s hodnotou: " + maxEduLvl.getEndPosIndex());
        } else if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() == 0))) {
            // do not run search in useAreaIndexes mode => indexes not found correctly and do not delimit back area
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: Nespoustim hledani v rezimu useAreaIndexes," +
                    " indexy nebyly spravne nalezeny a neohranicuji zadnou oblast");
            return false;
        } else if ((!useAreaIndexes) && (useEduSectionStartIndex)) {
            textAreaSubString = extractedText.substring(eduSectionStartIndex, eduSectionStartIndex + searchAreaLength);
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: Hledam pomoci substringu pouze" +
                    " v uvodni oblasti s popisem vzdelani");
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: " +
                    "Hledam od indexu s hodnotou: " + eduSectionStartIndex);
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: " +
                    "Hledam po index s hodnotou: " + (eduSectionStartIndex + searchAreaLength));
        } else {
            textAreaSubString = extractedText;
            eduLog.addLogText("MaxEducation-findFieldforVOSSSMatLevel:: Hledam v celem original extractedTextu");
        }


        String[] VOSSSMatFields = {
                "Informatika_a_management",
                "Elektrotechnika_technika_mechanika",
                "Obchod_a_ekonomie",
                "Zdravotnictvi_a_medicina",
                "Gymnazium_ZS",
                "Stavebnictvi",
                "Zemedelstvi_a_lesnictvi",
                "Pedagogika_ucitelstvi_a_telovychova",
                "Filosofie_politologie_historie_psychologie_sociologie_verejna_sprava",
                "Sluzby",
                "Umeni",
                "Pravo_obrana_a_ochrana",
                "Doprava",
                "Prirodni_vedy_chemie_fyzika_matematika",
                "Lingvistika"
        };

        int[] VOSSSMatFieldsMatches = new int[VOSSSMatFields.length];
        eduLog.addLogText("Nalezl jsem nasledujici oborova klicova slova:");

        regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Pp]očítač|\\sIT\\s|\\sICT\\s" +
                "|[Kk]omunikač|[Kk]ybernet|[Vv]ýpočetní)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[0] += 1;
        }


        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|[Mm]echani|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Ss]troj|[Tt]echni|[Rr]obot|tronik|[Aa]uto|\\sSPŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[1] += 1;
        }


        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|[Úú]četnic)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[2] += 1;
        }


        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Vv]eterin|\\sSZS|[Zz]dravotnický [Aa]sistent|[Zz]áchraná)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[3] += 1;
        }


        regex = "([Gg]ymn[aá]zium)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[4] += 1;
        }


        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|[Gg]eod)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[5] += 1;
        }


        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[6] += 1;
        }


        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|SPgŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[7] += 1;
        }


        regex = "([Pp]sychologie|[Pp]sychologic|[Ss]ociáln[ěí]|[Ss]ociolog|[Ss]tátní\\sspráva" +
                "|[Vv]eřejnosprávní\\s|[Ss]právní|[Hh]istorie|[Hh]istoric|[Dd]ějin|[Žž]urnalist|" +
                "([Tt]eologi(e|cká))|[Mm]ezinárodní(ch)?\\svztah|[Dd]ějiny|[Pp]oliti[ck])";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[8] += 1;
        }


        regex = "([Hh]otel[no]|[Rr]estaurat|[Gg]astro|[Cc]estovní|[Ss]lužeb|[Pp]odnik[aá]|[Aa]ranžér|" +
                "[Cc]ukrář|[Čč]aloun[ií]|[Ii]nstalatér|[Kk]adeřn[ií]|[Kk]rejč[oí]|[Kk]uchař|[Mm]alíř|" +
                "[Pp]ekař|[Pp]otravin|[Pp]rodavač|[Mm]asér|[Řř]ezník|[Uu]zenář|[Tt]extil|[Mm]askér|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[9] += 1;
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzervat|[Tt]aneční|[Ff]ilmová|[Gg]rafi" +
                "|[Ff]otograf|[Mm]ediální|[Pp]olygraf)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[10] += 1;
        }


        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|[Hh]asič|\\s[Pp]rávní|\\s[Pp]rávo)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[11] += 1;
        }


        regex = "([Dd]opravní|[Dd]oprava|[Ii]nfrastruktur|[Ll]ogisti)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[12] += 1;
        }


        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|" +
                "[Bb]iolog|[Jj]adern|[Mm]atemati|[Ss]tatisti)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[13] += 1;
        }


        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            VOSSSMatFieldsMatches[14] += 1;
        }

        eduLog.addLogText("\n");
        int maxMatches = 0;
        int j = -1;
        for (int i = 0; i < VOSSSMatFields.length; i++) {
            eduLog.addLogText("Matches pro obor: " + VOSSSMatFields[i] + " je: " + VOSSSMatFieldsMatches[i]);
            if (VOSSSMatFieldsMatches[i] > maxMatches) {
                maxMatches = VOSSSMatFieldsMatches[i];
                j = i;
            }
        }

        if (j > -1) {
            setEduGeneralField(VOSSSMatFields[j]);
            eduLog.addLogText("Nejvíce shod jsem našel na indexu: " + j);
            eduLog.addLogText("Jedná se o obor: " + eduGeneralField.getPrettyName());
            return true;
        } else {
            // failed to find match => no general edu field was identified
            eduLog.addLogText("Nenalezl jsem obor");
            return false;
        }
    }

    /**
     * Method find and set general education field for (Středoškolský / vyučen / bez maturity) education level
     *
     * @param extractedText           - String with extracted text content from attached CV file
     * @param useAreaIndexes          - boolean - if True, areaIndexes will be used during searching/extracting process
     * @param useEduSectionStartIndex - boolean - if True, eduSectionStartIndex will be used during searching/extracting process
     * @param eduLog                  - EduLog object to store process log information
     * @return boolean (True if general edu field was found and set)
     */
    public boolean findFieldForSSLevel(String extractedText, Boolean useAreaIndexes,
                                       Boolean useEduSectionStartIndex, EduLog eduLog) {
        String textAreaSubString;
        String regex;
        Pattern pattern;
        Matcher matcher;

        if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() > 0))) {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: Hledam pomoci substringu jen v okolni oblasti");
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: " +
                    "Hledam pomoci minStartIndex s hodnotou: " + maxEduLvl.getStartPosIndex());
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: " +
                    "Hledam pomoci maxEndIndex s hodnotou: " + maxEduLvl.getEndPosIndex());
        } else if ((useAreaIndexes) && ((maxEduLvl.getEndPosIndex() == 0))) {
            // do not run search in useAreaIndexes mode => indexes not found correctly and do not delimit back area
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: Nespoustim hledani v rezimu useAreaIndexes," +
                    " indexy nebyly spravne nalezeny a neohranicuji zadnou oblast");
            return false;
        } else if ((!useAreaIndexes) && (useEduSectionStartIndex)) {
            textAreaSubString = extractedText.substring(eduSectionStartIndex, eduSectionStartIndex + searchAreaLength);
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: Hledam pomoci substringu pouze" +
                    " v uvodni oblasti s popisem vzdelani");
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: " +
                    "Hledam od indexu s hodnotou: " + eduSectionStartIndex);
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: " +
                    "Hledam po index s hodnotou: " + (eduSectionStartIndex + searchAreaLength));
        } else {
            textAreaSubString = extractedText;
            eduLog.addLogText("MaxEducation-findFieldforSSLevel:: Hledam v celem original extractedTextu");
        }

        String[] SSFields = {
                "Elektrotechnika_technika_mechanika",
                "Zdravotnictvi_a_medicina",
                "Stavebnictvi",
                "Zemedelstvi_a_lesnictvi",
                "Sluzby",
                "Umeni",
                "Obrana_a_ochrana",
                "Remeslna_vyroba"
        };

        int[] SSFieldsMatches = new int[SSFields.length];
        eduLog.addLogText("Nalezl jsem nasledujici oborova klicova slova:");

        regex = "([Ee]lektrotech|[Ee]lektronik|[Mm]echani[ck]|[Aa]utomatiz|[Rr]oboti|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Tt]echni|[Rr]obot|tronik|[Aa]uto|[Oo]pravář|[Mm]ontér|\\sSPŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[0] += 1;
        }


        regex = "([Zz]dravot|[Oo]šetřov|[Pp]ečovat|[Ss]estra)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[1] += 1;
        }


        regex = "([Zz]edn[ií]|[Kk]onstru|[Ss]tavebn|[Pp]okrývač|[Gg]eod|[Zz]eměměřič)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[2] += 1;
        }


        regex = "([Zz]eměděl|[Dd]řevo|[Zz]pracovatel|[Vv]odař|[Ll]esn[ií])";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[3] += 1;
        }


        regex = "([Ss]luž[be]|[Hh]otel|[Cc]estovní(ho)?\\s[Rr]uch|[Gg]astronomi|[Rr]estraura|[Aa]ranžér" +
                "|[Ll]akýrník|[Cc]ukrář|[Čč]aloun[ií]|[Ii]nstalatér|[Kk]adeřn[ií]|[Kk]rejč[oí]" +
                "|[Kk]uchař|[Čč]íšn[ií]|[Mm]alíř|[Mm]anipulant|[Oo]perátor|[Pp]ekař|[Pp]otravin" +
                "|[Pp]rodavač|[Pp]rovozní|[Mm]asér|[Řř]ezník|[Uu]zenář|[Šš]i(tí|čka)|[Tt]extil" +
                "|[Mm]askér|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[4] += 1;
        }

        regex = "([Uu]měleck[áý]|[Uu]mění|[Gg]rafi[ck]|[Bb]ižuter)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[5] += 1;
        }

        regex = "([Oo]bran[ay]|[Oo]chran[ay]|[Hh]lídač|[Ss]trážný)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[6] += 1;
        }


        regex = "([Hh]utn[ií][ck]|[Kk]ameník|[Kk]amnař|[Kk]erami|[Kk]lempíř|[Kk]nihař|[Kk]omin[ií]|[Kk]ožeděl" +
                "|[Ll]odník|[Mm]odelář|[Oo]bráběč|[Ss]troj|[Kk]arosář|[Nn]ástrojař|[Pp]odkovář|[Kk]ovář|[Dd]laždič" +
                "|[Pp]odlahář|[Pp]uškař|[Ss]klář|[Ss]klenář|[Ss]lévač|[Tt]esař|[Tt]iskař|[Tt]ruhlář|[Vv]čelař" +
                "|[Žž]elezničář)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(textAreaSubString);

        while (matcher.find()) {
            eduLog.addLogText(matcher.group().replaceAll("\\s", "") + " ; ");
            SSFieldsMatches[7] += 1;
        }

        eduLog.addLogText("\n");
        int maxMatches = 0;
        int j = -1;
        for (int i = 0; i < SSFields.length; i++) {
            eduLog.addLogText("Matches pro obor: " + SSFields[i] + " je: " + SSFieldsMatches[i]);
            if (SSFieldsMatches[i] > maxMatches) {
                maxMatches = SSFieldsMatches[i];
                j = i;
            }
        }

        if (j > -1) {
            setEduGeneralField(SSFields[j]);
            eduLog.addLogText("Nejvíce shod jsem našel na indexu: " + j);
            eduLog.addLogText("Jedná se o obor: " + eduGeneralField.getPrettyName());
            return true;
        } else {
            if (useAreaIndexes || useEduSectionStartIndex) {
                // failed to find match => no general edu field was identified
                eduLog.addLogText("Nenalezl jsem obor");
                return false;
            } else {
                // if this is the second attempt with a flat search in the whole text
                // hard set "Remeslna_vyroba"
                setEduGeneralField(SSFields[7]);
                eduLog.addLogText("Nenalezen obor - fixně nastaven obor Řemeslná výroba..");
                return true;
            }
        }
    }
}