package tgpr.tuto.model;

import tgpr.framework.Error;
import tgpr.framework.ErrorList;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.regex.Pattern;

public abstract class MemberValidator {

    public static Error isValidBirthdate(LocalDate birthdate) {
        if (birthdate == null)
            return Error.NOERROR;
        if (birthdate.isAfter(LocalDate.now()))
            return new Error("may not be born in the future", Member.Fields.BirthDate);
        var age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < 18)
            return new Error("must be 18 years old", Member.Fields.BirthDate);
        else if (age > 120)
            return new Error("may not be older then 120 years", Member.Fields.BirthDate);
        return Error.NOERROR;
    }

    public static Error isValidPseudo(String pseudo) {
        if (pseudo == null || pseudo.isBlank())
            return new Error("pseudo required", Member.Fields.Pseudo);
        if (!Pattern.matches("[a-zA-Z][a-zA-Z0-9]{2,7}", pseudo))
            return new Error("invalid pseudo", Member.Fields.Pseudo);
        return Error.NOERROR;
    }

    public static Error isValidAvailablePseudo(String pseudo) {
        var error = isValidPseudo(pseudo);
        if (error != Error.NOERROR)
            return error;
        if (Member.getByPseudo(pseudo) != null)
            return new Error("not available", Member.Fields.Pseudo);
        return Error.NOERROR;
    }

    public static Error isValidPassword(String password) {
        if (password == null || password.isBlank())
            return new Error("password required", Member.Fields.Password);
        if (!Pattern.matches("[a-zA-Z0-9]{3,}", password))
            return new Error("invalid password", Member.Fields.Password);
        return Error.NOERROR;
    }

    public static List<Error> validate(Member member) {
        var errors = new ErrorList();

        // field validations
        errors.add(isValidPseudo(member.getPseudo()));
        errors.add(isValidBirthdate(member.getBirthdate()));

        // cross-fields validations
        if (member.getProfile() != null && !member.getProfile().isBlank() && member.getProfile().equals(member.getPseudo()))
            errors.add("profile must be different from pseudo", Member.Fields.Profile);

        return errors;
    }

}