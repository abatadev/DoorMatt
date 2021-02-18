package com.example.doormatt.common;

public class Common {

    // Firebase Realtime Database References
    public static final String RESIDENT_REF = "Resident";
    public static final String ADMIN_REF = "Admin";
    public static final String GUARD_REF = "Guard";
    public static final String ROLE_REF = "Role";
    public static final String LOGS_REF = "Logs";
    public static final String VISITOR_REF = "Visitor";
    public static final String NAME_PATTERN = "^[A-Za-z]+$";

    //Resident Status Code
    public static final int NONE = 0;
    public static final int CHECKED_OUT = 1;
    public static final int CHECKED_IN = 2;

    // Firebase Storage References
    public static final String AVATAR_IMAGES = "avatar_images";
    public static final String QR_IMAGES = "qr_images";

    // Firebase Role Types
    public static final int ADMIN_ROLE = 1;
    public static final int GUARD_ROLE = 2;
    public static final int RESIDENT_ROLE = 3;
    public static final int VISITOR_ROLE = 4;

    public static final int PICK_IMAGE_REQUEST = 1234;


}
