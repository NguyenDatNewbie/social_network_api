package fit.api.social_network.constant;


public class SocialConstant {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";


    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_USER = 2;

    public static final Integer STATUS_UPGRADING = 2;
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;
    public static final Integer STATUS_REJECT = -3;

    public static final Integer LIKE_KIND_POST = 1;
    public static final Integer LIKE_KIND_COMMENT = 2;

    public static final Integer COMMENT_KIND_POST = 1;
    public static final Integer COMMENT_KIND_COMMENT = 2;

    public static final Integer ROOM_TYPE_GROUP = 1;
    public static final Integer ROOM_TYPE_PERSONAL = 2;

    private SocialConstant(){
        throw new IllegalStateException("Utility class");
    }
}
