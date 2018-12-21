package utils;

public final class Constants {

    //TODO(All) maybe this should be a more specifical format, something like abc@def.ubbcluj.ro
    //now it matches all email adresses having a format like ab@bc.cd.de
//    public static final String UBB_EMAIL_FORMAT = "\\w+\\d*@\\w+(\\.\\w+){2}";
    public static final String UBB_EMAIL_FORMAT = "[a-zA-Z._][a-zA-Z0-9._]*@[a-zA-Z][a-zA-Z0-9]*(\\.[a-zA-Z][a-zA-Z0-9]*){2}";
    public static final String REDIRECT = "redirect:/";
//    public static final String UBB_EMAIL_FORMAT = ".*";

}
