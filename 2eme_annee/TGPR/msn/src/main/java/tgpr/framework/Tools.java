package tgpr.framework;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.apache.commons.text.WordUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static tgpr.framework.LocaleFormats.*;

/**
 * Cette classe propose diverses méthodes utilitaires.
 */
public abstract class Tools {
    private static final String PREFIX_SALT = "vJemLnU3";
    private static final String SUFFIX_SALT = "QUaLtRs7";

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.ENGLISH);

    /**
     * Retourne la valeur du premier argument, sauf dans le cas où celle-ci vaut {@code null} et pour lequel on
     * retourne alors la valeur de {@code defaultValue}.
     *
     * @param value        la valeur à évaluer
     * @param defaultValue la valeur par défaut à retourner si {@code value} est {@code null}
     * @return value si différente de {@code null}, sinon {@code defaultValue}
     */
    public static <T> T ifNull(T value, T defaultValue) {
        if (value == null)
            return defaultValue;
        return value;
    }

    /**
     * Retourne une clé de hachage calculée, avec l'algorithme MD5
     * (voir <a href="https://fr.wikipedia.org/wiki/MD5">https://fr.wikipedia.org/wiki/MD5</a>),
     * à partir d'une chaîne de caractères passée en paramètre.
     *
     * @param s la chaîne à hacher
     * @return la clé de hachage calculée
     */
    public static String hash(String s) {
        String pwd = PREFIX_SALT + s + SUFFIX_SALT;
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(pwd.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash)
                sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transforme un double en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractère obtenue
     */
    public static String toString(double val) {
        try {
            return NUMBER_FORMAT.format(val);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Retourne la valeur de la string passée en premier argument, sauf dans le cas où celle-ci vaut {@code null}
     * ou est vide ou ne contient que des espaces ("blanche"), et pour lequel on retourne alors la valeur
     * de {@code defaultValue}.
     *
     * @param str          la string à évaluer
     * @param defaultValue la string par défaut à retourner si {@code value} est {@code null} ou vide ou "blanche"
     * @return value si différente de {@code null}, sinon {@code defaultValue}
     */
    public static String ifNullOrBlank(String str, String defaultValue) {
        if (str == null || str.isBlank())
            return defaultValue;
        else
            return str;
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format décimal par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double parseDecimal(String str) {
        try {
            return NUMBER_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format monétaire par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double parseCurrency(String str) {
        try {
            str = str.replaceFirst("\\s*€$", " €");
            return CURRENCY_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme une date en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param ts la date à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asString(LocalDate ts) {
        return ts == null ? "" : DATE_FORMATTER.format(ts);
    }


    /**
     * Transforme une date en une chaîne de caractères en utilisant le format ISO (YYYY-MM-DD).
     *
     * @param ts la date à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asIsoString(LocalDate ts) {
        return ts == null ? "" : ISO_DATE_FORMATTER.format(ts);
    }

    /**
     * Transforme une date/heure en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param ts la date/heure à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asString(LocalDateTime ts) {
        return ts == null ? "" : DATETIME_FORMATTER.format(ts);
    }

    /**
     * Transforme une date/heure en une chaîne de caractères en utilisant le format ISO (YYYY-MM-DD HH:MM:SS).
     *
     * @param ts la date/heure à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asIsoString(LocalDateTime ts) {
        return ts == null ? "" : ISO_DATETIME_FORMATTER.format(ts);
    }

    /**
     * Transforme une chaîne de caractère en une chaîne multi-lignes en tenant compte d'une largeur maximale que peut
     * avoir chaque ligne.
     *
     * @param txt    le texte de départ
     * @param length la longueur maximale d'une ligne
     * @return le texte transformé
     */
    public static String wrap(String txt, int length) {
        if (txt == null) txt = "";
        return WordUtils.wrap(txt, length, null, true);
    }
    /**
     * Transforme une chaîne de caractère multi-lignes en une chaîne d'une seule ligne.
     *
     * @param txt    le texte de départ
     * @return le texte transformé
     */
    public static String unwrap(String txt) {
        if (txt == null) txt = "";
        return txt.replace("\n", " ");
    }

    /**
     * Abrège une chaîne de caractère pour qu'elle ne dépasse pas une longueur maximale donnée. Le texte abrégé se
     * termine par {@code "..."} pour montrer qu'il a été abrégé.
     *
     * @param txt    le texte de départ
     * @param length la longueur maximale d'une ligne
     * @return le texte transformé
     */
    public static String abbreviate(String txt, int length) {
        if (txt == null) txt = "";
        return WordUtils.abbreviate(txt, length, length, "...");
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une date valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDate(String str) {
        try {
            LocalDate.parse(str, INPUT_DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une date/heure valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDateTime(String str) {
        try {
            LocalDate.parse(str, INPUT_DATETIME_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une valeur double valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDouble(String str) {
        try {
            var number = NUMBER_FORMAT.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une valeur entière valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidInteger(String str) {
        try {
            var number = INTEGER_FORMAT.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double toDouble(String str) {
        try {
            return NUMBER_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double toEuro(String str) {
        try {
            return CURRENCY_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme un double en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractères obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static String asDecimal(double val) {
        try {
            return NUMBER_FORMAT.format(val);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transforme un double en une chaîne de caractères représentant un montant en devise (ici l'euro)
     * en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractères obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static String asCurrency(double val) {
        try {
            return CURRENCY_FORMAT.format(val);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transforme une chaîne de caractères en une date en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return la date obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static LocalDate toDate(String str) {
        try {
            LocalDate d = LocalDate.parse(str, INPUT_DATE_FORMATTER);
            if (d.getYear() < 100)
                d = d.plusYears(1900);
            return d;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Transforme une chaîne de caractères en une date/heure en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return la date/heure obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static LocalDateTime toDateTime(String str) {
        try {
            return LocalDateTime.parse(str, INPUT_DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
