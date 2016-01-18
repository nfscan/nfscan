package cc.nfscan.server.utils;

/**
 * Constants interface for whole application
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public interface Constants {
    /**
     * Default date format
     */
    String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * UF code list
     */
    String[] IBGE_UF_CODES = {

            // Região Norte
            "11",  // RO
            "12",  // AC
            "13",  // AM
            "14",  // RR
            "15",  // PA
            "16",  // AP
            "17",  // TO

            // Região Nordeste
            "21",  // MA
            "22",  // PI
            "23",  // CE
            "24",  // RN
            "25",  // PB
            "26",  // PE
            "27",  // AL
            "28",  // SE
            "29",  // BA

            // Região Sudeste
            "31",  // MG
            "32",  // ES
            "33",  // RJ
            "35",  // SP

            // Região Sul
            "41",  // PR
            "42",  // SC
            "43",  // RS

            // Região Centro-Oeste
            "50",  // MS
            "51",  // MT
            "52",  // GO
            "53",  // DF
    };
}
