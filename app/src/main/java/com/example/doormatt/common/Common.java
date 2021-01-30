package com.example.doormatt.common;

public class Common {

    // Firebase Realtime Database References
    public static final String RESIDENT_REF = "Resident";
    public static final String USER_REF = "User";
    public static final String GUARD_REF = "Guard";
    public static final String ROLE_REF = "Role";
    public static final String NAME_PATTERN = "^[A-Za-z]+$";

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
